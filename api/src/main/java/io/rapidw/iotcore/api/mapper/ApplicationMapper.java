package io.rapidw.iotcore.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.rapidw.iotcore.api.request.DevicePageRequest;
import io.rapidw.iotcore.common.entity.Application;
import io.rapidw.iotcore.common.entity.Device;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ApplicationMapper extends BaseMapper<Application> {


    IPage<Device> getDevices(Page<Device> page, String appId,DevicePageRequest request);

    @Select("select * from application a inner join application_device ad on a.identifier = ad.application_id inner join device d " +
            "on d.name = ad.device_name inner join product p on p.identifier = d.product_id inner join `function` f on f.product_id=p.identifier  " +
            "where a.identifier=#{appId} and p.identifier=#{productId} and d.name=#{deviceName} and f.identifier=#{functionId}")
    Object select(String appId,String productId,String deviceName,String functionId);

}