package com.rxy.qypg.common.pojo.vo;

/**
 * 常用API返回对象接口
 * Created by pointsok on 2019/4/19.
 */
public interface IErrorCode {
    /**
     * 返回码
     */
    long getCode();

    /**
     * 返回信息
     */
    String getMessage();
}
