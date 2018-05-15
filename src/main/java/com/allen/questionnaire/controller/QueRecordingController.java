package com.allen.questionnaire.controller;

import com.allen.questionnaire.entity.QuestionRecording;
import com.allen.questionnaire.entity.Questionnaire;
import com.allen.questionnaire.entity.RecordNumber;
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
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/recording")
public class QueRecordingController {
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    QueRecordingRepository queRecordingRepository;
    @Autowired
    QuestionnaireRepository questionnaireRepository;
    @Autowired
    RecordNumberRepository recordNumberRepository;

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
        //添加记录
        RecordNumber recordNumber = recordNumberRepository.findByQuestionnaireIdAndUserId(queRecordingReq.getQuestionnaireId(), queRecordingReq.getUserId());
        if (null == recordNumber) {
            recordNumber = new RecordNumber();
            recordNumber.setQuestionnaireId(queRecordingReq.getQuestionnaireId());
            recordNumber.setUserId(queRecordingReq.getUserId());
            recordNumber = recordNumberRepository.save(recordNumber);
        }

        List<QuestionRecording> questionRecordings = new ArrayList<>();
        for (QueRecording queRecording : queRecordingList) {
            QuestionRecording questionRecording = queRecordingRepository.findByRecordIdAndQuestionId(recordNumber.getId(), queRecording.getQuestionId());
            if (questionRecording == null) {
                questionRecording = new QuestionRecording();
                questionRecording.setRecordId(recordNumber.getId());
                questionRecording.setQuestionId(queRecording.getQuestionId());
            }
            String optionIds = queRecording.getOptionIds();
            questionRecording.setOptionIds(optionIds);
            questionRecordings.add(questionRecording);
        }
        Iterable<QuestionRecording> queRecordings = queRecordingRepository.saveAll(questionRecordings);
        Iterator<QuestionRecording> iterator = queRecordings.iterator();
        List<QuestionRecording> recordings = new ArrayList<>();
        while (iterator.hasNext()) {
            QuestionRecording next = iterator.next();
            recordings.add(next);
        }
        resp.setStatusCode(200);
        resp.setObject(recordings);
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
        List<RecordNumber> recordNumbers = recordNumberRepository.findAllByQuestionnaireId(questionnaireId);
        List<QuestionRecording> questionRecordings = new ArrayList<>();
        for (RecordNumber recordNumber : recordNumbers) {
            List<QuestionRecording> recordingList = queRecordingRepository.findAllByRecordId(recordNumber.getId());
            questionRecordings.addAll(recordingList);
        }
        resp.setStatusCode(200);
        resp.setObject(questionRecordings);
        return resp;
    }


}
