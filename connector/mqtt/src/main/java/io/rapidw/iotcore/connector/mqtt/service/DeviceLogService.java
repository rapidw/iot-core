package io.rapidw.iotcore.connector.mqtt.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.rapidw.iotcore.common.mapper.log.DeviceServiceMapper;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class DeviceLogService {
    private final DeviceServiceMapper deviceLogServiceMapper;

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
