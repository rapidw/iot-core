package io.rapidw.iotcore.connector.mqtt;

import io.rapidw.iotcore.connector.mqtt.config.AppConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableConfigurationProperties(AppConfig.class)
@ComponentScan(basePackages = "io.rapidw.iotcore.common")
public class App {
    public static void main(String [] args) {
        SpringApplication.run(App.class, args);
    }
}
