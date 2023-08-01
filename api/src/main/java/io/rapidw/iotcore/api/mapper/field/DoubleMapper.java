package io.rapidw.iotcore.api.mapper.field;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;


import io.rapidw.iotcore.common.entity.field.FieldDouble;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DoubleMapper extends BaseMapper<FieldDouble> {
}