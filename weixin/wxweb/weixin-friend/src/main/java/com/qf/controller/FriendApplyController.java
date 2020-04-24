package com.qf.controller;

import com.qf.entity.FriendApply;
import com.qf.entity.User;
import com.qf.entity.base.ResultEntity;
import com.qf.service.IFriendApplyService;
import com.qf.service.IFriendService;
import com.qf.service.api.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * @auther JC
 * @date 2020/4/16
 */
@RestController
@RequestMapping(value = "/friendApply")
public class FriendApplyController {

    @Autowired
    private IFriendApplyService friendApplyService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IFriendService friendService;

    @RequestMapping(value = "/addFriendApply")
    public ResultEntity addFriendApply(FriendApply friendApply, String username) {


        // 根据用户名称查询对象
        User user = userService.findUserByUsername(username);

        if (user.getId() == friendApply.getFid()) {
            return ResultEntity.error("不能添加自己为好友");
        }

        if (user != null) {
            System.out.println(friendApply);
            friendApply.setTid(user.getId());
            friendApply.setCreateTime(new Date());
            friendApply.setStatus(1);
            friendApplyService.insert(friendApply);
            return ResultEntity.success();
        } else {
            return ResultEntity.error("没有找到:" + username);
        }
    }

    @RequestMapping(value = "/getMyFriendApplyList")
    public ResultEntity getMyFriendApplyList(Integer uid) {
        List<FriendApply> friendApplyList = friendApplyService.getMyFriendApplyList(uid);
        for (FriendApply friendApply : friendApplyList) {
            Integer fid = friendApply.getFid();
            User user = userService.getUserById(fid);
            friendApply.setFriend(user);
        }
        return ResultEntity.success(friendApplyList);
    }

    // 如果是拒绝只修改状态就可以
    // 如果是同意，修改状态并且还要添加好友
    @RequestMapping(value = "/updateFriendApplyStatus")
    public ResultEntity updateFriendApplyStatus(Integer status, Integer id) {

        // 1.根据id查询申请记录对象
        FriendApply friendApply = friendApplyService.selectById(id);
        friendApply.setStatus(status);
        friendApplyService.update(friendApply);

        if (status == 2) { // 说明同意
            // 1.添加好友
            friendService.addFriend(friendApply.getTid(), friendApply.getFid(), 1);
        }
        return ResultEntity.success();
    }
}
