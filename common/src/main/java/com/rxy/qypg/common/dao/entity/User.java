package com.rxy.qypg.common.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author ${author}
 * @since 2023-05-14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="User对象", description="用户表")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "微信open_id")
    private String openId;

    @ApiModelProperty(value = "区号")
    private String countryCode;

    @ApiModelProperty(value = "手机号")
    private String phoneNumber;

    @ApiModelProperty(value = "图像地址")
    @TableField("imgUrl")
    private String imgUrl;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "性别：0 未填 1 女 2 男")
    private Integer gender;

    @ApiModelProperty(value = "所属行业id")
    private Integer industryId;

    @ApiModelProperty(value = "所属行业名称")
    private String industryName;

    @ApiModelProperty(value = "企业名称：用户填写")
    private String qyName;

    @ApiModelProperty(value = "部门名称：选择")
    private String department;

    @ApiModelProperty(value = "职位：选择")
    private String position;

    @ApiModelProperty(value = "状态 0 未生效 1 生效")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    private Date ctime;

    @ApiModelProperty(value = "修改时间")
    private Date mtime;


}
