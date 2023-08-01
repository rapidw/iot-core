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
@EqualsAndHashCode(callSuper = true)
@Builder
@TableName(value = "iotcore.entry_string")
@ApiModel
public class EntryString extends ProductIdIncluded {
    @TableId(value = "id", type = IdType.INPUT)
    @JsonIgnore
    private Integer id;

    @TableField(value = "entry_id")
    @JsonIgnore
    private Integer entryId;

    /**
     * 长度
     */
    @TableField(value = "`length`")
    @ApiModelProperty(value = "长度")
    private Integer length;
}