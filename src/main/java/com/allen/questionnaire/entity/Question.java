package com.allen.questionnaire.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 问题 实体类
 */
@Entity(name = "question")
public class Question implements Serializable {
    @Id
    @GeneratedValue
    private String id;
    @NotEmpty
    @Column(name = "question_des")
    private String questionDes;//问题描述
    @NotNull
    @Column(name="type")
    private Integer type;//问题类型（是否可以多选 0：不可以  1：可以）
    @NotEmpty
    @Column(name = "option_ids")
    private String optionIds;//问题选项id  多个id之间用逗号隔开
    @NotEmpty
    @Column(name = "questionnaire_id")
    private String questionnaireId;//问卷id


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

    public String getOptionIds() {
        return optionIds;
    }

    public void setOptionIds(String optionIds) {
        this.optionIds = optionIds;
    }

    public String getQuestionnaireId() {
        return questionnaireId;
    }

    public void setQuestionnaireId(String questionnaireId) {
        this.questionnaireId = questionnaireId;
    }
}
