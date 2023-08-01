package io.rapidw.iotcore.api.service.field;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.rapidw.iotcore.api.request.FieldRequest;
import io.rapidw.iotcore.api.response.FieldResponse;
import io.rapidw.iotcore.api.service.log.DeviceLogServiceService;
import io.rapidw.iotcore.common.entity.devicelog.DeviceLogService;
import io.rapidw.iotcore.common.entity.field.*;
import io.rapidw.iotcore.common.exception.AppException;
import io.rapidw.iotcore.common.exception.AppStatus;
import io.rapidw.iotcore.common.mapper.field.FieldMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static io.rapidw.iotcore.api.mapstruct.MapStructConverter.MAP_STRUCT_CONVERTER;

@Service
@RequiredArgsConstructor
public class FieldService extends ServiceImpl<FieldMapper, Field> implements IService<Field> {

    private final Int32Service int32Service;
    private final Int64Service int64Service;
    private final DoubleService doubleService;
    private final FloatService floatService;
    private final StringService stringService;
    private final StructService structService;

    private final DeviceLogServiceService deviceServiceService;

    public void insert(String functionId, List<FieldRequest> fields, String productId) {
        for (FieldRequest field : fields) {
            if (null != getOne(new LambdaQueryWrapper<Field>().eq(Field::getFunctionId, functionId).eq(Field::getName, field.getName()))) {
                throw new AppException(AppStatus.CONFLICT, "field name=" + field.getName());
            }
            if (null != getOne(new LambdaQueryWrapper<Field>().eq(Field::getFunctionId, functionId).eq(Field::getIdentifier, field.getFieldId()))) {
                throw new AppException(AppStatus.CONFLICT, "fieldId=" + field.getFieldId());
            }
            Field field1 = MAP_STRUCT_CONVERTER.toField(field);
            field1.setFunctionId(functionId);
            field1.setIdentifier(field.getFieldId());
            field1.setProductId(productId);
            save(field1);
            handleFieldType(field.getType(), field1.getId(), field, productId);
        }
    }


    public void update(String fieldId, String functionId, FieldRequest fieldRequest, String productId) {
        Field field = check(fieldId, functionId, productId);
        deleteFieldByFieldId(field.getId());
        update(MAP_STRUCT_CONVERTER.toField(fieldRequest), new LambdaQueryWrapper<Field>().eq(Field::getId, field.getId()));
        handleFieldType(field.getType(), field.getId(), fieldRequest, productId);
    }

    private void handleFieldType(Field.Type type, Integer fieldId, FieldRequest fieldRequest, String productId) {
        switch (type) {
            case INT32 -> {
                FieldRequest.FieldInt32Request fieldInt32 = fieldRequest.getFieldInt32();
                if (null == fieldInt32) {
                    throw new AppException(AppStatus.BAD_REQUEST, "缺少int32字段描述！");
                }
                FieldInt32 fieldInt321 = MAP_STRUCT_CONVERTER.toFieldInt32(fieldInt32);
                fieldInt321.setFieldId(fieldId);
                fieldInt321.setProductId(productId);
                int32Service.save(fieldInt321);
            }
            case INT64 -> {
                FieldRequest.FieldInt64Request fieldInt64 = fieldRequest.getFieldInt64();
                if (null == fieldInt64) {
                    throw new AppException(AppStatus.BAD_REQUEST, "缺少int64字段描述！");
                }
                FieldInt64 fieldInt641 = MAP_STRUCT_CONVERTER.toFieldInt64(fieldInt64);
                fieldInt641.setFieldId(fieldId);
                fieldInt641.setProductId(productId);
                int64Service.save(fieldInt641);
            }
            case FLOAT -> {
                FieldRequest.FieldFloatRequest fieldFloat = fieldRequest.getFieldFloat();
                if (null == fieldFloat) {
                    throw new AppException(AppStatus.BAD_REQUEST, "缺少float字段描述！");
                }
                FieldFloat fieldFloat1 = MAP_STRUCT_CONVERTER.toFieldFloat(fieldFloat);
                fieldFloat1.setFieldId(fieldId);
                fieldFloat1.setProductId(productId);
                floatService.save(fieldFloat1);
            }
            case DOUBLE -> {
                FieldRequest.FieldDoubleRequest fieldDouble = fieldRequest.getFieldDouble();
                if (null == fieldDouble) {
                    throw new AppException(AppStatus.BAD_REQUEST, "缺少double字段描述！");
                }
                FieldDouble fieldDouble1 = MAP_STRUCT_CONVERTER.toFieldDouble(fieldDouble);
                fieldDouble1.setFieldId(fieldId);
                fieldDouble1.setProductId(productId);
                doubleService.save(fieldDouble1);
            }
            case STRING -> {
                FieldRequest.FieldStringRequest fieldString = fieldRequest.getFieldString();
                if (null == fieldString) {
                    throw new AppException(AppStatus.BAD_REQUEST, "缺少string字段描述！");
                }
                FieldString fieldString1 = MAP_STRUCT_CONVERTER.toFieldString(fieldString);
                fieldString1.setFieldId(fieldId);
                fieldString1.setProductId(productId);
                stringService.save(fieldString1);
            }
            case STRUCT -> structService.insert(fieldId, fieldRequest.getFieldStruct(), productId);
            default -> {
            }
        }
    }


    public List<FieldResponse> getByFunctionId(String functionId) {
        List<Field> fields = list(new LambdaQueryWrapper<Field>().eq(Field::getFunctionId, functionId));
        if (CollectionUtils.isEmpty(fields)) {
            throw new AppException(AppStatus.NOT_FOUND, "No fields in the function");
        }
        List<FieldResponse> fieldResponses = new ArrayList<>();
        FieldResponse fieldResponse;
        for (Field field : fields) {
            fieldResponse = MAP_STRUCT_CONVERTER.toFieldResponse(field);
            handleField(field.getId(), field.getType(), fieldResponse);
            fieldResponses.add(fieldResponse);
        }
        return fieldResponses;
    }

    private void handleField(Integer id, Field.Type type, FieldResponse fieldResponse) {
        switch (type) {
            case INT32:
                FieldInt32 fieldInt32 = int32Service.getOne(new LambdaQueryWrapper<FieldInt32>().eq(FieldInt32::getFieldId, id));
                fieldResponse.setFieldInt32(fieldInt32);
                break;
            case INT64:
                FieldInt64 fieldInt64 = int64Service.getOne(new LambdaQueryWrapper<FieldInt64>().eq(FieldInt64::getFieldId, id));
                fieldResponse.setFieldInt64(fieldInt64);
                break;
            case DOUBLE:
                FieldDouble fieldDouble = doubleService.getOne(new LambdaQueryWrapper<FieldDouble>().eq(FieldDouble::getFieldId, id));
                fieldResponse.setFieldDouble(fieldDouble);
                break;
            case FLOAT:
                FieldFloat fieldFloat = floatService.getOne(new LambdaQueryWrapper<FieldFloat>().eq(FieldFloat::getFieldId, id));
                fieldResponse.setFieldFloat(fieldFloat);
                break;
            case STRING:
                FieldString fieldString = stringService.getOne(new LambdaQueryWrapper<FieldString>().eq(FieldString::getFieldId, id));
                fieldResponse.setFieldString(fieldString);
                break;
            case STRUCT:
                structService.getByFieldId(id, fieldResponse);
            default:
                break;

        }

    }

    /**
     * 根据功能ID删除字段
     *
     * @param functionId
     */
    public void deleteByFunctionId(String functionId) {
        List<Field> fields = list(new LambdaQueryWrapper<Field>().eq(Field::getFunctionId, functionId));
        if (CollectionUtils.isEmpty(fields)) {
            return;
        }
        deleteByFieldIds(fields);
    }

    /**
     * 根据fieldIds删除字段
     *
     * @param
     */
    public void deleteByFieldIds(List<Field> fields) {
        List<Integer> fieldIds = fields.stream().map(Field::getId).collect(Collectors.toList());
        removeByIds(fieldIds);
        //删除所有字
        int32Service.remove(new LambdaQueryWrapper<FieldInt32>().in(FieldInt32::getFieldId, fieldIds));
        int64Service.remove(new LambdaQueryWrapper<FieldInt64>().in(FieldInt64::getFieldId, fieldIds));
        doubleService.remove(new LambdaQueryWrapper<FieldDouble>().in(FieldDouble::getFieldId, fieldIds));
        stringService.remove(new LambdaQueryWrapper<FieldString>().in(FieldString::getFieldId, fieldIds));
        floatService.remove(new LambdaQueryWrapper<FieldFloat>().in(FieldFloat::getFieldId, fieldIds));
        //删除struct
        structService.delete(fieldIds);
    }


    public void deleteByFieldIds(List<String> fieldIds, String functionId, String productId) {
        Field field;
        for (String fieldId : fieldIds) {
            field = check(fieldId, functionId, productId);
            deleteFieldByFieldId(field.getId());
            remove(new LambdaQueryWrapper<Field>().eq(Field::getIdentifier, fieldId).eq(Field::getFunctionId, functionId));
        }
    }

    /**
     * 根据fieldId 删除功能字段
     */
    public void deleteFieldByFieldId(Integer fieldId) {
        //删除field对应的field描述
        int32Service.remove(new LambdaQueryWrapper<FieldInt32>().eq(FieldInt32::getFieldId, fieldId));
        int64Service.remove(new LambdaQueryWrapper<FieldInt64>().eq(FieldInt64::getFieldId, fieldId));
        doubleService.remove(new LambdaQueryWrapper<FieldDouble>().eq(FieldDouble::getFieldId, fieldId));
        floatService.remove(new LambdaQueryWrapper<FieldFloat>().eq(FieldFloat::getFieldId, fieldId));
        stringService.remove(new LambdaQueryWrapper<FieldString>().eq(FieldString::getFieldId, fieldId));
        structService.deleteByFieldId(fieldId);
    }

    /**
     * 根据productId删除字敦
     *
     * @param productId
     */
    public void deleteByProductId(String productId) {
        remove(new LambdaQueryWrapper<Field>().eq(Field::getProductId, productId));
        int32Service.remove(new LambdaQueryWrapper<FieldInt32>().eq(FieldInt32::getProductId, productId));
        int64Service.remove(new LambdaQueryWrapper<FieldInt64>().eq(FieldInt64::getProductId, productId));
        floatService.remove(new LambdaQueryWrapper<FieldFloat>().eq(FieldFloat::getProductId, productId));
        doubleService.remove(new LambdaQueryWrapper<FieldDouble>().eq(FieldDouble::getProductId, productId));
        stringService.remove(new LambdaQueryWrapper<FieldString>().eq(FieldString::getProductId, productId));
        structService.delete(productId);
    }

    /**
     * 删除校验field
     *
     * @param fieldId
     * @param functionId
     * @return
     */
    private Field check(String fieldId, String functionId, String productId) {
        Field field = getOne(new LambdaQueryWrapper<Field>().eq(Field::getIdentifier, fieldId).eq(Field::getFunctionId, functionId)
                .eq(Field::getProductId, productId));
        if (null == field) {
            throw new AppException(AppStatus.NOT_FOUND, "fieldId=" + fieldId);
        }
        if (!CollectionUtils.isEmpty(deviceServiceService.list(new LambdaQueryWrapper<DeviceLogService>().eq(DeviceLogService::getFunctionId, functionId)
                .eq(DeviceLogService::getProductId, productId)))) {
            throw new AppException(AppStatus.USING, "fieldId=" + fieldId);
        }
        return field;
    }
}


