package io.rapidw.iotcore.api.controller;

import io.rapidw.iotcore.api.request.ApplicationDeviceRequest;
import io.rapidw.iotcore.api.request.DevicePageRequest;
import io.rapidw.iotcore.api.service.ApplicationService;
import io.rapidw.iotcore.common.entity.Device;
import io.rapidw.iotcore.common.response.BaseResponse;
import io.rapidw.iotcore.common.response.PageResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/applications")
@Api(tags = "应用设备")
public class ApplicationDeviceController {

    private final ApplicationService applicationService;

    
    public ApplicationDeviceController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @PostMapping("/{id}/devices")
    @ApiOperation(value = "新增关联设备")
    public BaseResponse bindingDevice(@ApiParam(name="id",value = "应用id",required = true)@PathVariable("id")String id ,
                                      @Validated @RequestBody ApplicationDeviceRequest request){
        applicationService.bindingAppAndDevice(id,request);
        return BaseResponse.ok();
    }

    @GetMapping("/{id}/devices")
    @ApiOperation(value = "获取关联设备")
    public PageResponse<Device> getDevices(@ApiParam(name="id",value = "应用id",required = true)@PathVariable("id")String id, DevicePageRequest devicePageRequest){
        return PageResponse.of(applicationService.getList(id,devicePageRequest));
    }

    @DeleteMapping("/{id}/devices")
    @ApiOperation(value = "删除关联设备")
    public BaseResponse delete(@ApiParam(name = "id",value = "应用id",required = true)@PathVariable("id")String id ,
                               @ApiParam(name = "productId",value = "产品id",required = true)@RequestParam("productId")String productId,
                               @ApiParam(name = "deviceName",value = "设备名称列表",required = true)@RequestParam("deviceName") String deviceName){
        applicationService.deleteDevice(id,productId,deviceName);
        return BaseResponse.ok();
    }

}
