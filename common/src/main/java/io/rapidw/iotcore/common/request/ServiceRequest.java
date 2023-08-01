package io.rapidw.iotcore.common.request;

import io.rapidw.iotcore.common.dto.Field;
import lombok.Data;

import java.util.List;

@Data
public class ServiceRequest {
    // message id
    private String id;
    private List<Field> fields;
}
