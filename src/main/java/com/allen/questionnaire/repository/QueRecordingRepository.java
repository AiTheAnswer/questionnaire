package com.allen.questionnaire.repository;

import com.allen.questionnaire.entity.QuestionRecording;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface QueRecordingRepository extends CrudRepository<QuestionRecording,String>{
}
