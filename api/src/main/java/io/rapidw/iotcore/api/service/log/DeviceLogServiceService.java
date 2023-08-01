package io.rapidw.iotcore.api.service.log;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.rapidw.iotcore.api.mapper.log.DeviceServiceMapper;
import io.rapidw.iotcore.api.request.ServiceLogQueryRequest;
import io.rapidw.iotcore.api.service.ApplicationDeviceService;
import io.rapidw.iotcore.api.service.DeviceService;
import io.rapidw.iotcore.api.service.function.FunctionService;
import io.rapidw.iotcore.api.utils.PageUtils;
import io.rapidw.iotcore.common.entity.ApplicationDevice;
import io.rapidw.iotcore.common.entity.Device;
import io.rapidw.iotcore.common.entity.devicelog.DeviceLogService;
import io.rapidw.iotcore.common.entity.function.Function;
import io.rapidw.iotcore.common.exception.AppException;
import io.rapidw.iotcore.common.exception.AppStatus;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class DeviceLogServiceService extends ServiceImpl<DeviceServiceMapper, DeviceLogService> implements IService<DeviceLogService> {

    private final DeviceService deviceService;
    private final FunctionService functionService;
    private final ApplicationDeviceService applicationDeviceService;

    
    //构造注入循环依赖解决
    @Lazy
    public DeviceLogServiceService(DeviceService deviceService, FunctionService functionService, ApplicationDeviceService applicationDeviceService) {
        this.deviceService = deviceService;
        this.functionService = functionService;
        this.applicationDeviceService = applicationDeviceService;
    }

    public Page<DeviceLogService> getPage(String applicationId,String productId, String deviceName, ServiceLogQueryRequest request){
        Device device = deviceService.getOne(new LambdaQueryWrapper<Device>().eq(Device::getName,deviceName).eq(Device::getProductId,productId));
        if(null == device){
            throw new AppException(AppStatus.NOT_FOUND,"deviceName="+deviceName+",productId="+productId);
        }

        if(StringUtils.isNotBlank(applicationId) &&
                null == applicationDeviceService.getOne(new LambdaQueryWrapper<ApplicationDevice>()
                .eq(ApplicationDevice::getApplicationId,applicationId).eq(ApplicationDevice::getDeviceName,device.getName()))){
            throw new AppException(AppStatus.NOT_FOUND,"deviceName="+deviceName+",applicationId="+applicationId);
        }

        if(StringUtils.isNoneBlank(request.getFunctionId()) && null ==  functionService.getOne(new LambdaQueryWrapper<Function>().
                eq(Function::getIdentifier,request.getFunctionId()).eq(Function::getProductId,productId))){
                throw new AppException(AppStatus.NOT_FOUND,"functionId="+request.getFunctionId());
        }
        Page<DeviceLogService> page = PageUtils.getPage(request);
        LambdaQueryWrapper<DeviceLogService>  lambdaQueryWrapper = new LambdaQueryWrapper<DeviceLogService>().eq(DeviceLogService::getProductId,productId)
                .eq(DeviceLogService::getDeviceName,deviceName);
        if(StringUtils.isNotBlank(request.getFunctionId())){
            lambdaQueryWrapper.eq(DeviceLogService::getFunctionId,request.getFunctionId());
        }
        if(null != request.getStartTime() && null != request.getEndTime()){
            lambdaQueryWrapper.between(DeviceLogService::getRequestTime,request.getStartTime(),request.getEndTime());
        }
        baseMapper.selectPage(page,lambdaQueryWrapper);
        return page;
    }


}

