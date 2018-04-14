package com.allen.questionnaire.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

/**
 * 问题选项的实体类
 */
@Entity(name = "options")
public class Option {
    @Id
    @GeneratedValue
    private int id;
    @NotEmpty
    @Column(name = "option_des")
    private String optionDes;//选项描述


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOptionDes() {
        return optionDes;
    }

    public void setOptionDes(String optionDes) {
        this.optionDes = optionDes;
    }
}
