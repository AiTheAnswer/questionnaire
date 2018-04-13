package com.allen.questionnaire.req;

/**
 * 问卷的Request
 */
public class QuestionnaireReq {
    private String questionnaireName;
    private int categoryId;

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
