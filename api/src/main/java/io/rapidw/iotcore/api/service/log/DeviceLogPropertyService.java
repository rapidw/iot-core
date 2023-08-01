package io.rapidw.iotcore.api.service.log;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.rapidw.iotcore.common.mapper.log.DevicePropertyMapper;
import io.rapidw.iotcore.common.entity.devicelog.DeviceLogProperty;
import org.springframework.stereotype.Service;

@Service
public class DeviceLogPropertyService extends ServiceImpl<DevicePropertyMapper, DeviceLogProperty> implements IService<DeviceLogProperty> {



}

