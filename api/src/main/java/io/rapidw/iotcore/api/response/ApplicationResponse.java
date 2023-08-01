package io.rapidw.iotcore.api.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "创建应用返回体")
public class ApplicationResponse {

    @ApiModelProperty(value = "应用id")
    private String appId;
    @ApiModelProperty(value = "应用key")
    private String key;

}
