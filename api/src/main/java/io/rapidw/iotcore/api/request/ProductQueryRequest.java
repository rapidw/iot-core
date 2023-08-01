package io.rapidw.iotcore.api.request;

import io.rapidw.iotcore.common.request.PageRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel(description = "产品分页请求实体")
public class ProductQueryRequest extends PageRequest {
    /**
     * 名称
     */
    @ApiModelProperty(value = "产品名关键字")
    private String name;

}
