package io.rapidw.iotcore.api.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "connector")
@Component
@Getter
@Setter
public class ConnectorConfig {

    private String commandUrlSuffix;
}
