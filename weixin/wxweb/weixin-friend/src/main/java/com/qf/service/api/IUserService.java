package com.qf.service.api;

import com.qf.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @auther JC
 * @date 2020/4/16
 */
@FeignClient("USER_WEB")
public interface IUserService  {

    @RequestMapping(value = "/user/findUserByUsername")
    public User findUserByUsername(@RequestParam("username") String username);

    @RequestMapping(value = "/user/getUserById")
    public User getUserById(@RequestParam("id") Integer id);
}
