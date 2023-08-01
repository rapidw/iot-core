package io.rapidw.iotcore.api.service.field;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.rapidw.iotcore.common.entity.field.FieldString;
import io.rapidw.iotcore.common.mapper.field.FieldStringMapper;
import org.springframework.stereotype.Service;

@Service
public class StringService extends ServiceImpl<FieldStringMapper, FieldString> implements IService<FieldString> {

    

}


