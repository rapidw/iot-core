package io.rapidw.iotcore.common.response;

import io.rapidw.iotcore.common.entity.field.Field;
import lombok.Data;

import java.util.List;

@Data
public class ServiceResponse {
    private String id;
    private List<Field> fields;
    private Integer code;
    private String message;
}
