package com.qf.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @auther JC
 * @date 2020/4/16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_friend_apply")
public class FriendApply implements Serializable{

    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer fid;

    private Integer tid;

    private String msg;

    @TableField(value = "create_time")
    private Date createTime;

    private Integer status;

    @TableField(exist = false)
    private User friend;
}
