package io.rapidw.iotcore.connector.mqtt.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.rapidw.iotcore.connector.mqtt.service.ConnectionService;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.val;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    private final ConnectionService connectionService;
    private final ObjectMapper objectMapper;

    public TestController(ConnectionService connectionService, ObjectMapper objectMapper) {
        this.connectionService = connectionService;
        this.objectMapper = objectMapper;
    }

    // 检测connector是否存在的接口，勿删
    @GetMapping("/ok")
    public String ok() {
        return "ok";
    }

    @PostMapping("/mapper")
    @SneakyThrows
    public String mapper(@RequestBody String str) {
        val value = objectMapper.readValue(str, Value.class);
        return "ok";
    }

    @Data
    public static class Value {
        private Object a;
    }
}
