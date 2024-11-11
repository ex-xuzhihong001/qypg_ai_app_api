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
 * 用户评估历史记录
 * </p>
 *
 * @author ${author}
 * @since 2023-05-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="UserEvaluationHistory对象", description="用户评估历史记录")
public class UserEvaluationHistory implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "用户id")
    private Integer userId;

    @ApiModelProperty(value = "订单编号")
    private String orderNo;

    @ApiModelProperty(value = "问题回答批次编号")
    private String batchNo;

    @ApiModelProperty(value = "评估等级 0 普通、1 vip 、2 定制")
    private Integer level;

    @ApiModelProperty(value = "问题类型：0 现状描述 、1 提升空间、 2 发展建议、 3 风险收益 4 推荐技术及工具 ")
    private String type;

    @ApiModelProperty(value = "问题标题")
    private String title;

    @ApiModelProperty(value = "问题固定内容")
    private String callContent;

    @ApiModelProperty(value = "返回评估内容")
    private String backContent;

    @ApiModelProperty(value = "状态:0 无效 1 有效 ")
    private Integer status;

    @ApiModelProperty(value = "描述")
    private String info;

    @ApiModelProperty(value = "uk:订单编号+问题类型")
    private String uk;

    @ApiModelProperty(value = "创建时间")
    private Date ctime;

    @ApiModelProperty(value = "修改时间")
    private Date mtime;


}
