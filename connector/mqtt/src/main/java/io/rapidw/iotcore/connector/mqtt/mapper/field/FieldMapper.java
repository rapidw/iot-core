package io.rapidw.iotcore.connector.mqtt.mapper.field;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.rapidw.iotcore.common.entity.field.Field;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FieldMapper extends BaseMapper<Field> {
}
