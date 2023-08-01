package io.rapidw.iotcore.api.exception;


import io.rapidw.iotcore.common.exception.AppException;
import io.rapidw.iotcore.common.exception.AppStatus;
import io.rapidw.iotcore.common.response.BaseResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class AppExceptionHandler {

    @ExceptionHandler(AppException.class)
    public BaseResponse handleAppException(AppException e) {
        return BaseResponse.error(e);
    }

    /**
     * 方法参数校验
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return BaseResponse.error(AppStatus.BAD_REQUEST,e.getBindingResult().getFieldError().getDefaultMessage());
    }


    @ExceptionHandler(BindException.class)
    public BaseResponse bindExceptionHandler(BindException e) {
        return BaseResponse.error(AppStatus.BAD_REQUEST,e.getBindingResult().getFieldError().getDefaultMessage());
    }


    /**
     * 处理其他异常
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public BaseResponse exceptionHandler(HttpServletRequest req, Exception e) {
        log.error("error",e);
        return BaseResponse.error(AppStatus.INTERNAL_SERVER_ERROR);
    }
}
