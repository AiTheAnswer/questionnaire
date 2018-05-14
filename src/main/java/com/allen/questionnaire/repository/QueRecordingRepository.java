package com.allen.questionnaire.repository;

import com.allen.questionnaire.entity.QuestionRecording;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface QueRecordingRepository extends CrudRepository<QuestionRecording,String>{
    //获取当前问卷是否被当前用户登录
    QuestionRecording findByUserIdAndQuestionnaireId(String userId,String queId);
    //获取当前问卷的记录
    List<QuestionRecording> findByQuestionnaireId(String queId);
}
