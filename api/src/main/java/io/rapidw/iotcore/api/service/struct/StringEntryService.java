package io.rapidw.iotcore.api.service.struct;

import com.baomidou.mybatisplus.extension.service.IService;
import io.rapidw.iotcore.common.entity.struct.EntryString;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.rapidw.iotcore.api.mapper.struct.StringEntryMapper;

@Service
public class StringEntryService extends ServiceImpl<StringEntryMapper, EntryString> implements IService<EntryString> {

}


