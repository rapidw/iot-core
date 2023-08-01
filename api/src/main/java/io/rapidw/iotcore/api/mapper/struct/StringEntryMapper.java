package io.rapidw.iotcore.api.mapper.struct;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;


import io.rapidw.iotcore.common.entity.struct.EntryString;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StringEntryMapper extends BaseMapper<EntryString> {

}