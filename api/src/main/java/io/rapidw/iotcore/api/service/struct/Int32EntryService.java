package io.rapidw.iotcore.api.service.struct;

import com.baomidou.mybatisplus.extension.service.IService;
import io.rapidw.iotcore.common.entity.struct.EntryInt32;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.rapidw.iotcore.api.mapper.struct.Int32EntryMapper;

@Service
public class Int32EntryService extends ServiceImpl<Int32EntryMapper, EntryInt32> implements IService<EntryInt32> {

    

}


