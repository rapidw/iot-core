package io.rapidw.iotcore.api.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.rapidw.iotcore.api.mapper.DeviceMapper;
import io.rapidw.iotcore.api.request.DeviceAddRequest;
import io.rapidw.iotcore.api.request.DevicePageRequest;
import io.rapidw.iotcore.api.request.DeviceUpdateRequest;
import io.rapidw.iotcore.common.entity.ApplicationDevice;
import io.rapidw.iotcore.common.entity.Device;
import io.rapidw.iotcore.common.exception.AppException;
import io.rapidw.iotcore.common.exception.AppStatus;
import io.rapidw.iotcore.common.response.PageResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static io.rapidw.iotcore.api.mapstruct.MapStructConverter.MAP_STRUCT_CONVERTER;

@Service
public class DeviceService extends ServiceImpl<DeviceMapper, Device> implements IService<Device> {


    private final ApplicationDeviceService applicationDeviceService;

    public DeviceService(ApplicationDeviceService applicationDeviceService) {
        this.applicationDeviceService = applicationDeviceService;
    }

    /**
     * 创建设备
     *
     * @param productId
     * @param request
     */
    public void create(String productId, DeviceAddRequest request) {
        if (null != getOne(new LambdaQueryWrapper<Device>().eq(Device::getProductId, productId).eq(Device::getName, request.getName()))) {
            throw new AppException(AppStatus.CONFLICT, "name="+request.getName());
        }
        Device device =  MAP_STRUCT_CONVERTER.toDevice(request);
        device.setProductId(productId);
        device.setCeateTime(Instant.now());
        save(device);
    }

    /**
     * 批量创建设备
     * @param productId
     * @param requests
     */
    @Transactional(rollbackFor = {Exception.class})
    public void createBatch(String productId,List<DeviceAddRequest> requests){
        Device device;
        List<Device> devices = new ArrayList<>(requests.size());
        for(DeviceAddRequest deviceRequest:requests){
            if (null != getOne(new LambdaQueryWrapper<Device>().eq(Device::getProductId, productId).eq(Device::getName, deviceRequest.getName()))) {
                throw new AppException(AppStatus.CONFLICT, "name="+deviceRequest.getName());
            }
            device = MAP_STRUCT_CONVERTER.toDevice(deviceRequest);
            device.setProductId(productId);
            device.setCeateTime(Instant.now());
            devices.add(device);
        }
        saveBatch(devices);
    }


    /**
     * 更新设备
     * @param productId
     * @param
     * @param request
     */
    public void update(String productId, String deviceName, DeviceUpdateRequest request){
        if(null == getOne(new LambdaQueryWrapper<Device>().eq(Device::getName,deviceName).eq(Device::getProductId,productId))){
            throw new AppException(AppStatus.NOT_FOUND,"deviceName="+deviceName);
        }
        Device device = MAP_STRUCT_CONVERTER.toDevice(request);
        device.setProductId(productId);
        update(device,new LambdaQueryWrapper<Device>().eq(Device::getName,deviceName).eq(Device::getProductId,productId));
    }

    /**
     * 删除设备
     * @param productId
     * @param deviceName
     */
    public void delete(String productId, String deviceName) {
        Device device = getOne(new LambdaQueryWrapper<Device>().eq(Device::getName, deviceName).eq(Device::getProductId,productId));
        if(null == device){
            throw  new AppException(AppStatus.NOT_FOUND,"deviceName="+deviceName);
        }
        if (!CollectionUtils.isEmpty(applicationDeviceService.list(new LambdaQueryWrapper<ApplicationDevice>().eq(ApplicationDevice::getDeviceName, device.getName())))) {
            throw new AppException(AppStatus.USING, "deviceName="+deviceName);
        }
        remove(new LambdaQueryWrapper<Device>().eq(Device::getName, deviceName).eq(Device::getProductId, productId));
    }

    /**
     * 删除产品下所有设备
     * @param productId
     */
    public void delete(String productId){
        List<Device> devices = list(new LambdaQueryWrapper<Device>().eq(Device::getProductId,productId));
        if(CollectionUtils.isEmpty(devices)){
            throw new AppException(AppStatus.NOT_FOUND,"NO devices");
        }
        for(Device device:devices){
            delete(productId,device.getName());
        }
    }


    /**
     * 获取设备列表
     * @param
     * @param request
     * @return
     */
    public PageResponse<Device> getList(String productId, DevicePageRequest request){
        Page<Device> page = new Page<>(request.getPageNum(), request.getPageSize());
        LambdaQueryWrapper<Device> queryWrapper = new LambdaQueryWrapper<Device>().eq(Device::getProductId, productId);
        if(StringUtils.isNotBlank(request.getName())){
            queryWrapper.like(Device::getName,request.getName());
        }
        if(null != request.getStatus()){
            queryWrapper.eq(Device::getStatus,request.getStatus());
        }
        baseMapper.selectPage(page,queryWrapper);
        return PageResponse.of(page);
    }

    /**
     * 获取设备详情
     * @param productId
     * @param deviceName
     * @return
     */
    public Device get(String productId,String deviceName){
        Device device = getOne(new LambdaQueryWrapper<Device>().eq(Device::getProductId,productId).eq(Device::getName,deviceName));
        if(null == device){
            throw new AppException(AppStatus.NOT_FOUND,"deviceName="+deviceName);
        }
        return device;
    }

}


