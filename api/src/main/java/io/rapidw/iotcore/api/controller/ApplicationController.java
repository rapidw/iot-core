package io.rapidw.iotcore.api.controller;

import io.rapidw.iotcore.api.request.ApplicationAddRequest;
import io.rapidw.iotcore.api.request.ApplicationQueryRequest;
import io.rapidw.iotcore.api.request.ApplicationUpdateRequest;
import io.rapidw.iotcore.api.response.ApplicationResponse;
import io.rapidw.iotcore.api.service.ApplicationService;
import io.rapidw.iotcore.common.entity.Application;
import io.rapidw.iotcore.common.response.BaseResponse;
import io.rapidw.iotcore.common.response.DataResponse;
import io.rapidw.iotcore.common.response.PageResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/applications")
@Api(tags = "应用")
public class ApplicationController {

    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @PostMapping
    @ApiOperation(value = "新增应用" )
    public DataResponse<ApplicationResponse> create(@Validated @RequestBody ApplicationAddRequest request){
        return DataResponse.ok(applicationService.create(request));
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "更新应用")
    public BaseResponse updateById(@ApiParam(name = "id", value = "应用id",required = true) @PathVariable("id") String id, @Validated
    @RequestBody ApplicationUpdateRequest request) {
        applicationService.update(id, request);
        return BaseResponse.ok();
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "根据id删除应用")
    public BaseResponse deleteById(@ApiParam(name = "id", value = "应用id",required = true) @PathVariable("id") String id) {
        applicationService.delete(id);
        return BaseResponse.ok();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "获取应用详情")
    public DataResponse<Application> getById(@ApiParam(name = "id", value = "应用id", required = true) @PathVariable("id") String id) {
        return DataResponse.ok(applicationService.getOne(id));
    }


    @GetMapping
    @ApiOperation(value = "分页获取应用")
    public PageResponse<Application> getList(ApplicationQueryRequest pageRequest){
        return applicationService.getAll(pageRequest);
    }


}
