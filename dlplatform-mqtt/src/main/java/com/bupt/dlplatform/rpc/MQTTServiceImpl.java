package com.bupt.dlplatform.rpc;

import com.bupt.dlplatform.Entity.DownEntity;
import com.bupt.dlplatform.ServerMQTT;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by huhx on 2020/12/6
 */
public class MQTTServiceImpl implements MQTTService {
    @Override
    public String pushModel(List<String> deviceIds, String modelId, String modelLocation, String type, String parttern) {
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            System.out.print(df.format(new Date()));
            System.out.println(" 输入参数：");
            System.out.println("{deviceIds=" + deviceIds + ",");
            System.out.println(" modelId=" + modelId + ",");
            System.out.println(" modelLocation=" + modelLocation + ",");
            System.out.println(" type=" + type + "}");

            // 数据整理
            String command = "pushModel;" + type;
            String data = modelLocation + ";" + modelId;
            Long timestamp = System.currentTimeMillis();
            HashMap<String, Object> extra = new HashMap<String, Object>();
            extra.put("parttern", parttern);
            DownEntity downEntity = new DownEntity(command, data, deviceIds, timestamp, extra);

            // 序列化
            ObjectMapper objectMapper = new ObjectMapper();
            String sendData = objectMapper.writeValueAsString(downEntity);

            System.out.print(df.format(new Date()));
            System.out.println(" 发送数据：");
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
    public String fileModel(List<String> deviceIds, String fileId, String fileLocation, String type, String parttern){
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            System.out.print(df.format(new Date()));
            System.out.println(" 输入参数：");
            System.out.println("{deviceIds=" + deviceIds + ",");
            System.out.println(" fileId=" + fileId + ",");
            System.out.println(" fileLocation=" + fileLocation + ",");
            System.out.println(" type=" + type + "}");

            // 数据整理
            String command = "pushFile;" + type;
            String data = fileLocation + ";" + fileId;
            Long timestamp = System.currentTimeMillis();
            HashMap<String, Object> extra = new HashMap<String, Object>();
            extra.put("parttern", parttern);
            DownEntity downEntity = new DownEntity(command, data, deviceIds, timestamp, extra);

            // 序列化
            ObjectMapper objectMapper = new ObjectMapper();
            String sendData = objectMapper.writeValueAsString(downEntity);

            System.out.print(df.format(new Date()));
            System.out.println(" 发送数据：");
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
    public String pushConfig(HashMap<String,Object> resultConfigs, ArrayList<String> resultDeviceIds, String parttern){
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            System.out.print(df.format(new Date()));
            System.out.println(" 输入参数：");
            System.out.println("{resultConfigs=" + resultConfigs + ",");
            System.out.println(" resultDeviceIds=" + resultDeviceIds + "}");

            // 数据整理
            String command = "pushConfig";
            String data = "";
            for( Map.Entry<String,Object> map : resultConfigs.entrySet() ){
                data += map.getKey() + ":" + map.getValue() + ";";
            }
            data = data.substring(0,data.length()-1);
            List<String> deviceIds = resultDeviceIds;
            Long timestamp = System.currentTimeMillis();
            HashMap<String, Object> extra = new HashMap<String, Object>();
            extra.put("parttern", parttern);
            DownEntity downEntity = new DownEntity(command, data, deviceIds, timestamp, extra);

            // 序列化
            ObjectMapper objectMapper = new ObjectMapper();
            String sendData = objectMapper.writeValueAsString(downEntity);

            System.out.print(df.format(new Date()));
            System.out.println(" 发送数据：");
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

    public void printString(String data){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        System.out.print(df.format(new Date()));
        System.out.println("发送消息格式：");
        String[] printStr = data.split(",");
        for( int i = 0; i < printStr.length; i++ ){
            System.out.print(printStr[i]);
            if( i != printStr.length-1 ){
                System.out.println(",");
            }
        }
    }
}