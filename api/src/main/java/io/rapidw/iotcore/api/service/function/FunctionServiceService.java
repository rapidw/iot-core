package io.rapidw.iotcore.api.service.function;


import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.rapidw.iotcore.api.mapper.function.FunctionServiceMapper;
import io.rapidw.iotcore.common.entity.function.FunctionService;
import org.springframework.stereotype.Service;


@Service
public class FunctionServiceService extends ServiceImpl<FunctionServiceMapper, io.rapidw.iotcore.common.entity.function.FunctionService> implements IService<FunctionService> {

}


