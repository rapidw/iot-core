package io.rapidw.iotcore.api.service.struct;

import com.baomidou.mybatisplus.extension.service.IService;
import io.rapidw.iotcore.common.entity.struct.EntryInt64;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.rapidw.iotcore.api.mapper.struct.Int64EntryMapper;

@Service
public class Int64EntryService extends ServiceImpl<Int64EntryMapper, EntryInt64> implements IService<EntryInt64> {

    

}


