package com.allen.questionnaire.repository;

import com.allen.questionnaire.entity.Option;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface OptionRepository extends CrudRepository<Option,Long>,JpaRepository<Option,Long> {
    List<Option> findAllById(List<Integer> options);
}
