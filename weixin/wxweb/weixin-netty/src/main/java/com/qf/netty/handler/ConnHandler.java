package com.qf.netty.handler;

import com.google.gson.Gson;
import com.qf.entity.entty.ConnMsg;
import com.qf.entity.entty.ShutDownMsg;
import com.qf.netty.channel.ChannelGroup;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @auther JC
 * @date 2020/4/22
 */
public class ConnHandler extends SimpleChannelInboundHandler<ConnMsg> {

    private StringRedisTemplate redisTemplate;

    public ConnHandler(StringRedisTemplate redisTemplate){
        this.redisTemplate = redisTemplate;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ConnMsg connMsg) throws Exception {

        System.out.println("新的连接。。要保存到Map中"+connMsg);
        ChannelGroup.addChannel(connMsg.getDid(),channelHandlerContext.channel());

        Integer uid = connMsg.getUid();
        String did = connMsg.getDid();
        String redisDid = redisTemplate.opsForValue().get(uid.toString());
        if(redisDid != null && !redisDid.equals(did)){
            ShutDownMsg shutDownMsg = new ShutDownMsg();
            TextWebSocketFrame resp = new TextWebSocketFrame(new Gson().toJson(shutDownMsg));
            channelHandlerContext.writeAndFlush(resp);
        }
    }
}
