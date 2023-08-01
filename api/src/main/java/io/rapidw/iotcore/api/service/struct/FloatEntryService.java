package io.rapidw.iotcore.api.service.struct;

import com.baomidou.mybatisplus.extension.service.IService;
import io.rapidw.iotcore.common.entity.struct.EntryFloat;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.rapidw.iotcore.api.mapper.struct.FloatEntryMapper;

@Service
public class FloatEntryService extends ServiceImpl<FloatEntryMapper, EntryFloat> implements IService<EntryFloat> {


}


