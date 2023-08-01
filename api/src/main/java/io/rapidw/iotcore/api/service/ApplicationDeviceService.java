package io.rapidw.iotcore.api.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.rapidw.iotcore.api.mapper.ApplicationDeviceMapper;
import io.rapidw.iotcore.common.entity.ApplicationDevice;
import org.springframework.stereotype.Service;

@Service
public class ApplicationDeviceService extends ServiceImpl<ApplicationDeviceMapper, ApplicationDevice> implements IService<ApplicationDevice> {

    public void delete(String productId,String deviceName,String appId){
        remove(new LambdaQueryWrapper<ApplicationDevice>().eq(ApplicationDevice::getProductId,productId).eq(ApplicationDevice::getDeviceName,deviceName)
                .eq(ApplicationDevice::getApplicationId,appId));
    }
}


