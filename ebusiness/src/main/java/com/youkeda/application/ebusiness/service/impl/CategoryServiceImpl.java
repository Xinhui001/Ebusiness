package com.youkeda.application.ebusiness.service.impl;

import com.youkeda.application.ebusiness.common.Result;
import com.youkeda.application.ebusiness.dao.CategoryDAO;
import com.youkeda.application.ebusiness.dataobject.CategoryDO;
import com.youkeda.application.ebusiness.model.domain.Category;
import com.youkeda.application.ebusiness.service.CategoryService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 20891
 */
@Component
public class CategoryServiceImpl implements CategoryService {

    @Resource
    private CategoryDAO categoryDAO;

    @Override
    public Result<Category> add(Category category) {
        Result<Category> result = new Result<>();
        if (StringUtils.isEmpty(category.getName())){
            result.setCode("500");
            result.setMessage("类目名称不能为空");
            return result;
        }
        //防止HTML注入攻击
        String body = StringEscapeUtils.escapeHtml4(category.getDescription());

        CategoryDO categoryDO = new CategoryDO();
        categoryDO.setName(category.getName());
        categoryDO.setDescription(body);
        categoryDO.setParentCategoryId(category.getParentCategoryId());
        categoryDAO.insert(categoryDO);

        result.setData(categoryDO.toModel());

        return result;
    }

    @Override
    public Result<List<Category>> queryAll(String id) {
        Result<List<Category>> result = new Result<>();

        return result;
    }

    @Override
    public Result<List<Category>> get(String id, boolean deepFill) {
        Result<List<Category>> result = new Result<>();

        if (StringUtils.isAllEmpty(id)) {
            result.setCode("501");
            result.setMessage("类目id不能为空");
            return result;
        }
        CategoryDO categoryDO = categoryDAO.get(id);
        if (categoryDO == null) {
            result.setCode("502");
            result.setMessage("无此id对应的类目");
            result.setSuccess(false);
            return result;
        }

        //如果deepFill为false，则填充依次子类目
        if (!deepFill) {
            //只加载一次子类目
            List<Category> categories = categoryDAO.selectByParentId(id);
            //构建map结构
            Map<String,Category> categoryMap = new HashMap<>();
            //初始化一个虚拟根节点，0 ，可以对应的是所有的一级类目的父亲
            categoryMap.put("0",new Category());
            //把所有的评论转换为map数据
            categories.forEach(category1 -> categoryMap.put(category1.getId(),category1));
            //再次遍历类目数据
            categories.forEach(category1 -> {
                //得到父类目
                Category parent = categoryMap.get(category1.getParentCategoryId());
                if (parent != null) {
                    //初始化children变量
                    if (parent.getSubCategoryList() == null) {
                        parent.setSubCategoryList(new ArrayList<>());
                    }
                    //在父类目中添加子类目
                    parent.getSubCategoryList().add(category1);
                }
            });
            //得到所有一级类目
            List<Category> data = categoryMap.get("0").getSubCategoryList();

            result.setSuccess(true);
            result.setData(data);

            return result;
        }else {
            //深度加载所有子类目

        }

        return result;
    }

    private void loadAllChildren(String id) {
        //只加载一次子类目
        List<Category> categories = categoryDAO.selectByParentId(id);
        //构建map结构
        Map<String,Category> categoryMap = new HashMap<>();
        //初始化一个虚拟根节点，0 ，可以对应的是所有的一级类目的父亲
        categoryMap.put("0",new Category());
        //把所有的评论转换为map数据
        categories.forEach(category1 -> categoryMap.put(category1.getId(),category1));
        //再次遍历类目数据
        categories.forEach(category1 -> {
            //得到父类目
            Category parent = categoryMap.get(category1.getParentCategoryId());
            if (parent != null) {
                //初始化children变量
                if (parent.getSubCategoryList() == null) {
                    parent.setSubCategoryList(new ArrayList<>());
                }
                //在父类目中添加子类目
                parent.getSubCategoryList().add(category1);
            }
        });
        //得到所有一级类目
        List<Category> data = categoryMap.get("0").getSubCategoryList();
    }

    private void loadDirectChildren(Category category){
        //通过DAO查询所有子类目
        List<Category> children = categoryDAO.selectByParentId(category.getId());
        //将数据对象转换为模型对象并加入到当前类目的子类目列表中
        for (Category child : children) {
            category.getSubCategoryList().add(child);
        }

    }

}
