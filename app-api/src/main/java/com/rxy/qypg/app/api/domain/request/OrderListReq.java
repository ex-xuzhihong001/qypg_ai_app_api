package com.rxy.qypg.app.api.domain.request;

import com.rxy.qypg.common.pojo.vo.PageDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode
@ApiModel(value="OrderListReq对象", description="查询评估报告list")
public class OrderListReq extends PageDto {
    @ApiModelProperty("用户id")
    private Integer userId;
}
