package io.rapidw.iotcore.common.entity.struct;

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
@TableName(value = "iotcore.entry")
public class Entry extends ProductIdIncluded {
    public enum Type {
        INT32,
        INT64,
        DOUBLE,
        FLOAT,
        STRING,
    }

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * structId
     */
    @TableField(value = "struct_id")
    private Integer structId;

    /**
     * 名称
     */
    @TableField(value = "`name`")
    private String name;

    /**
     * 标识符
     */
    @TableField(value = "identifier")
    private String identifier;

    /**
     * 数据类型
     */
    @TableField(value = "type")
    private Type type;
}