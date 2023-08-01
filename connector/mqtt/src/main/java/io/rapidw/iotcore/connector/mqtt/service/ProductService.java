package io.rapidw.iotcore.connector.mqtt.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.rapidw.iotcore.common.entity.Product;
import io.rapidw.iotcore.connector.mqtt.mapper.ProductMapper;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductMapper productMapper;

    public ProductService(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    public Product get(String identifier) {
       return productMapper.selectOne(Wrappers.lambdaQuery(Product.class).eq(Product::getIdentifier, identifier));
    }
}
