package io.rapidw.iotcore.common.response;


import io.rapidw.iotcore.common.exception.AppException;
import io.rapidw.iotcore.common.exception.AppStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
@ApiModel(description = "统一返回实体(无数据)")
public class BaseResponse {

    @ApiModelProperty(value = "返回错误码")
    protected int code;
    @ApiModelProperty(value = "返回信息")
    protected String msg;

    private static final BaseResponse SUCCESS = new BaseResponse(0, "Success");

    public BaseResponse(AppStatus success) {
        this.code = success.getCode();
        this.msg = success.getMessage();
    }

    public static BaseResponse ok() {
        return SUCCESS;
    }

    public static BaseResponse error(AppException exception) {
        return new BaseResponse(exception.getCode(), exception.getMessage());
    }

    public static BaseResponse error(AppStatus status) {
        return new BaseResponse(status.getCode(), status.getMessage());
    }

    public static BaseResponse error(AppStatus status, String message) {
        return new BaseResponse(status.getCode(), status.getMessage() + ": " + message);
    }
}
