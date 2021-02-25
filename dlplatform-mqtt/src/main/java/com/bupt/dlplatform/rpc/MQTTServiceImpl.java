package com.bupt.dlplatform.rpc;

import com.bupt.dlplatform.Entity.DownEntity;
import com.bupt.dlplatform.ServerMQTT;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huhx on 2020/12/6
 */
public class MQTTServiceImpl implements MQTTService {
    @Override
    public String pushModel(List<String> deviceIds, String modelId, String modelLocation, String type) {
        try {
            // 数据整理
            String command = "pushModel;" + type;
            String data = modelLocation + ";" + modelId;
            Long timestamp = System.currentTimeMillis();
            DownEntity downEntity = new DownEntity(command, data, deviceIds, timestamp);

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

    @Override
    public String fileModel(List<String> deviceIds, String fileId, String fileLocation, String type){
        try {
            // 数据整理
            String command = "pushFile;" + type;
            String data = fileLocation + ";" + fileId;
            Long timestamp = System.currentTimeMillis();
            DownEntity downEntity = new DownEntity(command, data, deviceIds, timestamp);

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

    @Override
    public String pushConfig(HashMap<String,Object> resultConfigs, ArrayList<String> resultDeviceIds){
        try {
            // 数据整理
            String command = "pushConfig";
            String data = "";
            for( Map.Entry<String,Object> map : resultConfigs.entrySet() ){
                data += map.getKey() + ":" + map.getValue() + ";";
            }
            data = data.substring(0,data.length()-1);
            List<String> deviceIds = resultDeviceIds;
            Long timestamp = System.currentTimeMillis();
            DownEntity downEntity = new DownEntity(command, data, deviceIds, timestamp);

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