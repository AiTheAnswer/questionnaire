package com.allen.questionnaire.req;

/**
 * 获取问题详情的请求体
 *
 * @author Renjy
 */
public class GetQuestionDetailsReq {
    private String token;//用户Token
    private String questionnaireId; //问卷的Id

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getQuestionnaireId() {
        return questionnaireId;
    }

    public void setQuestionnaireId(String questionnaireId) {
        this.questionnaireId = questionnaireId;
    }
}
