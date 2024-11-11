package com.rxy.qypg.common.dao.entity;

import java.math.BigDecimal;
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
 * 评估报告商品表
 * </p>
 *
 * @author ${author}
 * @since 2023-05-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="SmsPgGoods对象", description="评估报告商品表")
public class SmsPgGoods implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "评估等级：0 普通、1 vip 、2 定制")
    private String level;

    @ApiModelProperty(value = "商品金额，0的话就直接生成无需支付")
    private BigDecimal amount;

    @ApiModelProperty(value = "商品标题")
    private String title;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "图片")
    private String imgUrl;

    @ApiModelProperty(value = "展示顺序")
    private Integer sort;

    @ApiModelProperty(value = "状态 0 无效 1 有效")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    private Date ctime;

    @ApiModelProperty(value = "修改时间")
    private Date mtime;


}
