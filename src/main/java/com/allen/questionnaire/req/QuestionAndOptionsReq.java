package com.allen.questionnaire.req;

import com.allen.questionnaire.entity.Option;
import com.allen.questionnaire.entity.Question;

import java.util.List;

/**
 * 问题及选项的实体
 */
public class QuestionAndOptionsReq {
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
