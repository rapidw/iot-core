package io.rapidw.iotcore.api.request;

import io.rapidw.iotcore.common.entity.field.Field;
import io.rapidw.iotcore.common.entity.struct.Entry;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@ApiModel(description = "字段请求实体")
public class FieldRequest {

     @ApiModelProperty(value = "字段id",required = true)
     @NotBlank(message = "fieldId不可为空！")
     private String fieldId;

     @ApiModelProperty(value = "字段名称",required = true)
     @NotBlank(message = "字段name不可为空！")
     private String name;

     @ApiModelProperty(value = "字段类型,对应类型属性名",required = true)
     @NotNull(message = "字段type不可为空！")
     private Field.Type type;

     @ApiModelProperty(value = "输入输出标识",required = true)
     @NotNull(message = "输入输出标识inOrOut不可为空！")
     private Field.InOrOut inOrOut;

     @ApiModelProperty("Double类型")
     private FieldDoubleRequest fieldDouble;

     @ApiModelProperty("Int64类型")
     private FieldInt64Request fieldInt64;

     @ApiModelProperty("Int32类型")
     private FieldInt32Request fieldInt32;

     @ApiModelProperty("Float类型")
     private FieldFloatRequest fieldFloat;

     @ApiModelProperty("String类型")
     private FieldStringRequest fieldString;

     @ApiModelProperty("Struct类型")
     private FieldStructRequest fieldStruct;


    @Data
    @ApiModel(description = "Int64类型返回体")
    public static class FieldInt64Request {

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
        private String unit;
    }

    @Data
    @ApiModel(description = "Double类型返回体")
    public static class FieldDoubleRequest {

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
        private String unit;
    }

    @Data
    @ApiModel(description = "Float类型返回体")
    public static class FieldFloatRequest {

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
        private String unit;

    }

    @Data
    @ApiModel(description = "Int32类型返回体")
    public static class FieldInt32Request {
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
        private String unit;
    }

    @Data
    @ApiModel(description = "String类型返回体")
    public static class FieldStringRequest {
        /**
         * 长度
         */
        @ApiModelProperty(value = "`length`")
        private Integer length;

    }

    @Data
    @ApiModel(description = "Struct类型返回体")
    public static class FieldStructRequest {
        @ApiModelProperty(value = "字段列表")
        @Valid
        @NotEmpty(message = "struct字段列表不可为空！")
        private List<StructField> fields;
    }
    @Data
    @ApiModel(description = "struct_entry字段")
    public static class StructField{

        @ApiModelProperty(value = "entryId",required = true)
        @NotBlank(message = "entryId不可为空！")
        private String entryId;

        @ApiModelProperty(value = "entry名称",required = true)
        @NotBlank(message = "entry name不可为空！")
        private String name;

        @ApiModelProperty(value = "字段类型,对应类型属性名",required = true)
        @NotNull(message = "entry type不可为空！")
        private Entry.Type type;

        @ApiModelProperty("Double类型")
        private FieldDoubleRequest fieldDouble;

        @ApiModelProperty("Int64类型")
        private FieldInt64Request fieldInt64;

        @ApiModelProperty("Int32类型")
        private FieldInt32Request fieldInt32;

        @ApiModelProperty("Float类型")
        private FieldFloatRequest fieldFloat;

        @ApiModelProperty("String类型")
        private FieldStringRequest fieldString;
    }

}
