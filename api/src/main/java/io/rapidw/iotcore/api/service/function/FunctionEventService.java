package io.rapidw.iotcore.api.service.function;

import com.baomidou.mybatisplus.extension.service.IService;
import io.rapidw.iotcore.common.entity.function.FunctionEvent;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.rapidw.iotcore.api.mapper.function.FunctionEventMapper;

@Service
public class FunctionEventService extends ServiceImpl<FunctionEventMapper, FunctionEvent> implements IService<FunctionEvent> {

    

}


