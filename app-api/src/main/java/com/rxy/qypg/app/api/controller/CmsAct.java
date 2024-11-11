package com.rxy.qypg.app.api.controller;

import com.rxy.qypg.app.api.domain.request.IndustryListReq;
import com.rxy.qypg.app.api.domain.response.IndustryVo;
import com.rxy.qypg.app.api.service.CmsIndustryService;
import com.rxy.qypg.common.dao.entity.SmsPgGoods;
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


@Api(tags = "配置管理")
@Controller
@RequestMapping(value = "/cms/v1")
public class CmsAct {

    Logger logger = LoggerFactory.getLogger(CmsAct.class);

    @Resource
    private CmsIndustryService cmsIndustryService;


    @ApiOperation(value = "查询行业、部门、职位信息")
    @RequestMapping(value = "/getIndustryListByType", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public CommonResult<Object> getIndustryListByType(@RequestBody IndustryListReq req) {
        CommonResult<Object> commonResult = new CommonResult<>(ResultCode.SUCCESS);
        try {
            logger.info("getIndustryListByType params ={}",req);
            List<IndustryVo> res = cmsIndustryService.getIndustryListByType(req);
            commonResult.initData(res);
        }catch (InnerException ce){
            commonResult.init(ce);
        }catch (Exception e) {
            logger.error("getIndustryListByType error ",e);
            commonResult.init(InnerExceptionEnum.SYSTEM_ERROR);
        }
        return commonResult;
    }


    @ApiOperation(value = "查询评估产品信息")
    @RequestMapping(value = "/getAiGoods", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public CommonResult<List<SmsPgGoods>> getAiGoods() {
        CommonResult<List<SmsPgGoods>> commonResult = new CommonResult<>(ResultCode.SUCCESS);
        try {
            List<SmsPgGoods> res = cmsIndustryService.getAiGoods();
            commonResult.initData(res);
        }catch (InnerException ce){
            commonResult.init(ce);
        }catch (Exception e) {
            logger.error("getAiGoods error ",e);
            commonResult.init(InnerExceptionEnum.SYSTEM_ERROR);
        }
        return commonResult;
    }
}
