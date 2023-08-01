package io.rapidw.iotcore.connector.mqtt;

import io.rapidw.iotcore.common.entity.Device;
import io.rapidw.iotcore.common.entity.Product;
import io.rapidw.iotcore.common.exception.AppException;
import io.rapidw.iotcore.common.exception.AppStatus;
import io.rapidw.iotcore.connector.mqtt.config.AppConfig;
import io.rapidw.iotcore.connector.mqtt.config.AppConstants;
import io.rapidw.iotcore.connector.mqtt.dto.mqtt.MqttServiceResponse;
import io.rapidw.iotcore.connector.mqtt.dto.request.FunctionRequest;
import io.rapidw.iotcore.connector.mqtt.dto.request.PropertyRequest;
import io.rapidw.iotcore.connector.mqtt.mapstruct.DtoMappers;
import io.rapidw.iotcore.connector.mqtt.service.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.HashedWheelTimer;
import io.rapidw.mqtt.codec.v3_1_1.*;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class MqttHandler extends SimpleChannelInboundHandler<MqttV311Packet> {

    private enum Subscription {
        PROPERTY,
        EVENT,
        SERVICE
    }

    private final AppConfig appConfig;
    private final ObjectMapper objectMapper;
    private final ConnectionService connectionService;
    private final ProductService productService;
    private final DeviceService deviceService;
    private final KafkaService kafkaService;
    private final RedisService redisService;
    private final ConnectorCheckService connectorCheckService;
    private final HashedWheelTimer hashedWheelTimer;

    private String productId;
    private String deviceName;
    private final AtomicInteger currentPacketId = new AtomicInteger(1);
    private final AtomicInteger currentServiceRequestId = new AtomicInteger(1);
    private final Set<Subscription> subscriptions = new HashSet<>();
    private final Map<Integer, ServiceService.Carrier> pendingPublishMessages = new ConcurrentHashMap<>();
    private ChannelHandlerContext ctx;

    public MqttHandler(AppConfig appConfig, ObjectMapper objectMapper, ConnectionService connectionService,
                       ProductService productService, DeviceService deviceService, KafkaService kafkaService,
                       HashedWheelTimer hashedWheelTimer, RedisService redisService, ConnectorCheckService connectorCheckService) {
        this.appConfig = appConfig;
        this.objectMapper = objectMapper;
        this.connectionService = connectionService;
        this.productService = productService;
        this.deviceService = deviceService;
        this.kafkaService = kafkaService;
        this.hashedWheelTimer = hashedWheelTimer;
        this.redisService = redisService;
        this.connectorCheckService = connectorCheckService;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MqttV311Packet msg) throws Exception {
        log.debug("mqtthandler channelRead0 start, msgType={}",msg.getType());
        switch (msg.getType()) {
            case CONNECT:
                handleConnect(ctx, ((MqttV311ConnectPacket) msg));
                break;
            case PINGREQ:
                handlePingReq(ctx);
                break;
            case PUBLISH:
                handlePublish(ctx, ((MqttV311PublishPacket) msg));
                break;
            case SUBSCRIBE:
                handleSubscribe(ctx, ((MqttV311SubscribePacket) msg));
                break;
            case UNSUBSCRIBE:
                handleUnsubscribe(ctx, ((MqttV311UnsubscribePacket) msg));
                break;
            case DISCONNECT:
                handleDisconnect(ctx);
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        if (productId != null && deviceName != null) {
            connectionService.removeConnection(this.productId, this.deviceName);
            kafkaService.sendDeviceOfflineNotification(productId, deviceName);
            deviceService.setStatus(this.productId, this.deviceName, Device.Status.OFFLINE);
            log.debug("device {} {} offline", productId, deviceName);
        }
        log.debug("channel {} INACTIVE", ctx.channel().id().asLongText());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if (cause instanceof IOException) {
            // ignore all IOException
            log.info("IO exception", cause);
        } else if (cause instanceof DecoderException) {
            log.info("Decoder exception", cause);
            ctx.channel().close();
        } else if (cause instanceof AppException) {
            log.info("App Exception", cause);
            ctx.channel().close();
        }
        else {
            log.error("error", cause);
            ctx.channel().close();
        }
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            if (((IdleStateEvent) evt).state() == IdleState.READER_IDLE) {
                log.info("reader idle, closing connection");
                ctx.channel().close();
            }
        }
    }

    private void handleConnect(ChannelHandlerContext ctx, MqttV311ConnectPacket packet) {
        log.debug("CONNECT received");

        if (!packet.isCleanSession()) {
            throw new AppException(AppStatus.BAD_REQUEST, "clean session must be set");
        }

        if (packet.getWill() != null) {
            throw new AppException(AppStatus.BAD_REQUEST, "will is not supported");
        }

        // clientId 设备名称, username 产品名称, password token
        this.productId = packet.getUsername();
        this.deviceName = packet.getClientId();

        try {
            auth(packet.getPassword());
        } catch (AppException e) {
            val builder = MqttV311ConnAckPacket.builder()
                    .connectReturnCode(MqttV311ConnectReturnCode.CONNECTION_REFUSED_BAD_USER_NAME_OR_PASSWORD)
                    .sessionPresent(false);
            ctx.channel().writeAndFlush(builder.build());
            ctx.channel().close();
            return;
        }

        // 设备已经在线
        val address = redisService.getDeviceConnectorAddress(productId, deviceName);
        if (address != null && connectorCheckService.isConnectorOnline(address)) {
            val builder = MqttV311ConnAckPacket.builder()
                    .connectReturnCode(MqttV311ConnectReturnCode.CONNECTION_REFUSED_IDENTIFIER_REJECTED)
                    .sessionPresent(false);
            ctx.channel().writeAndFlush(builder.build());
            ctx.channel().close();
            return;

        }

        val builder = MqttV311ConnAckPacket.builder()
                .connectReturnCode(MqttV311ConnectReturnCode.CONNECTION_ACCEPTED)
                .sessionPresent(false);
        ctx.channel().writeAndFlush(builder.build());

        if (packet.getKeepAliveSeconds() > 0) {
            ctx.pipeline().addBefore(AppConstants.SERVER_HANDLER_NAME, AppConstants.KEEPALIVE_HANDLER_NAME,
                    new IdleStateHandler((int) Math.round(packet.getKeepAliveSeconds() * 1.5), 0, 0));
        }

        this.connectionService.addConnection(this.productId, this.deviceName, this);
        kafkaService.sendDeviceOnlineNotification(productId, deviceName);
        deviceService.setStatus(this.productId, this.deviceName, Device.Status.ONLINE);
        this.ctx = ctx;
        log.debug("device {} {} online", this.productId, this.deviceName);
    }

    private void auth(byte[] password) {


        if (this.productId == null || this.deviceName == null || password == null) {
            throw new AppException(AppStatus.BAD_REQUEST, " client_id, username, password must be provided ");
        }
        val resource = "/products/" + productId + "/devices/" + deviceName;
        log.debug(resource);

        val parts = new String(password, StandardCharsets.UTF_8).split("&");
        String version = null, et = null, method = null, signature = null;
        for (String part: parts) {
            val kv = part.split("=");
            switch (kv[0]) {
                case "v":
                    version = kv[1];
                    break;
                case "et":
                    et = kv[1];
                    break;
                case "m":
                    method = kv[1];
                    break;
                case "s":
                    signature = kv[1];
                    break;
            }
        }
        if (version == null || et == null || method == null || signature == null) {
            throw new AppException(AppStatus.BAD_REQUEST, "missing some token part");
        }
        if (!version.equals("v1")) {
            throw new AppException(AppStatus.BAD_REQUEST, "unsupported authentication version");
        }
        val expireTime = Instant.ofEpochSecond(Long.parseLong(et));
        if (expireTime.isBefore(Instant.now())) {
            throw new AppException(AppStatus.BAD_REQUEST, "token expired");
        }

        Product product = productService.get(productId);
        if (product == null) {
            throw new AppException(AppStatus.BAD_REQUEST, "product_id not found");
        }
        val key = product.getKey();

        val stringForSignature = et + method + resource + version;
        String newSignature;
        switch (method) {
            case "md5":
                newSignature = Base64.encodeBase64URLSafeString(new HmacUtils(HmacAlgorithms.HMAC_MD5, key).hmac(stringForSignature));
                break;
            case "sha1":
                newSignature = Base64.encodeBase64URLSafeString(new HmacUtils(HmacAlgorithms.HMAC_SHA_1, key).hmac(stringForSignature));
                break;
            case "sha256":
                newSignature = Base64.encodeBase64URLSafeString(new HmacUtils(HmacAlgorithms.HMAC_SHA_256, key).hmac(stringForSignature));
                break;
            default:
                throw new AppException(AppStatus.BAD_REQUEST, "unsupported hash method");
        }
        if (!signature.equals(newSignature)) {
            throw new AppException(AppStatus.BAD_REQUEST, "signature not match");
        }

        //check deviceName existence
        if (!deviceService.exists(productId, deviceName)) {
            throw new AppException(AppStatus.BAD_REQUEST, "device not exists");
        }
    }

    private void handlePingReq(ChannelHandlerContext ctx) {
        log.debug("PINGREQ received");
        ctx.channel().writeAndFlush(MqttV311PingRespPacket.INSTANCE);
    }

    private void handlePublish(ChannelHandlerContext ctx, MqttV311PublishPacket packet) {
        log.debug("PUBLISH received");

        if (packet.getQosLevel() != MqttV311QosLevel.AT_MOST_ONCE) {
              throw new AppException(AppStatus.BAD_REQUEST, "only support qos 0");
        }
        if (packet.isDupFlag() || packet.isRetain()) {
            throw new AppException(AppStatus.BAD_REQUEST, "invalid dup or retain");
        }

        String string = new String(packet.getPayload());
        String topic = packet.getTopic();
        switch (topic) {
            case AppConstants.PROPERTY_REQUEST_TOPIC:
                postProperty(deserialize(string, PropertyRequest.class));
                break;
            case AppConstants.EVENT_REQUEST_TOPIC:
                postEvent(deserialize(string, PropertyRequest.class));
                break;
            case AppConstants.SERVICE_RESP_TOPIC:
                replyService(string, deserialize(string, MqttServiceResponse.class));
                break;
            default:
                throw new AppException(AppStatus.BAD_REQUEST, "publish to unsupported topic: " + topic);
        }
    }

    public <T> T deserialize(String string, Class<T> clazz) {
        try {
            return objectMapper.readValue(string, clazz);
        } catch (Exception e) {
            log.debug("json parse error", e);
            throw new AppException(AppStatus.BAD_REQUEST, "invalid payload");
        }
    }

    private void postProperty(PropertyRequest data) {
        // validate data
    }

    private void postEvent(PropertyRequest data) {
        // validate data
    }

    private void replyService(String responseString, MqttServiceResponse data) {
        // validate data
        val carrier = pendingPublishMessages.get(data.getRequestId());
        if (carrier != null) {
            carrier.setResponseString(responseString);
            carrier.setResponse(DtoMappers.INSTANCE.mqttServiceResponseToServiceResponse(data));
            carrier.getLatch().countDown();
        }
    }

    private void handleSubscribe(ChannelHandlerContext ctx, MqttV311SubscribePacket packet) {
        log.debug("SUBSCRIBE received");

        val qosLevels = new ArrayList<MqttV311QosLevel>(packet.getTopicAndQosLevels().size());

        packet.getTopicAndQosLevels().forEach(v -> {
            if (v.getQosLevel() != MqttV311QosLevel.AT_MOST_ONCE) {
                throw new AppException(AppStatus.BAD_REQUEST, "invalid subscription qos");
            }
            switch (v.getTopicFilter()) {
                case AppConstants.PROPERTY_RESP_TOPIC:
                    subscriptions.add(Subscription.PROPERTY);
                    break;
                case AppConstants.EVENT_RESP_TOPIC:
                    subscriptions.add(Subscription.EVENT);
                    break;
                case AppConstants.SERVICE_REQUEST_TOPIC:
                    log.debug("subscriptions add service topic");
                    subscriptions.add(Subscription.SERVICE);
                    break;
                default:
                    throw new AppException(AppStatus.BAD_REQUEST, "subscribe unsupported topic: " + v.getTopicFilter());
            }
            qosLevels.add(MqttV311QosLevel.AT_MOST_ONCE);
        });

        val builder = MqttV311SubAckPacket.builder()
                .packetId(packet.getPacketId())
                .qosLevels(qosLevels);
        ctx.channel().writeAndFlush(builder.build());
    }

    private void handleUnsubscribe(ChannelHandlerContext ctx, MqttV311UnsubscribePacket packet) {
        log.debug("UNSUBSCRIBE received");

        packet.getTopicFilters().forEach(v -> {
            switch (v) {
                case AppConstants.PROPERTY_RESP_TOPIC:
                    subscriptions.remove(Subscription.PROPERTY);
                    break;
                case AppConstants.EVENT_RESP_TOPIC:
                    subscriptions.remove(Subscription.EVENT);
                    break;
                case AppConstants.SERVICE_REQUEST_TOPIC:
                    subscriptions.remove(Subscription.SERVICE);
                    break;
                default:
                    throw new AppException(AppStatus.BAD_REQUEST, "unsubscribe unsupported topic: " + v);
            }
        });

        val builder = MqttV311UnsubAckPacket.builder()
                .packetId(packet.getPacketId());

        ctx.channel().writeAndFlush(builder.build());
    }

    private void handleDisconnect(ChannelHandlerContext ctx) {
        log.debug("DISCONNECT received");
        ctx.channel().close();
    }

    @SneakyThrows
    public void invokeService(FunctionRequest request, String functionId, ServiceService.Carrier carrier) {

        if (subscriptions.contains(Subscription.SERVICE)) {
            log.debug("mqttHandler invokeService start");
            val mqttRequest = DtoMappers.INSTANCE.functionRequestToMqttServiceRequest(request);
            val messageId = currentServiceRequestId.getAndIncrement();
            mqttRequest.setRequestId(messageId);
            mqttRequest.setFunctionId(functionId);
            val bytes = objectMapper.writeValueAsBytes(mqttRequest);

            val builder = MqttV311PublishPacket.builder()
                    //when qos 0, DO NOT set packetId
                    .dupFlag(false)
                    .qosLevel(MqttV311QosLevel.AT_MOST_ONCE)
                    .retain(false)
                    .topic(AppConstants.SERVICE_REQUEST_TOPIC)
                    .payload(bytes);

            ctx.channel().writeAndFlush(builder.build());
            pendingPublishMessages.put(messageId, carrier);

            hashedWheelTimer.newTimeout(timeout -> {
                log.debug("timer isExpired {}, isCancelled {}", timeout.isExpired(), timeout.isCancelled());
                if (timeout.isExpired() && !timeout.isCancelled()) {
                    pendingPublishMessages.remove(messageId);
                }
            }, appConfig.getServiceTimeout(), TimeUnit.SECONDS);
        }
    }
}
