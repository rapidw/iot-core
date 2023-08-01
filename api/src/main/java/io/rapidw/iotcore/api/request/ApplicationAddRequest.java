package io.rapidw.iotcore.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(description = "应用新增实体")
public class ApplicationAddRequest {
    @ApiModelProperty(value = "应用名称",required = true)
    @NotBlank(message = "name不可为空！")
    private String name;

    @ApiModelProperty(value = "appId",hidden = true)
    @Null(message = "appId不可自定义！")
    private String appId;

    @ApiModelProperty(value = "应用key",hidden = true)
    @Null(message = "key不可自定义!")
    private String key;
}
