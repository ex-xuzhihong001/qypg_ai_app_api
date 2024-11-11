package com.rxy.qypg.app.api.domain.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode
public class UserLoginReq {
    @ApiModelProperty("微信登录code")
    private String code;
    @ApiModelProperty("调用微信服务获取用户手机号使用code")
    private String phoneCode;
}
