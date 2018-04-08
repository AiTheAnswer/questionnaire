package com.allen.questionnaire.controller;

import com.allen.questionnaire.entity.Student;
import com.allen.questionnaire.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/student")
public class StudentController {
    @Autowired
    StudentRepository studentRepository;

    @RequestMapping(value = "/say", method = RequestMethod.GET)
    public String say(@RequestParam(value = "id", required = false, defaultValue = "200") Integer id) {
        return "Hello Spring Boot" + "--- id = " + id;
    }

    @GetMapping(value = "/sayHello")
    public String sayHello(@RequestParam(value = "studentId", required = true, defaultValue = "") String studentId) {
        return "";
    }

    /**
     * 添加学生
     *
     * @param student 学生对象
     * @return 成功学生的uid(用户的Token)
     */
    @PostMapping(value = "/addStudent")
    public Student addStudent(Student student) {
        Student result = studentRepository.save(student);
        return result;
    }
}
