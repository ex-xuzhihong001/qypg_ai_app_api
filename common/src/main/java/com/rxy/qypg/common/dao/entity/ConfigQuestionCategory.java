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
 * 问题维度表
 * </p>
 *
 * @author ${author}
 * @since 2023-05-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="ConfigQuestionCategory对象", description="问题维度表")
public class ConfigQuestionCategory implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "维度名称")
    private String name;

    @ApiModelProperty(value = "维度类型： 0 组织战略 1 技术基础 2 仓储物流 3 采购销售 4 生产生产 5 设计设计 6 职能支持")
    private Integer categoryType;

    @ApiModelProperty(value = "企业类型：0 标准、1 无仓储物流企业、2 无生产有设计企业、3 无设计企业、 4 无生产无设计企业")
    private Integer qyType;

    @ApiModelProperty(value = "企业类型名称")
    private String qyTypeName;

    @ApiModelProperty(value = "权重_系数")
    private Integer weight;

    @ApiModelProperty(value = "状态 0 未生效 1 生效")
    private Integer status;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "创建时间")
    private Date ctime;

    @ApiModelProperty(value = "修改时间")
    private Date mtime;


}
