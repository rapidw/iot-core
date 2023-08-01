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
@TableName(value = "iotcore.entry_int64")
@ApiModel
public class EntryInt64 extends ProductIdIncluded {
    @TableId(value = "id", type = IdType.INPUT)
    @JsonIgnore
    private Integer id;

    @TableField(value = "entry_id")
    @JsonIgnore
    private Integer entryId;

    /**
     * 最大值
     */
    @TableField(value = "`max`")
    @ApiModelProperty(value = "最大值")
    private Long max;

    /**
     * 最小值
     */
    @TableField(value = "`min`")
    @ApiModelProperty(value = "最小值")
    private Long min;

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