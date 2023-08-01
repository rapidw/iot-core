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
@TableName(value = "iotcore.`application`")
@ApiModel(description = "数据库应用实体")
public class Application {
    @TableId(value = "id", type = IdType.INPUT)
    @JsonIgnore
    private Integer id;

    /**
     * 名称
     */
    @TableField(value = "`name`")
    @ApiModelProperty(value = "应用名称")
    private String name;

    @JsonProperty(value = "appId")
    @TableField(value = "identifier")
    @ApiModelProperty(value = "应用id")
    private String identifier;

    /**
     * 创建时间
     */
    @TableField(value = "creat_time")
    @ApiModelProperty(value = "应用创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Instant createTime;

    @TableField(value = "`key`")
    @JsonIgnore
    private String key;
}