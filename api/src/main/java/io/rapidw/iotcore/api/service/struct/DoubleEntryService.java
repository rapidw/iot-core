package io.rapidw.iotcore.api.service.struct;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.rapidw.iotcore.common.entity.struct.EntryDouble;
import io.rapidw.iotcore.common.mapper.struct.EntryDoubleMapper;
import org.springframework.stereotype.Service;

@Service
public class DoubleEntryService extends ServiceImpl<EntryDoubleMapper, EntryDouble> implements IService<EntryDouble> {


}


