package io.rapidw.iotcore.common.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "翻页请求实体")
public class PageRequest {

    @NotNull(message = "should set page num")
    @Min(value = 1, message = "page num should greater than 1")
    @Max(value = 100, message = "page num should smaller than 100")
    @ApiModelProperty(value = "查询页数(最小值为1)", example = "1")
    private int pageNum = 1;

    @NotNull(message = "should set page size")
    @Min(value = 1, message = "page size should greater than 1")
    @Max(value = 100, message = "page size should smaller than 100")
    @ApiModelProperty(value = "每页展示记录数(最小值为1)", example = "1")
    private int pageSize = 20;


}
