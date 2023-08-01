package io.rapidw.iotcore.api.service.field;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.rapidw.iotcore.api.request.FieldRequest;
import io.rapidw.iotcore.api.response.FieldResponse;
import io.rapidw.iotcore.api.service.struct.StructEntryService;
import io.rapidw.iotcore.common.entity.field.FieldStruct;
import io.rapidw.iotcore.common.entity.struct.Entry;
import io.rapidw.iotcore.common.exception.AppException;
import io.rapidw.iotcore.common.exception.AppStatus;
import io.rapidw.iotcore.common.mapper.field.FieldStructMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StructService extends ServiceImpl<FieldStructMapper, FieldStruct> implements IService<FieldStruct> {

    private final StructEntryService structEntryService;

    public StructService(StructEntryService structEntryService) {
        this.structEntryService = structEntryService;
    }

    public void insert(Integer fieldId,FieldRequest.FieldStructRequest fieldStruct,String productId){
        if(null == fieldStruct){
            throw new AppException(AppStatus.BAD_REQUEST,"缺少struct字段描述！");
        }
        FieldStruct fieldStruct1 = FieldStruct.builder().fieldId(fieldId).build();
        fieldStruct1.setProductId(productId);
        save(fieldStruct1);
        int structId = fieldStruct1.getId();
        List<FieldRequest.StructField> fieldList = fieldStruct.getFields();
        structEntryService.handleStructField(structId,fieldList,productId);
    }

    public void getByFieldId(Integer fieldId,FieldResponse fieldResponse){
        FieldStruct fieldStruct = getOne(Wrappers.lambdaQuery(FieldStruct.class).eq(FieldStruct::getFieldId,fieldId));
        if(null == fieldStruct){
            return;
        }
        structEntryService.getByFieldStructId(fieldStruct.getId(),fieldResponse);
    }

    public void delete(String productId){
        remove(Wrappers.lambdaQuery(FieldStruct.class).eq(FieldStruct::getProductId,productId));
        structEntryService.deleteByProductId(productId);
    }

    public void delete(List<Integer> fieldIds){
        //删除struct
        List<FieldStruct> fieldStructs = list(Wrappers.lambdaQuery(FieldStruct.class).in(FieldStruct::getFieldId,fieldIds));
        if (CollectionUtils.isEmpty(fieldStructs)) {
            return;
        }
        List<Integer> structIds = fieldStructs.stream().map(FieldStruct::getId).collect(Collectors.toList());
        removeByIds(structIds);
        structEntryService.deleteByIds(structIds);
    }

    public void deleteByFieldId(Integer fieldId){
        FieldStruct fieldStruct = getOne(Wrappers.lambdaQuery(FieldStruct.class).eq(FieldStruct::getFieldId,fieldId));
        if(null == fieldStruct){
            return;
        }
        List<Entry> structEntrys = structEntryService.list(Wrappers.lambdaQuery(Entry.class).eq(Entry::getStructId,fieldStruct.getId()));
        removeById(fieldStruct.getId());
        List<Integer> structIds = structEntrys.stream().map(Entry::getId).collect(Collectors.toList());
        remove(new LambdaQueryWrapper<FieldStruct>().eq(FieldStruct::getFieldId,fieldId));
        structEntryService.deleteByIds(structIds);
    }
}


