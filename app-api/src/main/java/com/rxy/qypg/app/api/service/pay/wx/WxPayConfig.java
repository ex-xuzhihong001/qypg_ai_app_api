package com.rxy.qypg.app.api.service.pay.wx;

import com.wechat.pay.contrib.apache.httpclient.WechatPayHttpClientBuilder;
import com.wechat.pay.contrib.apache.httpclient.auth.PrivateKeySigner;
import com.wechat.pay.contrib.apache.httpclient.auth.ScheduledUpdateCertificatesVerifier;
import com.wechat.pay.contrib.apache.httpclient.auth.WechatPay2Credentials;
import com.wechat.pay.contrib.apache.httpclient.auth.WechatPay2Validator;
import com.wechat.pay.contrib.apache.httpclient.util.PemUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.util.concurrent.TimeUnit;


@Configuration
@PropertySource("classpath:wx_pay.properties")
@ConfigurationProperties(prefix = "wx-pay")
@Data
@Slf4j
public class WxPayConfig {

    // 商户号
    private String mchId;
    // 商户API证书序列号
    private String mchSerialNo;
    // 商户私钥文件
    private String privateKeyPath;
    // APIv3密钥
    private String apiV3Key;
    // APPID
    private String appid;
    // 微信服务器地址
    private String domain;
    // 接收结果通知地址
    private String notifyDomain;

    /**获取商户的私钥文件*/
    public PrivateKey getPrivateKey(String filename) {
        try {
            ClassPathResource resource = new ClassPathResource(filename);
            InputStream inputStream = resource.getInputStream();
            return PemUtil.loadPrivateKey(inputStream);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("私钥文件不存在", e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**获取签名验证器*/
    @Bean
    public ScheduledUpdateCertificatesVerifier getVerifier() {
        //获取商户私钥
        PrivateKey privateKey = getPrivateKey(privateKeyPath);
        //私钥签名对象
        PrivateKeySigner privateKeySigner = new PrivateKeySigner(mchSerialNo, privateKey);
        //身份认证对象
        WechatPay2Credentials wechatPay2Credentials = new WechatPay2Credentials(mchId, privateKeySigner);
        // 使用定时更新的签名验证器，不需要传入证书
        return new ScheduledUpdateCertificatesVerifier(wechatPay2Credentials,apiV3Key.getBytes(StandardCharsets.UTF_8));
    }


    private static final int timeout = 3000;
    private static final int maxConnTotal = 300;
    private static final int maxConnPerRoute = 150;
    private static final PoolingHttpClientConnectionManager connectionManager;
    static {
        connectionManager = new PoolingHttpClientConnectionManager(30000, TimeUnit.MILLISECONDS);
        connectionManager.setMaxTotal(maxConnTotal);
        connectionManager.setDefaultMaxPerRoute(maxConnPerRoute);
    }


    /**获取http请求对象*/
    @Bean(name = "wxPayClient")
    public CloseableHttpClient getWxPayClient(ScheduledUpdateCertificatesVerifier verifier) {
        //获取商户私钥
        PrivateKey privateKey = getPrivateKey(privateKeyPath);
        //处理签名和验签，并进行证书自动更新
        WechatPayHttpClientBuilder builder = WechatPayHttpClientBuilder.create().withMerchant(mchId, mchSerialNo, privateKey)
                .withValidator(new WechatPay2Validator(verifier));

        // 可以通过builder设置各种参数，来配置你的HttpClient
        RequestConfig config = RequestConfig.custom().setConnectionRequestTimeout(timeout).setConnectTimeout(timeout).setSocketTimeout(timeout).build();
        builder.setConnectionManager(connectionManager).setDefaultRequestConfig(config);

        return builder.build();
    }

    /**获取HttpClient，无需进行应答签名验证，跳过验签的流程 */
    @Bean(name = "wxPayNoSignClient")
    public CloseableHttpClient getWxPayNoSignClient() {
        //获取商户私钥
        PrivateKey privateKey = getPrivateKey(privateKeyPath);
        //用于构造HttpClient 无需进行签名验证、通过withValidator((response) -> true)实现
        WechatPayHttpClientBuilder builder = WechatPayHttpClientBuilder.create().withMerchant(mchId, mchSerialNo, privateKey)
                .withValidator((response) -> true);
        return builder.build();
    }

}
