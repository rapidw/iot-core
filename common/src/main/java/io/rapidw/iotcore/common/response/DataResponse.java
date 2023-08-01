package io.rapidw.iotcore.common.response;

import io.rapidw.iotcore.common.exception.AppStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Data
@ApiModel(description = "统一返回实体")
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DataResponse<T> extends BaseResponse {

    @ApiModelProperty(value = "返回数据")
    private T data;

    public DataResponse(int code, String message, T data) {
        super(code, message);
        this.data = data;
    }

    public static <T> DataResponse<T> ok(T data) {
        return new DataResponse<>(AppStatus.SUCCESS.getCode(), AppStatus.SUCCESS.getMessage(), data);
    }


}
