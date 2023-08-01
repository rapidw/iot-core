package io.rapidw.iotcore.common.entity.field;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.rapidw.iotcore.common.entity.ProductIdIncluded;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@TableName(value = "iotcore.field_double")
@ApiModel(description = "FieldDouble数据库实体")
public class FieldDouble extends ProductIdIncluded {
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
     * 最大值
     */
    @TableField(value = "`max`")
    @ApiModelProperty(value = "最大值")
    private Double max;

    /**
     * 最小值
     */
    @TableField(value = "`min`")
    @ApiModelProperty(value = "最小值")
    private Double min;

    /**
     * 步长
     */
    @TableField(value = "step")
    @ApiModelProperty(value = "步长")
    private Double step;

    /**
     * 单位
     */
    @TableField(value = "unit")
    @ApiModelProperty(value = "单位")
    private String unit;
}