package com.youkeda.application.ebusiness.control;

import com.youkeda.application.ebusiness.common.Result;
import com.youkeda.application.ebusiness.model.domain.Category;
import com.youkeda.application.ebusiness.service.CategoryService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @author Super
 */
@Controller
public class CategoryTestController {

    @Resource
    private CategoryService categoryService;

    @Resource
    private RedisTemplate redisTemplate;

    @PostMapping(path = "/create")
    @ResponseBody
    public Result<Category> add(@RequestBody Category category) {

        Result<Category> result = categoryService.add(category);
        //不为空，写入Redis
        if (result != null) {
            System.out.println("注册成功！");
            redisTemplate.opsForValue().set(category.getName(),category);
            redisTemplate.opsForList().leftPush("categoryList",category);
        }

        return result;
    }

}
