package com.allen.questionnaire.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 问卷表的实体类
 */
@Entity(name = "questionnaire")
public class Questionnaire {
    @Id
    @GeneratedValue
    private int id;
    @NotEmpty
    @Column(name = "questionnaire_name")
    private String questionnaireName;
    @NotNull
    @Column(name = "category_id")
    private int categoryId;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQuestionnaireName() {
        return questionnaireName;
    }

    public void setQuestionnaireName(String questionnaireName) {
        this.questionnaireName = questionnaireName;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
}

