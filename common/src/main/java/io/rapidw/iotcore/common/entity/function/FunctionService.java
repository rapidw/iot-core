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
@TableName(value = "iotcore.function_service")
public class FunctionService extends ProductIdIncluded {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.INPUT)
    private Integer id;

    /**
     * 功能ID
     */
    @TableField(value = "function_id")
    private Integer functionId;

    /**
     * 调用方法
     */
    @TableField(value = "sync_or_async")
    private SyncOrAsync syncOrAsync;
    
    public enum SyncOrAsync {
        SYNC, ASYNC;
    }
}