package io.rapidw.iotcore.api.service.field;

import com.baomidou.mybatisplus.extension.service.IService;
import io.rapidw.iotcore.common.entity.field.FieldDouble;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.rapidw.iotcore.api.mapper.field.DoubleMapper;

@Service
public class DoubleService extends ServiceImpl<DoubleMapper, FieldDouble> implements IService<FieldDouble> {

    

}


