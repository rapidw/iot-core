package io.rapidw.iotcore.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AppException extends RuntimeException {

    private int code;
    private String message;

    public AppException(AppStatus status) {
        super(status.getMessage());
        this.code = status.getCode();
        this.message = status.getMessage();

    }

    public AppException(AppStatus status, String extraMessage) {
        super(status.getMessage() + ": " + extraMessage);
        this.message = status.getMessage() + ": " + extraMessage;
        this.code = status.getCode();
    }
}
