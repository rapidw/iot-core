package io.rapidw.iotcore.api.service.field;

import com.baomidou.mybatisplus.extension.service.IService;
import io.rapidw.iotcore.common.entity.field.FieldString;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.rapidw.iotcore.api.mapper.field.StringMapper;

@Service
public class StringService extends ServiceImpl<StringMapper, FieldString> implements IService<FieldString> {

    

}


