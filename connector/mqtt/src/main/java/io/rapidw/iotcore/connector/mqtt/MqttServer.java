package io.rapidw.iotcore.connector.mqtt;

import io.rapidw.iotcore.connector.mqtt.config.AppConfig;
import io.rapidw.iotcore.connector.mqtt.config.AppConstants;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.rapidw.mqtt.codec.v3_1_1.MqttV311Decoder;
import io.rapidw.mqtt.codec.v3_1_1.MqttV311Encoder;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MqttServer {

    private final AppConfig appConfig;
//    private ApplicationContext applicationContext;

    public MqttServer(AppConfig appConfig, MqttHandler mqttHandler) {
        this.appConfig = appConfig;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void start() {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            val bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<>() {
                        @Override
                        protected void initChannel(Channel ch) {
                            val pipeline = ch.pipeline();
                            pipeline.addLast(new MqttV311Decoder());
                            pipeline.addLast(MqttV311Encoder.INSTANCE);
                            MqttHandler mqttHandler = getMqttHandler();
                            pipeline.addLast(AppConstants.SERVER_HANDLER_NAME, mqttHandler);
                        }
                    });
            log.info("binding mqtt server on {}", appConfig.getMqttPort());
            val channelFuture = bootstrap.bind(appConfig.getMqttPort()).sync();
            log.info("mqtt server listen on {}", appConfig.getMqttPort());
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    @Lookup
    public MqttHandler getMqttHandler() {
        return null;
    }
}
