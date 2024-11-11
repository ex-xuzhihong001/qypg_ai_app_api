package com.rxy.qypg.common.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 职能配置表
 * </p>
 *
 * @author ${author}
 * @since 2023-05-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="ConfigIndustry对象", description="职能配置表")
public class ConfigIndustry implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "父类id")
    private Integer parentId;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "类型：1 ->行业、2 ->部门、3 ->职位")
    private Integer type;

    @ApiModelProperty(value = "0 标准、1 无仓储物流企业、2 无生产有设计企业、3 无设计企业、 4 无生产无设计企业")
    private Integer qyType;

    @ApiModelProperty(value = "状态 0 未生效 1 生效")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    private Date ctime;

    @ApiModelProperty(value = "修改时间")
    private Date mtime;

    @TableField(exist = false)
    @ApiModelProperty(value = "子类集合")
    private List<ConfigIndustry> childrenList;

}
