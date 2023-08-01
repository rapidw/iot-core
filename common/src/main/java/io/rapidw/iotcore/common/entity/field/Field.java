package io.rapidw.iotcore.common.entity.field;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.rapidw.iotcore.common.entity.ProductIdIncluded;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName(value = "iotcore.field")
public class Field extends ProductIdIncluded {
    /**
     * 功能数据id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 功能ID
     */
    @TableField(value = "function_id")
    private String functionId;

    /**
     * 数据类型
     */
    @TableField(value = "`type`")
    private Type type;

    /**
     * 输入输出标识
     */
    @TableField(value = "in_or_out")
    private InOrOut inOrOut;

    @TableField(value = "name")
    private String name;

    @TableField(value = "identifier")
    private String identifier;
    
    public enum Type {
        INT32,
        INT64,
        DOUBLE,
        FLOAT,
        STRING,
        STRUCT;
    }

    public enum InOrOut {
        OUT, IN;
    }
}