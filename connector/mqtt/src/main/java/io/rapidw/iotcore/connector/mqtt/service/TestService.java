package io.rapidw.iotcore.connector.mqtt.service;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class TestService {
    private final StringRedisTemplate stringRedisTemplate;

    public TestService(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void setPodIp() {
        stringRedisTemplate.opsForValue().set("connector", System.getenv("MY_POD_IP"));
    }
}
