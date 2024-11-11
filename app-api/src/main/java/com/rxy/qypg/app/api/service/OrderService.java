package com.rxy.qypg.app.api.service;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rxy.qypg.app.api.domain.request.OrderCreateReq;
import com.rxy.qypg.app.api.domain.request.OrderInfoReq;
import com.rxy.qypg.app.api.domain.request.OrderListReq;
import com.rxy.qypg.app.api.domain.response.OrderCreateRes;
import com.rxy.qypg.app.api.domain.response.OrderInfoRes;
import com.rxy.qypg.app.api.service.pay.wx.WxPayConfig;
import com.rxy.qypg.app.api.service.pay.wx.WxPayService;
import com.rxy.qypg.common.dao.entity.*;
import com.rxy.qypg.common.dao.service.impl.OmsOrderServiceImpl;
import com.rxy.qypg.common.dao.service.impl.SmsPgGoodsServiceImpl;
import com.rxy.qypg.common.dao.service.impl.UserScoreServiceImpl;
import com.rxy.qypg.common.dao.service.impl.UserServiceImpl;
import com.rxy.qypg.common.enums.InnerExceptionEnum;
import com.rxy.qypg.common.enums.OrderAiStatus;
import com.rxy.qypg.common.enums.OrderPayStatus;
import com.rxy.qypg.common.enums.StatusEnums;
import com.rxy.qypg.common.exceptions.InnerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.security.Signature;
import java.util.*;


@Service
public class OrderService {

    Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Resource
    private UserServiceImpl userServiceImpl;
    @Resource
    private UserScoreServiceImpl userScoreServiceImpl;
    @Resource
    private SmsPgGoodsServiceImpl smsPgGoodsServiceImpl;
    @Resource
    private OmsOrderServiceImpl omsOrderServiceImpl;
    @Resource
    private WxPayService wxPayService;
    @Resource
    private WxPayConfig wxPayConfig;



    public OrderCreateRes createOrder(OrderCreateReq req) {
        User user = userServiceImpl.getById(req.getUserId());
        if(user==null){
            throw new InnerException(InnerExceptionEnum.PARAMS_FAIL.getCode(),"该用户不存在");
        }
        List<UserScore> list = userScoreServiceImpl.getListByBatchNo(req.getBatchNo());
        if(CollectionUtils.isEmpty(list)){
            throw new InnerException(InnerExceptionEnum.PARAMS_FAIL.getCode(),"该批次号问题未查询到得分情况");
        }
        SmsPgGoods smsPgGoods = smsPgGoodsServiceImpl.getById(req.getGoodId());
        if(smsPgGoods==null){
            throw new InnerException(InnerExceptionEnum.PARAMS_FAIL.getCode(),"该评估产品不存在");
        }
        if(smsPgGoods.getAmount().compareTo(req.getAmount())!=0){
            throw new InnerException(InnerExceptionEnum.PARAMS_FAIL.getCode(),"支付金额有误");
        }
        String orderNo = "O_" + IdWorker.getId();
        Integer total = smsPgGoods.getAmount().multiply(new BigDecimal(100)).intValue();
        String prepayId = wxPayService.getPrepayId(smsPgGoods.getTitle(), total, orderNo, user.getOpenId());
        Date date = new Date();
        OmsOrder order = OmsOrder.builder().ctime(date).mtime(date)
                .orderNo(orderNo).batchNo(req.getBatchNo()).prepayId(prepayId).userId(user.getId())
                .totalAmount(smsPgGoods.getAmount()).payAmount(smsPgGoods.getAmount()).payType(2)
                .payStatus(OrderPayStatus.NO_PAY.getCode()).orderStatus(OrderAiStatus.INIT.getCode())
                .note(req.getNote()).deleteStatus(0).build();
        omsOrderServiceImpl.save(order);
        return buildCreateOrderRes(orderNo, prepayId);
    }

    private OrderCreateRes buildCreateOrderRes(String orderNo, String prepayId) {
        OrderCreateRes res = new OrderCreateRes();
        res.setAppId(wxPayConfig.getAppid());
        long timestamp = System.currentTimeMillis() / 1000;
        res.setTimeStamp(timestamp + "");
        String nonceStr = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
        res.setNonceStr(nonceStr);
        res.setPackageStr("prepay_id=" + prepayId);
        res.setSignType("RSA");
        String message = wxPayConfig.getAppid() + "\n"
                + timestamp + "\n"
                + nonceStr + "\n"
                + "prepay_id=" + prepayId + "\n";
        String signature;
        try {
            Signature sign = Signature.getInstance("SHA256withRSA");
            PrivateKey privateKey = wxPayConfig.getPrivateKey(wxPayConfig.getPrivateKeyPath());
            sign.initSign(privateKey);
            sign.update(message.getBytes(StandardCharsets.UTF_8));
            signature = Base64.getEncoder().encodeToString(sign.sign());
        } catch (Exception e) {
            throw new InnerException(InnerExceptionEnum.SYSTEM_ERROR.getCode(), "签名失败");
        }
        res.setPaySign(signature);
        res.setOrderNo(orderNo);
        return res;
    }

    public Page<OmsOrder> getOrderList(OrderListReq req) {
        LambdaQueryWrapper<OmsOrder> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OmsOrder::getUserId,req.getUserId()).eq(OmsOrder::getDeleteStatus, 0);
        queryWrapper.orderByDesc(OmsOrder::getId);
        return omsOrderServiceImpl.page(new Page<>(req.getPage(), req.getPageSize()), queryWrapper);
    }

    public OrderInfoRes getOrderInfo(OrderInfoReq req) {
        return null;
    }
}
