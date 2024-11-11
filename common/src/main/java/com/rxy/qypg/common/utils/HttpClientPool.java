package com.rxy.qypg.common.utils;

import com.alibaba.fastjson.JSON;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Commons HttpClient项目现已结束，不再开发。
 * 它已被其HttpClient和HttpCore模块中的Apache HttpComponents项目所取代，它们提供更好的性能和更大的灵活性。
 */
public class HttpClientPool {
    private static final Logger logger = LoggerFactory.getLogger(HttpClientPool.class);


    private static final int timeout = 3000;
    private static final int maxConnTotal = 300;
    private static final int maxConnPerRoute = 150;

    private static final PoolingHttpClientConnectionManager connectionManager;

    public  static List<Header> defaultHeaders = Collections.singletonList(new BasicHeader("Content-Type", "application/json;charset=UTF-8"));

    static {
        connectionManager = new PoolingHttpClientConnectionManager(30000, TimeUnit.MILLISECONDS);
        connectionManager.setMaxTotal(maxConnTotal);
        connectionManager.setDefaultMaxPerRoute(maxConnPerRoute);
    }


    /**连接池信息：
     * ConnectionRequestTimeout:从连接池中获取连接的超时时间
     * ConnectTimeout:与服务器连接超时时间：httpclient会创建一个异步线程用以创建socket连接，此处设置该socket的连接超时时间
     * SocketTimeout:socket读数据超时时间：从服务器获取响应数据的超时时间
     * */
    private static CloseableHttpClient getHttpClient(){
        RequestConfig config = RequestConfig.custom().setConnectionRequestTimeout(timeout).setConnectTimeout(timeout).setSocketTimeout(timeout).build();
        return HttpClients.custom().setConnectionManager(connectionManager).setDefaultRequestConfig(config).build();
    }


    public static <T> T sendPostRequest(String url, String data, List<Header> headers, Class<T> resType) {
        try {
            HttpResponse response;
            String result;
            HttpPost post = new HttpPost(url);
            if(!CollectionUtils.isEmpty(headers)){
                post.setHeaders(headers.toArray(new Header[0]));
            }
            if(data!=null){
                post.setEntity(new StringEntity(data));
            }
            response =  getHttpClient().execute(post);
            if(response!=null && response.getStatusLine().getStatusCode()==200){
                result = EntityUtils.toString(response.getEntity(),"UTF-8");
                return JSON.parseObject(result,resType);
            }else {
                logger.info("sendPostRequest error url={},data={},response={}", url, data, response);
            }
        }catch (Exception e){
            logger.error("sendPostRequest url={} error e", url, e);
        }
        return null;
    }

    public static <T> T sendGetRequest(String url, List<Header> headers, Class<T> resType) {
        try {
            HttpResponse response;
            String result;
            HttpGet post = new HttpGet(url);
            if(!CollectionUtils.isEmpty(headers)){
                post.setHeaders(headers.toArray(new Header[0]));
            }
            response =  getHttpClient().execute(post);
            if(response!=null && response.getStatusLine().getStatusCode()==200){
                result = EntityUtils.toString(response.getEntity(),"UTF-8");
                return JSON.parseObject(result,resType);
            }else {
                logger.info("sendGetRequest error url={},response={}", url,  response);
            }
        }catch (Exception e){
            logger.error("sendGetRequest url={} error e", url, e);
        }
        return null;
    }


    public static String sendPostRequest(String url, List<Header> headers, UrlEncodedFormEntity formEntity) {
        try {
            HttpResponse response;
            HttpPost post = new HttpPost(url);
            if(!CollectionUtils.isEmpty(headers)){
                post.setHeaders(headers.toArray(new Header[0]));
            }
            post.setEntity(formEntity);
            response =  getHttpClient().execute(post);
            if(response!=null && response.getStatusLine().getStatusCode()==200){
                return EntityUtils.toString(response.getEntity(),"UTF-8");
            }else {
                logger.info("sendPostRequest error url={},data={},response={}", url,formEntity, response);
            }
        }catch (Exception e){
            logger.error("sendPostRequest url={} error e", url, e);
        }
        return null;
    }


}

