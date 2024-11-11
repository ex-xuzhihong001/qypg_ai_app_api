package com.rxy.qypg.app.api.domain.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode
@ApiModel(value="QuestionHistoryReq对象", description="查询历史问题请求")
public class QuestionHistoryReq {
    @ApiModelProperty("批次编号")
    private String batchNo;
    @ApiModelProperty("用户id")
    private Long userId;
}
