package com.qf.service;

import com.qf.base.service.BaseService;
import com.qf.entity.User;

/**
 * @auther JC
 * @date 2020/4/14
 */
public interface IUserService extends BaseService<User> {
    Integer register(User user);

    User getUserByUsername(String username);
}
