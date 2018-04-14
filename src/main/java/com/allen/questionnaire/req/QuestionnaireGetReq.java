package com.allen.questionnaire.req;

/**
 *  获取问卷的Request
 */
public class QuestionnaireGetReq {
    private String token;  //token
    private int categoryId; //类别id

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
}
