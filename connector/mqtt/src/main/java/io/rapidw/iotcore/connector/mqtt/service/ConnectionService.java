package io.rapidw.iotcore.connector.mqtt.service;

import io.rapidw.iotcore.connector.mqtt.MqttHandler;
import io.rapidw.iotcore.connector.mqtt.config.AppConfig;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
public class ConnectionService {

    private final RedisService redisService;
    private final AppConfig appConfig;

    private final Map<String, Map<String, MqttHandler>> connections = new ConcurrentHashMap<>();

    public ConnectionService(RedisService redisService, AppConfig appConfig) {
        this.redisService = redisService;
        this.appConfig = appConfig;
    }

    public void addConnection(String productId, String deviceName, MqttHandler handler) {
        val deviceMap = connections.computeIfAbsent(productId, k -> new ConcurrentHashMap<>());
        deviceMap.put(deviceName, handler);

        String env = System.getenv("MY_POD_IP");
        if (env == null) {
            env = appConfig.getAddress();
        }
        redisService.registerDevice(productId, deviceName, env);
    }

    public void removeConnection(String productId, String deviceName) {

        if (connections.containsKey(productId)) {
            val deviceMap = connections.get(productId);
            deviceMap.remove(deviceName);
            if (deviceMap.isEmpty()) {
                connections.remove(productId);
            }
            redisService.unregisterDevice(productId, deviceName);
        }
    }

    public MqttHandler getConnection(String productId, String deviceName) {
        return connections.get(productId).get(deviceName);
    }
}
