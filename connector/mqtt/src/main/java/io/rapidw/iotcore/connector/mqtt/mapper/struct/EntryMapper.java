package io.rapidw.iotcore.connector.mqtt.mapper.struct;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.rapidw.iotcore.common.entity.struct.Entry;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EntryMapper extends BaseMapper<Entry> {
}
