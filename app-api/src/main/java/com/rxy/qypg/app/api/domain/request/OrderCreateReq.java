package com.rxy.qypg.app.api.domain.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;


@Data
@EqualsAndHashCode
@ApiModel(value="OrderCreateReq对象", description="发起评估请求")
public class OrderCreateReq {
    @ApiModelProperty("问题批次编号")
    private String batchNo;
    @ApiModelProperty("用户id")
    private Long userId;
    @ApiModelProperty("评估产品Id")
    private Long goodId;
    @ApiModelProperty("实际支付金额")
    private BigDecimal amount;
    @ApiModelProperty("支付备注")
    private String note;
}
