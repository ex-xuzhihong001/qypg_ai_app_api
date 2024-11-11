package com.rxy.qypg.app.api.controller;

import com.rxy.qypg.app.api.domain.request.UserInfoReq;
import com.rxy.qypg.app.api.domain.request.UserLoginReq;
import com.rxy.qypg.app.api.service.UserService;
import com.rxy.qypg.common.dao.entity.User;
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
import java.util.Map;


@Api(tags = "用户管理")
@Controller
@RequestMapping(value = "/user/v1")
public class UserAct {

    Logger logger = LoggerFactory.getLogger(UserAct.class);

    @Resource
    private UserService userService;

    @ApiOperation("微信授权登录")
    @RequestMapping(value = "login", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<User> login(@RequestBody UserLoginReq req) {
        CommonResult<User> commonResult = new CommonResult<>(ResultCode.SUCCESS);
        try{
            /**用户是否登录，前端保存用户信息，没有认为用户没有登录， 用户发起登录调用接口我保存信息并返回前端，前端认为登录成功*/
            logger.info("login params ={}",req);
            User cmsUser = userService.login(req);
            commonResult.initData(cmsUser);
        }catch (InnerException be){
            commonResult.init(be);
        }catch (Exception e){
            logger.error("login error ",e);
            commonResult.init(InnerExceptionEnum.SYSTEM_ERROR);
        }
        return  commonResult;
    }

    @ApiOperation("调用微信服务获取用户手机号")
    @RequestMapping(value = "getUserPhone", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<Object> getUserPhone(@RequestBody UserLoginReq req) {
        CommonResult<Object> commonResult = new CommonResult<>(ResultCode.SUCCESS);
        try{
            logger.info("getUserPhone params ={}",req);
            Map<String,String> res = userService.getUserPhone(req);
            commonResult.initData(res);
        }catch (InnerException be){
            commonResult.init(be);
        }catch (Exception e){
            logger.error("getUserPhone error ",e);
            commonResult.init(InnerExceptionEnum.SYSTEM_ERROR);
        }
        return  commonResult;
    }

    @ApiOperation(value = "查询用户信息")
    @RequestMapping(value = "/getUserInfo", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public CommonResult<User> getUserInfo(@RequestBody UserInfoReq req) {
        CommonResult<User> commonResult = new CommonResult<>(ResultCode.SUCCESS);
        try {
            logger.info("getUserInfo params ={}",req);
            User res = userService.getUserInfo(req);
            commonResult.initData(res);
        }catch (InnerException ce){
            commonResult.init(ce);
        }catch (Exception e) {
            logger.error("getUserInfo error ",e);
            commonResult.init(InnerExceptionEnum.SYSTEM_ERROR);
        }
        return commonResult;
    }

    @ApiOperation(value = "添加用户信息")
    @RequestMapping(value = "/addUserInfo", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public CommonResult<Object> addUserInfo(@RequestBody User req) {
        CommonResult<Object> commonResult = new CommonResult<>(ResultCode.SUCCESS);
        try {
            logger.info("addUserInfo params ={}",req);
            userService.addUserInfo(req);
        }catch (InnerException ce){
            commonResult.init(ce);
        }catch (Exception e) {
            logger.error("addUserInfo error ",e);
            commonResult.init(InnerExceptionEnum.SYSTEM_ERROR);
        }
        return commonResult;
    }


    @ApiOperation(value = "修改用户信息")
    @RequestMapping(value = "/updateUserInfo", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public CommonResult<Object> updateUserInfo(@RequestBody User req) {
        CommonResult<Object> commonResult = new CommonResult<>(ResultCode.SUCCESS);
        try {
            logger.info("updateUserInfo params ={}",req);
            userService.updateUserInfo(req);
        }catch (InnerException ce){
            commonResult.init(ce);
        }catch (Exception e) {
            logger.error("updateUserInfo error ",e);
            commonResult.init(InnerExceptionEnum.SYSTEM_ERROR);
        }
        return commonResult;
    }

}
