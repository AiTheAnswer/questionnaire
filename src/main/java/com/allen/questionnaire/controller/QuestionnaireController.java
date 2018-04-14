package com.allen.questionnaire.controller;

import com.allen.questionnaire.entity.Category;
import com.allen.questionnaire.entity.Questionnaire;
import com.allen.questionnaire.repository.CategoryRepository;
import com.allen.questionnaire.repository.QuestionnaireRepository;
import com.allen.questionnaire.req.QuestionnaireReq;
import com.allen.questionnaire.resp.Resp;
import com.allen.questionnaire.util.TextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping
public class QuestionnaireController {
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    QuestionnaireRepository questionnaireRepository;

    @PostMapping(value = "addQuestionnaire")
    public Resp addQuestionnaire(@RequestBody QuestionnaireReq questionnaireReq){
        Resp resp = new Resp();
        if(null == questionnaireReq){
            resp.setStatusCode(400);
            resp.setReason("参数为空");
            return resp;
        }
        if(TextUtil.isEmpty(questionnaireReq.getQuestionnaireName())){
            resp.setStatusCode(400);
            resp.setReason("问卷名称为空");
            return resp;
        }
        if(0 == questionnaireReq.getCategoryId()){
            resp.setStatusCode(400);
            resp.setReason("类别id不正确");
            return resp;
        }
        Optional<Category> categoryOptional = categoryRepository.findById(questionnaireReq.getCategoryId());
        if(!categoryOptional.isPresent()){
            resp.setStatusCode(400);
            resp.setReason("类别id不正确");
            return resp;
        }
        Questionnaire questionnaire = new Questionnaire();
        questionnaire.setCategoryId(questionnaireReq.getCategoryId());
        questionnaire.setQuestionnaireName(questionnaireReq.getQuestionnaireName());
        questionnaire = questionnaireRepository.save(questionnaire);
        if(null != questionnaire && 0 != questionnaire.getId()){
            resp.setStatusCode(200);
            resp.setObject(questionnaire);
            return resp;
        }else{
            resp.setStatusCode(400);
            resp.setReason("保存问卷失败，请检查参数");
            return resp;
        }
    }
}
