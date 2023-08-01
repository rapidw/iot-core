package io.rapidw.iotcore.connector.mqtt.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisService {
    private final StringRedisTemplate stringRedisTemplate;

    public void registerDevice(String productId, String deviceName, String address) {
        stringRedisTemplate.opsForHash().put(productId, deviceName, address);
    }

    public void unregisterDevice(String productId, String deviceName) {
        stringRedisTemplate.opsForHash().delete(productId, deviceName);
    }

    public String getDeviceConnectorAddress(String productId, String deviceName) {
        return stringRedisTemplate.<String, String>opsForHash().get(productId, deviceName);
    }
}
