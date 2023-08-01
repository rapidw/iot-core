package io.rapidw.iotcore.api.service.struct;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.rapidw.iotcore.api.mapper.struct.StructEntryMapper;
import io.rapidw.iotcore.api.request.FieldRequest;
import io.rapidw.iotcore.api.response.FieldResponse;
import io.rapidw.iotcore.common.entity.struct.*;
import io.rapidw.iotcore.common.exception.AppException;
import io.rapidw.iotcore.common.exception.AppStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static io.rapidw.iotcore.api.mapstruct.MapStructConverter.MAP_STRUCT_CONVERTER;

@Service
public class StructEntryService extends ServiceImpl<StructEntryMapper, Entry> implements IService<Entry> {
    private final Int32EntryService int32EntryService;
    private final Int64EntryService int64EntryService;
    private final DoubleEntryService doubleEntryService;
    private final FloatEntryService floatEntryService;
    private final StringEntryService stringEntryService;

    public StructEntryService(Int32EntryService int32EntryService, Int64EntryService int64EntryService,
                              DoubleEntryService doubleEntryService, FloatEntryService floatEntryService,
                              StringEntryService stringEntryService) {
        this.int32EntryService = int32EntryService;
        this.int64EntryService = int64EntryService;
        this.doubleEntryService = doubleEntryService;
        this.floatEntryService = floatEntryService;
        this.stringEntryService = stringEntryService;
    }


    public void getByFieldStructId(Integer fieldStructId,FieldResponse fieldResponse){
        List<Entry> structEntrys= list(new LambdaQueryWrapper<Entry>().eq(Entry::getStructId,fieldStructId));
        if(CollectionUtils.isEmpty(structEntrys)){
            return;
        }
        FieldResponse.FieldStruct fieldStruct1 = new FieldResponse.FieldStruct();
        buildFieldStruct(structEntrys,fieldStruct1);
        fieldResponse.setFieldStruct(fieldStruct1);
    }

    /**
     * 构建struct下的field
     * @param structEntrys
     * @param fieldStruct
     */
    private void buildFieldStruct(List<Entry> structEntrys, FieldResponse.FieldStruct fieldStruct){
        List<FieldResponse.Field> fields = new ArrayList<>();
        FieldResponse.Field field;
        for(Entry structEntry:structEntrys){
            Entry.Type type = structEntry.getType();
            Integer id = structEntry.getId();
            field = MAP_STRUCT_CONVERTER.toFieldResponseField(structEntry);
            fields.add(field);
            switch (type){
                case INT32:
                    EntryInt32 fieldInt32 = int32EntryService.getOne(new LambdaQueryWrapper<EntryInt32>().eq(EntryInt32::getEntryId,id));
                    field.setFieldInt32(fieldInt32);
                    break;
                case INT64:
                    EntryInt64 fieldInt64 = int64EntryService.getOne(new LambdaQueryWrapper<EntryInt64>().eq(EntryInt64::getEntryId,id));
                    field.setFieldInt64(fieldInt64);
                    break;
                case DOUBLE:
                    EntryDouble fieldDouble = doubleEntryService.getOne(new LambdaQueryWrapper<EntryDouble>().eq(EntryDouble::getEntryId,id));
                    field.setFieldDouble(fieldDouble);
                    break;
                case FLOAT:
                    EntryFloat fieldFloat = floatEntryService.getOne(new LambdaQueryWrapper<EntryFloat>().eq(EntryFloat::getEntryId,id));
                    field.setFieldFloat(fieldFloat);
                    break;
                case STRING:
                    EntryString fieldString = stringEntryService.getOne(new LambdaQueryWrapper<EntryString>().eq(EntryString::getEntryId,id));
                    field.setFieldString(fieldString);
                    break;
                default:
                    break;
            }
        }
        fieldStruct.setFields(fields);
    }


    /**
     * 处理struct的字段
     * @param structId
     * @param fieldList
     */
    public void handleStructField(int structId, List<FieldRequest.StructField> fieldList,String productId){
        for(FieldRequest.StructField field:fieldList){
            Entry.Type type = field.getType();
            if(null == type){
                throw new AppException(AppStatus.BAD_REQUEST,"struct entry type不可为空!");
            }
            if(null == field.getName()){
                throw new AppException(AppStatus.BAD_REQUEST,"struct entry name不可为空!");
            }
            if(null == field.getEntryId()){
                throw new AppException(AppStatus.BAD_REQUEST,"struct entry name不可为空!");
            }
            if(null != getOne(new LambdaQueryWrapper<Entry>().eq(Entry::getStructId,structId).eq(Entry::getName,field.getName()))){
                throw new AppException(AppStatus.CONFLICT,"struct entry name="+field.getName());
            }
            if(null != getOne(new LambdaQueryWrapper<Entry>().eq(Entry::getStructId,structId).eq(Entry::getIdentifier,field.getEntryId()))){
                throw new AppException(AppStatus.CONFLICT,"struct entry id="+field.getEntryId());
            }
            Entry structEntry = MAP_STRUCT_CONVERTER.toStructEntry(field);
            structEntry.setIdentifier(field.getEntryId());
            structEntry.setStructId(structId);
            structEntry.setProductId(productId);
            save(structEntry);
            switch (type){
                case INT32:
                    FieldRequest.FieldInt32Request fieldInt32 = field.getFieldInt32();
                    if(null == fieldInt32){
                        throw new AppException(AppStatus.BAD_REQUEST,"struct缺少int32字段描述！");
                    }
                    EntryInt32 fieldInt321 = MAP_STRUCT_CONVERTER.toStructEntryInt32(fieldInt32);
                    fieldInt321.setEntryId(structEntry.getId());
                    fieldInt321.setProductId(productId);
                    int32EntryService.save(fieldInt321);
                    break;
                case INT64:
                    FieldRequest.FieldInt64Request fieldInt64 = field.getFieldInt64();
                    if(null == fieldInt64){
                        throw new AppException(AppStatus.BAD_REQUEST,"struct缺少int64字段描述！");
                    }
                    EntryInt64 fieldInt641 = MAP_STRUCT_CONVERTER.toStructEntryInt64(fieldInt64);
                    fieldInt641.setEntryId((structEntry.getId()));
                    fieldInt641.setProductId(productId);
                    int64EntryService.save(fieldInt641);
                    break;
                case FLOAT:
                    FieldRequest.FieldFloatRequest fieldFloat = field.getFieldFloat();
                    if(null == fieldFloat){
                        throw new AppException(AppStatus.BAD_REQUEST,"struct缺少float字段描述！");
                    }
                    EntryFloat fieldFloat1 = MAP_STRUCT_CONVERTER.toStructEntryFloat(fieldFloat);
                    fieldFloat1.setEntryId((structEntry.getId()));
                    fieldFloat1.setProductId(productId);
                    floatEntryService.save(fieldFloat1);
                    break;
                case DOUBLE:
                    FieldRequest.FieldDoubleRequest fieldDouble = field.getFieldDouble();
                    if(null == fieldDouble){
                        throw new AppException(AppStatus.BAD_REQUEST,"struct缺少double字段描述！");
                    }
                    EntryDouble fieldDouble1 = MAP_STRUCT_CONVERTER.toStructEntryDouble(fieldDouble);
                    fieldDouble1.setEntryId(structEntry.getId());
                    fieldDouble1.setProductId(productId);
                    doubleEntryService.save(fieldDouble1);
                    break;
                case STRING:
                    FieldRequest.FieldStringRequest fieldString = field.getFieldString();
                    if(null == fieldString){
                        throw new AppException(AppStatus.BAD_REQUEST,"缺少string字段描述！");
                    }
                    EntryString fieldString1 = MAP_STRUCT_CONVERTER.toStructEntryString(fieldString);
                    fieldString1.setEntryId((structEntry.getId()));
                    fieldString1.setProductId(productId);
                    stringEntryService.save(fieldString1);
                    break;
                default:
                    break;
            }
        }
    }


    public void deleteByIds(List<Integer> structIds) {
        List<Entry> structEntries = list(new LambdaQueryWrapper<Entry>().in(Entry::getStructId, structIds));
        if (CollectionUtils.isEmpty(structEntries)) {
            return;
        }
        List<Integer> structEntryIds = structEntries.stream().map(Entry::getId).collect(Collectors.toList());
        removeByIds(structEntryIds);
        int32EntryService.remove(new LambdaQueryWrapper<EntryInt32>().in(EntryInt32::getEntryId, structEntryIds));
        int64EntryService.remove(new LambdaQueryWrapper<EntryInt64>().in(EntryInt64::getEntryId, structEntryIds));
        floatEntryService.remove(new LambdaQueryWrapper<EntryFloat>().in(EntryFloat::getEntryId, structEntryIds));
        doubleEntryService.remove(new LambdaQueryWrapper<EntryDouble>().in(EntryDouble::getEntryId, structEntryIds));
        stringEntryService.remove(new LambdaQueryWrapper<EntryString>().in(EntryString::getEntryId, structEntryIds));
    }

    public void deleteByProductId(String productId){
        remove(new LambdaQueryWrapper<Entry>().eq(Entry::getProductId,productId));
        int32EntryService.remove(new LambdaQueryWrapper<EntryInt32>().eq(EntryInt32::getProductId,productId));
        int64EntryService.remove(new LambdaQueryWrapper<EntryInt64>().eq(EntryInt64::getProductId,productId));
        floatEntryService.remove(new LambdaQueryWrapper<EntryFloat>().eq(EntryFloat::getProductId,productId));
        doubleEntryService.remove(new LambdaQueryWrapper<EntryDouble>().eq(EntryDouble::getProductId,productId));
        stringEntryService.remove(new LambdaQueryWrapper<EntryString>().eq(EntryString::getProductId,productId));
    }




}


