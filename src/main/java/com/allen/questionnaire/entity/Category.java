package com.allen.questionnaire.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

/**
 * 调查问卷类别实体类
 */
@Entity(name = "category")
public class Category {
    @Id
    @GeneratedValue
    private int id;//id
    @NotEmpty
    @Column(name = "category_name")
    private String categoryName; //类别名称
    @NotEmpty
    @Column(name = "parent_id")
    private int parentId;//父级类别id

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }
}
