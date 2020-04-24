package com.qf.netty.handler;

import com.qf.entity.entty.ChatMsg;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * @auther JC
 * @date 2020/4/22
 */
public class ChatHandler extends SimpleChannelInboundHandler<ChatMsg> {

    private RabbitTemplate rabbitTemplate;

    public ChatHandler(RabbitTemplate rabbitTemplate){
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ChatMsg chatMsg) throws Exception {

        System.out.println("用户聊天的信息"+chatMsg);

        rabbitTemplate.convertAndSend("ws_exchange","",chatMsg);
    }
}
