package com.rxy.qypg.app.api.service.pay.wx.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum WxApiType {
	JSAPI_PAY("/v3/pay/transactions/jsapi","JSAPI支付下单"),
	ORDER_QUERY_BY_NO("/v3/pay/transactions/out-trade-no/%s?mchid=%s","查询订单"),
	CLOSE_ORDER_BY_NO("/v3/pay/transactions/out-trade-no/%s/close","关闭订单"),
	DOMESTIC_REFUNDS("/v3/refund/domestic/refunds","申请退款"),
	DOMESTIC_REFUNDS_QUERY("/v3/refund/domestic/refunds/%s","查询单笔退款"),
	TRADE_BILLS("/v3/bill/tradebill","申请交易账单"),
	FUND_FLOW_BILLS("/v3/bill/fundflowbill","申请资金账单");
	private final String type;
	private final String desc;
}
