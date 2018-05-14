package com.allen.questionnaire.controller;

import com.allen.questionnaire.entity.QuestionRecording;
import com.allen.questionnaire.entity.Questionnaire;
import com.allen.questionnaire.entity.Student;
import com.allen.questionnaire.repository.*;
import com.allen.questionnaire.req.GetRecordingReq;
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
import java.util.Optional;

@RestController
@RequestMapping(value = "/recording")
public class QueRecordingController {
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    QuestionRepository questionRepository;
    @Autowired
    OptionRepository optionRepository;
    @Autowired
    QueRecordingRepository queRecordingRepository;
    @Autowired
    QuestionnaireRepository questionnaireRepository;

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
        } else {
            Optional<Student> students = studentRepository.findById(userId);
            if (!students.isPresent()) {
                resp.setStatusCode(400);
                resp.setReason("用户Id不存在");
                return resp;
            }

        }
        if (TextUtil.isEmpty(queRecordingReq.getQuestionnaireId())) {
            resp.setStatusCode(400);
            resp.setReason("问卷Id不能为空");
            return resp;
        } else {
            Optional<Questionnaire> questionnaires = questionnaireRepository.findById(queRecordingReq.getQuestionnaireId());
            if (!questionnaires.isPresent()) {
                resp.setStatusCode(400);
                resp.setReason("问卷id对应的问卷不存在");
                return resp;
            }
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
            recordingEntity.setQuestionnaireId(queRecordingReq.getQuestionnaireId());
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
        resp.setStatusCode(200);
        resp.setObject(queRecordings);
        return resp;
    }

    @PostMapping(value = "/getRecordings")
    public Resp getRecordingd(@RequestBody GetRecordingReq recordingReq) {
        Resp resp = new Resp();
        if (null == recordingReq) {
            resp.setStatusCode(400);
            resp.setReason("参数不能为空");
            return resp;
        }
        String userId = recordingReq.getUserId();
        if (TextUtil.isEmpty(userId)) {
            resp.setStatusCode(400);
            resp.setReason("用户id不能为空");
            return resp;
        } else {
            Optional<Student> students = studentRepository.findById(recordingReq.getUserId());
            if (!students.isPresent()) {
                resp.setStatusCode(400);
                resp.setReason("用户id不存在");
                return resp;
            }
        }
        String questionnaireId = recordingReq.getQuestionnaireId();
        if (TextUtil.isEmpty(questionnaireId)) {
            resp.setStatusCode(400);
            resp.setReason("问卷id不能为空");
            return resp;
        } else {
            Optional<Questionnaire> questionnaires = questionnaireRepository.findById(questionnaireId);
            if (!questionnaires.isPresent()) {
                resp.setStatusCode(400);
                resp.setReason("w问卷id不存在");
                return resp;
            }
        }
        List<QuestionRecording> recordingList = queRecordingRepository.findByQuestionnaireId(questionnaireId);
        resp.setStatusCode(200);
        resp.setObject(recordingList);
        return resp;
    }


}
