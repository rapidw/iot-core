package io.rapidw.iotcore.api.service.log;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.rapidw.iotcore.common.mapper.log.DeviceEventMapper;
import io.rapidw.iotcore.common.entity.devicelog.DeviceLogEvent;
import org.springframework.stereotype.Service;

@Service
public class DeviceLogEventService extends ServiceImpl<DeviceEventMapper, DeviceLogEvent> implements IService<DeviceLogEvent> {



}

