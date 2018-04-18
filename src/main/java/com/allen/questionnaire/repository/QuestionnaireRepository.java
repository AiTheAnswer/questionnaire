package com.allen.questionnaire.repository;

import com.allen.questionnaire.entity.Questionnaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface QuestionnaireRepository extends CrudRepository<Questionnaire,String>,JpaRepository<Questionnaire,String> {

    List<Questionnaire> findByCategoryId(Integer integer);
}
