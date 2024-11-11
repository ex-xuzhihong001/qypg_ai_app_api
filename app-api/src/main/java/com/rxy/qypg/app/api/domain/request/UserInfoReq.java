package com.rxy.qypg.app.api.domain.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode
public class UserInfoReq {
    @ApiModelProperty("微信openId")
    private String openId;
}
