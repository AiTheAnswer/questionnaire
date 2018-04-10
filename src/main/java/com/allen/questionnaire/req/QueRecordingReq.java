package com.allen.questionnaire.req;

import java.util.List;

public class QueRecordingReq {
    private String userId;
    private List<QueRecording> queRecordingList;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<QueRecording> getQueRecordingList() {
        return queRecordingList;
    }

    public void setQueRecordingList(List<QueRecording> queRecordingList) {
        this.queRecordingList = queRecordingList;
    }
}
