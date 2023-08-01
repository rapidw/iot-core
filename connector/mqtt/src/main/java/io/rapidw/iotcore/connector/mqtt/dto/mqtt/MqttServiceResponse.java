package io.rapidw.iotcore.connector.mqtt.dto.mqtt;

import io.rapidw.iotcore.connector.mqtt.dto.response.ServiceResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class MqttServiceResponse extends ServiceResponse {
    private Integer requestId;
}
