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
 * 支付记录表
 * </p>
 *
 * @author ${author}
 * @since 2023-05-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="OmsPayHistory对象", description="支付记录表")
public class OmsPayHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "支付记录id")
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "商户订单编号")
    private String orderNo;

    @ApiModelProperty(value = "支付系统交易编号")
    private String transactionId;

    @ApiModelProperty(value = "支付方式：0->未支付；1->支付宝；2->微信")
    private Integer payType;

    @ApiModelProperty(value = "交易类型：小程序支付")
    private String tradeType;

    @ApiModelProperty(value = "交易状态：待支付、支付成功、支付失败、超时未支付、订单关闭")
    private String tradeState;

    @ApiModelProperty(value = "支付金额(分)")
    private Integer payerTotal;

    @ApiModelProperty(value = "通知参数")
    private String content;

    @ApiModelProperty(value = "创建时间")
    private Date ctime;

    @ApiModelProperty(value = "修改时间")
    private Date mtime;


}
