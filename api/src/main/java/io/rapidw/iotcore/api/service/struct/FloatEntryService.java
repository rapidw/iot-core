package io.rapidw.iotcore.api.service.struct;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.rapidw.iotcore.common.entity.struct.EntryFloat;
import io.rapidw.iotcore.common.mapper.struct.EntryFloatMapper;
import org.springframework.stereotype.Service;

@Service
public class FloatEntryService extends ServiceImpl<EntryFloatMapper, EntryFloat> implements IService<EntryFloat> {


}


