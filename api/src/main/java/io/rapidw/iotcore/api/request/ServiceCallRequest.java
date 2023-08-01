package io.rapidw.iotcore.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@ApiModel(description = "设备命令调用请求体")
public class ServiceCallRequest {


    @ApiModelProperty(value = "设备名称",required = true)
    @NotBlank(message = "设备名称不可为空！")
    private String deviceName;

    @ApiModelProperty(value = "命令字符串",required = true)
    @NotBlank(message = "命令不可为空！")
    private String command;
}
