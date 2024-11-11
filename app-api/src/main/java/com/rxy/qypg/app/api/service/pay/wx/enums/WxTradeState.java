package com.rxy.qypg.app.api.service.pay.wx.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum WxTradeState {
    SUCCESS("SUCCESS","支付成功"),
    NOTPAY("NOTPAY","未支付"),
    CLOSED("CLOSED","已关闭"),
    REFUND("REFUND","转入退款");
    private final String type;
    private final String desc;
}
