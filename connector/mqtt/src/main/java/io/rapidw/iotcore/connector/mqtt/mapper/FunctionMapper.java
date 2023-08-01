package io.rapidw.iotcore.connector.mqtt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.rapidw.iotcore.common.entity.function.Function;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FunctionMapper extends BaseMapper<Function> {
}
