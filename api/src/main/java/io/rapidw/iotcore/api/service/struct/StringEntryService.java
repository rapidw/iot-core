package io.rapidw.iotcore.api.service.struct;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.rapidw.iotcore.common.entity.struct.EntryString;
import io.rapidw.iotcore.common.mapper.struct.EntryStringMapper;
import org.springframework.stereotype.Service;

@Service
public class StringEntryService extends ServiceImpl<EntryStringMapper, EntryString> implements IService<EntryString> {

}


