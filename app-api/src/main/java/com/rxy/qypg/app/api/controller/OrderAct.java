package com.rxy.qypg.app.api.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rxy.qypg.app.api.domain.request.OrderCreateReq;
import com.rxy.qypg.app.api.domain.request.OrderInfoReq;
import com.rxy.qypg.app.api.domain.request.OrderListReq;
import com.rxy.qypg.app.api.domain.response.OrderCreateRes;
import com.rxy.qypg.app.api.domain.response.OrderInfoRes;
import com.rxy.qypg.app.api.service.OrderService;
import com.rxy.qypg.app.api.service.QuestionService;
import com.rxy.qypg.common.dao.entity.OmsOrder;
import com.rxy.qypg.common.enums.InnerExceptionEnum;
import com.rxy.qypg.common.exceptions.InnerException;
import com.rxy.qypg.common.pojo.vo.CommonPage;
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
import java.util.Map;

@Api(tags = "订单管理")
@Controller
@RequestMapping(value = "/oms/v1")
public class OrderAct {

    Logger logger = LoggerFactory.getLogger(CmsAct.class);

    @Resource
    private OrderService orderService;

    @ApiOperation(value = "发起评估")
    @RequestMapping(value = "/createOrder", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public CommonResult<OrderCreateRes> createOrder(@RequestBody OrderCreateReq req) {
        CommonResult<OrderCreateRes> commonResult = new CommonResult<>(ResultCode.SUCCESS);
        try {
            logger.info("createOrder params ={}",req);
            /**返回支付url*/
            OrderCreateRes res = orderService.createOrder(req);
            commonResult.initData(res);
        }catch (InnerException ce){
            commonResult.init(ce);
        }catch (Exception e) {
            logger.error("createOrder error ",e);
            commonResult.init(InnerExceptionEnum.SYSTEM_ERROR);
        }
        return commonResult;
    }


    @ApiOperation(value = "分页查询订单列表")
    @RequestMapping(value = "/getOrderList", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public CommonResult<Object> getOrderList(@RequestBody OrderListReq req) {
        CommonResult<Object> commonResult = new CommonResult<>(ResultCode.SUCCESS);
        try {
            logger.info("getOrderList params ={}",req);
            Page<OmsOrder> res = orderService.getOrderList(req);
            commonResult.initData(CommonPage.restPage(res));
        }catch (InnerException ce){
            commonResult.init(ce);
        }catch (Exception e) {
            logger.error("getOrderList error ",e);
            commonResult.init(InnerExceptionEnum.SYSTEM_ERROR);
        }
        return commonResult;
    }



    @ApiOperation(value = "订单详情:通过id查询是否评估完成")
    @RequestMapping(value = "/getOrderInfo", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public CommonResult<OrderInfoRes> getOrderInfo(@RequestBody OrderInfoReq req) {
        CommonResult<OrderInfoRes> commonResult = new CommonResult<>(ResultCode.SUCCESS);
        try {
            logger.info("getOrderInfo params ={}",req);
            OrderInfoRes res = orderService.getOrderInfo(req);
            commonResult.initData(res);
        }catch (InnerException ce){
            commonResult.init(ce);
        }catch (Exception e) {
            logger.error("getOrderInfo error ",e);
            commonResult.init(InnerExceptionEnum.SYSTEM_ERROR);
        }
        return commonResult;
    }
}
