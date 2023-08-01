package io.rapidw.iotcore.connector.mqtt.service;

import io.rapidw.iotcore.common.entity.field.Field;
import io.rapidw.iotcore.common.entity.function.Function;
import io.rapidw.iotcore.common.exception.AppException;
import io.rapidw.iotcore.common.exception.AppStatus;
import io.rapidw.iotcore.connector.mqtt.config.AppConfig;
import io.rapidw.iotcore.connector.mqtt.dto.request.FunctionRequest;
import io.rapidw.iotcore.connector.mqtt.dto.response.ServiceResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class ServiceService {

    private final ConnectionService connectionService;
    private final ObjectMapper objectMapper;
    private final DeviceLogService deviceLogService;
    private final FieldService fieldService;
    private final AppConfig appConfig;

    public ServiceService(ConnectionService connectionService, ObjectMapper objectMapper,
                          DeviceLogService deviceLogService, FieldService fieldService, AppConfig appConfig) {
        this.connectionService = connectionService;
        this.objectMapper = objectMapper;
        this.deviceLogService = deviceLogService;
        this.fieldService = fieldService;
        this.appConfig = appConfig;
    }

    @SneakyThrows
    public ServiceResponse invoke(String productId, String deviceName, String functionId, String requestString) {

        Integer id = deviceLogService.saveServiceRequest(productId, deviceName, functionId, requestString);
        val request = validateRequest(productId, functionId, requestString);

        val carrier = new Carrier();
        connectionService.getConnection(productId, deviceName).invokeService(request, functionId, carrier);
        log.debug("before await");
        carrier.getLatch().await(appConfig.getServiceTimeout(), TimeUnit.SECONDS);
        log.debug("after await");

        ServiceResponse response = carrier.getResponse();
        String responseString;
        if (response != null) {
            validateResponse(productId, functionId, response);
            responseString = carrier.getResponseString();
        } else {
            response = ServiceResponse.builder().code(1).message("timeout").build();
            responseString = objectMapper.writeValueAsString(response);
        }
        deviceLogService.saveServiceResponse(id, responseString);
        return response;
    }

    private FunctionRequest validateRequest(String productId, String functionId, String requestString) {
        try {
            val request = objectMapper.readValue(requestString, FunctionRequest.class);
            doValidateRequest(productId, functionId, request);
            return request;
        } catch (JsonProcessingException e) {
            throw new AppException(AppStatus.BAD_REQUEST, "invalid request");
        }

    }

    private void doValidateRequest(String productId, String functionId, FunctionRequest request) {
        val fields = fieldService.getAllFields(productId, functionId, Function.Type.SERVICE, Field.InOrOut.OUT);
        request.getFields().forEach(requestField -> {
            val fieldId = requestField.getId();
            val currentField = fields.get(fieldId);
            if (currentField == null) {
                throw new AppException(AppStatus.BAD_REQUEST, "field not found in fields: " + fieldId + " and product_id " + productId);
            }
            val fieldValue = requestField.getValue();
            switch (currentField.getType()) {
                case INT32:
                    if (!(fieldValue instanceof Integer)) {
                        throwFieldException(fieldId, fieldValue);
                    }
                    break;
                case INT64:
                    if (fieldValue instanceof Long)
                        break;
                    if (fieldValue instanceof Integer) {
                        requestField.setValue(((Integer) fieldValue).doubleValue());
                    }
                    throwFieldException(fieldId, fieldValue);
                    break;
                case FLOAT:
                    if (fieldValue instanceof Double) {
                        requestField.setValue(((Double) fieldValue).floatValue());
                    }
                    throwFieldException(fieldId, fieldValue);
                case DOUBLE:
                    if (!(fieldValue instanceof Double)) {
                        throwFieldException(fieldId, fieldValue);
                    }
                    break;
                case STRING:
                    if (!(fieldValue instanceof String)) {
                        throwFieldException(fieldId, fieldValue);
                    }
                    break;
                case STRUCT:
                    if (!(fieldValue instanceof Map)) {
                        throwFieldException(fieldId, fieldValue);
                    }
                    //noinspection unchecked
                    val valueMap = (Map<String, Object>) fieldValue;
                    val entries = currentField.getFieldStruct().getEntries();
                    valueMap.entrySet().forEach(entry -> {
                        val entryId = entry.getKey();
                        val currentEntry = entries.get(entryId);
                        val entryValue = entry.getValue();
                        switch (currentEntry.getType()) {
                            case INT32:
                                if (!(entryValue instanceof Integer)) {
                                    throwEntryException(fieldId, entryId, entryValue);
                                }
                                break;
                            case INT64:
                                if (entryValue instanceof Long)
                                    break;
                                if (entryValue instanceof Integer) {
                                    entry.setValue(((Integer) entryValue).doubleValue());
                                }
                                throwEntryException(fieldId, entryId, entryValue);
                                break;
                            case FLOAT:
                                if (entryValue instanceof Double) {
                                    entry.setValue(((Double) entryValue).floatValue());
                                }
                                throwEntryException(fieldId, entryId, entryValue);
                                break;
                            case DOUBLE:
                                if (!(entryValue instanceof Double)) {
                                    throwEntryException(fieldId, entryId, entryValue);
                                }
                                break;
                            case STRING:
                                if (!(entryValue instanceof String)) {
                                    throwEntryException(fieldId, entryId, entryValue);
                                }
                                break;
                        }
                    });
                    break;
            }
        });
    }

    private void throwFieldException(String fieldId, Object value) {
        throw new AppException(AppStatus.BAD_REQUEST, "invalid value: " + value + " for field: " + fieldId);
    }

    private void throwEntryException(String fieldId, String entryId, Object value) {
        throw new AppException(AppStatus.BAD_REQUEST, "invalid value: " + value + " for entry: " + entryId + "in field: " + fieldId);
    }

    private void validateResponse(String productId, String functionId, ServiceResponse data) {
        val fields = fieldService.getAllFields(productId, functionId, Function.Type.SERVICE, Field.InOrOut.IN);
        data.getFields().forEach(requestField -> {
            val fieldId = requestField.getId();
            val currentField = fields.get(fieldId);
            if (currentField == null) {
                throw new AppException(AppStatus.BAD_REQUEST, "field not found in fields: " + fieldId + " and product_id " + productId);
            }
            val fieldValue = requestField.getValue();
            switch (currentField.getType()) {
                case INT32:
                    if (!(fieldValue instanceof Integer)) {
                        throwFieldException(fieldId, fieldValue);
                    }
                    break;
                case INT64:
                    if (fieldValue instanceof Long)
                        break;
                    if (fieldValue instanceof Integer) {
                        requestField.setValue(((Integer) fieldValue).doubleValue());
                    }
                    throwFieldException(fieldId, fieldValue);
                    break;
                case FLOAT:
                    if (fieldValue instanceof Double) {
                        requestField.setValue(((Double) fieldValue).floatValue());
                    }
                    throwFieldException(fieldId, fieldValue);
                case DOUBLE:
                    if (!(fieldValue instanceof Double)) {
                        throwFieldException(fieldId, fieldValue);
                    }
                    break;
                case STRING:
                    if (!(fieldValue instanceof String)) {
                        throwFieldException(fieldId, fieldValue);
                    }
                    break;
                case STRUCT:
                    if (!(fieldValue instanceof Map)) {
                        throwFieldException(fieldId, fieldValue);
                    }
                    //noinspection unchecked
                    val valueMap = (Map<String, Object>) fieldValue;
                    val entries = currentField.getFieldStruct().getEntries();
                    valueMap.entrySet().forEach(entry -> {
                        val entryId = entry.getKey();
                        val currentEntry = entries.get(entryId);
                        val entryValue = entry.getValue();
                        switch (currentEntry.getType()) {
                            case INT32:
                                if (!(entryValue instanceof Integer)) {
                                    throwEntryException(fieldId, entryId, entryValue);
                                }
                                break;
                            case INT64:
                                if (entryValue instanceof Long)
                                    break;
                                if (entryValue instanceof Integer) {
                                    entry.setValue(((Integer) entryValue).doubleValue());
                                }
                                throwEntryException(fieldId, entryId, entryValue);
                                break;
                            case FLOAT:
                                if (entryValue instanceof Double) {
                                    entry.setValue(((Double) entryValue).floatValue());
                                }
                                throwEntryException(fieldId, entryId, entryValue);
                                break;
                            case DOUBLE:
                                if (!(entryValue instanceof Double)) {
                                    throwEntryException(fieldId, entryId, entryValue);
                                }
                                break;
                            case STRING:
                                if (!(entryValue instanceof String)) {
                                    throwEntryException(fieldId, entryId, entryValue);
                                }
                                break;
                        }
                    });
                    break;
            }
        });
    }

    @Data
    public static class Carrier {
        private CountDownLatch latch = new CountDownLatch(1);
        private ServiceResponse response;
        private String responseString;
    }
}
