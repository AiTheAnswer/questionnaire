package com.allen.questionnaire.req;

import java.io.Serializable;

/**
 * 添加问卷的Request
 */
public class QuestionnaireAddReq implements Serializable{
    private String questionnaireName;
    private Integer categoryId;

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
