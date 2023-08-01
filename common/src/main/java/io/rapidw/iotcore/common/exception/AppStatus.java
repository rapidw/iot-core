package io.rapidw.iotcore.common.exception;


public enum AppStatus {

    /**
     * 成功
     */
    SUCCESS(0, "Success"),
    /**
     * 参数校验失败
     */
    BAD_REQUEST(400, "Bad request"),
    /**
     * 未认证
     */
    UNAUTHORIZED(401, "Unauthorized"),
    /**
     * 相关资源不存在
     */
    NOT_FOUND(404, "Resource not found"),

    /**
     * 冲突
     */
    CONFLICT(409, "Resource already exist"),

    /**
     * 服务器内部错误
     */
    INTERNAL_SERVER_ERROR(500, "Internal server error"),
    /**
     * 空指针
     */
    NULL_POINTER(500, "Null pointer"),
    /**
     * 相关资源使用中
     */
    USING(409, "Resource occupied");


    private int code;

    private final String message;


    AppStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }
}
