package io.rapidw.iotcore.api.response;

import io.rapidw.iotcore.common.entity.field.Field;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(description = "设备service数据返回体")
public class DeviceDataServiceResponse {

    @ApiModelProperty(value = "设备名称")
    private String deviceName;

    @ApiModelProperty(value = "字段名称")
    private String fieldName;

    @ApiModelProperty(value = "设备数据")
    private String data;

    @ApiModelProperty(value = "数据类型")
    private Field.Type type;


    @ApiModelProperty(value = "时间")
    private Date time;
}
