package com.allen.questionnaire.repository;

import com.allen.questionnaire.entity.Questionnaire;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;
import java.util.List;

public interface QuestionnaireRepository extends CrudRepository<Questionnaire,Integer>{

    List<Questionnaire> findByCategoryId(Integer integer);
}
