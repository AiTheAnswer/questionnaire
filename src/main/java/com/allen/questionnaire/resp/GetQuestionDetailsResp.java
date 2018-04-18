package com.allen.questionnaire.resp;

import java.util.List;

/**
 * 获取问卷详细的响应实体类
 *
 * @author Renjy
 */
public class GetQuestionDetailsResp {
    private List<QuestionAndOptionsResp> quesDetail;

    public List<QuestionAndOptionsResp> getQuesDetail() {
        return quesDetail;
    }

    public void setQuesDetail(List<QuestionAndOptionsResp> quesDetail) {
        this.quesDetail = quesDetail;
    }
}
