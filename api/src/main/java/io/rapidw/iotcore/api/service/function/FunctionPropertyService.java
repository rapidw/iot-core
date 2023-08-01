package io.rapidw.iotcore.api.service.function;

import com.baomidou.mybatisplus.extension.service.IService;
import io.rapidw.iotcore.common.entity.function.FunctionProperty;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.rapidw.iotcore.api.mapper.function.FunctionPropertyMapper;

@Service
public class FunctionPropertyService extends ServiceImpl<FunctionPropertyMapper, FunctionProperty> implements IService<FunctionProperty> {

    

}


