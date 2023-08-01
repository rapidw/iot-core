package io.rapidw.iotcore.api.controller;


import io.rapidw.iotcore.api.request.ProductAddRequest;
import io.rapidw.iotcore.api.request.ProductQueryRequest;
import io.rapidw.iotcore.api.request.ProductUpdateRequest;
import io.rapidw.iotcore.api.response.ProductResponse;
import io.rapidw.iotcore.api.service.ProductService;
import io.rapidw.iotcore.common.entity.Product;
import io.rapidw.iotcore.common.response.BaseResponse;
import io.rapidw.iotcore.common.response.DataResponse;
import io.rapidw.iotcore.common.response.PageResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
@Api(tags = "产品")
public class ProductController {

    private final ProductService productService;


    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    @PostMapping
    @ApiOperation(value = "新增产品", notes = "新增产品")
    public DataResponse<ProductResponse> create(@Validated @RequestBody ProductAddRequest productRequest) {
        return DataResponse.ok(productService.insert(productRequest));
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "修改产品", notes = "修改产品")
    public BaseResponse update(@ApiParam(name = "id", required = true, value = "产品id") @PathVariable("id") String productId,
                               @Validated @RequestBody ProductUpdateRequest productRequest) {
        productService.update(productId, productRequest);
        return BaseResponse.ok();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "获取产品详情", notes = "获取产品详情")
    public DataResponse<Product> getById(@ApiParam(name = "id", required = true, value = "产品id") @PathVariable("id") String id) {
        return DataResponse.ok(productService.getOne(id));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除产品", notes = "删除产品")
    public BaseResponse deleteById(@ApiParam(name = "id", required = true, value = "产品id") @PathVariable("id") String id) {
        productService.delete(id);
        return BaseResponse.ok();
    }

    @GetMapping
    @ApiOperation(value = "分页获取产品", notes = "分页获取产品")
    public PageResponse<Product> getList(@Validated ProductQueryRequest pageRequest) {
        return PageResponse.of(productService.getList(pageRequest));
    }


}
