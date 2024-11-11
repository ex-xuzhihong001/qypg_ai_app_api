package com.rxy.qypg.app.api.service;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.rxy.qypg.app.api.domain.request.QuestionHistoryReq;
import com.rxy.qypg.app.api.domain.request.QuestionHistorySaveReq;
import com.rxy.qypg.app.api.domain.request.QuestionNextReq;
import com.rxy.qypg.app.api.service.redis.impl.RedisServiceImpl;
import com.rxy.qypg.common.dao.entity.*;
import com.rxy.qypg.common.dao.service.impl.ConfigIndustryServiceImpl;
import com.rxy.qypg.common.dao.service.impl.ConfigQuestionCategoryServiceImpl;
import com.rxy.qypg.common.dao.service.impl.ConfigQuestionServiceImpl;
import com.rxy.qypg.common.dao.service.impl.UserServiceImpl;
import com.rxy.qypg.common.enums.ConditionEnums;
import com.rxy.qypg.common.enums.InnerExceptionEnum;
import com.rxy.qypg.common.exceptions.InnerException;
import com.rxy.qypg.common.pojo.dto.TriggerConditionDto;
import jdk.nashorn.internal.ir.CallNode;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;


@Service
public class QuestionService {

    Logger logger = LoggerFactory.getLogger(QuestionService.class);

    @Resource
    private RedisServiceImpl redisService;
    @Resource
    private ConfigQuestionServiceImpl questionServiceImpl;
    @Resource
    private ConfigIndustryServiceImpl configIndustryServiceImpl;
    @Resource
    private ConfigQuestionCategoryServiceImpl configQuestionCategoryServiceImpl;

    public List<ConfigQuestionCategory> getCategoryListByIndustry(User req) {
        ConfigIndustry configIndustry = configIndustryServiceImpl.getById(req.getIndustryId());
        if(configIndustry==null){
            throw new InnerException(InnerExceptionEnum.NOT_EXIST.getCode(),"没有查询到该用户的行业信息");
        }
        return configQuestionCategoryServiceImpl.queryByQyType(configIndustry.getQyType());
    }

    public ConfigQuestion getNextQuestion(QuestionNextReq req) {
        Integer questionId = req.getConfigQuestionId();
        if(questionId==null){
            return questionServiceImpl.queryLastOneByCategoryId(req.getCategoryType());
        }
       List<ConfigQuestion> childrenList = questionServiceImpl.queryChildrenListByParentId(questionId);
       if(CollectionUtils.isEmpty(childrenList)){
           return questionServiceImpl.queryLastOneByCategoryId(req.getCategoryType());
       }
        List<ConfigQuestion> collect = childrenList.stream().filter(configQuestion -> {
            String triggerCondition = configQuestion.getTriggerCondition();
            TriggerConditionDto trigger = JSON.parseObject(triggerCondition, new TypeReference<TriggerConditionDto>() {
            });
            if(trigger==null){
                return false;
            }
            return ConditionEnums.calculate(req.getCurrentQuestionScore(), trigger.getScore(), trigger.getCondition());
        }).collect(Collectors.toList());
       if(CollectionUtils.isNotEmpty(collect)){
           if(collect.size()>1){
               throw new InnerException(InnerExceptionEnum.OPT_FAIL.getCode(),"获取到两个满足条件的子问题");
           }else{
               return collect.get(0);
           }
       }
       return null;
    }

    public List<ConfigQuestion> getQuestionHistory(QuestionHistoryReq req) {
        return null;
    }

    public void saveQuestions(QuestionHistorySaveReq req) {
        /**返回一个订单批次号*/
        String batchNo = "P_" + IdWorker.getId();

    }
}
