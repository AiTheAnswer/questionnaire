package com.allen.questionnaire.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * 问题选项的实体类
 */
@Entity(name = "options")
public class Option implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty
    @Column(name = "option_des")
    private String optionDes;//选项描述

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOptionDes() {
        return optionDes;
    }

    public void setOptionDes(String optionDes) {
        this.optionDes = optionDes;
    }
}
