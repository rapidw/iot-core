package io.rapidw.iotcore.api.controller;

import io.rapidw.iotcore.api.request.ServiceCallRequest;
import io.rapidw.iotcore.api.response.ServiceCallResponse;
import io.rapidw.iotcore.api.service.ApplicationService;
import io.rapidw.iotcore.api.service.ProductService;
import io.rapidw.iotcore.common.response.DataResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
@Api(tags = "服务调用")
@RequiredArgsConstructor
public class ServiceController {

    private final ProductService productService;
    private final ApplicationService applicationService;

    @PostMapping(value = "/products/{id}/devices/{deviceName}/services/{functionId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "调用设备服务(产品)", notes = "调用设备服务(产品)")
    public String productService(@ApiParam(name = "id", required = true, value = "产品id") @PathVariable("id") String productId,
                                 @ApiParam(name = "deviceName", value = "设备名称",required = true) @PathVariable("deviceName") String deviceName,
                                 @ApiParam(name = "functionId" , value = "功能id",required = true) @PathVariable("functionId") String functionId,
                                 @RequestBody String requestString) {
        return productService.callService(productId, deviceName,functionId, requestString);
    }

    @PostMapping(value = "/applications/{id}/services", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "调用设备服务(应用)", notes = "调用设备服务(应用)")
    public DataResponse<String> appService(@ApiParam(name = "id", required = true, value = "应用id") @PathVariable("id") String id,
                         @ApiParam(name = "deviceName" , value = "设备名称",required = true) @RequestParam("deviceName") String deviceName,
                         @ApiParam(name = "productId" , value = "产品id",required = true) @RequestParam("productId") String productId,
                         @ApiParam(name = "functionId" , value = "功能id",required = true) @RequestParam("functionId") String functionId,
                         @RequestBody String requestString) {
        return DataResponse.ok(applicationService.callService(id,productId, deviceName,functionId, requestString));
    }

    @PostMapping(value = "/products/{id}/services/{functionId}/batch")
    @ApiOperation(value = "批量调用设备服务(产品)", notes = "批量调用设备服务(产品)")
    public DataResponse<List<ServiceCallResponse>> batchProductService(@ApiParam(name = "id", required = true, value = "产品id") @PathVariable("id") String productId,
                                                                       @ApiParam(name = "functionId" , value = "功能id",required = true) @PathVariable("functionId") String functionId,
                                                                       @RequestBody List<ServiceCallRequest> requests) throws InterruptedException, JsonProcessingException {
       return DataResponse.ok(productService.callServiceBatch(productId,functionId,requests));
    }
}
