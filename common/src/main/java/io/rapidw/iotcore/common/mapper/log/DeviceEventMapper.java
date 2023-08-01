package io.rapidw.iotcore.common.mapper.log;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.rapidw.iotcore.common.entity.devicelog.DeviceLogEvent;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DeviceEventMapper extends BaseMapper<DeviceLogEvent> {

}