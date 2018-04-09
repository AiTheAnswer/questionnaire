package com.allen.questionnaire.entity;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity(name = "student")
public class Student {
    @Id
    @GeneratedValue
    private String id;//Uid
    @NotEmpty
    @Column(name = "student_id")
    private String studentId; //学号
    @NotEmpty
    @Column(name = "id_number")
    private String idNumber; //身份证号
    @NotEmpty
    @Column(name = "name")
    private String name;//姓名
    @NotNull
    @Column(name = "sex")
    private int sex;//性别 0: 男 1：女
    @NotEmpty
    @Column(name = "grade")
    private String grade;//年级
    @NotEmpty
    @Column(name = "profession")
    private String profession; //专业

    public Student() {
    }

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }
}
