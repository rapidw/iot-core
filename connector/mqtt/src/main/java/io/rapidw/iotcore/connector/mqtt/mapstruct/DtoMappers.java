package io.rapidw.iotcore.connector.mqtt.mapstruct;

import io.rapidw.iotcore.common.entity.field.Field;
import io.rapidw.iotcore.common.entity.field.FieldStruct;
import io.rapidw.iotcore.common.entity.struct.Entry;
import io.rapidw.iotcore.connector.mqtt.dto.mqtt.MqttServiceRequest;
import io.rapidw.iotcore.connector.mqtt.dto.mqtt.MqttServiceResponse;
import io.rapidw.iotcore.connector.mqtt.dto.request.FunctionRequest;
import io.rapidw.iotcore.connector.mqtt.dto.response.ServiceResponse;
import io.rapidw.iotcore.connector.mqtt.service.EntryService;
import io.rapidw.iotcore.connector.mqtt.service.FieldService;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(builder = @Builder(disableBuilder = true), unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DtoMappers {

    DtoMappers INSTANCE = Mappers.getMapper(DtoMappers.class);

    MqttServiceRequest functionRequestToMqttServiceRequest(FunctionRequest data);

    ServiceResponse mqttServiceResponseToServiceResponse(MqttServiceResponse data);

    FieldService.FullField fieldToFullField(Field field);

    FieldService.FullFieldStruct structToFullStruct(FieldStruct struct);

    EntryService.FullEntry entryToFullEntry(Entry entry);
}
