package io.rapidw.iotcore.api.request;

import io.rapidw.iotcore.common.request.PageRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@ApiModel(description = "设备数据请求实体")
public class DeviceDataRequest extends PageRequest {

    @ApiModelProperty(value = "产品id",required = true)
    @NotBlank(message = "productId不可为空！")
    private String productId;
    @ApiModelProperty(value = "设备名称",required = true)
    @NotBlank(message = "deviceName不可为空！")
    private String deviceName;
    @ApiModelProperty(value = "功能id",required = true)
    @NotBlank(message = "functionId不可为空！")
    private String functionId;
    @ApiModelProperty(value = "字段id",required = true)
    @NotBlank(message = "字段id不可为空！")
    private String fieldId;

    @ApiModelProperty(value = "开始时间")
    private Date startTime;

    @ApiModelProperty(value = "结束时间")
    private Date endTime;
}
