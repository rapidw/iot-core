package io.rapidw.iotcore.api.request;

import io.rapidw.iotcore.common.entity.Device;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(description = "设备新增实体")
public class DeviceAddRequest {

    @ApiModelProperty(value = "设备名称",required = true)
    @NotBlank(message = "name不可为空！")
    private String name;

    @ApiModelProperty(value = "状态")
    private Device.Status status = Device.Status.NEVER_CONNECTED;

    @ApiModelProperty(value = "经度")
    private Double longitude;

    @ApiModelProperty(value = "纬度")
    private Double latitude;
}
