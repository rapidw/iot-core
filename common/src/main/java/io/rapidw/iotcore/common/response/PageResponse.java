package io.rapidw.iotcore.common.response;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.rapidw.iotcore.common.exception.AppStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@ApiModel(description = "翻页请求返回体")
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public final class PageResponse<T> extends io.rapidw.iotcore.common.response.BaseResponse {


    @ApiModelProperty(value = "翻页请求返回数据")
    private PageData<T> data;

    private PageResponse(PageData<T> data) {
        super(AppStatus.SUCCESS);
        this.data = data;
    }


    public static <T> PageResponse<T> of(Page<T> page) {
        PageData<T> data = new PageData<T>(page);
        return new PageResponse<>(data);
    }

    public static Object of(IPage name) {
        return name;
    }


    @Data
    @ApiModel(description = "分页数据实体")
    public static class PageData<T> {

        @ApiModelProperty(value = "分页后的内容")
        private List<T> data;

        /**
         * 分页属性
         */
        @ApiModelProperty(value = "当前页面序号", example = "1")
        private long pageNum;
        @ApiModelProperty(value = "当前页面大小", example = "20")
        private long pageSize;
        @ApiModelProperty(value = "总页面数", example = "5")
        private long pages;
        @ApiModelProperty(value = "总记录数", example = "100")
        private long total;

        PageData(Page<T> page) {
            this.data = page.getRecords();
            this.pageNum = page.getCurrent();
            this.pageSize = page.getTotal();
            this.pages = page.getPages();
            this.total = page.getTotal();
        }
    }
}
