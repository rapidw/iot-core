package io.rapidw.iotcore.common.entity.devicelog;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.rapidw.iotcore.common.entity.function.FunctionEvent;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@ApiModel(value = "io.rapidw-iotcore-api-entity-DeviceLogEvent")
@Data
@Builder
@TableName(value = "iotcore.device_log_event")
public class DeviceLogEvent {
    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value = "id")
    private Integer id;

    /**
     * 时间
     */
    @TableField(value = "create_time")
    @ApiModelProperty(value = "时间")
    private Instant createTime;

    @TableField(value = "`data`")
    @ApiModelProperty(value = "data")
    private String data;

    /**
     * 设备ID
     */
    @TableField(value = "device_name")
    @ApiModelProperty(value = "设备ID")
    private String deviceName;

    @TableField(value = "product_id")
    @ApiModelProperty(value = "")
    private String productId;

    @TableField(value = "function_id")
    @ApiModelProperty(value = "功能id")
    private String functionId;

    @TableField(value = "level")
    @ApiModelProperty(value = "级别")
    private FunctionEvent.Level level;
}