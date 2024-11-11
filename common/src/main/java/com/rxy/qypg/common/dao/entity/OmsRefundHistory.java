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
 * 退款记录表
 * </p>
 *
 * @author ${author}
 * @since 2023-05-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="OmsRefundHistory对象", description="退款记录表")
public class OmsRefundHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "款单id")
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "订单编号")
    private String orderNo;

    @ApiModelProperty(value = "商户退款单编号")
    private String refundNo;

    @ApiModelProperty(value = "支付系统退款单号")
    private String refundId;

    @ApiModelProperty(value = "原订单金额(分)")
    private Integer totalFee;

    @ApiModelProperty(value = "退款金额(分)")
    private Integer refund;

    @ApiModelProperty(value = "退款原因")
    private String reason;

    @ApiModelProperty(value = "退款状态：待退款、退款成功、退款异常")
    private String refundStatus;

    @ApiModelProperty(value = "申请退款返回参数")
    private String contentReturn;

    @ApiModelProperty(value = "退款结果通知参数")
    private String contentNotify;

    @ApiModelProperty(value = "创建时间")
    private Date ctime;

    @ApiModelProperty(value = "修改时间")
    private Date mtime;


}
