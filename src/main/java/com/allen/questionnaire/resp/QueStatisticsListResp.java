package com.allen.questionnaire.resp;

import com.allen.questionnaire.entity.Option;
import com.allen.questionnaire.entity.Question;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 问卷分析统计数据集合的实体类
 *
 * @author Renjy
 */
public class QueStatisticsListResp implements Serializable {

    public QueStatisticsListResp() {
        this.statisticsList = new ArrayList<>();
    }

    private List<QuestionnaireStatistics> statisticsList;

    public List<QuestionnaireStatistics> getStatisticsList() {
        return statisticsList;
    }

    public void setStatisticsList(List<QuestionnaireStatistics> statisticsList) {
        this.statisticsList = statisticsList;
    }

    public void addQueStatistics(Question question, List<Option> options, List<Option> selectOptions) {
        QuestionnaireStatistics statistic = getQuestionnaireStatistic(question);
        if (null != statistic) {
            List<OptionStatistics> optionStatistics = statistic.getOptionStatistics();
            for (Option option : selectOptions) {
                for (OptionStatistics optionStatistic : optionStatistics) {
                    if (optionStatistic.getOption().getId() == option.getId()) {
                        Long count = optionStatistic.getCount();
                        optionStatistic.setCount(++count);
                    }
                }
            }
        } else {
            statistic = new QuestionnaireStatistics(question);
            List<OptionStatistics> optionStatisticsList = new ArrayList<>();
            for (Option option : options) {
                OptionStatistics optionStatistics = new OptionStatistics();
                optionStatistics.setOption(option);
                optionStatistics.setCount(0L);
                optionStatisticsList.add(optionStatistics);
            }
            for (Option option : selectOptions) {
                for (OptionStatistics optionStatistic : optionStatisticsList) {
                    if (optionStatistic.getOption().getId() == option.getId()) {
                        Long count = optionStatistic.getCount();
                        optionStatistic.setCount(++count);
                    }
                }

            }
            statistic.setOptionStatistics(optionStatisticsList);
            statisticsList.add(statistic);
        }


    }

    /**
     * 通过问题去获取列表中是否存在当前问题的统计
     *
     * @param question 问题
     */
    private QuestionnaireStatistics getQuestionnaireStatistic(Question question) {
        QuestionnaireStatistics statistics = null;
        for (QuestionnaireStatistics questionnaireStatistics : statisticsList) {
            if (questionnaireStatistics.getQuestion().getId().equals(question.getId())) {
                statistics = questionnaireStatistics;
                break;
            }
        }
        return statistics;
    }

}
