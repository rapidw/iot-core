package io.rapidw.iotcore.common.entity.field;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.rapidw.iotcore.common.entity.ProductIdIncluded;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "iotcore.field_struct")
@ApiModel(description = "FieldStruct数据库实体")
public class FieldStruct extends ProductIdIncluded {
    @TableId(value = "id", type = IdType.AUTO)
    @JsonIgnore
    private Integer id;

    /**
     * 功能数据ID
     */
    @TableField(value = "field_id")
    @JsonIgnore
    private Integer fieldId;
}