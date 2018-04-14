package com.allen.questionnaire.controller;

import com.allen.questionnaire.entity.*;
import com.allen.questionnaire.repository.*;
import com.allen.questionnaire.req.QuestionnaireAddReq;
import com.allen.questionnaire.req.QuestionnaireGetReq;
import com.allen.questionnaire.resp.QuestionnaireResp;
import com.allen.questionnaire.resp.Resp;
import com.allen.questionnaire.util.TextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping
public class QuestionnaireController {
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    QuestionRepository questionRepository;
    @Autowired
    QuestionnaireRepository questionnaireRepository;
    @Autowired
    QueRecordingRepository recordingRepository;

    /**
     * 添加问卷
     * @param questionnaireReq
     * @return
     */
    @PostMapping(value = "addQuestionnaire")
    public Resp addQuestionnaire(@RequestBody QuestionnaireAddReq questionnaireReq){
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
        if(!TextUtil.isEmpty(questionnaire.getId())){
            resp.setStatusCode(200);
            resp.setObject(questionnaire);
            return resp;
        }else{
            resp.setStatusCode(400);
            resp.setReason("保存问卷失败，请检查参数");
            return resp;
        }
    }
    @PostMapping(value = "getQuestionnaire")
    public Resp getQuestionnaire(@RequestBody QuestionnaireGetReq queGetReq){
        Resp resp = new Resp();
        if(null == queGetReq){
            resp.setStatusCode(400);
            resp.setReason("参数为空");
            return resp;
        }
        String token = queGetReq.getToken();
        if(TextUtil.isEmpty(token)){
            resp.setStatusCode(400);
            resp.setReason("token为空");
            return resp;
        }
        Optional<Student> student = studentRepository.findById(token);
        if(!student.isPresent()){
            resp.setStatusCode(400);
            resp.setReason("token错误");
            return resp;
        }
        if(null == queGetReq.getCategoryId()){
            resp.setStatusCode(400);
            resp.setReason("类别id为空");
            return resp;
        }
        List<Questionnaire> questionnaireList = questionnaireRepository.findByCategoryId(queGetReq.getCategoryId());
        if(null == questionnaireList){
            resp.setStatusCode(400);
            resp.setReason("请检查类别id是否正确");
            return resp;
        }
        List<QuestionnaireResp> questionnaireResps = new ArrayList<>();
        for (Questionnaire questionnaire:questionnaireList) {
            QuestionnaireResp questionnaireResp = new QuestionnaireResp();
            questionnaireResp.setId(questionnaire.getId());
            questionnaireResp.setQuestionnaireName(questionnaire.getQuestionnaireName());
            questionnaireResp.setCategoryId(questionnaire.getCategoryId());
            String queId = questionnaire.getId();
            List<Question> questions = questionRepository.findByQuestionnaireId(queId);
            questionnaireResp.setQuestionNumber(questions.size());
            QuestionRecording queRecording = recordingRepository.findByUserIdAndQuestionnaireId(token, queId);
            questionnaireResp.setUse(null != queRecording);
            List<QuestionRecording> questionRecordings = recordingRepository.findByQuestionnaireId(queId);
            questionnaireResp.setUseNumber(questionRecordings.size());
            questionnaireResps.add(questionnaireResp);
        }
        resp.setStatusCode(200);
        resp.setObject(questionnaireResps);
        return resp;
    }
}
