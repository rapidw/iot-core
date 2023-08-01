package io.rapidw.iotcore.api.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(description = "服务日志返回体")
public class ServiceLogResponse {

    @ApiModelProperty(value="时间")
    private Date createTime;

    @ApiModelProperty(value="响应")
    private String response;

    @ApiModelProperty(value="请求")
    private String request;

    @ApiModelProperty(value = "功能id")
    private String functionId;
}
