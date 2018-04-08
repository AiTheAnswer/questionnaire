package com.allen.questionnaire.repository;

import com.allen.questionnaire.entity.Student;
import org.springframework.data.repository.Repository;

public interface StudentRepository extends Repository<Student,String> {
    Student save(Student student);
}
