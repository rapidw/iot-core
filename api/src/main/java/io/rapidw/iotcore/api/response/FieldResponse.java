package io.rapidw.iotcore.api.response;

import io.rapidw.iotcore.common.entity.field.*;
import io.rapidw.iotcore.common.entity.struct.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(description = "字段信息返回实体")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FieldResponse {

    @ApiModelProperty(value = "fieldId")
    private String fieldId;
    @ApiModelProperty(value = "字段名称")
    private String name;
    @ApiModelProperty(value = "字段类型,对应类型属性名")
    private io.rapidw.iotcore.common.entity.field.Field.Type type;
    @ApiModelProperty(value = "输入输出标识")
    private io.rapidw.iotcore.common.entity.field.Field.InOrOut inOrOut;
    @ApiModelProperty(value = "Double类型")
    private FieldDouble fieldDouble;
    @ApiModelProperty(value = "Int64类型")
    private FieldInt64 fieldInt64;
    @ApiModelProperty(value = "Int32类型")
    private FieldInt32 fieldInt32;
    @ApiModelProperty(value = "Float类型")
    private FieldFloat fieldFloat;
    @ApiModelProperty(value = "String类型")
    private FieldString fieldString;
    @ApiModelProperty(value = "Struct类型")
    private FieldStruct fieldStruct;

    @Data
    @ApiModel(description = "Struct类型返回体")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class FieldStruct {

        @ApiModelProperty(value = "struct字段列表")
        private List<Field> fields;

    }

    @ApiModel(description = "struct返回字段实体")
    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Field {

        @ApiModelProperty("entry_id")
        private String entryId;
        @ApiModelProperty("字段名称")
        private String name;
        @ApiModelProperty(value = "字段类型,对应类型属性名",example = "")
        private io.rapidw.iotcore.common.entity.field.Field.Type type;
        @ApiModelProperty("输入输出标识 1-输入  0-输出")
        private Integer inOrOut;
        @ApiModelProperty("Double类型")
        private EntryDouble fieldDouble;
        @ApiModelProperty("Int64类型")
        private EntryInt64 fieldInt64;
        @ApiModelProperty("Int32类型")
        private EntryInt32 fieldInt32;
        @ApiModelProperty("Float类型")
        private EntryFloat fieldFloat;
        @ApiModelProperty("String类型")
        private EntryString fieldString;

    }

}
