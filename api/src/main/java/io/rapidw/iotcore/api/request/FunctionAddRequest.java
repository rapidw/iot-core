package io.rapidw.iotcore.api.request;

import io.rapidw.iotcore.common.entity.function.Function;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(description = "功能新增实体")
public class FunctionAddRequest {

    @ApiModelProperty(value = "功能名称",required = true)
    @NotBlank(message = "name不可为空!")
    private String name;

    @ApiModelProperty(value = "functionId",required = true)
    @NotBlank(message = "functionId不可为空！")
    private String functionId;

    @ApiModelProperty(value = "功能类型",required = true)
    @NotNull(message = "type不可为空!")
    private Function.Type type;

    @ApiModelProperty(value = "描述")
    private String desc;
}
