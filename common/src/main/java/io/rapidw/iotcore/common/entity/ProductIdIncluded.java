package io.rapidw.iotcore.common.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "数据库共用product_id字段")
public class ProductIdIncluded {

    /**
     * 产品ID
     */
    @TableField(value = "product_id")
    @ApiModelProperty(value = "产品id")
    @JsonIgnore
    private String productId;
}
