package com.allen.questionnaire.req;

import java.util.List;

/**
 * 添加问卷集合的请求对象
 */
public class QuestionnaireListReq {
    private List<QuestionnaireAddReq> mQueList;

    public List<QuestionnaireAddReq> getmQueList() {
        return mQueList;
    }

    public void setmQueList(List<QuestionnaireAddReq> mQueList) {
        this.mQueList = mQueList;
    }
}
