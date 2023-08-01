package io.rapidw.iotcore.common.mapper.log;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.rapidw.iotcore.common.entity.devicelog.DeviceLogProperty;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DevicePropertyMapper extends BaseMapper<DeviceLogProperty> {

}