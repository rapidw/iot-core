package io.rapidw.iotcore.connector.mqtt.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("app")
@Data
public class AppConfig {
    private int mqttPort;
    private String address;
    private int serviceTimeout;
}
