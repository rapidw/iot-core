package io.rapidw.iotcore.connector.mqtt.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.rapidw.iotcore.common.entity.Device;
import io.rapidw.iotcore.common.mapper.DeviceMapper;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeviceService {

    private final DeviceMapper deviceMapper;

    public boolean exists(String productId, String deviceName) {
        val count = deviceMapper.selectCount(Wrappers.lambdaQuery(Device.class).
                eq(Device::getProductId, productId)
                .eq(Device::getName, deviceName));
        return count == 1;
    }

    public void setStatus(String productId, String deviceName, Device.Status status) {
        val builder = Device.builder().status(status);
        deviceMapper.update(builder.build(), Wrappers.lambdaQuery(Device.class)
                .eq(Device::getProductId, productId)
                .eq(Device::getName, deviceName));
    }
}
