package com.qf.netty.server;

import com.qf.netty.handler.ChatHandler;
import com.qf.netty.handler.ConnHandler;
import com.qf.netty.handler.HeardHandler;
import com.qf.netty.handler.WebSocketInChannleHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @auther JC
 * @date 2020/4/22
 */
@Component
public class StartWebSocketServer implements CommandLineRunner {

    @Value("${netty.port}")
    private Integer port;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private RabbitTemplate rabbitTemplate;


    @Override
    public void run(String... args) throws Exception {

        EventLoopGroup master = new NioEventLoopGroup();
        EventLoopGroup salve = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(master,salve);
            bootstrap.channel(NioServerSocketChannel.class);
            bootstrap.childHandler(new ChannelInitializer() {
                @Override
                protected void initChannel(Channel channel) throws Exception {
                    ChannelPipeline pipeline = channel.pipeline();

                    pipeline.addLast(new HttpServerCodec()); // HttpRequset
                    pipeline.addLast(new HttpObjectAggregator(1024*10)); // FullHttpReqeust

                    // 添加WebSocket解编码
                    pipeline.addLast(new WebSocketServerProtocolHandler("/"));

                    // 添加处理客户端的请求的处理器
                    pipeline.addLast(new WebSocketInChannleHandler());

                    // 添加处理新连接的handler
                    pipeline.addLast(new ConnHandler(redisTemplate));

                    pipeline.addLast(new HeardHandler());

                    pipeline.addLast(new ChatHandler(rabbitTemplate));

                }
            });
            ChannelFuture channelFuture = bootstrap.bind(port);
            channelFuture.sync();
            System.out.println("服务端启动成功");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
//            master.shutdownGracefully();
//            salve.shutdownGracefully();
        }

    }
}
