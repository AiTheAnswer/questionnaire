package com.allen.questionnaire.controller;

import com.allen.questionnaire.entity.*;
import com.allen.questionnaire.repository.*;
import com.allen.questionnaire.req.GetQueStatisticalReq;
import com.allen.questionnaire.req.GetRecordingReq;
import com.allen.questionnaire.req.QueRecording;
import com.allen.questionnaire.req.QueRecordingReq;
import com.allen.questionnaire.resp.QueStatisticsListResp;
import com.allen.questionnaire.resp.QuestionnaireResp;
import com.allen.questionnaire.resp.QuestionnaireStatistics;
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
    @Autowired
    QuestionRepository questionRepository;
    @Autowired
    OptionRepository optionRepository;


    /**
     * 提交问卷用户作答答案
     *
     * @param queRecordingReq
     * @return
     */
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

    /**
     * 获取已作答的问卷列表
     *
     * @param recordingReq 用户Id
     * @return
     */
    @PostMapping(value = "/getRecordings")
    public Resp getRecordings(@RequestBody GetRecordingReq recordingReq) {
        Resp resp = new Resp();
        if (null == recordingReq) {
            resp.setStatusCode(400);
            resp.setReason("参数不能为空");
            return resp;
        }
        String userId = recordingReq.getToken();
        if (TextUtil.isEmpty(userId)) {
            resp.setStatusCode(400);
            resp.setReason("token不能为空");
            return resp;
        } else {
            Optional<Student> students = studentRepository.findById(recordingReq.getToken());
            if (!students.isPresent()) {
                resp.setStatusCode(400);
                resp.setReason("token不存在");
                return resp;
            }
        }
        int categoryId = recordingReq.getCategoryId();
        int type = recordingReq.getType();


        Iterable<RecordNumber> recordNumbers = recordNumberRepository.findAll();
        List<QuestionnaireResp> questionnaireRespList = new ArrayList<>();
        for (RecordNumber recordNumber : recordNumbers) {
            Optional<Questionnaire> questionnaires = questionnaireRepository.findById(recordNumber.getQuestionnaireId());
            if (questionnaires.isPresent()) {
                Questionnaire questionnaire = questionnaires.get();
                if (categoryId != 0) {
                    if (categoryId != questionnaire.getCategoryId()) {
                        continue;
                    }
                }
                if (type != 0) {
                    if (type == 1) {//当前用户作答
                        if (!userId.equals(recordNumber.getUserId())) {
                            continue;
                        }
                    } else {//非当前用户作答
                        if (userId.equals(recordNumber.getUserId())) {
                            continue;
                        }
                    }
                }
                QuestionnaireResp questionnaireResp = new QuestionnaireResp();
                questionnaireResp.setId(questionnaire.getId());
                questionnaireResp.setQuestionnaireName(questionnaire.getQuestionnaireName());
                questionnaireResp.setCategoryId(questionnaire.getCategoryId());
                String queId = questionnaire.getId();
                List<Question> questions = questionRepository.findByQuestionnaireId(queId);
                questionnaireResp.setQuestionNumber(questions.size());
                questionnaireResp.setUse(recordNumber.getUserId().equals(userId));
                List<RecordNumber> records = recordNumberRepository.findAllByQuestionnaireId(queId);
                if (null == records) {
                    questionnaireResp.setUseNumber(0);
                } else {
                    questionnaireResp.setUseNumber(records.size());
                }

                questionnaireRespList.add(questionnaireResp);
            }
        }
        resp.setStatusCode(200);
        resp.setObject(questionnaireRespList);
        return resp;
    }


    @PostMapping(value = "/getQueStatistical")
    public Resp getQueStatistical(@RequestBody GetQueStatisticalReq statisticalReq) {
        Resp resp = new Resp();
        if (null == statisticalReq) {
            resp.setStatusCode(400);
            resp.setReason("参数不能为空");
            return resp;
        }
        String userId = statisticalReq.getToken();
        if (TextUtil.isEmpty(userId)) {
            resp.setStatusCode(400);
            resp.setReason("参数token不能为空");
            return resp;
        } else {
            Optional<Student> student = studentRepository.findById(userId);
            if (!student.isPresent()) {
                resp.setStatusCode(400);
                resp.setReason("参数token错误");
                return resp;
            }
        }
        String questionnaireId = statisticalReq.getQuestionnaireId();
        if (TextUtil.isEmpty(questionnaireId)) {
            resp.setStatusCode(400);
            resp.setReason("参数questionnaireId不能为空");
            return resp;
        } else {
            Optional<Questionnaire> questionnaire = questionnaireRepository.findById(questionnaireId);
            if (!questionnaire.isPresent()) {
                resp.setStatusCode(400);
                resp.setReason("参数questionnaireId错误");
                return resp;
            }
        }
        List<RecordNumber> recordNumbers = recordNumberRepository.findAllByQuestionnaireId(questionnaireId);
        QueStatisticsListResp statisticsListResp = new QueStatisticsListResp();
        for (RecordNumber recordNumber : recordNumbers) {
            List<QuestionRecording> questionRecordings = queRecordingRepository.findAllByRecordId(recordNumber.getId());
            for (QuestionRecording questionRecording : questionRecordings) {
                String questionId = questionRecording.getQuestionId();
                Optional<Question> optional = questionRepository.findById(questionId);
                if (optional.isPresent()) {
                    Question question = optional.get();
                    String optionIds = question.getOptionIds();
                    List<Option> optionList = getOptions(optionIds);
                    String selectOptionIds = questionRecording.getOptionIds();
                    List<Option> selectOptionList = getOptions(selectOptionIds);
                    statisticsListResp.addQueStatistics(question, optionList, selectOptionList);
                }

            }
        }
        resp.setStatusCode(200);
        resp.setObject(statisticsListResp);
        return resp;
    }


    /**
     * 获取问题选项集合根据选项id字符串，多个id之间用逗号隔开
     *
     * @param optionIds id串
     * @return 选项集合
     */
    public List<Option> getOptions(String optionIds) {
        List<Option> optionList = new ArrayList<>();
        String[] optionIdArray = optionIds.split(",");
        for (String id : optionIdArray) {
            Optional<Option> optional = optionRepository.findById(Long.parseLong(id));
            if (optional.isPresent()) {
                Option option = optional.get();
                optionList.add(option);
            }
        }
        return optionList;
    }

}
