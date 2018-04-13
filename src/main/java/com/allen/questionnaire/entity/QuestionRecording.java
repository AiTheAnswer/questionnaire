package com.allen.questionnaire.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

/**
 * 问题调查记录表
 */
@Entity(name = "question_recording")
public class QuestionRecording {
    @Id
    @GeneratedValue
    private String id;
    @NotEmpty
    @Column(name = "user_id")
    private String userId;//用户id
    @NotEmpty
    @Column(name = "question_id")
    private String questionId;//问题Id
    @NotEmpty
    @Column(name = "option_ids")
    private String optionIds;//选项id,多个选项用逗号隔开
    @NotEmpty
    @Column(name = "questionnaire_id")
    private String questionnaireId;

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
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
