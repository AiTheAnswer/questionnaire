package com.allen.questionnaire.controller;

import com.allen.questionnaire.entity.Category;
import com.allen.questionnaire.entity.Student;
import com.allen.questionnaire.repository.CategoryRepository;
import com.allen.questionnaire.repository.StudentRepository;
import com.allen.questionnaire.req.TokenReq;
import com.allen.questionnaire.resp.Resp;
import com.allen.questionnaire.util.TextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/category")
public class CategoryController {
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    CategoryRepository categoryRepository;


    @PostMapping(value = "getCategoryList")
    public Resp getCategoryList(@RequestBody TokenReq token) {
        Resp resp = new Resp();
        if (null == token) {
            resp.setStatusCode(400);
            resp.setReason("参数不能为空");
            return resp;
        }
        if (TextUtil.isEmpty(token.getToken())) {
            resp.setStatusCode(400);
            resp.setReason("参数不能为空");
            return resp;
        }
        Optional<Student> students = studentRepository.findById(token.getToken());
        if (!students.isPresent()) {
            resp.setStatusCode(400);
            resp.setReason("用户token错误");
            return resp;
        }
        Iterable<Category> categories = categoryRepository.findAll();
        Iterator<Category> iterator = categories.iterator();
        List<Category> categoryList = new ArrayList<>();
        while (iterator.hasNext()) {
            Category category = iterator.next();
            if (null != category && 0 != category.getId()) {
                categoryList.add(category);
            }
        }
        resp.setStatusCode(200);
        resp.setObject(categoryList);
        return resp;
    }
}
