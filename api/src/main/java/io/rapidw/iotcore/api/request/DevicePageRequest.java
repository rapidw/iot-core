package io.rapidw.iotcore.api.request;

import io.rapidw.iotcore.common.entity.Device;
import io.rapidw.iotcore.common.request.PageRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(description = "设备分页请求实体")
public class DevicePageRequest extends PageRequest {

    /**
     * 名称
     */
    @ApiModelProperty(value = "设备名称")
    private String name;


    /**
     * 状态
     */
    @ApiModelProperty(value = "设备状态")
    private Device.Status status;
}
