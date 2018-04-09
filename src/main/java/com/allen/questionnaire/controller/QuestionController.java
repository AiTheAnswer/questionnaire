package com.allen.questionnaire.controller;

import com.allen.questionnaire.entity.Option;
import com.allen.questionnaire.entity.Question;
import com.allen.questionnaire.entity.QuestionAndOptions;
import com.allen.questionnaire.entity.Resp;
import com.allen.questionnaire.repository.OptionRepository;
import com.allen.questionnaire.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/question")
public class QuestionController {
    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    OptionRepository optionRepository;

    /**
     * 添加问题和选项
     *
     * @param questionAndOptions 问题和选项
     * @return
     */
    @PostMapping(value = "/addQueAndOpt")
    public Resp addQuestion(@RequestBody QuestionAndOptions questionAndOptions) {
        Question clientQuestion = questionAndOptions.getQuestion();
        List<Option> optionList = questionAndOptions.getOptionList();

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
}
