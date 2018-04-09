package com.allen.questionnaire.repository;

import com.allen.questionnaire.entity.Question;
import org.springframework.data.repository.CrudRepository;

public interface QuestionRepository extends CrudRepository<Question, String> {
}
