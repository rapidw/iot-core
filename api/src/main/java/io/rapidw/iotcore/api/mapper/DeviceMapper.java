package io.rapidw.iotcore.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;


import io.rapidw.iotcore.common.entity.Device;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface DeviceMapper extends BaseMapper<Device> {

}