package com.qf.controller;

import com.qf.entity.User;
import com.qf.entity.base.BusinessException;
import com.qf.entity.base.ResultEntity;
import com.qf.entity.entty.ShutDownMsg;
import com.qf.service.IUserService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * @auther JC
 * @date 2020/4/14
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RequestMapping(value = "/register")
    public ResultEntity register(User user){

        System.out.println("UserController.register " + user);
        Integer num = userService.register(user);
        if (num == 1) {
            return ResultEntity.error("用户名已存在");
        } else if (num == 2) {
            return ResultEntity.error("手机号已被注册");
        }
        return ResultEntity.success();
    }

    /**
     *
     * @param username
     * @param password
     * @param did 设备id
     * @return
     */
    @RequestMapping(value = "/login")
    public ResultEntity login(String username, String password,String did) {
        User user = userService.getUserByUsername(username);
        if(user != null && user.getPassword().equals(password)){
            user.setPassword(null); // 密码不能写到手机里面

            // 1.先根据用户id查询设备id
            String redisDid = redisTemplate.opsForValue().get(user.getId().toString());
            if(redisDid != null && !did.equals(redisDid)){
                System.out.println("发送给交换机");
                // 挤下其他的设备
                ShutDownMsg shutDownMsg = new ShutDownMsg();
                shutDownMsg.setDid(redisDid); // redis中的设备id
                rabbitTemplate.convertAndSend("ws_exchange","",shutDownMsg);
            }
            // 登录成功后要保护用户id和设备id
            redisTemplate.opsForValue().set(user.getId().toString(),did);


            return ResultEntity.success(user);
        }else{
            return ResultEntity.error("用户名或密码错误");
        }
    }

    @RequestMapping(value = "/test")
    public ResultEntity test(@CookieValue(name = "name",required = false) String name, HttpServletResponse resp){
        //   int i = 10/0; 模拟系统异常

        if(name.equals("admin")){
            throw  new BusinessException("0001","xxxx错误");
        }

        System.out.println("name = [" + name + "]");
        Cookie cookie = new Cookie("name","admin");
        cookie.setMaxAge(10000);
        cookie.setPath("/");
        resp.addCookie(cookie);
        return ResultEntity.success();
    }

    /**
     * 主要页面来调用
     * @param username
     * @return
     */
    @RequestMapping(value = "/getUserByUsername")
    public ResultEntity getUserByUsername(String username){
        User user = userService.getUserByUsername(username);
        return ResultEntity.success(user);
    }

    /**
     * 让 其他微服务调用
     * @param username
     * @return 用户对象
     */
    @RequestMapping(value = "/findUserByUsername")
    public User findUserByUsername(String username){
        System.out.println("username = [" + username + "]");
        if(!StringUtils.isEmpty(username)){
            return userService.getUserByUsername(username);
        }
        return null;
    }

    @RequestMapping(value = "/getUserById")
    public User getUserById(Integer id){
        System.out.println("id = [" + id + "]");
        if(id != null){
            return userService.selectById(id);
        }
        return null;
    }

}
