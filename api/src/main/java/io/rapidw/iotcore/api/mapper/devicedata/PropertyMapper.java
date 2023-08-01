package io.rapidw.iotcore.api.mapper.devicedata;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import io.rapidw.iotcore.common.entity.devicedata.DeviceDataProperty;
import org.apache.ibatis.annotations.Mapper;

// 设备数据-属性
@Mapper
public interface PropertyMapper extends BaseMapper<DeviceDataProperty> {

}