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
 * 用户答题历史表
 * </p>
 *
 * @author ${author}
 * @since 2023-05-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="UserQuestionHistory对象", description="用户答题历史表")
public class UserQuestionHistory implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "用户id")
    private Integer userId;

    @ApiModelProperty(value = "问题回答批次编号")
    private String batchNo;

    @ApiModelProperty(value = "问题维度id")
    private Integer categoryId;

    @ApiModelProperty(value = "问题id")
    private Integer questionId;

    @ApiModelProperty(value = "答案")
    private String answer;

    @ApiModelProperty(value = "问题标题")
    private String title;

    @ApiModelProperty(value = "问题类型：0 问答题、1 单选题【判断题】、2 多选题")
    private Integer type;

    @ApiModelProperty(value = "得分")
    private Integer score;

    @ApiModelProperty(value = "选项")
    private String options;

    @ApiModelProperty(value = "状态 0 无效 1 有效")
    private Integer status;

    @ApiModelProperty(value = "uk:批次id+问题id")
    private String uk;

    @ApiModelProperty(value = "创建时间")
    private Date ctime;

    @ApiModelProperty(value = "修改时间")
    private Date mtime;


}
