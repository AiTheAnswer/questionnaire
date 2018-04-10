package com.allen.questionnaire.resp;

import com.allen.questionnaire.entity.Option;
import com.allen.questionnaire.entity.Question;

import java.util.List;

public class QuestionAndOptionsResp {
    private Question question;
    private List<Option> optionList;

    public QuestionAndOptionsResp(Question question, List<Option> optionList) {
        this.question = question;
        this.optionList = optionList;
    }

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
