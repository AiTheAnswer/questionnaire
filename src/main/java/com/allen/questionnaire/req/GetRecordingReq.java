package com.allen.questionnaire.req;

/**
 * 获取问卷的作答记录通过问卷的Id
 *
 * @author Renjy
 */
public class GetRecordingReq {
    private String token;
    private int categoryId;
    private int type;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
