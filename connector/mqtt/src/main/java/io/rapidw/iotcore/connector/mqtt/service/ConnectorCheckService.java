package io.rapidw.iotcore.connector.mqtt.service;

import org.springframework.stereotype.Service;

@Service
public class ConnectorCheckService {

    public boolean isConnectorOnline(String addr) {
        return false;
    }
}
