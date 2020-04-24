package com.qf.controller;

import com.qf.entity.Friend;
import com.qf.entity.base.ResultEntity;
import com.qf.service.IFriendService;
import com.qf.service.api.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @auther JC
 * @date 2020/4/16
 */
@RestController
@RequestMapping(value = "/friend")
public class FriendController {

    @Autowired
    private IFriendService friendService;

    @Autowired
    private IUserService userService;

    @RequestMapping(value = "/getFriendListByUid")
    public ResultEntity getFriendListByUid(Integer uid ) {
        List<Friend> friendList = friendService.getFriendListByUid(uid);
        for(Friend f:friendList){
            f.setFriendObj(userService.getUserById(f.getFid()));
        }
        return ResultEntity.success(friendList);
    }
}
