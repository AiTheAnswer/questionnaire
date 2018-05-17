package com.allen.questionnaire.resp;

import com.allen.questionnaire.entity.Option;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * 选项被选择的个数
 *
 * @author Renjy
 */
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class OptionStatistics implements Serializable {
    private Option option;
    private Long count;

    public Option getOption() {
        return option;
    }

    public void setOption(Option option) {
        this.option = option;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
