package com.youkeda.application.ebusiness.dataobject;

import com.youkeda.application.ebusiness.model.domain.Category;

import java.time.LocalDateTime;

public class CategoryDO {

    private String id;

    private LocalDateTime gmtCreated;

    private LocalDateTime gmtModified;

    //类目名称
    private String name;

    //类目描述
    private String description;

    //父类目id
    private String parentCategoryId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getGmtCreated() {
        return gmtCreated;
    }

    public void setGmtCreated(LocalDateTime gmtCreated) {
        this.gmtCreated = gmtCreated;
    }

    public LocalDateTime getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(LocalDateTime gmtModified) {
        this.gmtModified = gmtModified;
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

    public Category toModel(){
        Category category = new Category();
        category.setId(getId());
        category.setName(getName());
        category.setParentCategoryId(getParentCategoryId());
        category.setDescription(getDescription());
        category.setGmtCreated(getGmtCreated());
        category.setGmtModified(getGmtModified());
        return category;
    }

}
