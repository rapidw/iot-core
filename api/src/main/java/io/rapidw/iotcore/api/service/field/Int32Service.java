package io.rapidw.iotcore.api.service.field;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.rapidw.iotcore.common.entity.field.FieldInt32;
import io.rapidw.iotcore.common.mapper.field.FieldInt32Mapper;
import org.springframework.stereotype.Service;

@Service
public class Int32Service extends ServiceImpl<FieldInt32Mapper, FieldInt32> implements IService<FieldInt32> {


}


