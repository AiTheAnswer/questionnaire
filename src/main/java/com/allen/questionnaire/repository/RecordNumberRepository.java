package com.allen.questionnaire.repository;

import com.allen.questionnaire.entity.RecordNumber;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RecordNumberRepository extends CrudRepository<RecordNumber, String> {
    List<RecordNumber> findAllByQuestionnaireId(String questionnaireId);

    List<RecordNumber> findAllByUserId(String userId);

    RecordNumber findByQuestionnaireIdAndUserId(String questionnaireId, String userId);
}
