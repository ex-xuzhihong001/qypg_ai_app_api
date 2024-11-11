package com.rxy.qypg.app.api.service;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rxy.qypg.app.api.config.ConfigCommonProperties;
import com.rxy.qypg.app.api.config.ConfigWxProperties;
import com.rxy.qypg.app.api.domain.request.UserInfoReq;
import com.rxy.qypg.app.api.domain.request.UserLoginReq;
import com.rxy.qypg.common.dao.entity.User;
import com.rxy.qypg.common.dao.service.IUserService;
import com.rxy.qypg.common.dao.service.impl.UserServiceImpl;
import com.rxy.qypg.common.enums.InnerExceptionEnum;
import com.rxy.qypg.common.exceptions.InnerException;
import com.rxy.qypg.common.utils.HttpClientPool;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Service
public class UserService {

    Logger logger = LoggerFactory.getLogger(UserService.class);

    @Resource
    private ConfigWxProperties wxProperties;
    @Resource
    private ConfigCommonProperties commonProperties;
    @Resource
    private UserServiceImpl userServiceImpl;

    public User login(UserLoginReq req) {
        String url = String.format(wxProperties.getWxLoginUrl(),wxProperties.getAppId(),wxProperties.getAppSecret(),req.getCode());
        JSONObject res = HttpClientPool.sendGetRequest(url, null,JSONObject.class);
        logger.info("微信登录返回：{}",res);
        if(res!=null && StringUtils.isNotBlank(res.getString("openid"))){
            String openId =  res.getString("openid");
            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getOpenId,openId);
            User user = userServiceImpl.getOne(queryWrapper);
            if(user==null){
                Date date = new Date();
                user = User.builder().ctime(date).mtime(date).openId(openId).nickname("啥也没写").status(1).imgUrl(commonProperties.getDefaultPicture()).build();
                userServiceImpl.save(user);
            }else{
                user.setMtime(new Date());
                userServiceImpl.updateByOpenId(user);
            }
            return user;
        }else {
            throw new InnerException(InnerExceptionEnum.OPT_FAIL.getCode(),"登录失败："+res);
        }
    }

    public Map<String, String> getUserPhone(UserLoginReq req) {
        Map<String,String> resMap = new HashMap<>();
        String tokenUrl = String.format(wxProperties.getWxTokenUrl(),wxProperties.getAppId(),wxProperties.getAppSecret());
        JSONObject res = HttpClientPool.sendGetRequest(tokenUrl, null,JSONObject.class);
        if(res==null){
            throw new InnerException(InnerExceptionEnum.OPT_FAIL.getCode(),"微信获取token返回为null");
        }
        logger.info("微信获取token返回:"+res.toJSONString());
        String access_token = res.getString("access_token");
        if(StringUtils.isBlank(access_token)){
            throw new InnerException(InnerExceptionEnum.OPT_FAIL.getCode(),"微信获取token返回:"+res.toJSONString());
        }

        Map<String,String> map = new HashMap<>();
        map.put("code",req.getPhoneCode());
        String url = String.format(wxProperties.getWxGetPhoneUrl(),access_token);
        JSONObject getPhoneRes = HttpClientPool.sendPostRequest(url, JSON.toJSONString(map),HttpClientPool.defaultHeaders,JSONObject.class);
        if(getPhoneRes!=null){
            logger.info("微信获取手机号返回:"+getPhoneRes.toJSONString());
            JSONObject phone_info = getPhoneRes.getJSONObject("phone_info");
            if(phone_info!=null){
                String countryCode = phone_info.getString("countryCode");
                String purePhoneNumber = phone_info.getString("purePhoneNumber");
                if(StringUtils.isNotBlank(countryCode)){
                    resMap.put("countryCode",countryCode);
                }
                if(StringUtils.isNotBlank(purePhoneNumber)){
                    resMap.put("purePhoneNumber",purePhoneNumber);
                }
            }
        }
        return resMap;
    }

    public User getUserInfo(UserInfoReq req) {
        return userServiceImpl.getByOpenId(req.getOpenId());
    }

    public void addUserInfo(User user) {
        userServiceImpl.save(user);
    }

    public void updateUserInfo(User user) {
        userServiceImpl.updateById(user);
    }
}
