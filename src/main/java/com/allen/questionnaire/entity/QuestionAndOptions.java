package com.allen.questionnaire.entity;

import java.util.List;

/**
 * 问题及选项的实体
 */
public class QuestionAndOptions {
    private Question question;
    private List<Option> optionList;

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public List<Option> getOptionList() {
        return optionList;
    }

    public void setOptionList(List<Option> optionList) {
        this.optionList = optionList;
    }
}
