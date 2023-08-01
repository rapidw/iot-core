package io.rapidw.iotcore.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;


import io.rapidw.iotcore.common.entity.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ProductMapper extends BaseMapper<Product> {

    @Select("select * from product p inner join device d on d.product_id=p.identifier inner join `function` f on f.product_id = p.identifier" +
            " where p.identifier = #{productId} and d.name = #{deviceName} and f.identifier = #{functionId}")
    Object select(String productId,String deviceName,String functionId);
}
