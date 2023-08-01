package io.rapidw.iotcore.api.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "批量执行设备命令返回体")
public class ServiceCallResponse {

    @ApiModelProperty(value="设备名称")
    private String deviceName;

    @ApiModelProperty(value = "0-mqtt连接成功,-1-mqtt连接失败")
    private int code;

    @ApiModelProperty(value = "执行命令返回字符串")
    private String response;
}
