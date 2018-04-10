package com.allen.questionnaire.repository;

import com.allen.questionnaire.entity.Student;
import org.springframework.data.repository.CrudRepository;

public interface StudentRepository extends CrudRepository<Student,String> {
    Student findByStudentIdAndIdNumber(String studentId,String idNumber);
}
