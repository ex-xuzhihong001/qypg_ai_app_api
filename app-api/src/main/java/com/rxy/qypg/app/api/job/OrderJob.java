package com.rxy.qypg.app.api.job;


import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


import java.util.Date;




@Component
public class OrderJob {

    Logger logger = LoggerFactory.getLogger(OrderJob.class);

    @Scheduled(initialDelay = 5000, fixedDelay = 30000)
    public void dealPayOrders() {
        try {
            /**查询到待支付的订单查询微信看看是否支付*/

            /**通过评估等级找到 发起ai的内容*/

            /**通过批次id查询到 获得的分数情况*/

            Date startTime = DateUtils.addDays(new Date(), -2);
            Date endTime = DateUtils.addMinutes(new Date(), -5);

        }catch (Exception e){
            logger.error("dealOrders error",e);
        }
    }

    @Scheduled(initialDelay = 5000, fixedDelay = 30000)
    public void dealBackOrders() {
        try {
            /**查询到待支付的订单查询微信看看是否支付*/

            /**通过评估等级找到 发起ai的内容*/

            /**通过批次id查询到 获得的分数情况*/

            Date startTime = DateUtils.addDays(new Date(), -2);
            Date endTime = DateUtils.addMinutes(new Date(), -5);

        }catch (Exception e){
            logger.error("dealOrders error",e);
        }
    }

    @Scheduled(initialDelay = 5000, fixedDelay = 30000)
    public void dealPayNoAiOrders() {
        try {
            /**查询到待支付的订单查询微信看看是否支付*/

            /**通过评估等级找到 发起ai的内容*/

            /**通过批次id查询到 获得的分数情况*/

            Date startTime = DateUtils.addDays(new Date(), -2);
            Date endTime = DateUtils.addMinutes(new Date(), -5);

        }catch (Exception e){
            logger.error("dealOrders error",e);
        }
    }

    @Scheduled(initialDelay = 5000, fixedDelay = 30000)
    public void dealPayAiFailOrders() {
        try {
            /**查询到待支付的订单查询微信看看是否支付*/

            /**通过评估等级找到 发起ai的内容*/

            /**通过批次id查询到 获得的分数情况*/

            Date startTime = DateUtils.addDays(new Date(), -2);
            Date endTime = DateUtils.addMinutes(new Date(), -5);

        }catch (Exception e){
            logger.error("dealOrders error",e);
        }
    }



}
