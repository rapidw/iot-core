package io.rapidw.iotcore.api.service.field;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.rapidw.iotcore.common.entity.field.FieldInt32;
import org.springframework.stereotype.Service;

import io.rapidw.iotcore.api.mapper.field.Int32Mapper;

@Service
public class Int32Service extends ServiceImpl<Int32Mapper, FieldInt32> implements IService<FieldInt32> {


}


