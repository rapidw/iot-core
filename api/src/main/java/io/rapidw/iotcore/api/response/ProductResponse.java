package io.rapidw.iotcore.api.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "创建产品返回体")
public class ProductResponse {

    @ApiModelProperty(value = "产品id")
    private String productId;
    @ApiModelProperty(value = "产品key")
    private String key;
}
