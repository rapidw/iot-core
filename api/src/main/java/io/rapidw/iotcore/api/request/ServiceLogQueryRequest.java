package io.rapidw.iotcore.api.request;

import io.rapidw.iotcore.common.request.PageRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.Instant;

@Getter
@Setter
@ApiModel(description = "服务日志查询请求体")
public class ServiceLogQueryRequest extends PageRequest {

    @ApiModelProperty(value = "功能id")
    private String functionId;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "开始时间")
    private Instant startTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "结束时间")
    private Instant endTime;

}
