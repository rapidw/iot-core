package io.rapidw.iotcore.connector.mqtt.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.rapidw.iotcore.connector.mqtt.mapper.DeviceLogServiceMapper;
import lombok.val;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class DeviceLogService {
    private final DeviceLogServiceMapper deviceLogServiceMapper;

    public DeviceLogService(DeviceLogServiceMapper deviceLogServiceMapper) {
        this.deviceLogServiceMapper = deviceLogServiceMapper;
    }

    public Integer saveServiceRequest(String productId, String deviceName, String serviceId, String requestString) {
        val now = Instant.now();
        val request = io.rapidw.iotcore.common.entity.devicelog.DeviceLogService.builder()
                .productId(productId)
                .deviceName(deviceName)
                .request(requestString)
                .functionId(serviceId)
                .requestTime(now).build();
        deviceLogServiceMapper.insert(request);
        return request.getId();
    }

    public void saveServiceResponse(Integer id, String responseString) {
        val builder = io.rapidw.iotcore.common.entity.devicelog.DeviceLogService.builder()
                .response(responseString)
                .responseTime(Instant.now());
        deviceLogServiceMapper.update(builder.build(),
                Wrappers.lambdaQuery(io.rapidw.iotcore.common.entity.devicelog.DeviceLogService.class)
                        .eq(io.rapidw.iotcore.common.entity.devicelog.DeviceLogService::getId, id));
    }
}
