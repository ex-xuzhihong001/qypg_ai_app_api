package com.rxy.qypg.app.api.service.pay.wx;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;

import com.rxy.qypg.app.api.service.pay.wx.enums.WxApiType;
import com.rxy.qypg.app.api.service.pay.wx.enums.WxNotifyType;
import com.rxy.qypg.app.api.service.pay.wx.enums.WxTradeState;
import com.rxy.qypg.common.enums.InnerExceptionEnum;
import com.rxy.qypg.common.exceptions.InnerException;
import com.wechat.pay.contrib.apache.httpclient.auth.Verifier;
import com.wechat.pay.contrib.apache.httpclient.util.AesUtil;
import org.apache.commons.collections.MapUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.PrivateKey;
import java.security.Signature;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Service
public class WxPayService {

    Logger logger = LoggerFactory.getLogger(WxPayService.class);

    @Resource
    private WxPayConfig wxPayConfig;
    @Resource
    private Verifier verifier;
    @Resource
    private CloseableHttpClient wxPayClient;
    @Resource
    private CloseableHttpClient wxPayNoSignClient;


    public String sign(byte[] message) {
        Signature sign;
        try {
            sign = Signature.getInstance("SHA256withRSA");
            PrivateKey privateKey = wxPayConfig.getPrivateKey(wxPayConfig.getPrivateKeyPath());
            sign.initSign(privateKey);
            sign.update(message);
            return Base64.getEncoder().encodeToString(sign.sign());
        } catch (Exception e) {
            throw new InnerException(InnerExceptionEnum.SYSTEM_ERROR.getCode(), "签名失败");
        }
    }

    /**支付下单*/
    public String getPrepayId(String desc, Integer total, String orderNo, String openId) {
        Map<String, Object> paramsMap = buildPayParamMap(desc,total,orderNo,openId);
        logger.error("支付下单参数={} " ,JSON.toJSONString(paramsMap));
        String url = wxPayConfig.getDomain().concat(WxApiType.JSAPI_PAY.getType());
        try (CloseableHttpResponse response = postExecute(url, JSON.toJSONString(paramsMap))) {
            String bodyAsString = EntityUtils.toString(response.getEntity());
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                logger.info("下单失败,响应码 = " + statusCode + ",返回结果 = " + bodyAsString);
                throw new InnerException(InnerExceptionEnum.SYSTEM_ERROR.getCode(), "微信支付响应失败:"+bodyAsString);
            }
            Map<String, String> resultMap = JSON.parseObject(bodyAsString, new TypeReference<Map<String, String>>() {
            });
            return MapUtils.getString(resultMap, "prepay_id");
        } catch (InnerException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**支付通知*/
    public void payNotify(HttpServletRequest request) throws Exception {
        String body = HttpUtils.readData(request);
        Map<String, Object> bodyMap = JSON.parseObject(body, new TypeReference<Map<String, Object>>() {});
        String requestId = MapUtils.getString(bodyMap, "id");
        /**签名的验证*/
        WechatPay2ValidatorForRequest wechatPay2ValidatorForRequest = new WechatPay2ValidatorForRequest(verifier, requestId, body);
        if (!wechatPay2ValidatorForRequest.validate(request)) {
            throw new InnerException(InnerExceptionEnum.OPT_FAIL.getCode(), "通知验签失败");
        }
        String orderNo = "";
        try{
            orderNo = savePayDetail(bodyMap);
        } catch (DuplicateKeyException e) {
            logger.info("订单已保存 : {}", orderNo);
        }

        /**调用chatgpt*/


    }


    /**用户取消*/
    public void closeOrder(String orderNo) throws Exception {
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("mchid", wxPayConfig.getMchId());
        String url = wxPayConfig.getDomain().concat(String.format(WxApiType.CLOSE_ORDER_BY_NO.getType(), orderNo));
        try (CloseableHttpResponse response = postExecute(url, JSON.toJSONString(paramsMap))) {
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200 && statusCode != 204) {
                logger.info("关闭订单失败,响应码 = " + statusCode);
                throw new InnerException(InnerExceptionEnum.SYSTEM_ERROR.getCode(), "关闭订单失败");
            }
            /*orderEventPublisher.publish(orderNo, OrderPayStatus.CANCEL);*/
        }
    }

    /**补偿检查*/
    @Transactional(rollbackFor = Exception.class)
    public void checkOrderStatus(String orderNo) throws Exception {
        String url = wxPayConfig.getDomain().concat(String.format(WxApiType.ORDER_QUERY_BY_NO.getType(), orderNo, wxPayConfig.getMchId()));
        try (CloseableHttpResponse response = getExecute(url, true)) {
            String plainText = EntityUtils.toString(response.getEntity());
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                logger.info("查单接口调用,响应码 = " + statusCode + ",返回结果 = " + plainText);
                throw new InnerException(InnerExceptionEnum.SYSTEM_ERROR.getCode(), "查单接口调用失败:"+orderNo);
            }
            Map<String, Object> resultMap = JSON.parseObject(plainText, new TypeReference<Map<String, Object>>() {
            });

            //获取微信支付端的订单状态
            String tradeState = (String) resultMap.get("trade_state");
            if (WxTradeState.CLOSED.getType().equals(tradeState)) {
                /**订单关闭*/
                return;
            }
            String transactionId = (String) resultMap.get("transaction_id"); //业务编号


            //判断订单状态
            if (WxTradeState.SUCCESS.getType().equals(tradeState)) {
                String tradeType = (String) resultMap.get("trade_type"); //支付类型
                Map<String, Object> amount = (Map) resultMap.get("amount"); //用户实际支付金额
                Integer payerTotal = (Integer)amount.get("total");

            }

            if (WxTradeState.NOTPAY.getType().equals(tradeState)) {
                this.closeOrder(orderNo);



            }
        }
    }

    /**====================申请退款====================================*/

    /**申请退款需要退卡*/
    @Transactional(rollbackFor = Exception.class)
    public void refund(String orderNo, String reason) throws Exception {
        /**生成退款订单*/
        String refundNo = "R_"+IdWorker.getId();



        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("out_trade_no", orderNo);//订单编号
        paramsMap.put("out_refund_no", refundNo);//退款单编号
        paramsMap.put("reason", reason);//退款原因
        paramsMap.put("notify_url", wxPayConfig.getNotifyDomain().concat(WxNotifyType.REFUND_NOTIFY.getType()));//退款通知地址
        Map<String, Object> amountMap = new HashMap<>();
        amountMap.put("refund", "");//退款金额
        amountMap.put("total", "");//原订单金额
        amountMap.put("currency", "CNY");//退款币种
        paramsMap.put("amount", amountMap);
        String url = wxPayConfig.getDomain().concat(WxApiType.DOMESTIC_REFUNDS.getType());
        try (CloseableHttpResponse response = postExecute(url, JSON.toJSONString(paramsMap))) {
            String bodyAsString = EntityUtils.toString(response.getEntity());
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200 && statusCode != 204) {
                throw new InnerException(InnerExceptionEnum.SYSTEM_ERROR.getCode(), "退款异常, 响应码 = " + statusCode + ", 退款返回结果 = " + bodyAsString + ";订单编号="+orderNo);
            }
            //新增退款订单
            Map<String, Object> resultMap = JSON.parseObject(bodyAsString, new TypeReference<Map<String, Object>>() {
            });

        }
    }


    /**退款成功通知*/
    public void refundsNotify(HttpServletRequest request) throws Exception {
        String body = HttpUtils.readData(request);
        Map<String, Object> bodyMap = JSON.parseObject(body, new TypeReference<Map<String, Object>>() {
        });
        String requestId = (String) bodyMap.get("id");

        //签名的验证
        WechatPay2ValidatorForRequest wechatPay2ValidatorForRequest = new WechatPay2ValidatorForRequest(verifier, requestId, body);
        if (!wechatPay2ValidatorForRequest.validate(request)) {
            throw new InnerException(InnerExceptionEnum.OPT_FAIL.getCode(), "通知验签失败");
        }

        String plainText = decryptFromResource(bodyMap);
        Map<String, Object> resultMap = JSON.parseObject(plainText, new TypeReference<Map<String, Object>>() {
        });
        logger.info("收到退款通知 refundsNotify plainText={} ", plainText);


    }




    /**检查退款订单*/
    @Transactional(rollbackFor = Exception.class)
    public void checkRefundStatus(String orderNum) throws Exception {


        String url = wxPayConfig.getDomain().concat(String.format(WxApiType.DOMESTIC_REFUNDS_QUERY.getType(), "订单号"));
        try (CloseableHttpResponse response = getExecute(url, true)) {
            String plainText = EntityUtils.toString(response.getEntity());
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                throw new InnerException(InnerExceptionEnum.SYSTEM_ERROR.getCode(), "查询退款异常, 响应码 = " + statusCode + ", 查询退款返回结果 = " + plainText);
            }

            Map<String, String> resultMap = JSON.parseObject(plainText, new TypeReference<Map<String, String>>() {
            });

            String status = resultMap.get("status");
            String orderNo = resultMap.get("out_trade_no");


        }
    }


    /**====================下载账单=============================================*/

    /**下载账单*/
    public String downLoadBill(String billDate, String type) throws Exception {
        String downloadUrl = this.queryBill(billDate, type);
        try (CloseableHttpResponse response = getExecute(downloadUrl, false)) {
            String bodyAsString = EntityUtils.toString(response.getEntity());
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                throw new InnerException(InnerExceptionEnum.SYSTEM_ERROR.getCode(), "下载账单异常, 响应码 = " + statusCode + ", 下载账单返回结果 = " + bodyAsString);
            }
            return bodyAsString;
        }
    }

    /**申请账单*/
    public String queryBill(String billDate, String type) throws Exception {
        logger.warn("申请账单接口调用 {}", billDate);
        String url = "";
        if (WxApiType.TRADE_BILLS.name().equalsIgnoreCase(type)) {
            url = WxApiType.TRADE_BILLS.getType();
        } else if (WxApiType.FUND_FLOW_BILLS.name().equalsIgnoreCase(type)) {
            url = WxApiType.FUND_FLOW_BILLS.getType();
        } else {
            throw new RuntimeException("不支持的账单类型");
        }
        url = wxPayConfig.getDomain().concat(url).concat("?bill_date=").concat(billDate);
        try (CloseableHttpResponse response = getExecute(url, true)) {
            String bodyAsString = EntityUtils.toString(response.getEntity());
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                throw new InnerException(InnerExceptionEnum.SYSTEM_ERROR.getCode(), "申请账单异常, 响应码 = " + statusCode + ", 申请账单返回结果 = " + bodyAsString);
            }
            Map<String, Object> resultMap = JSON.parseObject(bodyAsString, new TypeReference<Map<String, Object>>() {
            });
            return MapUtils.getString(resultMap, "download_url");
        }
    }


    public CloseableHttpResponse postExecute(String url, String data) throws IOException {
        HttpPost httpPost = new HttpPost(url);
        StringEntity entity = new StringEntity(data, "utf-8");
        entity.setContentType("application/json");
        httpPost.setEntity(entity);
        httpPost.setHeader("Accept", "application/json");
        return wxPayClient.execute(httpPost);
    }

    public CloseableHttpResponse getExecute(String url, boolean needSign) throws IOException {
        HttpGet httpGet = new HttpGet(url);
        httpGet.addHeader("Accept", "application/json");
        if (needSign) {
            return wxPayClient.execute(httpGet);
        } else {
            return wxPayNoSignClient.execute(httpGet);
        }
    }

    private String savePayDetail(Map<String, Object> bodyMap) throws GeneralSecurityException {
        //解密报文
        String plainText = decryptFromResource(bodyMap);
        logger.info("收到通知支付成通知 : {}", plainText);
        Map<String, Object> plainTextMap = JSON.parseObject(plainText, new TypeReference<Map<String, Object>>() {
        });
        String orderNo = (String) plainTextMap.get("out_trade_no"); //订单号
        String transactionId = (String) plainTextMap.get("transaction_id"); //业务编号
        String tradeType = (String) plainTextMap.get("trade_type"); //支付类型
        String tradeState = (String) plainTextMap.get("trade_state"); //交易状态
        Map<String, Object> amount = MapUtils.getMap(plainTextMap,"amount"); //用户实际支付金额
        Integer payerTotal = (Integer)amount.get("total");
        Date date = new Date();
       /**保存支付明细*/
        return orderNo;
    }

    private Map<String, Object> buildPayParamMap(String desc, Integer total, String orderNo, String openId) {
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("appid", wxPayConfig.getAppid());
        paramsMap.put("mchid", wxPayConfig.getMchId());
        paramsMap.put("notify_url", wxPayConfig.getNotifyDomain().concat(WxNotifyType.JSAPI_NOTIFY.getType()));
        paramsMap.put("description", desc);//"商品描述"
        paramsMap.put("out_trade_no", orderNo);//"商户系统内部订单号，只能是数字、大小写字母_-*且在同一个商户号下唯一"
        Map<String, Object> amountMap = new HashMap<>();
        amountMap.put("total", total);//支付金额（分）
        amountMap.put("currency", "CNY");
        paramsMap.put("amount", amountMap);
        Map<String, Object> payer = new HashMap<>();
        payer.put("openid", openId);//"用户在公众号内的身份标识，不同公众号拥有不同的openid"
        paramsMap.put("payer", payer);
        return paramsMap;
    }

    /**对称解密*/
    private String decryptFromResource(Map<String, Object> bodyMap) throws GeneralSecurityException {
        //通知数据
        Map<String, String> resourceMap = (Map) bodyMap.get("resource");
        //数据密文
        String cipherText = resourceMap.get("ciphertext");
        //随机串
        String nonce = resourceMap.get("nonce");
        //附加数据
        String associatedData = resourceMap.get("associated_data");
        AesUtil aesUtil = new AesUtil(wxPayConfig.getApiV3Key().getBytes(StandardCharsets.UTF_8));
        return aesUtil.decryptToString(associatedData.getBytes(StandardCharsets.UTF_8), nonce.getBytes(StandardCharsets.UTF_8), cipherText);
    }


}
