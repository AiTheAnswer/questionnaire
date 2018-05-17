package com.allen.questionnaire.resp;

import com.allen.questionnaire.entity.Question;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

/**
 * 问卷中某个问题的统计
 *
 * @author Renjy
 */
public class QuestionnaireStatistics implements Serializable {
    private Question question;
    private List<OptionStatistics> optionStatistics;

    public QuestionnaireStatistics(Question question) {
        this.question = question;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public List<OptionStatistics> getOptionStatistics() {
        return optionStatistics;
    }

    public void setOptionStatistics(List<OptionStatistics> optionStatistics) {
        this.optionStatistics = optionStatistics;
    }
}
