package io.rapidw.iotcore.api.request;


import io.rapidw.iotcore.common.entity.Device;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.Null;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@ApiModel(description = "设备请求实体")
public class DeviceUpdateRequest {

    @ApiModelProperty(value = "设备名称",hidden = true)
    @Null(message = "name不可更改！")
    private String name;

    @ApiModelProperty(value = "状态")
    private Device.Status status;

    @ApiModelProperty(value = "最后在线时间")
    private Date lastOnlineTime;

    @ApiModelProperty(value = "经度")
    private Double longitude;

    @ApiModelProperty(value = "纬度")
    private Double latitude;
}
