package com.qf.netty.handler;


import com.google.gson.Gson;
import com.qf.entity.entty.ChatMsg;
import com.qf.entity.entty.ConnMsg;
import com.qf.entity.entty.HeardMsg;
import com.qf.entity.entty.NettyMsg;
import com.qf.netty.channel.ChannelGroup;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

/**
 * @auther JC
 * @date 2020/4/22
 */
public class WebSocketInChannleHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) throws Exception {
        String text = textWebSocketFrame.text();
        System.out.println("客户端发送的数据:"+text);
        Gson gson = new Gson();

        // 根据不同的类型转成具体的实体类
        // 在交给具体的handler处理
        // 把JSON字符串转成对象
        NettyMsg nettyMsg = gson.fromJson(text, NettyMsg.class);
        if(nettyMsg.getType() == 1){ // 新的连接
            nettyMsg = gson.fromJson(text, ConnMsg.class);
        }else if(nettyMsg.getType() == 2){
            nettyMsg = gson.fromJson(text, HeardMsg.class);
        }else if(nettyMsg.getType() == 3){ // 单聊
            nettyMsg = gson.fromJson(text, ChatMsg.class);
        }

        // 往下传递
        channelHandlerContext.fireChannelRead(nettyMsg);

    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        //System.out.println("新客户端连接。。");
    }

    // 客户端断开后重Map中删除这个连接
    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        //System.out.println("客户端断开。。");
        ChannelGroup.removeChannel(ctx.channel());
    }
}
