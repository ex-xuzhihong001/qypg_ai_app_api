package com.rxy.qypg.app.api.domain.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;



@Data
@EqualsAndHashCode
@ApiModel(value="IndustryListReq对象", description="查询行业、部门、职位")
public class IndustryListReq {
    @ApiModelProperty("类型：不传->全部， 1->行业、 2->部门、 3->职业 ")
    private Integer type;
}
