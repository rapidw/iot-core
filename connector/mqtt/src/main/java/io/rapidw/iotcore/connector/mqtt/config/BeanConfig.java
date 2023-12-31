package io.rapidw.iotcore.connector.mqtt.config;

import io.netty.util.HashedWheelTimer;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class BeanConfig {
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.requestFactory(OkHttp3ClientHttpRequestFactory.class).build();
    }

    @Bean
    public HashedWheelTimer hashedWheelTimer() {
        return new HashedWheelTimer();
    }
}
