package io.rapidw.iotcore.api.service.field;

import com.baomidou.mybatisplus.extension.service.IService;
import io.rapidw.iotcore.common.entity.field.FieldFloat;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.rapidw.iotcore.api.mapper.field.FloatMapper;

@Service
public class FloatService extends ServiceImpl<FloatMapper, FieldFloat> implements IService<FieldFloat> {

    

}


