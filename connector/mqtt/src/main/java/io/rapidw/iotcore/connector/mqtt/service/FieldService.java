package io.rapidw.iotcore.connector.mqtt.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.rapidw.iotcore.common.entity.ProductIdIncluded;
import io.rapidw.iotcore.common.entity.field.*;
import io.rapidw.iotcore.common.entity.function.Function;
import io.rapidw.iotcore.connector.mqtt.mapper.field.*;
import io.rapidw.iotcore.connector.mqtt.mapstruct.DtoMappers;
import lombok.*;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class FieldService {
    private final FieldMapper fieldMapper;
    private final FieldInt32Mapper fieldInt32Mapper;
    private final FieldInt64Mapper fieldInt64Mapper;
    private final FieldFloatMapper fieldFloatMapper;
    private final FieldDoubleMapper fieldDoubleMapper;
    private final FieldStringMapper fieldStringMapper;
    private final FieldStructMapper fieldStructMapper;
    private final EntryService entryService;

    public FieldService(FieldMapper fieldMapper, FieldInt32Mapper fieldInt32Mapper, FieldInt64Mapper fieldInt64Mapper,
                        FieldFloatMapper fieldFloatMapper, FieldDoubleMapper fieldDoubleMapper,
                        FieldStringMapper fieldStringMapper, FieldStructMapper fieldStructMapper,
                        EntryService entryService) {
        this.fieldMapper = fieldMapper;
        this.fieldInt32Mapper = fieldInt32Mapper;
        this.fieldInt64Mapper = fieldInt64Mapper;
        this.fieldFloatMapper = fieldFloatMapper;
        this.fieldDoubleMapper = fieldDoubleMapper;
        this.fieldStringMapper = fieldStringMapper;
        this.fieldStructMapper = fieldStructMapper;
        this.entryService = entryService;
    }

    public Map<String, FullField> getAllFields(String productId, String functionId, Function.Type type, Field.InOrOut inOrOut) {
        Map<String, FullField> map = new HashMap<>();

        val fields = fieldMapper.selectList(Wrappers.lambdaQuery(Field.class)
                .eq(ProductIdIncluded::getProductId, productId)
                .eq(Field::getFunctionId, functionId)
                .eq(Field::getInOrOut, inOrOut));

        fields.forEach(v -> {
            val fullField = DtoMappers.INSTANCE.fieldToFullField(v);
            switch (fullField.getType()) {
                case INT32:
                    fullField.setFieldInt32(fieldInt32Mapper.selectOne(Wrappers.lambdaQuery(FieldInt32.class)
                            .eq(FieldInt32::getFieldId, fullField.getId())));
                    break;
                case INT64:
                    fullField.setFieldInt64(fieldInt64Mapper.selectOne(Wrappers.lambdaQuery(FieldInt64.class)
                            .eq(FieldInt64::getFieldId, fullField.getId())));
                    break;
                case FLOAT:
                    fullField.setFieldFloat(fieldFloatMapper.selectOne(Wrappers.lambdaQuery(FieldFloat.class)
                            .eq(FieldFloat::getFieldId, fullField.getId())));
                    break;
                case DOUBLE:
                    fullField.setFieldDouble(fieldDoubleMapper.selectOne(Wrappers.lambdaQuery(FieldDouble.class)
                            .eq(FieldDouble::getFieldId, fullField.getId())));
                    break;
                case STRING:
                    fullField.setFieldString(fieldStringMapper.selectOne(Wrappers.lambdaQuery(FieldString.class)
                            .eq(FieldString::getFieldId, fullField.getId())));
                    break;
                case STRUCT:
                    val struct = fieldStructMapper.selectOne(Wrappers.lambdaQuery(FieldStruct.class)
                            .eq(FieldStruct::getFieldId, fullField.getId()));

                    val fullStruct = DtoMappers.INSTANCE.structToFullStruct(struct);
                    fullStruct.setEntries(entryService.getAllStructEntry(struct.getId()));
                    fullField.setFieldStruct(fullStruct);
                    break;
            }
            map.put(fullField.getIdentifier(), fullField);
        });
        return map;
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class FullField extends Field {
        private FieldInt32 fieldInt32;
        private FieldInt64 fieldInt64;
        private FieldFloat fieldFloat;
        private FieldDouble fieldDouble;
        private FieldString fieldString;
        private FullFieldStruct fieldStruct;
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FullFieldStruct extends FieldStruct {
        private Map<String, EntryService.FullEntry> entries;
    }
}
