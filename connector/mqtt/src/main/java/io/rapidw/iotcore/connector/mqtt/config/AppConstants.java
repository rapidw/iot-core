package io.rapidw.iotcore.connector.mqtt.config;

public class AppConstants {
    public final static String SERVER_HANDLER_NAME = "mqtt_server_handler";
    public final static String KEEPALIVE_HANDLER_NAME = "mqtt_keepalive_handler";

    public final static String PROPERTY_RESP_TOPIC = "$sys/v1/property/response";
    public final static String EVENT_RESP_TOPIC = "$sys/v1/event/response";
    public final static String SERVICE_REQUEST_TOPIC = "$sys/v1/service/request";

    public final static String PROPERTY_REQUEST_TOPIC = "$sys/v1/property/request";
    public final static String EVENT_REQUEST_TOPIC = "$sys/v1/event/request";
    public final static String SERVICE_RESP_TOPIC = "$sys/v1/service/response";
}
