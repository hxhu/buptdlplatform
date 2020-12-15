package com.bupt.dlplatform.rpc;

import com.bupt.dlplatform.Entity.DownEntity;
import com.bupt.dlplatform.ServerMQTT;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.List;

/**
 * Created by huhx on 2020/12/6
 */
public class MQTTServiceImpl implements MQTTService {
    @Override
    public String pushModel(List<String> deviceIds, String modelId, String modelLocation, String type) {
        try {
            // 数据整理
            String command = "pushModel" + type;
            String data = modelLocation + ";" + modelId;
            String target = "";
            for (int i = 0; i < deviceIds.size(); i++) {
                if (i >= (deviceIds.size() - 1)) {
                    target += deviceIds.get(i);
                } else {
                    target += deviceIds.get(i) + ";";
                }
            }
            Long timestamp = System.currentTimeMillis();
            DownEntity downEntity = new DownEntity(command, data, target, timestamp);

            // 序列化
            ObjectMapper objectMapper = new ObjectMapper();
            String sendData = objectMapper.writeValueAsString(downEntity);

            System.out.println(sendData);
            // MQTT发送
            ServerMQTT server = new ServerMQTT();
            server.message = new MqttMessage();
            server.message.setQos(1);
            server.message.setRetained(true);
            server.message.setPayload(sendData.getBytes());
            server.publish(server.topic11, server.message);
            server.client.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
            return "ERROR";
        }

        return "OK";
    }
}