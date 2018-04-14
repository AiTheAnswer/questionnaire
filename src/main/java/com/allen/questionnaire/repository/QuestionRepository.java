package com.allen.questionnaire.repository;

import com.allen.questionnaire.entity.Question;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface QuestionRepository extends CrudRepository<Question, String> {
    List<Question> findByQuestionnaireId(String queId);
}
