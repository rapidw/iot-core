package io.rapidw.iotcore.common.entity.function;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.rapidw.iotcore.common.entity.ProductIdIncluded;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@TableName(value = "iotcore.`function`")
@ApiModel(value = "Function", description = "功能数据库实体")
public class Function extends ProductIdIncluded {
    /**
     * 功能ID
     */
    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value = "功能id")
    @JsonIgnore
    private Integer id;


    /**
     * 功能类型（1：属性 2：事件 3：服务）
     */
    @TableField(value = "`type`")
    @ApiModelProperty(value = "功能类型")
    private Type type;

    /**
     * 功能名称
     */
    @TableField(value = "`name`")
    @ApiModelProperty(value = "名称")
    private String name;

    /**
     * 标识符
     */
    @JsonProperty(value = "functionId")
    @TableField(value = "identifier")
    @ApiModelProperty(value = "标识符")
    private String identifier;

    /**
     * 描述
     */
    @TableField(value = "`desc`")
    @ApiModelProperty(value = "描述")
    private String desc;

    public enum Type {
        PROPERTY,
        EVENT,
        SERVICE;
    }
}