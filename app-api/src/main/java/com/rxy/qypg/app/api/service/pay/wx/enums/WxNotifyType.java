package com.rxy.qypg.app.api.service.pay.wx.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum WxNotifyType {
	JSAPI_NOTIFY("/wx/v1/pay/notify","支付通知"),
	REFUND_NOTIFY("/wx/v1/refunds/notify","退款结果通知");
	private final String type;
	private final String desc;
}
