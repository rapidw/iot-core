package io.rapidw.iotcore.api.response;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(description = "设备信息返回实体")
public class DeviceResponse {

    /**
     * 设备id
     */
    @ApiModelProperty(value = "设备id")
    private String deviceId;


    @ApiModelProperty(value = "产品id")
    private String productId;


    @ApiModelProperty(value = "产品key")
    private String key;

    /**
     * 名称
     */
    @TableField(value = "`name`")
    @ApiModelProperty(value = "设备名称")
    private String name;


    /**
     * 状态
     */
    @ApiModelProperty(value = "设备状态 1-在线  0-离线")
    private Integer status;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date ceateTime;


    /**
     * 最后在线时间
     */
    @ApiModelProperty(value = "最后在线时间")
    private Date lastOnlineTime;

}
