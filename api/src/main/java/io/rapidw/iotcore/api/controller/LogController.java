package io.rapidw.iotcore.api.controller;

import io.rapidw.iotcore.api.request.ServiceLogQueryRequest;
import io.rapidw.iotcore.api.service.log.DeviceLogServiceService;
import io.rapidw.iotcore.common.entity.devicelog.DeviceLogService;
import io.rapidw.iotcore.common.response.PageResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@Api(tags = "设备服务日志")
public class LogController {

    private final DeviceLogServiceService deviceLogServiceService;

    public LogController(DeviceLogServiceService deviceLogServiceService) {
        this.deviceLogServiceService = deviceLogServiceService;
    }

    @GetMapping("/products/{id}/devices/{deviceName}/logs/services")
    @ApiOperation(notes = "获取服务日志(产品)", value = "获取服务日志(产品)")
    public PageResponse<DeviceLogService> getDeviceData(@ApiParam(name = "id", value = "产品id",required = true) @PathVariable("id") String id,
                                                         @ApiParam(name = "deviceName", value = "设备名称",required = true) @PathVariable("deviceName") String deviceName,
                                                         @Validated ServiceLogQueryRequest request) {
        return PageResponse.of(deviceLogServiceService.getPage(null,id,deviceName,request));
    }

    @GetMapping("/applications/{id}/logs/services")
    @ApiOperation(notes = "获取服务日志(应用)", value = "获取服务日志(应用)")
    public PageResponse<DeviceLogService> getDeviceData(@ApiParam(name = "id", value = "应用id",required = true) @PathVariable("id") String id,
                                                           @ApiParam(name = "deviceName", value = "设备名称",required = true) @RequestParam("deviceName") String deviceName,
                                                           @ApiParam(name = "productId", value = "产品id",required = true) @RequestParam("productId") String productId,
                                                           @Validated ServiceLogQueryRequest request) {
        return PageResponse.of(deviceLogServiceService.getPage(id,productId,deviceName,request));
    }
}
