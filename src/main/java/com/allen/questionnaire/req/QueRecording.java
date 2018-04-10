package com.allen.questionnaire.req;

import java.util.List;

public class QueRecording {
    private String questionId;
    private List<String> optionIds;

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public List<String> getOptionIds() {
        return optionIds;
    }

    public void setOptionIds(List<String> optionIds) {
        this.optionIds = optionIds;
    }
}
