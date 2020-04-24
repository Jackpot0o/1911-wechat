package com.qf.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @auther JC
 * @date 2020/4/16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_friend")
public class Friend {

    @TableId(type= IdType.AUTO)
    private Integer id;

    private Integer uid;

    private Integer fid;

    private Integer status;

    private String remark;

    @TableField(exist = false)
    private User friendObj; // 好友对象
}
