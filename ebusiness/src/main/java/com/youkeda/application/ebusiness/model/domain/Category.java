package com.youkeda.application.ebusiness.model.domain;

import com.youkeda.application.ebusiness.common.BaseID;

import java.io.Serializable;
import java.util.List;

public class Category extends BaseID implements Serializable {

    /**
     * 主键
     */
    private String id;

    /**
     * 类目名称
     */
    private String name;

    /**
     * 类目描述
     */
    private String description;

    /**
     * 父类目id
     */
    private String parentCategoryId;

    /**
     * 子类目列表
     */
    private List<Category> subCategoryList;


    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getParentCategoryId() {
        return parentCategoryId;
    }

    public void setParentCategoryId(String parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }

    public List<Category> getSubCategoryList() {
        return subCategoryList;
    }

    public void setSubCategoryList(List<Category> subCategoryList) {
        this.subCategoryList = subCategoryList;
    }
}
