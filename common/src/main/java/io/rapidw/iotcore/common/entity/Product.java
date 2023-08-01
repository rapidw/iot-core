package io.rapidw.iotcore.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
@TableName(value = "iotcore.product")
@ApiModel(description = "产品数据库实体")
public class Product {
    /**
     * 产品ID
     */
    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value = "id")
    @JsonIgnore
    private Integer id;

    /**
     * 产品名称
     */
    @TableField(value = "`name`")
    @ApiModelProperty(value = "产品名称")
    private String name;

    /**
     * 产品KEY
     */
    @TableField(value = "`key`")
    @ApiModelProperty(value = "产品key")
    @JsonIgnore
    private String key;

    /**
     * 产品id
     */
    @JsonProperty(value = "productId")
    @TableField(value = "identifier")
    @ApiModelProperty(value = "产品id")
    private String identifier;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Instant createTime;
}