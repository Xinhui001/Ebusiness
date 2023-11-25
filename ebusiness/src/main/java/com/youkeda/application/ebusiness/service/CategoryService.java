package com.youkeda.application.ebusiness.service;

import com.youkeda.application.ebusiness.model.domain.Category;
import com.youkeda.application.ebusiness.common.Result;

import java.util.List;

/**
 * @author 20891
 */
public interface CategoryService {
    /**
     * 新增
     * @param category
     * @return
     */
    public Result<Category> add(Category category);

    /**
     * 取得所有类目
     * @param id
     * @return
     */
    public Result<List<Category>> queryAll(String id);

    /**
     * 根据主键取得类目
     *     deepFill:是否需要深度填充子类目
     *     false:值填充依次子类目
     *     true:深度填充所有子类目的子类目
     * @param id
     * @param deepFill
     * @return
     */
    public Result<List<Category>> get(String id,boolean deepFill);

}
