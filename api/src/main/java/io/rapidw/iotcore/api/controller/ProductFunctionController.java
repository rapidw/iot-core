package io.rapidw.iotcore.api.controller;

import io.rapidw.iotcore.api.request.FieldRequest;
import io.rapidw.iotcore.api.request.FunctionAddRequest;
import io.rapidw.iotcore.api.request.FunctionQueryRequest;
import io.rapidw.iotcore.api.request.FunctionUpdateRequest;
import io.rapidw.iotcore.api.response.FieldResponse;
import io.rapidw.iotcore.api.service.function.FunctionService;
import io.rapidw.iotcore.common.entity.function.Function;
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
@Api(tags = "功能")
@RequiredArgsConstructor
public class ProductFunctionController {

    private final FunctionService functionService;

    @PostMapping("/{id}/functions/")
    @ApiOperation(value = "新增功能", notes = "新增功能")
    public BaseResponse create(@ApiParam(name = "id", value = "产品id",required = true) @PathVariable("id") String id,
                               @Validated @RequestBody FunctionAddRequest functionRequest) {
        functionService.insert(id, functionRequest);
        return BaseResponse.ok();
    }

    @PutMapping("/{id}/functions/{functionId}")
    @ApiOperation(value = "修改功能", notes = "修改功能")
    public BaseResponse updateById(@ApiParam(name = "id", value = "产品id",required = true) @PathVariable("id") String id,
                                   @ApiParam(name = "functionId", value = "功能id",required = true) @PathVariable("functionId") String functionId,
                                   @Validated @RequestBody FunctionUpdateRequest functionRequest) {
        functionService.update(id, functionId, functionRequest);
        return BaseResponse.ok();
    }

    @DeleteMapping("/{id}/functions/{functionId}")
    @ApiOperation(value = "删除功能", notes = "删除功能")
    public BaseResponse deleteById(@ApiParam(name = "id", value = "产品id",required = true) @PathVariable("id") String id,
                                   @ApiParam(name = "functionId", required = true, value = "功能id") @PathVariable("functionId") String functionId) {
        functionService.deleteById(id, functionId);
        return BaseResponse.ok();
    }

    @GetMapping("/{id}/functions")
    @ApiOperation(value = "获取功能", notes = "获取功能")
    public PageResponse<Function> get(@ApiParam(name = "id", value = "产品id",required = true) @PathVariable("id") String id,
                                            FunctionQueryRequest functionRequest) {
        return PageResponse.of(functionService.getPage(id, functionRequest));
    }

    @PostMapping("/{id}/functions/{functionId}/fields")
    @ApiOperation(value = "新增(修改)字段", notes = "新增(修改)字段")
    public BaseResponse createFields(@ApiParam(name = "id", required = true,value = "产品id") @PathVariable("id") String id,
                                     @ApiParam(name = "functionId", required = true, value = "功能id") @PathVariable("functionId") String functionId,
                                     @Validated @RequestBody List<FieldRequest> fieldRequests) {
        functionService.createField(id, functionId, fieldRequests);
        return BaseResponse.ok();
    }

    @PutMapping("/{id}/functions/{functionId}/fields/{fieldId}")
    @ApiOperation(value = "修改字段", notes = "修改字段",hidden = true)
    public BaseResponse updateFields(@ApiParam(name = "id", value = "产品id",required = true) @PathVariable("id") String id,
                                     @ApiParam(name = "functionId", required = true, value = "功能id") @PathVariable("functionId") String functionId,
                                     @ApiParam(name = "fieldId", required = true, value = "字段id") @PathVariable("fieldId") String fieldId,
                                     @Validated @RequestBody FieldRequest fieldRequest) {
        functionService.updateField(id, functionId, fieldId, fieldRequest);
        return BaseResponse.ok();
    }

    @GetMapping("/{id}/functions/{functionId}/fields")
    @ApiOperation(value = "获取字段", notes = "获取字段")
    public DataResponse<List<FieldResponse>> getFields(@ApiParam(name = "id", value = "产品id",required = true) @PathVariable("id") String id,
                                                       @ApiParam(name = "functionId", required = true, value = "功能id") @PathVariable("functionId") String functionId) {
        return DataResponse.ok(functionService.getFields(id, functionId));
    }

    @DeleteMapping("/{id}/functions/{functionId}/fields/{fieldIds}")
    @ApiOperation(value = "删除字段", notes = "删除字段")
    public BaseResponse deleteFields(@ApiParam(name = "id", value = "产品id",required = true) @PathVariable("id") String id,
                                     @ApiParam(name = "functionId", required = true, value = "功能id") @PathVariable("functionId") String functionId,
                                     @ApiParam(name = "fieldIds", required = true, value = "字段id列表") @PathVariable("fieldIds") List<String> fieldIds) {
        functionService.deleteFields(id, functionId, fieldIds);
        return BaseResponse.ok();
    }
}
