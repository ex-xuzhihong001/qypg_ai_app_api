package com.rxy.qypg.app.api.controller;

import com.rxy.qypg.app.api.domain.request.QuestionHistoryReq;
import com.rxy.qypg.app.api.domain.request.QuestionHistorySaveReq;
import com.rxy.qypg.app.api.domain.request.QuestionNextReq;
import com.rxy.qypg.app.api.service.QuestionService;
import com.rxy.qypg.common.dao.entity.ConfigQuestion;
import com.rxy.qypg.common.dao.entity.ConfigQuestionCategory;
import com.rxy.qypg.common.dao.entity.User;
import com.rxy.qypg.common.dao.entity.UserQuestionHistory;
import com.rxy.qypg.common.enums.InnerExceptionEnum;
import com.rxy.qypg.common.exceptions.InnerException;
import com.rxy.qypg.common.pojo.vo.CommonResult;
import com.rxy.qypg.common.pojo.vo.ResultCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;


@Api(tags = "问题管理")
@Controller
@RequestMapping(value = "/question/v1")
public class QuestionAct {

    Logger logger = LoggerFactory.getLogger(QuestionAct.class);

    @Resource
    private QuestionService questionService;

    @ApiOperation(value = "通过行业查询问题维度:只需要传行业id即可")
    @RequestMapping(value = "/getCategoryListByIndustry", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public CommonResult<List<ConfigQuestionCategory>> getCategoryListByIndustry(@RequestBody User req) {
        CommonResult<List<ConfigQuestionCategory>> commonResult = new CommonResult<>(ResultCode.SUCCESS);
        try {
            logger.info("getCategoryListByIndustry params ={}",req);
            List<ConfigQuestionCategory> res = questionService.getCategoryListByIndustry(req);
            commonResult.initData(res);
        }catch (InnerException ce){
            commonResult.init(ce);
        }catch (Exception e) {
            logger.error("getNextQuestion error ",e);
            commonResult.init(InnerExceptionEnum.SYSTEM_ERROR);
        }
        return commonResult;
    }

    @ApiOperation(value = "查询下个问题:用户id、传的问题id为空[认为是第一题],返回如果为空，说明该维度下问题回答完毕，可以下一个维度")
    @RequestMapping(value = "/getNextQuestion", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public CommonResult<ConfigQuestion> getNextQuestion(@RequestBody QuestionNextReq req) {
        CommonResult<ConfigQuestion> commonResult = new CommonResult<>(ResultCode.SUCCESS);
        try {
            logger.info("getNextQuestion params ={}",req);
            ConfigQuestion res = questionService.getNextQuestion(req);
            commonResult.initData(res);
        }catch (InnerException ce){
            commonResult.init(ce);
        }catch (Exception e) {
            logger.error("getNextQuestion error ",e);
            commonResult.init(InnerExceptionEnum.SYSTEM_ERROR);
        }
        return commonResult;
    }

    @ApiOperation(value = "保存问题")
    @RequestMapping(value = "/saveQuestions", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public CommonResult<Object> saveQuestions(@RequestBody QuestionHistorySaveReq req) {
        CommonResult<Object> commonResult = new CommonResult<>(ResultCode.SUCCESS);
        try {
            logger.info("getNextQuestion params ={}",req);
            questionService.saveQuestions(req);
        }catch (InnerException ce){
            commonResult.init(ce);
        }catch (Exception e) {
            logger.error("getNextQuestion error ",e);
            commonResult.init(InnerExceptionEnum.SYSTEM_ERROR);
        }
        return commonResult;
    }

    @ApiOperation(value = "查询问题回答历史")
    @RequestMapping(value = "/getQuestionHistory", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public CommonResult<List<ConfigQuestion>> getQuestionHistory(@RequestBody QuestionHistoryReq req) {
        CommonResult<List<ConfigQuestion>> commonResult = new CommonResult<>(ResultCode.SUCCESS);
        try {
            logger.info("getQuestionHistory params ={}",req);
            List<ConfigQuestion> res = questionService.getQuestionHistory(req);
            commonResult.initData(res);
        }catch (InnerException ce){
            commonResult.init(ce);
        }catch (Exception e) {
            logger.error("getQuestionHistory error ",e);
            commonResult.init(InnerExceptionEnum.SYSTEM_ERROR);
        }
        return commonResult;
    }
}
