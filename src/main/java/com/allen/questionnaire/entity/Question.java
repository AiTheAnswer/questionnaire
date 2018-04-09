package com.allen.questionnaire.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 问题表的实体类
 */
@Entity(name = "question")
public class Question {
    @Id
    @GeneratedValue
    private String id;
    @NotEmpty
    @Column(name = "question_des")
    private String questionDes;//问题描述
    @NotNull
    @Column(name="type")
    private Integer type;//问题类型（是否可以多选 0：不可以  1：可以）

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name="system-uuid" ,strategy = "uuid")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestionDes() {
        return questionDes;
    }

    public void setQuestionDes(String questionDes) {
        this.questionDes = questionDes;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
