package io.rapidw.iotcore.api.service.function;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.rapidw.iotcore.api.mapper.function.FunctionMapper;
import io.rapidw.iotcore.api.request.FieldRequest;
import io.rapidw.iotcore.api.request.FunctionAddRequest;
import io.rapidw.iotcore.api.request.FunctionQueryRequest;
import io.rapidw.iotcore.api.request.FunctionUpdateRequest;
import io.rapidw.iotcore.api.response.FieldResponse;
import io.rapidw.iotcore.api.service.field.FieldService;
import io.rapidw.iotcore.api.service.log.DeviceLogServiceService;
import io.rapidw.iotcore.api.utils.PageUtils;
import io.rapidw.iotcore.common.entity.devicelog.DeviceLogService;
import io.rapidw.iotcore.common.entity.function.Function;
import io.rapidw.iotcore.common.exception.AppException;
import io.rapidw.iotcore.common.exception.AppStatus;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

import static io.rapidw.iotcore.api.mapstruct.MapStructConverter.MAP_STRUCT_CONVERTER;

@Service
public class FunctionService extends ServiceImpl<FunctionMapper, Function> implements IService<Function> {


    private final FieldService fieldService;
    private final DeviceLogServiceService deviceServiceService;

    
    public FunctionService(FieldService fieldService,  DeviceLogServiceService deviceServiceService) {
        this.fieldService = fieldService;
        this.deviceServiceService = deviceServiceService;
    }


    public Boolean insert(String productId, FunctionAddRequest request) {
        if(null != getOne(Wrappers.lambdaQuery(Function.class).eq(Function::getProductId,productId)
                .eq(Function::getIdentifier,request.getFunctionId()))){
            throw new AppException(AppStatus.CONFLICT,"functionId="
                    +request.getFunctionId());
        }
        if(null != getOne(Wrappers.lambdaQuery(Function.class).eq(Function::getProductId,productId)
                .eq(Function::getName,request.getName()))){
            throw new AppException(AppStatus.CONFLICT,"name="
                    +request.getName());
        }
        Function function = MAP_STRUCT_CONVERTER.toFunction(request);
        function.setIdentifier(request.getFunctionId());
        function.setProductId(productId);
        return save(function);
    }

    
    public Boolean update(String productId, String functionId, FunctionUpdateRequest request) {
        LambdaQueryWrapper<Function> lambdaQueryWrapper = Wrappers.lambdaQuery(Function.class).
                eq(Function::getIdentifier,functionId).eq(Function::getProductId,productId);
        if(null == getOne(lambdaQueryWrapper)){
            throw new AppException(AppStatus.NOT_FOUND,"functionId="+functionId);
        }
        if(null != getOne((Wrappers.lambdaQuery(Function.class).eq(Function::getProductId,productId)
                .eq(Function::getName,request.getName())))){
            throw new AppException(AppStatus.CONFLICT,"name="
                    +request.getName());
        }
        Function function = MAP_STRUCT_CONVERTER.toFunction(request);
        return update(function,lambdaQueryWrapper);
    }

    /**
     * 根据productId及functionId删除
     * @param productId
     * @param functionId
     * @return
     */
    @Transactional(rollbackFor = {Exception.class})
    public void deleteById(String productId, String functionId){
        LambdaQueryWrapper<Function> lambdaQueryWrapper = Wrappers.lambdaQuery(Function.class).eq(Function::getIdentifier,functionId)
                .eq(Function::getProductId,productId);
        if(null == getOne(lambdaQueryWrapper)){
            throw new AppException(AppStatus.NOT_FOUND,"functionId="+functionId);
        }
        if(!CollectionUtils.isEmpty(deviceServiceService.list(Wrappers.lambdaQuery(DeviceLogService.class).eq(DeviceLogService::getFunctionId,functionId)
                .eq(DeviceLogService::getProductId,productId)))){
            throw new AppException(AppStatus.USING,"functionId="+functionId);
        }
        remove(lambdaQueryWrapper);
        fieldService.deleteByFunctionId(functionId);
    }

    /**
     * 根据productId删除功能
     * @param productId
     */
    public void deleteByProductId(String productId){
        remove(Wrappers.lambdaQuery(Function.class).eq(Function::getProductId,productId));
        fieldService.deleteByProductId(productId);
    }



    public Page<Function> getPage(String productId, FunctionQueryRequest functionRequest){
        Page<Function> page = PageUtils.getPage(functionRequest);
        LambdaQueryWrapper<Function> queryWrapper = Wrappers.lambdaQuery(Function.class).eq(Function::getProductId,productId);
        if(StringUtils.isNotBlank(functionRequest.getName())){
            queryWrapper.like(Function::getName,functionRequest.getName());
        }
        if(null!=functionRequest.getType()){
            queryWrapper.eq(Function::getType,functionRequest.getType());
        }
        if(!CollectionUtils.isEmpty(functionRequest.getFunctionIds())){
            queryWrapper.in(Function::getIdentifier,functionRequest.getFunctionIds());
        }
        baseMapper.selectPage(page,queryWrapper);
        return page;
    }


    /**
     * 功能下新增字段
     * @param productId
     * @param id
     * @param fields
     */
    @Transactional(rollbackFor = {Exception.class})
    public void createField(String productId, String id, List<FieldRequest> fields){
        if(null == getOne( Wrappers.lambdaQuery(Function.class).eq(Function::getProductId,productId).eq(Function::getIdentifier,id))){
            throw new AppException(AppStatus.NOT_FOUND,"functionId="+id);
        }
        if(!CollectionUtils.isEmpty(deviceServiceService.list(new LambdaQueryWrapper<DeviceLogService>().eq(DeviceLogService::getFunctionId,id)
                .eq(DeviceLogService::getProductId,productId)))){
            throw new AppException(AppStatus.USING,"functionId="+id);
        }
        fieldService.deleteByFunctionId(id);
        fieldService.insert(id,fields,productId);
    }


    /**
     * 根据功能id获取字段
     * @param productId
     * @param functionId
     * @return·
     */
    public List<FieldResponse> getFields(String productId,String functionId){
        if(null == getOne(new LambdaQueryWrapper<Function>().eq(Function::getIdentifier,functionId).eq(Function::getProductId,productId))){
            throw new AppException(AppStatus.NOT_FOUND,"functionId="+functionId);
        }
        return fieldService.getByFunctionId(functionId);
    }


    /**
     * 更新功能字段
     * @param productId
     * @param functionId
     * @param fieldId
     * @param fieldRequest
     */
    @Transactional(rollbackFor = {Exception.class})
    public void updateField(String productId,String functionId,String fieldId,FieldRequest fieldRequest){
        if(null == getOne(new LambdaQueryWrapper<Function>().eq(Function::getProductId,productId).eq(Function::getIdentifier,functionId))){
            throw new AppException(AppStatus.NOT_FOUND,"functionId="+functionId);
        }
        fieldService.update(fieldId, functionId, fieldRequest,productId);
    }

    /**
     * 根据fieldIds列表批量删除功能字段
     * @param productId
     * @param functionId
     * @param fieldIds
     */
    @Transactional(rollbackFor = {Exception.class})
    public void deleteFields(String productId,String functionId,List<String> fieldIds){
        if(null == getOne(new LambdaQueryWrapper<Function>().eq(Function::getProductId,productId).eq(Function::getIdentifier,functionId))){
            throw new AppException(AppStatus.NOT_FOUND,"functionId="+functionId);
        }
        if(!CollectionUtils.isEmpty(deviceServiceService.list(new LambdaQueryWrapper<DeviceLogService>().eq(DeviceLogService::getFunctionId,functionId)
                .eq(DeviceLogService::getProductId,productId)))){
            throw new AppException(AppStatus.USING,"functionId="+functionId);
        }
        fieldService.deleteByFieldIds(fieldIds,functionId,productId);
    }


}


