package com.bupt.dlplatform.listener;

import com.bupt.dlplatform.vo.KafKaConsumerVO;
import com.bupt.dlplatform.vo.MDataEntityInputVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Optional;

/**
 * Created by huhx on 2020/10/3
 */

@Component
@Slf4j
public class KafkaConsumer {
    private ObjectMapper mapper = new ObjectMapper();

    @KafkaListener(topics = "mqtt", groupId = "group")
    public void consume(ConsumerRecord<?, ?> record, Acknowledgment ack, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {

        Optional message = Optional.ofNullable(record.value());
        if (message.isPresent()) {
            Object msg = message.get();
            log.info("|接收边缘节点消息 KafKa消息| Topic:" + topic + ",Message:" + msg);
//            String str = "{\"id\": \"N1312003955798052864\", \"deviceId\":\"N1311344863756685312\", \"type\": \"list\", \"value\": \"add\"}";

            try{
                // 解析消息  反序列化为kafKaConsumerVO
                KafKaConsumerVO kafKaConsumerVO = mapper.readValue(msg.toString(), KafKaConsumerVO.class);

                // 调用数据更新接口 controller
                RestTemplate restTemplate = new RestTemplate();
                String url = "http://127.0.0.1:18800/dlplatform/dataDisplay/update";  //嵌入式端只更新数据 只调用dataSource服务就可以
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                MDataEntityInputVO mDataEntityInputVO = new MDataEntityInputVO(kafKaConsumerVO);

                HttpEntity<MDataEntityInputVO> request = new HttpEntity<MDataEntityInputVO>(mDataEntityInputVO, headers);
                ResponseEntity<String> response = restTemplate.postForEntity( url, request , String.class );
                if( response.getStatusCodeValue() != 200 ){
                    throw new Exception(response.getBody());
                }
            }catch ( IOException e ){
                log.error("KafKa消息解析异常", e);
            }catch ( Exception e ) {
                log.error("调用内部接口异常", e);
            }finally {
                ack.acknowledge();
            }

        }
    }

}

