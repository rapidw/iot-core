package io.rapidw.iotcore.api.request;

import io.rapidw.iotcore.common.request.PageRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(description = "应用翻页请求实体")
public class ApplicationQueryRequest extends PageRequest {

    @ApiModelProperty(value = "应用名称关键字")
    private String name;
}
