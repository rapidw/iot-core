package io.rapidw.iotcore.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "iotcore.device")
@ApiModel(description = "设备信息数据库实体")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Device {
    @TableId(value = "id", type = IdType.INPUT)
    @JsonIgnore
    private Integer id;

    @TableField(value = "product_id")
    @ApiModelProperty(value = "产品id")
    private String productId;

    /**
     * 名称
     */
    @TableField(value = "`name`")
    @ApiModelProperty(value = "设备名称")
    private String name;


    /**
     * 状态
     */
    @TableField(value = "`status`")
    @ApiModelProperty(value = "设备状态")
    private Status status;

    /**
     * 创建时间
     */
    @TableField(value = "ceate_time")
    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Instant ceateTime;


    /**
     * 最后在线时间
     */
    @TableField(value = "last_online_time")
    @ApiModelProperty(value = "最后在线时间")
    private Instant lastOnlineTime;

    @TableField(value = "longitude")
    @ApiModelProperty(value = "经度")
    private Double longitude;

    @TableField(value = "latitude")
    @ApiModelProperty(value = "纬度")
    private Double latitude;

    public enum Status {
        NEVER_CONNECTED,
        ONLINE,
        OFFLINE;
    }

}