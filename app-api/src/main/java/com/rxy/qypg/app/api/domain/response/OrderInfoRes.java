package com.rxy.qypg.app.api.domain.response;

import com.rxy.qypg.common.dao.entity.UserEvaluationHistory;
import com.rxy.qypg.common.dao.entity.UserScore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.List;


@Data
@EqualsAndHashCode
@ApiModel(value="OrderInfoRes对象", description="评估报告详情返回")
public class OrderInfoRes {
    @ApiModelProperty("返回评估内容")
    private List<UserEvaluationHistory> userEvaluationHistoryList;
    @ApiModelProperty("问题维度分数")
    private List<UserScore> userScoreList;
    @ApiModelProperty("总分")
    private BigDecimal totalScore;
}
