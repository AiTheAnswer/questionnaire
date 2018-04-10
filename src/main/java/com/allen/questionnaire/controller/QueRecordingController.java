package com.allen.questionnaire.controller;

import com.allen.questionnaire.entity.QuestionRecording;
import com.allen.questionnaire.repository.OptionRepository;
import com.allen.questionnaire.repository.QueRecordingRepository;
import com.allen.questionnaire.repository.QuestionRepository;
import com.allen.questionnaire.req.QueRecording;
import com.allen.questionnaire.req.QueRecordingReq;
import com.allen.questionnaire.resp.Resp;
import com.allen.questionnaire.util.TextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/recording")
public class QueRecordingController {
    @Autowired
    QuestionRepository questionRepository;
    @Autowired
    OptionRepository optionRepository;

    @Autowired
    QueRecordingRepository queRecordingRepository;

    @PostMapping(value = "/addRecordings")
    public Resp addRecordings(@RequestBody QueRecordingReq queRecordingReq) {
        Resp resp = new Resp();
        if (null == queRecordingReq) {
            resp.setStatusCode(400);
            resp.setReason("参数不能为空");
            return resp;
        }
        String userId = queRecordingReq.getUserId();
        List<QueRecording> queRecordingList = queRecordingReq.getQueRecordingList();
        if (TextUtil.isEmpty(userId)) {
            resp.setStatusCode(400);
            resp.setReason("用户Id不能为空");
            return resp;
        }
        if (null == queRecordingList || queRecordingList.size() < 1) {
            resp.setStatusCode(400);
            resp.setReason("问题记录不能为空");
            return resp;
        }
        List<QuestionRecording> questionRecordings = new ArrayList<>();
        for (QueRecording queRecording : queRecordingList) {
            QuestionRecording recordingEntity = new QuestionRecording();
            recordingEntity.setUserId(userId);
            recordingEntity.setQuestionId(queRecording.getQuestionId());
            StringBuffer stringBuffer = new StringBuffer();
            List<String> optionIds = queRecording.getOptionIds();
            for (String optionId : optionIds) {
                stringBuffer.append(optionId);
                stringBuffer.append(",");
            }
            String options = stringBuffer.toString();
            String substring = options.substring(0, options.length() - 1);
            recordingEntity.setOptionIds(substring);
            questionRecordings.add(recordingEntity);
        }
        Iterable<QuestionRecording> queRecordings = queRecordingRepository.saveAll(questionRecordings);
        if(null == queRecordings){
            resp.setStatusCode(500);
            resp.setObject(queRecordings);
            resp.setReason("保存问题调查记录");
        }
        resp.setStatusCode(200);
        resp.setObject(queRecordings);
        return resp;
    }


}
