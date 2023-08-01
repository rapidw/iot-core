package io.rapidw.iotcore.common.entity.devicedata;

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

@ApiModel(value = "DeviceDataEvent")
@Data
@Builder
@TableName(value = "iotcore.device_data_event")
public class DeviceDataEvent {
    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value = "id")
    private Integer id;

    /**
     * 产品ID
     */
    @TableField(value = "product_id")
    @ApiModelProperty(value = "产品ID")
    private String productId;

    /**
     * 设备ID
     */
    @TableField(value = "device_name")
    @ApiModelProperty(value = "设备名称")
    private String deviceName;

    @TableField(value = "function_id")
    @ApiModelProperty(value = "功能id")
    private String functionId;

    @TableField(value = "field_id")
    @ApiModelProperty(value = "字段id")
    private String fieldId;

    @TableField(value = "level")
    @ApiModelProperty(value = "级别")
    private FunctionEvent.Level level;

    /**
     * 数据
     */
    @TableField(value = "`data`")
    @ApiModelProperty(value = "数据")
    private String data;

    /**
     * 时间
     */
    @TableField(value = "time")
    @ApiModelProperty(value = "时间")
    private Instant time;
}