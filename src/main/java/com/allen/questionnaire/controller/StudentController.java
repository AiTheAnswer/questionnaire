package com.allen.questionnaire.controller;

import com.allen.questionnaire.resp.Resp;
import com.allen.questionnaire.entity.Student;
import com.allen.questionnaire.req.LoginReq;
import com.allen.questionnaire.repository.StudentRepository;
import com.allen.questionnaire.util.TextUtil;
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

    /**
     * 添加学生
     *
     * @param student 学生对象
     * @return 成功学生的uid(用户的Token)
     */
    @PostMapping(value = "/addStudent")
    public Resp addStudent(@RequestBody Student student) {

        Student result = studentRepository.save(student);
        Resp resp = new Resp();
        if (null != result) {
            resp.setObject(result.getId());
            resp.setStatusCode(200);
        }else{
            resp.setStatusCode(400);
            resp.setReason("参数错误，请检查参数");
        }
        return resp;
    }

    @PostMapping(value = "/login")
    public Resp login(@RequestBody LoginReq loginReq){
        Resp resp = new Resp();
        String idNumber = loginReq.getIdNumber();
        String studentId = loginReq.getStudentId();

        if(null == loginReq ){
            resp.setStatusCode(400);
            resp.setReason("参数不能为空");
            return resp;
        }
       if(TextUtil.isEmpty(idNumber)){
           resp.setStatusCode(400);
           resp.setReason("身份证号为空");
           return resp;
       }
       if(TextUtil.isEmpty(studentId)){
           resp.setStatusCode(400);
           resp.setReason("学号为空");
           return resp;
       }
        Student student = studentRepository.findByStudentIdAndIdNumber(studentId, idNumber);
       resp.setStatusCode(200);
       resp.setObject(student);
       return resp;
    }
}
