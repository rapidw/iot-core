package io.rapidw.iotcore.connector.mqtt.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.val;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaService {
    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendDeviceOnlineNotification(String productId, String deviceName) {
        sendDeviceNotification(productId, deviceName, true);
    }

    public void sendDeviceOfflineNotification(String productId, String deviceName) {
        sendDeviceNotification(productId, deviceName, false);
    }

    @SneakyThrows
    private void sendDeviceNotification(String productId, String deviceName, boolean online) {
        val notification = new DeviceNotification();
        notification.setProductId(productId);
        notification.setDeviceName(deviceName);
        notification.setOnline(online);

        kafkaTemplate.send("device_notification", objectMapper.writeValueAsString(notification));
    }

    @Data
    public static class DeviceNotification {
        private String productId;
        private String deviceName;
        private boolean online;
    }
}
