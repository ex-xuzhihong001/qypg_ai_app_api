package com.rxy.qypg.app.api.domain.request;

import com.rxy.qypg.common.dao.entity.ConfigQuestion;
import com.rxy.qypg.common.dao.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;


@Data
@EqualsAndHashCode
@ApiModel(value="QuestionHistoryReq对象", description="查询下一个问题请求")
public class QuestionNextReq {
    @ApiModelProperty("当前题目Id")
    private Integer configQuestionId;
    @ApiModelProperty("当前问题得到分数 0,20,40,60,80,100")
    private Integer currentQuestionScore;
    @ApiModelProperty("问题维度类型")
    private Integer categoryType;
}
