package com.allen.questionnaire.req;

import java.util.List;

public class QueRecordingReq {
    private String userId;
    private String questionnaireId;
    private List<QueRecording> queRecordingList;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getQuestionnaireId() {
        return questionnaireId;
    }

    public void setQustionnaireId(String questionnaireId) {
        this.questionnaireId = questionnaireId;
    }

    public List<QueRecording> getQueRecordingList() {
        return queRecordingList;
    }

    public void setQueRecordingList(List<QueRecording> queRecordingList) {
        this.queRecordingList = queRecordingList;
    }
}
