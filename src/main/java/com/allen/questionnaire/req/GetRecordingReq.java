package com.allen.questionnaire.req;

/**
 * 获取问卷的作答记录通过问卷的Id
 *
 * @author Renjy
 */
public class GetRecordingReq {
    private String userId;
    private String questionnaireId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getQuestionnaireId() {
        return questionnaireId;
    }

    public void setQuestionnaireId(String questionnaireId) {
        this.questionnaireId = questionnaireId;
    }
}
