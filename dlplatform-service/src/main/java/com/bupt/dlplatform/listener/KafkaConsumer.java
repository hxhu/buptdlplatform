package com.bupt.dlplatform.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Created by huhx on 2020/10/3
 */

@Component
@Slf4j
public class KafkaConsumer {

    @KafkaListener(topics = "mqtt", groupId = "group")
    public void consume(ConsumerRecord<?, ?> record, Acknowledgment ack, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {

        Optional message = Optional.ofNullable(record.value());
        if (message.isPresent()) {
            Object msg = message.get();
            log.info("|接收边缘节点消息 KafKa消息| Topic:" + topic + ",Message:" + msg);
            // 解析消息
            // 调用相应接口
            ack.acknowledge();
        }
    }

}

