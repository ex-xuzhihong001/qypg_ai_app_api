package com.rxy.qypg.common.utils;


import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**日期工具类 */
public class DateUtil {

    private static final Logger logger = LoggerFactory.getLogger(DateUtil.class);


    public final static String yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";


    public static Date getDate() {
        return Calendar.getInstance().getTime();
    }

    /**判断thisDate 与 thatDate 大小关系*/
    public static boolean greaterThan(Date thisDate, Date thatDate) {
        return compare(thisDate, thatDate) > 0;
    }

    /**比较两个日期的大小关系*/
    public static int compare(Date thisDate, Date thatDate) {
        return Long.compare(thisDate.getTime(), thatDate.getTime());
    }

    /**Date 转 String*/
    public static String formatDate(Date date) {
    	if(date==null) {
    		return "";
    	}
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(date);
    }



    public static String formatDate(Date date, String pattern) {
        DateFormat df = new SimpleDateFormat(pattern);
        return df.format(date);
    }


    /** String 转  Date*/
    public static Date parseDate(String dateTime) {
        try {
            if(StringUtils.isEmpty(dateTime)){
                logger.error("日期参数错误，错误信息为：{}", dateTime);
                return null;
            }
            return DateUtils.parseDate(dateTime, new String[]{
                    "yyyy-MM-dd HH:mm:ss",
                    "yyyy-MM-ddHH:mm:ss",
                    "yyyy/MM/dd HH:mm:ss",
                    "yyyy-MM-dd HH:mm",
                    "yyyy/MM/dd HH:mm",
                    "yyyy-MM-dd",
                    "yyyyMMdd",
                    "yyyy年MM月dd日",
                    "yyyy/MM/dd"});
        } catch (Exception e1) {
            logger.error("日期参数错误，日期={}，错误信息为：{}",dateTime, e1.getMessage());
            return null;
        }
    }

    public static String getDateTime() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(new Date());
    }

}

