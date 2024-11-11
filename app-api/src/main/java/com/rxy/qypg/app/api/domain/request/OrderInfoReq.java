package com.rxy.qypg.app.api.domain.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;


@Data
@EqualsAndHashCode
@ApiModel(value="OrderInfoReq对象", description="评估报告详情请求")
public class OrderInfoReq {
    @ApiModelProperty("订单编号")
    private String orderNo;

}
