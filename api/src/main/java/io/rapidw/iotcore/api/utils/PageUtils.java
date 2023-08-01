package io.rapidw.iotcore.api.utils;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.rapidw.iotcore.common.request.PageRequest;

public class PageUtils {

    public static <T>  Page<T> getPage(PageRequest pageRequest){
        return new Page<>(pageRequest.getPageNum(), pageRequest.getPageSize());
    }
}
