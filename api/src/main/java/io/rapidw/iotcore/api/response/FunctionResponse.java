package io.rapidw.iotcore.api.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(description = "字段信息返回实体")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FunctionResponse {

    @ApiModelProperty("功能id")
    private String functionId;
    @ApiModelProperty("功能名称")
    private String functionName;

    @ApiModelProperty("功能描述")
    private String desc;

    @ApiModelProperty(value = "功能字段",dataType = "list")
    private List<Field> fields;



    @ApiModel(description = "返回字段实体")
    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Field{
        @ApiModelProperty("字段名称")
        private String name;
        @ApiModelProperty(value = "字段类型,对应类型属性名",example = "fieldDouble,fieldInt64,fieldInt32,fieldFloat,fieldString")
        private String type;
        @ApiModelProperty("输入输出标识 1-输入  0-输出")
        private Integer inOrOut;

        @ApiModelProperty("Double类型")
        private FieldDouble fieldDouble;

        @ApiModelProperty("Int64类型")
        private FieldInt64 fieldInt64;

        @ApiModelProperty("Int32类型")
        private FieldInt32 fieldInt32;

        @ApiModelProperty("Float类型")
        private FieldFloat fieldFloat;

        @ApiModelProperty("String类型")
        private FieldString fieldString;

        @ApiModelProperty("Struct类型")
        private FieldStruct fieldStruct;
    }


    
    
    

    @Data
    @ApiModel(description = "Int64类型返回体")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class FieldInt64 {

        /**
         * 最大值
         */
        @ApiModelProperty(value = "最大值")
        private Long max;
    
        /**
         * 最小值
         */
        @ApiModelProperty(value = "最小值")
        private Long min;
    
        /**
         * 步长
         */
        @ApiModelProperty(value = "步长")
        private Double step;
    
        /**
         * 单位
         */
        @ApiModelProperty(value = "单位")
        private Integer unit;
    

    }

    @Data
    @ApiModel(description = "Double类型返回体")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class FieldDouble {


    
        /**
         * 最大值
         */
        @ApiModelProperty(value = "最大值")
        private Double max;
    
        /**
         * 最小值
         */
        @ApiModelProperty(value = "最小值")
        private Double min;
    
        /**
         * 步长
         */
        @ApiModelProperty(value = "步长")
        private Double step;
    
        /**
         * 单位
         */
        @ApiModelProperty(value = "单位")
        private Integer unit;
    

    }

    @Data
    @ApiModel(description = "Float类型返回体")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class FieldFloat {

    
        /**
         * 最大值
         */
        @ApiModelProperty(value = "最大值")
        private Float max;
    
        /**
         * 最小值
         */
        @ApiModelProperty(value = "最小值")
        private Float min;
    
        /**
         * 步长
         */
        @ApiModelProperty(value = "步长")
        private Double step;
    
        /**
         * 单位
         */
        @ApiModelProperty(value = "单位")
        private Integer unit;
    

    

    }

    @Data
    @ApiModel(description = "Int32类型返回体")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class FieldInt32 {

        /**
         * 最大值
         */
        @ApiModelProperty(value = "最大值")
        private Integer max;
    
        /**
         * 最小值
         */
        @ApiModelProperty(value = "最小值")
        private Integer min;
    
        /**
         * 步长
         */
        @ApiModelProperty(value = "步长")
        private Double step;
    
        /**
         * 单位
         */
        @ApiModelProperty(value = "单位")
        private Integer unit;
    


    }

    @Data
    @ApiModel(description = "String类型返回体")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class FieldString {

    
        /**
         * 长度
         */
        @ApiModelProperty(value = "`length`")
        private Integer length;

    }

    @Data
    @ApiModel(description = "Struct类型返回体")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class FieldStruct {

        @ApiModelProperty(value = "字段列表")
        private List<Field> fields;
    
    
    
    }
}
