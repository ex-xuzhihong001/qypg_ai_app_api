package com.rxy.qypg.app.api.domain.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value="IndustryVo对象", description="行业、职位、部门信息对象")
public class OrderCreateRes implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("订单号")
    private String orderNo;
    @ApiModelProperty("appId")
    private String appId;
    @ApiModelProperty("timeStamp")
    private String timeStamp;
    @ApiModelProperty("nonceStr")
    private String nonceStr;
    @ApiModelProperty("package")
    private String packageStr;
    @ApiModelProperty("signType")
    private String signType;
    @ApiModelProperty("paySign")
    private String paySign;

}
