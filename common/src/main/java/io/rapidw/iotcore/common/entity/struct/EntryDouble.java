package io.rapidw.iotcore.common.entity.struct;

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
@TableName(value = "iotcore.entry_double")
@ApiModel(description = "StructEntryDouble返回体")
public class EntryDouble extends ProductIdIncluded {
    @TableId(value = "id", type = IdType.INPUT)
    @JsonIgnore
    private Integer id;

    @TableField(value = "entry_id")
    @ApiModelProperty(value = "entry_id")
    @JsonIgnore
    private Integer entryId;

    /**
     * 最大值
     */
    @ApiModelProperty(value = "最大值")
    @TableField(value = "`max`")
    private Double max;

    /**
     * 最小值
     */
    @ApiModelProperty(value = "最小值")
    @TableField(value = "`min`")
    private Double min;

    /**
     * 步长
     */
    @ApiModelProperty(value = "步长")
    @TableField(value = "step")
    private Double step;

    /**
     * 单位
     */
    @ApiModelProperty(value = "单位")
    @TableField(value = "unit")
    private String unit;
}