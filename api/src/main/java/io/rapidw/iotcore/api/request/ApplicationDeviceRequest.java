package io.rapidw.iotcore.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(description = "应用绑定设备请求实体")
public class ApplicationDeviceRequest {

    @NotEmpty(message = "deviceIds不可为空!")
    @ApiModelProperty(value = "设备列表",required = true)
    @Valid
    private List<AppDevice> devices;


    @ApiModel(description = "设备请求实体")
    @Data
    public static class AppDevice {

        @ApiModelProperty(value = "产品id",required = true)
        @NotBlank(message = "产品id为空！")
        private String productId;
        @ApiModelProperty(value = "设备名称",required = true)
        @NotBlank(message = "设备名称不可为空！")
        private String deviceName;
    }

}
