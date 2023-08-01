package io.rapidw.iotcore.api.request;

import io.rapidw.iotcore.common.entity.function.Function;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.Null;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(description = "功能请求实体")
public class FunctionUpdateRequest {

    @ApiModelProperty(value = "功能名称")
    private String name;

    @ApiModelProperty(value = "functionId",hidden = true)
    @Null(message = "functionId不可更改!")
    private String functionId;

    @ApiModelProperty(value = "功能类型")
    private Function.Type type;

    @ApiModelProperty(value = "描述")
    private String desc;


}
