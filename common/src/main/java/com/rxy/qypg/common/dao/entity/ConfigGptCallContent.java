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
 * chat_gpt调用内容配置表
 * </p>
 *
 * @author ${author}
 * @since 2023-05-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="ConfigGptCallContent对象", description="chat_gpt调用内容配置表")
public class ConfigGptCallContent implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "评估等级：0 普通、1 vip 、2 定制")
    private String level;

    @ApiModelProperty(value = "问题类型：0 现状描述 、1 提升空间、 2 发展建议、 3 风险收益 4 推荐技术及工具 ")
    private String type;

    @ApiModelProperty(value = "问题标题")
    private String title;

    @ApiModelProperty(value = "问题固定内容")
    private String callContent;

    @ApiModelProperty(value = "限制内容：如返回数量、默认语言等")
    private String limitContent;

    @ApiModelProperty(value = "动态参数：行业、总分")
    private String dynamicParams;

    @ApiModelProperty(value = "请求顺序")
    private Integer sort;

    @ApiModelProperty(value = "状态 0 无效 1 有效")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    private Date ctime;

    @ApiModelProperty(value = "修改时间")
    private Date mtime;


}
