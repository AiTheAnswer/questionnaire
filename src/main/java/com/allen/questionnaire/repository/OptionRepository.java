package com.allen.questionnaire.repository;

import com.allen.questionnaire.entity.Option;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OptionRepository extends CrudRepository<Option,String> {
    List<Option> findByQuestionId(String questionId);
}
