package io.rapidw.iotcore.common.entity.field;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.rapidw.iotcore.common.entity.ProductIdIncluded;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@TableName(value = "iotcore.field_string")
@ApiModel(description = "FieldString数据库实体")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FieldString extends ProductIdIncluded {
    @TableId(value = "id", type = IdType.INPUT)
    @JsonIgnore
    private Integer id;

    /**
     * 功能数据ID
     */
    @TableField(value = "field_id")
    @JsonIgnore
    private Integer fieldId;

    /**
     * 长度
     */
    @TableField(value = "`length`")
    @ApiModelProperty(value = "长度")
    private Integer length;
}