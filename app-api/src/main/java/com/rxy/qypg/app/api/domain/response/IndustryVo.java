package com.rxy.qypg.app.api.domain.response;

import com.rxy.qypg.common.dao.entity.ConfigIndustry;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(value="IndustryVo对象", description="行业、职位、部门信息对象")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IndustryVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "类型:1->行业、 2->部门、 3->职位")
    private Integer type;

    @ApiModelProperty(value = "类型名称")
    private String name;

    @ApiModelProperty(value = "子类集合")
    private List<ConfigIndustry> list;

}
