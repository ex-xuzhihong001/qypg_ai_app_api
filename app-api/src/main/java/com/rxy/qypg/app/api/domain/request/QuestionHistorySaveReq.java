package com.rxy.qypg.app.api.domain.request;

import com.rxy.qypg.common.dao.entity.ConfigQuestion;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;


@Data
@EqualsAndHashCode
@ApiModel(value="QuestionHistorySaveReq对象", description="保存历史问题请求")
public class QuestionHistorySaveReq {
    @ApiModelProperty("批次编号")
    private String batchNo;
    @ApiModelProperty("回答问题集合")
    private List<ConfigQuestion> questionList;
}
