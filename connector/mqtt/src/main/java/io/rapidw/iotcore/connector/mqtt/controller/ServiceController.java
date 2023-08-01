package io.rapidw.iotcore.connector.mqtt.controller;

import io.rapidw.iotcore.connector.mqtt.dto.response.ServiceResponse;
import io.rapidw.iotcore.connector.mqtt.service.ServiceService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServiceController {

    private final ServiceService serviceService;

    public ServiceController(ServiceService serviceService) {
        this.serviceService = serviceService;
    }

    @PostMapping("/service")

    public ServiceResponse invokeService(@RequestParam("productId") String productId,
                                                       @RequestParam("deviceName") String deviceName,
                                                       @RequestParam("functionId") String functionId,
                                                       @RequestBody String requestString) {
        return serviceService.invoke(productId, deviceName, functionId, requestString);
    }


}
