package io.rapidw.iotcore.connector.mqtt.dto.request;

import io.rapidw.iotcore.connector.mqtt.dto.Field;
import lombok.Data;

import java.util.List;

@Data
public class PropertyRequest {
    private String id;
    private List<Field> fields;
}
