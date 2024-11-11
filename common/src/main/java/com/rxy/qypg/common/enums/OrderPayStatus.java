package com.rxy.qypg.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum OrderPayStatus {
    NO_PAY(0,"未支付"),
    SUCCESS(1,"支付成功,待抽卡"),
    CLOSED(2,"支付超时已关闭"),
    CANCEL(3,"用户已取消"),
    ACCEPT_REFUND(4,"退款受理中"),
    REFUND_PROCESSING(5,"退款中"),
    REFUND_SUCCESS(6,"已退款"),
    REFUND_ABNORMAL(7,"退款异常"),
    ;

    public static OrderPayStatus checkCanRefundStatus(Integer code){
        if(code==null){
            return null;
        }
        for (OrderPayStatus value : OrderPayStatus.values()) {
            if (value.getCode()==code && OrderPayStatus.SUCCESS==value) {
                 return value;
            }
        }
        return null;
    }

    private final int code;
    private final String type;
}
