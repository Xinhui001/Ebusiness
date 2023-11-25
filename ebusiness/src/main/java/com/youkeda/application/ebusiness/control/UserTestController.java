package com.youkeda.application.ebusiness.control;

import com.youkeda.application.ebusiness.model.enums.Gender;
import com.youkeda.application.ebusiness.common.Result;
import com.youkeda.application.ebusiness.model.domain.User;
import com.youkeda.application.ebusiness.service.UserService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Super
 */
@Controller
public class UserTestController {

    @Resource
    private UserService userService;

    @Resource
    private RedisTemplate redisTemplate;

    @GetMapping(path = "/test/user")
    @ResponseBody
    public Map<Object, Result<User>> testUser(){
        Map<Object, Result<User>> map = new HashMap<>();
        User user = new User();
        String gender = Gender.MALE.toString();
        //gmt_created, gmt_modified, user_name, password, mobile, email, name, avatar_url, gender
        System.out.println(gender);
        user.setUserName("测试用户名123");
        user.setPwd("123456789");
        user.setEmail("test@qq.com");
        user.setGender(gender);
        user.setMobile("19838960502");
        user.setAvatarUrl("www.4399.com");
        user.setName("测试名字123");
        Result<User> result = userService.register(user);
        if(result != null){
            System.out.println("注册成功");
        }
        map.put("test",result);
        return map;
    }

}