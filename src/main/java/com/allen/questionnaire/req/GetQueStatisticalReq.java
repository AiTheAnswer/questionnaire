package com.allen.questionnaire.req;

/**
 * 获取某个问卷作答统计的请求体
 *
 * @author Renjy
 */
public class GetQueStatisticalReq {
    private String token;
    private String questionnaireId;

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
