package com.allen.questionnaire.controller;

import com.allen.questionnaire.entity.*;
import com.allen.questionnaire.req.QuestionAndOptionsReq;
import com.allen.questionnaire.req.TokenReq;
import com.allen.questionnaire.resp.QuestionAndOptionsResp;
import com.allen.questionnaire.resp.Resp;
import com.allen.questionnaire.repository.OptionRepository;
import com.allen.questionnaire.repository.QuestionRepository;
import com.allen.questionnaire.repository.StudentRepository;
import com.allen.questionnaire.util.TextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/question")
public class QuestionController {
    @Autowired
    StudentRepository studentRepository;

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    OptionRepository optionRepository;

    /**
     * 添加问题和选项
     *
     * @param questionAndOptionsReq 问题和选项
     * @return
     */
    @PostMapping(value = "/addQueAndOpt")
    public Resp addQuestion(@RequestBody QuestionAndOptionsReq questionAndOptionsReq) {
        Question clientQuestion = questionAndOptionsReq.getQuestion();
        List<Option> optionList = questionAndOptionsReq.getOptionList();

        Resp resp = new Resp();
        if (null == clientQuestion) {
            resp.setStatusCode(400);
            resp.setReason("问题不能空");
            return resp;
        }
        if (null == optionList || optionList.size() < 1) {
            resp.setStatusCode(400);
            resp.setReason("问题选项不能为空");
            return resp;
        }
        Question dbQuestion = questionRepository.save(clientQuestion);
        if (null == dbQuestion) {
            resp.setStatusCode(500);
            resp.setReason("保存问题失败");
            return resp;
        }
        for (Option option : optionList) {
            option.setQuestionId(dbQuestion.getId());

        }
        Iterable<Option> options = optionRepository.saveAll(optionList);
        if (null == options) {
            resp.setStatusCode(500);
            resp.setReason("保存选项失败");
            return resp;
        }
        resp.setStatusCode(200);
        resp.setObject(dbQuestion.getId());
        return resp;

    }

    @PostMapping(value = "/getQuestion")
    public Resp getQuestion(@RequestBody TokenReq tokenReq) {
        Resp resp = new Resp();
        String token = tokenReq.getToken();
        if (TextUtil.isEmpty(token)) {
            resp.setStatusCode(400);
            resp.setReason("请求参数token 不能为空");
            return resp;
        }
        Optional<Student> students = studentRepository.findById(token);
        if (!students.isPresent()) {
            resp.setStatusCode(400);
            resp.setReason("用户token错误");
            return resp;
        }
        Iterable<Question> questionRepositoryAll = questionRepository.findAll();
        List<QuestionAndOptionsResp> questionAndOptionsResps = new ArrayList<>();
        for (Question question : questionRepositoryAll) {
            List<Option> options = optionRepository.findByQuestionId(question.getId());
            QuestionAndOptionsResp questionAndOptionsResp = new QuestionAndOptionsResp(question, options);
            questionAndOptionsResps.add(questionAndOptionsResp);
        }
        resp.setStatusCode(200);
        resp.setObject(questionAndOptionsResps);
        return resp;
    }
}
