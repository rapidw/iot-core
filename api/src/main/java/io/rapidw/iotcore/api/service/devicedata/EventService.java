package io.rapidw.iotcore.api.service.devicedata;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.rapidw.iotcore.api.mapper.devicedata.EventMapper;
import io.rapidw.iotcore.common.entity.devicedata.DeviceDataEvent;
import org.springframework.stereotype.Service;
@Service
public class EventService extends ServiceImpl<EventMapper, DeviceDataEvent> {


}
