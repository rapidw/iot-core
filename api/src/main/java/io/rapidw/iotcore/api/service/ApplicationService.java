package io.rapidw.iotcore.api.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.rapidw.iotcore.common.mapper.ApplicationMapper;
import io.rapidw.iotcore.api.request.*;
import io.rapidw.iotcore.api.response.ApplicationResponse;
import io.rapidw.iotcore.api.utils.StringUtil;
import io.rapidw.iotcore.common.entity.Application;
import io.rapidw.iotcore.common.entity.ApplicationDevice;
import io.rapidw.iotcore.common.entity.Device;
import io.rapidw.iotcore.common.exception.AppException;
import io.rapidw.iotcore.common.exception.AppStatus;
import io.rapidw.iotcore.common.response.PageResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static io.rapidw.iotcore.api.mapstruct.MapStructConverter.MAP_STRUCT_CONVERTER;

@Service
@RequiredArgsConstructor
public class ApplicationService extends ServiceImpl<ApplicationMapper, Application> implements IService<Application> {

    private final DeviceService deviceService;
    private final ApplicationDeviceService applicationDeviceService;
    private final CallServiceService callServiceService;


    /**
     * 创建应用
     * @param request
     * @return
     */
    public ApplicationResponse create(ApplicationAddRequest request){
        if(null != getOne(new LambdaQueryWrapper<Application>().eq(Application::getName,request.getName()))){
            throw new AppException(AppStatus.CONFLICT,"name:"+request.getName());
        }
        Application application = MAP_STRUCT_CONVERTER.toApplication(request);
        String identifier = StringUtil.generateUUID();
        application.setIdentifier(identifier);
        String key = StringUtil.generateKey();
        application.setKey(key);
        application.setCreateTime(Instant.now());
        save(application);
        ApplicationResponse response = new ApplicationResponse();
        response.setAppId(identifier);
        response.setKey(key);
        return response;
    }

    public Application getOne(String appId){
        return getOne(new LambdaQueryWrapper<Application>().eq(Application::getIdentifier,appId));
    }

    /**
     * 更新应用
     *
     * @param appId
     * @param request
     * @return
     */
    public void update(String appId, ApplicationUpdateRequest request) {
        if (null != getOne(new LambdaQueryWrapper<Application>().eq(Application::getName, request.getName()))) {
            throw new AppException(AppStatus.CONFLICT, "name=" + request.getName());
        }
        Application application =  MAP_STRUCT_CONVERTER.toApplication(request);
        update(application,new LambdaQueryWrapper<Application>().eq(Application::getIdentifier,appId));
    }


    public void delete(String appId) {
        if(!CollectionUtils.isEmpty(applicationDeviceService.list(new LambdaQueryWrapper<ApplicationDevice>().eq(ApplicationDevice::getApplicationId,appId)))){
            throw new AppException(AppStatus.USING,"该应用下存在使用中的设备！");
        }
        remove(new LambdaQueryWrapper<Application>().eq(Application::getIdentifier,appId));
        applicationDeviceService.remove(new LambdaQueryWrapper<ApplicationDevice>().eq(ApplicationDevice::getApplicationId, appId));
    }


    public PageResponse<Application> getAll(ApplicationQueryRequest pageRequest) {
        Page<Application> page = new Page<>(pageRequest.getPageNum(), pageRequest.getPageSize());
        baseMapper.selectPage(page, StringUtils.isBlank(pageRequest.getName()) ? null : new QueryWrapper<Application>().like("name", pageRequest.getName()));
        return PageResponse.of(page);
    }


    /**
     * 应用绑定设备
     *
     * @param applicationId
     * @param request
     */
    @Transactional(rollbackFor = {Exception.class})
    public void bindingAppAndDevice(String applicationId, ApplicationDeviceRequest request) {
        List<ApplicationDeviceRequest.AppDevice> devices = request.getDevices();
        List<ApplicationDevice> applicationDevices = new ArrayList<>(request.getDevices().size());
        ApplicationDevice applicationDevice;
        for(ApplicationDeviceRequest.AppDevice device:devices){
            //忽略已存在的设备
            if(null != applicationDeviceService.getOne(new LambdaQueryWrapper<ApplicationDevice>().eq(ApplicationDevice::getDeviceName,device.getDeviceName())
                    .eq(ApplicationDevice::getProductId,device.getProductId()))){
                continue;
            }
            Device device1 = deviceService.getOne(new LambdaQueryWrapper<Device>().eq(Device::getName,device.getDeviceName())
                    .eq(Device::getProductId,device.getProductId()));
            if(null == device1){
                throw new AppException(AppStatus.NOT_FOUND,"deviceName="+device.getDeviceName()+",productId="+device.getProductId());
            }
            applicationDevice = ApplicationDevice.builder().applicationId(applicationId).deviceName(device1.getName())
                    .productId(device1.getProductId()).build();
            applicationDevices.add(applicationDevice);
        }
        applicationDeviceService.saveBatch(applicationDevices);
    }


    /**
     * 获取应用下设备列表
     *
     * @param appId
     * @param pageRequest
     * @return
     */
    public Page<Device> getList(String appId, DevicePageRequest pageRequest) {
        Page<Device> page = new Page<>(pageRequest.getPageNum(), pageRequest.getPageSize());
        baseMapper.getDevices(page, appId, pageRequest.getName(), pageRequest.getStatus());
        return page;
    }

    /**
     * 删除产品下绑定设备
     *
     * @param appId
     * @param
     */
    public void deleteDevice(String appId,String productId, String deviceName) {
        applicationDeviceService.delete(productId,deviceName,appId);
    }


    public String callService(String appId,String productId, String deviceName, String functionId, String request) {
        if (null == baseMapper.select(appId,productId,deviceName,functionId)) {
            throw new AppException(AppStatus.NOT_FOUND);
        }
        return callServiceService.callService(productId, deviceName, functionId,request);
    }
}


