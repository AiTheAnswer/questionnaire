package com.allen.questionnaire.req;

import com.allen.questionnaire.entity.Questionnaire;

import java.util.List;

/**
 * 添加问卷集合的请求对象
 */
public class QuestionnaireListReq {
    private List<Questionnaire> questionnaires;

    public List<Questionnaire> getQuestionnaires() {
        return questionnaires;
    }

    public void setQuestionnaires(List<Questionnaire> questionnaires) {
        this.questionnaires = questionnaires;
    }
}
