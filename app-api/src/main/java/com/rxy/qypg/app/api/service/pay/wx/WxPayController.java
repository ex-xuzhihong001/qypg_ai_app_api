package com.rxy.qypg.app.api.service.pay.wx;

import com.alibaba.fastjson.JSON;
import com.rxy.qypg.common.exceptions.InnerException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Api(tags = "微信回调",hidden = true)
@Controller
@RequestMapping("/wx/v1")
public class WxPayController {

    Logger logger = LoggerFactory.getLogger(WxPayController.class);

    @Resource
    private WxPayService wxPayService;

    @ApiOperation("支付回调通知")
    @PostMapping("/pay/notify")
    @ResponseBody
    public String payNotify(HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> map = new HashMap<>();
        try{
            logger.error("WxPayController payNotify start");
            response.setStatus(200);
            wxPayService.payNotify(request);
        }catch (InnerException be){
            logger.error("WxPayController payNotify inner error={} ",be.getMessage());
            response.setStatus(500);
            map.put("code", "ERROR");
            map.put("message", be.getMessage());
        }catch (Exception e){
            logger.error("WxPayController payNotify error ",e);
            response.setStatus(500);
            map.put("code", "ERROR");
            map.put("message", "失败");
        }
        return  JSON.toJSONString(map);
    }


    @ApiOperation("退款回调通知")
    @PostMapping("/refunds/notify")
    @ResponseBody
    public String refundsNotify(HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> map = new HashMap<>();
        try{
            response.setStatus(200);
            wxPayService.refundsNotify(request);
        }catch (InnerException be){
            logger.error("WxPayController payNotify inner error={} ",be.getMessage());
            response.setStatus(500);
            map.put("code", "ERROR");
            map.put("message", be.getMessage());
        }catch (Exception e){
            logger.error("WxPayController refundsNotify error ",e);
            response.setStatus(500);
            map.put("code", "ERROR");
            map.put("message", "失败");
        }
        return  JSON.toJSONString(map);
    }
}
