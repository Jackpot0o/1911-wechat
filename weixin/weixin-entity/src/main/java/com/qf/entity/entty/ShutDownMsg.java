package com.qf.entity.entty;

import lombok.Data;

/**
 * @auther JC
 * @date 2020/4/22
 */
@Data
public class ShutDownMsg extends NettyMsg {
    {
        setType(6);
    }
}
