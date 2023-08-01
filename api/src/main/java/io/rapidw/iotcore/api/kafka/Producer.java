//package io.rapidw.iotcore.api.kafka;
//
//import com.alibaba.nacos.common.utils.UuidUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.stereotype.Component;
//
//import java.time.LocalDateTime;
//
//@Component
//public class Producer {
//
//    
//    private KafkaTemplate kafkaTemplate;
//
//
//
//    //发送消息方法
//    public void send() {
//        Message message = new Message();
//        message.setId("KF_"+System.currentTimeMillis());
//        message.setMsg(UuidUtils.generateUuid().toString());
//        message.setSendTime(LocalDateTime.now());
//
//        // 指定topic sl_test
//        kafkaTemplate.send("sl_test","kafkakafkaa");
//    }
//}
