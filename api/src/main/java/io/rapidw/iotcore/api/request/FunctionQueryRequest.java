package io.rapidw.iotcore.api.request;

import io.rapidw.iotcore.common.entity.function.Function;
import io.rapidw.iotcore.common.request.PageRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@ApiModel(description = "功能分页请求实体")
public class FunctionQueryRequest extends PageRequest {

    @ApiModelProperty(value = "功能名称")
    private String name;

    @ApiModelProperty(value = "功能Id")
    private List<String> functionIds;

    @ApiModelProperty(value = "功能类型")
    private Function.Type type;

}
