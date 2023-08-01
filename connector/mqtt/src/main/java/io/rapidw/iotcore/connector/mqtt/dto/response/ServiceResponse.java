package io.rapidw.iotcore.connector.mqtt.dto.response;

import io.rapidw.iotcore.connector.mqtt.dto.Field;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceResponse {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Field> fields;
    private Integer code;
    private String message;
}
