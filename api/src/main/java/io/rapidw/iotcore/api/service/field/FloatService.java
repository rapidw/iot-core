package io.rapidw.iotcore.api.service.field;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.rapidw.iotcore.common.entity.field.FieldFloat;
import io.rapidw.iotcore.common.mapper.field.FieldFloatMapper;
import org.springframework.stereotype.Service;

@Service
public class FloatService extends ServiceImpl<FieldFloatMapper, FieldFloat> implements IService<FieldFloat> {

    

}


