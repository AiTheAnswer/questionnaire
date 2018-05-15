package com.allen.questionnaire.controller;

import com.allen.questionnaire.entity.*;
import com.allen.questionnaire.repository.*;
import com.allen.questionnaire.req.GetQuestionDetailsReq;
import com.allen.questionnaire.req.QuestionnaireListReq;
import com.allen.questionnaire.req.QuestionnaireGetReq;
import com.allen.questionnaire.resp.GetQuestionDetailsResp;
import com.allen.questionnaire.resp.QuestionAndOptionsResp;
import com.allen.questionnaire.resp.QuestionnaireResp;
import com.allen.questionnaire.resp.Resp;
import com.allen.questionnaire.util.TextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Array;
import java.util.*;

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
    @Autowired
    OptionRepository optionRepository;
    @Autowired
    RecordNumberRepository recordNumberRepository;

    /**
     * 批量添加问卷
     *
     * @param questionnaireListReq
     * @return
     */
    @PostMapping(value = "/addQuestionnaires")
    public Resp addQuestionnaires(@RequestBody QuestionnaireListReq questionnaireListReq) {
        Resp resp = new Resp();
        if (null == questionnaireListReq) {
            resp.setStatusCode(400);
            resp.setReason("参数为空");
            return resp;
        }
        for (Questionnaire questionnaireReq : questionnaireListReq.getQuestionnaires()) {
            if (TextUtil.isEmpty(questionnaireReq.getQuestionnaireName())) {
                resp.setStatusCode(400);
                resp.setReason("问卷名称为空");
                return resp;
            }
            if (0 == questionnaireReq.getCategoryId()) {
                resp.setStatusCode(400);
                resp.setReason("类别id不正确");
                return resp;
            }
            Optional<Category> categoryOptional = categoryRepository.findById(questionnaireReq.getCategoryId());
            if (!categoryOptional.isPresent()) {
                resp.setStatusCode(400);
                resp.setReason("类别id不正确");
                return resp;
            }
        }
        List<Questionnaire> questionnaires = new ArrayList<>();
        for (Questionnaire questionnaireReq : questionnaireListReq.getQuestionnaires()) {
            Questionnaire questionnaire = new Questionnaire();
            questionnaire.setCategoryId(questionnaireReq.getCategoryId());
            questionnaire.setQuestionnaireName(questionnaireReq.getQuestionnaireName());
            questionnaires.add(questionnaire);
        }

        questionnaires = questionnaireRepository.saveAll(questionnaires);
        for (Questionnaire questionnaire : questionnaires) {
            if (TextUtil.isEmpty(questionnaire.getId())) {
                resp.setStatusCode(400);
                resp.setReason("保存问卷失败，请检查参数");
                return resp;
            }
        }
        resp.setStatusCode(200);
        resp.setObject(questionnaires);
        return resp;

    }

    /**
     * 添加问卷
     *
     * @param questionnaireReq
     * @return
     */
    @PostMapping(value = "/addQuestionnaire")
    public Resp addQuestionnaire(@RequestBody Questionnaire questionnaireReq) {
        Resp resp = new Resp();
        if (null == questionnaireReq) {
            resp.setStatusCode(400);
            resp.setReason("参数为空");
            return resp;
        }
        if (TextUtil.isEmpty(questionnaireReq.getQuestionnaireName())) {
            resp.setStatusCode(400);
            resp.setReason("问卷名称为空");
            return resp;
        }
        if (0 == questionnaireReq.getCategoryId()) {
            resp.setStatusCode(400);
            resp.setReason("类别id不正确");
            return resp;
        }
        Optional<Category> categoryOptional = categoryRepository.findById(questionnaireReq.getCategoryId());
        if (!categoryOptional.isPresent()) {
            resp.setStatusCode(400);
            resp.setReason("类别id不正确");
            return resp;
        }
        Questionnaire questionnaire = new Questionnaire();
        questionnaire.setCategoryId(questionnaireReq.getCategoryId());
        questionnaire.setQuestionnaireName(questionnaireReq.getQuestionnaireName());
        questionnaire = questionnaireRepository.save(questionnaire);
        if (!TextUtil.isEmpty(questionnaire.getId())) {
            resp.setStatusCode(200);
            resp.setObject(questionnaire);
            return resp;
        } else {
            resp.setStatusCode(400);
            resp.setReason("保存问卷失败，请检查参数");
            return resp;
        }
    }

    /**
     * 获取问卷列表 通过类别id
     *
     * @param queGetReq
     * @return
     */
    @PostMapping(value = "getQuestionnaire")
    public Resp getQuestionnaire(@RequestBody QuestionnaireGetReq queGetReq) {
        Resp resp = new Resp();
        if (null == queGetReq) {
            resp.setStatusCode(400);
            resp.setReason("参数为空");
            return resp;
        }
        String token = queGetReq.getToken();
        if (TextUtil.isEmpty(token)) {
            resp.setStatusCode(400);
            resp.setReason("token为空");
            return resp;
        }
        Optional<Student> student = studentRepository.findById(token);
        if (!student.isPresent()) {
            resp.setStatusCode(400);
            resp.setReason("token错误");
            return resp;
        }
        if (null == queGetReq.getCategoryId()) {
            resp.setStatusCode(400);
            resp.setReason("类别id为空");
            return resp;
        }
        List<Questionnaire> questionnaireList = questionnaireRepository.findByCategoryId(queGetReq.getCategoryId());
        if (null == questionnaireList) {
            resp.setStatusCode(400);
            resp.setReason("请检查类别id是否正确");
            return resp;
        }
        List<QuestionnaireResp> questionnaireResps = new ArrayList<>();
        for (Questionnaire questionnaire : questionnaireList) {
            QuestionnaireResp questionnaireResp = new QuestionnaireResp();
            questionnaireResp.setId(questionnaire.getId());
            questionnaireResp.setQuestionnaireName(questionnaire.getQuestionnaireName());
            questionnaireResp.setCategoryId(questionnaire.getCategoryId());
            String queId = questionnaire.getId();
            List<Question> questions = questionRepository.findByQuestionnaireId(queId);
            questionnaireResp.setQuestionNumber(questions.size());
            RecordNumber recordNumber = recordNumberRepository.findByQuestionnaireIdAndUserId(token, queId);
            questionnaireResp.setUse(null != recordNumber);
            List<RecordNumber> records = recordNumberRepository.findAllByQuestionnaireId(queId);
            if (null == records) {
                questionnaireResp.setUseNumber(0);
            } else {
                questionnaireResp.setUseNumber(records.size());
            }
            questionnaireResps.add(questionnaireResp);
        }
        resp.setStatusCode(200);
        resp.setObject(questionnaireResps);
        return resp;
    }

    /**
     * 获取某个问卷的详情（问题和选项）
     *
     * @param questionDetailsReq 请求参数实体
     * @return
     */
    @PostMapping(value = "getQuestionDetails")
    public Resp getQuestionDetails(@RequestBody GetQuestionDetailsReq questionDetailsReq) {
        Resp resp = new Resp();
        if (null == questionDetailsReq) {
            resp.setStatusCode(400);
            resp.setReason("参数不能为空");
            return resp;
        }
        String token = questionDetailsReq.getToken();
        String questionnaireId = questionDetailsReq.getQuestionnaireId();
        if (TextUtil.isEmpty(token) || TextUtil.isEmpty(questionnaireId)) {
            resp.setStatusCode(400);
            resp.setReason("参数不能为空");
            return resp;
        }
        //验证用户Token
        Optional<Student> student = studentRepository.findById(token);
        if (!student.isPresent()) {
            resp.setStatusCode(400);
            resp.setReason("token错误");
            return resp;
        }
        //验证问卷id
        Optional<Questionnaire> questionnaire = questionnaireRepository.findById(questionnaireId);
        if (!questionnaire.isPresent()) {
            resp.setStatusCode(400);
            resp.setReason("问卷不存在，请检查问卷id");
            return resp;
        }
        List<Question> questionList = questionRepository.findByQuestionnaireId(questionnaireId);
        if (null == questionList) {
            resp.setStatusCode(500);
            resp.setReason("获取问题失败");
            return resp;
        }
        if (questionList.size() < 1) {
            resp.setStatusCode(200);
            resp.setObject(questionList);
            return resp;
        }
        GetQuestionDetailsResp detailsResp = new GetQuestionDetailsResp();
        List<QuestionAndOptionsResp> quesDetail = new ArrayList<>();

        for (Question question : questionList) {
            String optionIds = question.getOptionIds();
            if (TextUtil.isEmpty(optionIds)) {
                break;
            }
            String[] optionIdArray = optionIds.split(",");
            List<Long> optionIdList = new ArrayList<>();
            for (String optionId : optionIdArray) {
                if (!TextUtil.isEmpty(optionId)) {
                    optionIdList.add(Long.parseLong(optionId));
                }
            }
            List<Option> optionList = optionRepository.findAllById(optionIdList);
            QuestionAndOptionsResp questionAddOption = new QuestionAndOptionsResp(question, optionList);
            quesDetail.add(questionAddOption);
        }
        detailsResp.setQuesDetail(quesDetail);
        resp.setStatusCode(200);
        resp.setObject(detailsResp);
        return resp;
    }

}
