package io.rapidw.iotcore.api.mapstruct;

import io.rapidw.iotcore.api.request.*;
import io.rapidw.iotcore.api.response.FieldResponse;
import io.rapidw.iotcore.common.entity.Application;
import io.rapidw.iotcore.common.entity.Device;
import io.rapidw.iotcore.common.entity.Product;
import io.rapidw.iotcore.common.entity.field.*;
import io.rapidw.iotcore.common.entity.function.Function;
import io.rapidw.iotcore.common.entity.struct.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface MapStructConverter {
    MapStructConverter MAP_STRUCT_CONVERTER = Mappers.getMapper(MapStructConverter.class);

    Application toApplication(ApplicationAddRequest request);

    Application toApplication(ApplicationUpdateRequest request);

    Product toProduct(ProductUpdateRequest request);

    Product toProduct(ProductAddRequest request);

    @Mapping(source = "functionId" , target = "identifier")
    Function toFunction(FunctionAddRequest request);

    Function toFunction(FunctionUpdateRequest request);

    Device toDevice(DeviceAddRequest request);

    Device toDevice(DeviceUpdateRequest request);

    FieldInt32 toFieldInt32(FieldRequest.FieldInt32Request request);

    FieldInt64 toFieldInt64(FieldRequest.FieldInt64Request request);

    FieldFloat toFieldFloat(FieldRequest.FieldFloatRequest request);

    FieldDouble toFieldDouble(FieldRequest.FieldDoubleRequest request);

    FieldString toFieldString(FieldRequest.FieldStringRequest request);

    @Mapping(source = "fieldId" , target = "identifier")
    Field toField(FieldRequest request);

    @Mapping(source = "identifier" , target = "fieldId")
    FieldResponse toFieldResponse(Field field);

    @Mapping(source = "identifier" , target = "entryId")
    FieldResponse.Field toFieldResponseField(Entry structEntry);

    @Mapping(source = "entryId" , target = "identifier")
    Entry toStructEntry(FieldRequest.StructField field);

    EntryInt32 toStructEntryInt32(FieldRequest.FieldInt32Request fieldInt32);

    EntryInt64 toStructEntryInt64(FieldRequest.FieldInt64Request fieldInt64);

    EntryFloat toStructEntryFloat(FieldRequest.FieldFloatRequest fieldFloat);

    EntryDouble toStructEntryDouble(FieldRequest.FieldDoubleRequest fieldDouble);

    EntryString toStructEntryString(FieldRequest.FieldStringRequest fieldString);

}
