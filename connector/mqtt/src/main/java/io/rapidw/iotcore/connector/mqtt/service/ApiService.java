package io.rapidw.iotcore.connector.mqtt.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class ApiService {
    private final RestTemplate restTemplate;

    public String test() {
        return restTemplate.getForEntity("http://api:8080/test", String.class).getBody();
    }

    public String getKey(String productId, String deviceName) {
        return null;
    }

    public void getModel(String productId) {
//        return null;
    }
}
