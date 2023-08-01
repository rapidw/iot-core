package io.rapidw.iotcore.connector.mqtt.dto.mqtt;

import io.rapidw.iotcore.connector.mqtt.dto.request.FunctionRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class MqttServiceRequest extends FunctionRequest {
    private Integer requestId;
    private String functionId;
}
