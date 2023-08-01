package io.rapidw.iotcore.api.controller;

import io.rapidw.iotcore.api.request.DeviceAddRequest;
import io.rapidw.iotcore.api.request.DevicePageRequest;
import io.rapidw.iotcore.api.request.DeviceUpdateRequest;
import io.rapidw.iotcore.api.service.DeviceService;
import io.rapidw.iotcore.common.entity.Device;
import io.rapidw.iotcore.common.response.BaseResponse;
import io.rapidw.iotcore.common.response.DataResponse;
import io.rapidw.iotcore.common.response.PageResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@Api(tags = "产品设备")
@RequiredArgsConstructor
public class ProductDeviceController {

    private final DeviceService deviceService;

    @PostMapping("/{id}/devices")
    @ApiOperation(value = "新增设备", notes = "新增设备")
    public BaseResponse create(@ApiParam(name = "id", value = "产品id",required = true) @PathVariable("id") String id,
                               @Validated@RequestBody DeviceAddRequest deviceRequest) {
        deviceService.create(id, deviceRequest);
        return BaseResponse.ok();
    }

    @PostMapping("/{id}/devices-batch")
    @ApiOperation(value = "批量新增设备", notes = "批量新增设备")
    public BaseResponse batchCreate(@ApiParam(name = "id", value = "产品id",required = true) @PathVariable("id") String id,
                                    @Validated @RequestBody List<DeviceAddRequest> deviceRequests) {
        deviceService.createBatch(id, deviceRequests);
        return BaseResponse.ok();
    }

    @PutMapping("/{id}/devices/{deviceName}")
    @ApiOperation(value = "更新设备", notes = "更新设备")
    public BaseResponse updateById(@ApiParam(name = "id", value = "产品id",required = true) @PathVariable("id") String id,
                                   @ApiParam(name = "deviceName", value = "设备名称",required = true) @PathVariable("deviceName") String deviceName,
                                   @Validated @RequestBody DeviceUpdateRequest deviceRequest) {
        deviceService.update(id, deviceName, deviceRequest);
        return BaseResponse.ok();
    }

    @DeleteMapping("/{id}/devices/{deviceName}")
    @ApiOperation(value = "删除设备", notes = "删除设备")
    public BaseResponse deleteById(@ApiParam(name = "id", value = "产品id",required = true) @PathVariable("id") String id,
                                   @ApiParam(name = "deviceName", required = true, value = "设备id") @PathVariable("deviceName") String deviceName) {
        deviceService.delete(id, deviceName);
        return BaseResponse.ok();
    }

    @DeleteMapping("/{id}/devices")
    @ApiOperation(value = "删除所有设备")
    public BaseResponse deleteBatch(@ApiParam(name = "id", value = "产品id",required = true) @PathVariable("id") String id){
        deviceService.delete(id);
        return BaseResponse.ok();
    }

    @GetMapping("/{id}/devices/{deviceName}")
    @ApiOperation(value = "获取设备详情")
    public DataResponse<Device> getDevice(@ApiParam(name = "id", value = "产品id",required = true) @PathVariable("id") String id,
                                         @ApiParam(name = "deviceName", value = "设备名称",required = true) @PathVariable("deviceName") String deviceName){
        return DataResponse.ok(deviceService.get(id,deviceName));
    }

    @GetMapping("/{id}/devices")
    @ApiOperation(value = "分页获取设备")
    public PageResponse<Device> getDevices(@ApiParam(name = "id", value = "产品id",required = true) @PathVariable("id") String id,
                                           DevicePageRequest pageRequest) {
        return deviceService.getList(id, pageRequest);
    }

}
