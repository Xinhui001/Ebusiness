package com.youkeda.application.ebusiness.service;

import com.youkeda.application.ebusiness.common.Result;
import com.youkeda.application.ebusiness.model.domain.User;

public interface UserService {

    /**
     * 注册
     * @param user
     * @return
     */
    public Result<User> register(User user);

    /**
     * 登录
     * @param userName
     * @param pwd
     * @return
     */
    public Result<User> login(String userName,String pwd);

}
