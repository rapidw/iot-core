package io.rapidw.iotcore.api.mapper.log;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.rapidw.iotcore.api.request.ServiceLogQueryRequest;
import io.rapidw.iotcore.common.entity.devicelog.DeviceLogService;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DeviceServiceMapper extends BaseMapper<DeviceLogService> {

    /**
     * 获取设备日志数据
     */
    IPage<DeviceLogService> getByPage(Page<DeviceLogService> page,String productId,String deviceName,
                                        ServiceLogQueryRequest request);

}