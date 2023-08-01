package io.rapidw.iotcore.common.entity.devicelog;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import org.apache.ibatis.type.JdbcType;

import java.time.Instant;

@ApiModel(description = "服务日志数据库实体")
@Data
@Builder
@TableName(value = "iotcore.device_log_service")
public class DeviceLogService {
    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value = "")
    @JsonIgnore
    private Integer id;

    /**
     * 设备名称
     */
    @TableField(value = "device_name")
    @ApiModelProperty(value = "设备名称")
    private String deviceName;

    @TableField(value = "product_id")
    @ApiModelProperty(value = "产品id")
    private String productId;

    @TableField(value = "function_id")
    @ApiModelProperty(value = "功能id")
    private String functionId;

    /**
     * 时间
     */
    @TableField(value = "request_time")
    @ApiModelProperty(value = "请求时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Instant requestTime;

    @TableField(value = "response_time")
    @ApiModelProperty(value = "响应时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Instant responseTime;

    @TableField(value = "response",jdbcType = JdbcType.LONGVARCHAR)
    @ApiModelProperty(value = "响应")
    private String response;

    @TableField(value = "request",jdbcType = JdbcType.LONGVARCHAR)
    @ApiModelProperty(value = "请求")
    private String request;
}