package com.rxy.qypg.common.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 问题配置表
 * </p>
 *
 * @author ${author}
 * @since 2023-05-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="ConfigQuestion对象", description="问题配置表")
public class ConfigQuestion implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "父类id")
    private Integer parentId;

    @ApiModelProperty(value = "维度类型")
    private Integer categoryType;

    @ApiModelProperty(value = "维度名称")
    private Integer categoryName;

    @ApiModelProperty(value = "问题名称")
    private String questionName;

    @ApiModelProperty(value = "问题触发类型：0 分数触发、1 必答题")
    private Integer triggerType;

    @ApiModelProperty(value = "json字段：分数、其他")
    private String triggerCondition;

    @ApiModelProperty(value = "出现顺序")
    private Integer sort;

    @ApiModelProperty(value = "分数")
    private Integer score;

    @ApiModelProperty(value = "权重_系数")
    private Integer weight;

    @ApiModelProperty(value = "问题标题")
    private String title;

    @ApiModelProperty(value = "问题类型：0 问答题、1 单选题【判断题】、2 多选题 目前只支持单选")
    private Integer type;

    @ApiModelProperty(value = "选项内容，目前前端写死5档")
    private String options;

    @ApiModelProperty(value = "状态 0 未发布、1 发布")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    private Date ctime;

    @ApiModelProperty(value = "修改时间")
    private Date mtime;


}
