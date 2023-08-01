package io.rapidw.iotcore.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@TableName(value = "iotcore.application_device")
public class ApplicationDevice {
    @TableId(value = "id", type = IdType.INPUT)
    private Integer id;

    /**
     * 设备ID
     */
    @TableField(value = "device_name")
    private String deviceName;

    @TableField(value = "product_id")
    private String productId;

    /**
     * 服务ID
     */
    @TableField(value = "application_id")
    private String applicationId;
}