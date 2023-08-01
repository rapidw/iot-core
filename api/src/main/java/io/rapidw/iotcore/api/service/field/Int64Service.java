package io.rapidw.iotcore.api.service.field;

import com.baomidou.mybatisplus.extension.service.IService;
import io.rapidw.iotcore.common.entity.field.FieldInt64;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.rapidw.iotcore.api.mapper.field.Int64Mapper;

@Service
public class Int64Service extends ServiceImpl<Int64Mapper, FieldInt64> implements IService<FieldInt64> {

    

}


