package com.youkeda.application.ebusiness.service.impl;

import com.youkeda.application.ebusiness.dao.UserDAO;
import com.youkeda.application.ebusiness.dataobject.UserDO;
import com.youkeda.application.ebusiness.common.Result;
import com.youkeda.application.ebusiness.model.domain.User;
import com.youkeda.application.ebusiness.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author 20891
 */
@Component
public class UserServiceImpl implements UserService {

    @Resource
    private UserDAO userDAO;

    @Resource
    private RedisTemplate redisTemplate;

    @Override
    public Result<User> register(User user) {
        Result<User> result = new Result<>();

        if (StringUtils.isEmpty(user.getUserName())) {
            result.setCode("600");
            result.setMessage("用户名不能为空");
            return result;
        }
        if (StringUtils.isEmpty(user.getPwd())) {
            result.setCode("601");
            result.setMessage("密码不能为空");
            return result;
        }
        if (StringUtils.isEmpty(user.getMobile())) {
            result.setCode("602");
            result.setMessage("手机号不能为空");
            return result;
        }
        if (StringUtils.isEmpty(user.getName())) {
            result.setCode("603");
            result.setMessage("名字不能为空");
            return result;
        }
        if (StringUtils.isEmpty(user.getAvatarUrl())) {
            result.setCode("604");
            result.setMessage("头像不能为空");
            return result;
        }
        if (StringUtils.isEmpty(user.getGender())) {
            result.setCode("605");
            result.setMessage("性别不能为空");
            return result;
        }

        UserDO userDO = (UserDO) redisTemplate.opsForValue().get(user);
        //读取Redis缓存中的用户信息，如果为空再从数据库中查询
        if (userDO == null) {
            userDO = userDAO.findByUserName(user.getName());
        }
        //若数据库中信息为空，则用户不存在
        if (userDO != null) {
            result.setCode("602");
            result.setMessage("用户名已经存在");
            return result;
        }

        // 密码加自定义盐值，确保密码安全
        String saltPwd = user.getPwd() + "_business2050";
        // 生成md5值，并转为大写字母
        String md5Pwd = DigestUtils.md5Hex(saltPwd).toUpperCase();

        UserDO userDO1 = new UserDO();
        userDO1.setUserName(user.getUserName());
        userDO1.setPassword(md5Pwd);
        userDO1.setEmail(user.getEmail());
        userDO1.setGender(user.getGender());
        userDO1.setMobile(user.getMobile());
        userDO1.setAvatarUrl(user.getAvatarUrl());
        userDO1.setName(user.getName());
        try {
            int add = userDAO.add(userDO1);
        } catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException("userDAO错误");
        }

        result.setSuccess(true);

        result.setData(userDO1.toModel());
        //注入Redis缓存中，存储用户信息
        redisTemplate.opsForValue().set(user,userDO1);

        return result;
    }

    @Override
    public Result<User> login(String userName, String pwd) {
        Result<User> result = new Result<>();

        if (StringUtils.isEmpty(userName)) {
            result.setCode("600");
            result.setMessage("用户名不能为空");
            return result;
        }
        if (StringUtils.isEmpty(pwd)) {
            result.setCode("601");
            result.setMessage("密码不能为空");
            return result;
        }

        UserDO userDO = (UserDO) redisTemplate.opsForValue().get(userName);
        //读取Redis缓存中的用户信息，如果为空再从数据库中查询
        if (userDO==null) {
            userDO = userDAO.findByUserName(userName);
        }
        //若数据库中信息为空，则用户不存在
        if (userDO == null) {
            result.setCode("602");
            result.setMessage("用户名不存在");
            //只new但不设置任何属性，相当于传入一个空对象
            userDO = new UserDO();
            redisTemplate.opsForValue().set(userName,userDO,5, TimeUnit.MINUTES);
            return result;
        }

        // 密码加自定义盐值，确保密码安全
        String saltPwd = pwd + "_business2050";
        // 生成md5值，并转为大写字母
        String md5Pwd = DigestUtils.md5Hex(saltPwd).toUpperCase();

        if (!StringUtils.equals(md5Pwd, userDO.getPassword())) {
            result.setCode("603");
            result.setMessage("密码错误");
            return result;
        }


        result.setSuccess(true);

        result.setData(userDO.toModel());

        // 登录成功后重新设置一次
        redisTemplate.opsForValue().set(userName, userDO);

        return result;
    }
}
