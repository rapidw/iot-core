package io.rapidw.iotcore.api.service.struct;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.rapidw.iotcore.api.mapper.struct.DoubleEntryMapper;
import io.rapidw.iotcore.common.entity.struct.EntryDouble;
import org.springframework.stereotype.Service;

@Service
public class DoubleEntryService extends ServiceImpl<DoubleEntryMapper, EntryDouble> implements IService<EntryDouble> {


}


