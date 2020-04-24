package com.qf.netty.handler;

import com.qf.entity.entty.HeardMsg;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

/**
 * @auther JC
 * @date 2020/4/22
 */
public class HeardHandler extends SimpleChannelInboundHandler<HeardMsg> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, HeardMsg heardMsg) throws Exception {
        System.out.println("处理心跳:"+heardMsg);

        // 响应心跳
        TextWebSocketFrame resp = new TextWebSocketFrame("heard");
        channelHandlerContext.writeAndFlush(resp);
    }
}
