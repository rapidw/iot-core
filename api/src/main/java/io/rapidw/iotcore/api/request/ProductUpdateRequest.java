package io.rapidw.iotcore.api.request;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@ApiModel(description = "产品修改实体")
public class ProductUpdateRequest {

    /**
     * 名称
     */
    @ApiModelProperty(value = "产品名称",required = true)
    @NotBlank(message = "name不可为空！")
    private String name;

    @ApiModelProperty(value = "productId",hidden = true)
    @Null(message = "productId不可更改!")
    private String productId;

    @ApiModelProperty(value = "产品key",hidden = true)
    @Null(message = "产品key不可更改!")
    private String key;

}
