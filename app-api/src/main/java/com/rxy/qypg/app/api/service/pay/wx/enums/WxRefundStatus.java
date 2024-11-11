package com.rxy.qypg.app.api.service.pay.wx.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum WxRefundStatus {
    SUCCESS("SUCCESS","退款成功"),
    CLOSED("CLOSED","退款关闭"),
    PROCESSING("PROCESSING","退款处理中"),
    ABNORMAL("ABNORMAL","退款异常");
    private final String type;
    private final String desc;
}
