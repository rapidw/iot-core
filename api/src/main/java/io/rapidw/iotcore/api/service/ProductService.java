package io.rapidw.iotcore.api.service;

import com.alibaba.nacos.api.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.rapidw.iotcore.api.mapper.ProductMapper;
import io.rapidw.iotcore.api.request.ProductAddRequest;
import io.rapidw.iotcore.api.request.ProductQueryRequest;
import io.rapidw.iotcore.api.request.ProductUpdateRequest;
import io.rapidw.iotcore.api.request.ServiceCallRequest;
import io.rapidw.iotcore.api.response.ProductResponse;
import io.rapidw.iotcore.api.response.ServiceCallResponse;
import io.rapidw.iotcore.api.service.function.FunctionService;
import io.rapidw.iotcore.api.utils.PageUtils;
import io.rapidw.iotcore.api.utils.StringUtil;
import io.rapidw.iotcore.common.entity.Device;
import io.rapidw.iotcore.common.entity.Product;
import io.rapidw.iotcore.common.exception.AppException;
import io.rapidw.iotcore.common.exception.AppStatus;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.Instant;
import java.util.List;

import static io.rapidw.iotcore.api.mapstruct.MapStructConverter.MAP_STRUCT_CONVERTER;

@Service
public class ProductService extends ServiceImpl<ProductMapper, Product> implements IService<Product> {


    private final FunctionService functionService;
    private final DeviceService deviceService;
    private final CallServiceService callServiceService;

    
    public ProductService(FunctionService functionService, DeviceService deviceService,
                          CallServiceService callServiceService) {
        this.functionService = functionService;
        this.deviceService = deviceService;
        this.callServiceService = callServiceService;
    }


    public ProductResponse insert(ProductAddRequest request) {
        if(getOne(new LambdaQueryWrapper<Product>().eq(Product::getName,request.getName()))!=null){
            throw new AppException(AppStatus.CONFLICT, "name="+request.getName());
        }
        Product product = MAP_STRUCT_CONVERTER.toProduct(request);
        String key = StringUtil.generateKey();
        String productId = StringUtil.generateUUID();
        product.setKey(key);
        product.setIdentifier(productId);
        product.setCreateTime(Instant.now());
        baseMapper.insert(product);
        ProductResponse responseDto = new ProductResponse();
        responseDto.setProductId(productId);
        responseDto.setKey(key);
        return responseDto;
    }


    public boolean update(String productId, ProductUpdateRequest request) {
        Product product = MAP_STRUCT_CONVERTER.toProduct(request);
        return update(product,new LambdaQueryWrapper<Product>().eq(Product::getIdentifier,productId));
    }

    public Product getOne(String productId){
        return getOne(new LambdaQueryWrapper<Product>().eq(Product::getIdentifier, productId));
    }


    public Page<Product> getList(ProductQueryRequest request) {
        Page<Product> page = PageUtils.getPage(request);
        baseMapper.selectPage(page, StringUtils.isBlank(request.getName())?null:new LambdaQueryWrapper<Product>().like(Product::getName,request.getName()));
        return page;
    }

    @Transactional(rollbackFor = {Exception.class})
    public void delete(String productId) {
        if(!CollectionUtils.isEmpty(deviceService.list(new LambdaQueryWrapper<Device>().eq(Device::getProductId,productId)))){
            throw new AppException(AppStatus.USING,"该产品下存在使用中的设备！");
        }
        remove(new LambdaQueryWrapper<Product>().eq(Product::getIdentifier,productId));
        functionService.deleteByProductId(productId);
    }


    public String callService(String productId, String deviceName, String functionId, String request) {
        if (null == baseMapper.select(productId,deviceName,functionId)) {
            throw new AppException(AppStatus.NOT_FOUND, "deviceName="+deviceName+",functionId="+functionId);
        }
        return callServiceService.callService(productId, deviceName,functionId, request);
    }


    public List<ServiceCallResponse> callServiceBatch(String productId, String functionId, List<ServiceCallRequest> serviceCallRequests) throws InterruptedException, JsonProcessingException {
        serviceCallRequests.forEach(s->{
            if (null == baseMapper.select(productId,s.getDeviceName(),functionId)) {
                throw new AppException(AppStatus.NOT_FOUND, "deviceName="+s.getDeviceName()+",functionId="+functionId);
            }
        });
        return callServiceService.callService(productId, functionId, serviceCallRequests);
    }



}



