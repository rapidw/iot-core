package io.rapidw.iotcore.common.entity.function;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.rapidw.iotcore.common.entity.ProductIdIncluded;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@TableName(value = "iotcore.function_property")
public class FunctionProperty extends ProductIdIncluded {
    @TableId(value = "id", type = IdType.INPUT)
    private Integer id;

    /**
     * 功能ID
     */
    @TableField(value = "function_id")
    private Integer functionId;
}