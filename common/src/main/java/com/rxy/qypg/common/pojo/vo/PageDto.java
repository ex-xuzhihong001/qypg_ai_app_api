package com.rxy.qypg.common.pojo.vo;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PageDto {
    @ApiModelProperty(value = "page")
    private int page = 1;
    @ApiModelProperty(value = "pageSize")
    private int pageSize = 10;
}


