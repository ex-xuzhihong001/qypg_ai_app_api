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
 * kv配置表
 * </p>
 *
 * @author ${author}
 * @since 2023-05-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="ConfigKv对象", description="kv配置表")
public class ConfigKv implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "key")
    private String configKey;

    @ApiModelProperty(value = "值")
    private String configValue;

    @ApiModelProperty(value = "描述")
    private String info;

    @ApiModelProperty(value = "状态 0 未生效 1 生效")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    private Date ctime;

    @ApiModelProperty(value = "修改时间")
    private Date mtime;


}
