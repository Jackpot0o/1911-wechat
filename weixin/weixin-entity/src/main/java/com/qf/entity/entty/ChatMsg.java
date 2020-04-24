package com.qf.entity.entty;

import lombok.Data;

/**
 * @auther JC
 * @date 2020/4/22
 */
@Data
public class ChatMsg extends NettyMsg {

    private Integer fid;
    private Integer tid;
    private String content;
}
