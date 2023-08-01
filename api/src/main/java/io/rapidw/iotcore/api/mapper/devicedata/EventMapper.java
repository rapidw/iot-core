package io.rapidw.iotcore.api.mapper.devicedata;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.rapidw.iotcore.common.entity.devicedata.DeviceDataEvent;
import org.apache.ibatis.annotations.Mapper;

// 设备数据-事件
@Mapper
public interface EventMapper extends BaseMapper<DeviceDataEvent> {

}