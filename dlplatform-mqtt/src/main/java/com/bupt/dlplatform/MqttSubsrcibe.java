package com.bupt.dlplatform;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import java.util.concurrent.ScheduledExecutorService;

/**
 * 模拟一个客户端接收消息
 *
 */
public class MqttSubsrcibe {

    public static final String HOST = "tcp://p8e0buw.mqtt.iot.gz.baidubce.com:1883";
    public static String[] topics = {"edge/list", "edge/figure", "edge/picture", "edge/video", "edge/map", "edge/heartbeat"};
    private static String clientid = "center";

    private String userName = "p8e0buw/bupt";    //非必须
    private String passWord = "QBeYiSPj7p1ZoPZb";  //非必须
    private ScheduledExecutorService scheduler;

    private MqttClient client; //单例模式
    private MqttConnectOptions options;

//    public MqttSubsrcibe( String topic, String clientid ){
//        this.topic = topic;
//        this.clientid = clientid;
//    }

    public void start() {
        try {
            // host为主机名，clientid即连接MQTT的客户端ID，一般以唯一标识符表示，MemoryPersistence设置clientid的保存形式，默认为以内存保存
            client = new MqttClient(HOST, clientid, new MemoryPersistence());
            // MQTT的连接设置
            options = new MqttConnectOptions();
            // 设置是否清空session,这里如果设置为false表示服务器会保留客户端的连接记录，设置为true表示每次连接到服务器都以新的身份连接
            options.setCleanSession(false);
            // 设置连接的用户名
            options.setUserName(userName);
            // 设置连接的密码
            options.setPassword(passWord.toCharArray());
            // 设置超时时间 单位为秒
            options.setConnectionTimeout(10);
            // 设置会话心跳时间 单位为秒 服务器会每隔1.5*20秒的时间向客户端发送个消息判断客户端是否在线，但这个方法并没有重连的机制
            options.setKeepAliveInterval(20);
            //设置断开后重新连接
            options.setAutomaticReconnect(true);
            // 设置回调
            client.setCallback(new PushCallback());
//            MqttTopic topic = client.getTopic(TOPIC);
            //setWill方法，如果项目中需要知道客户端是否掉线可以调用该方法。设置最终端口的通知消息
            //遗嘱
//            options.setWill(topic, "close".getBytes(), 1, true);
            client.connect(options);
            //订阅消息
            int[] Qos = new int[topics.length];//0：最多一次 、1：最少一次 、2：只有一次
            for( int i = 0; i < topics.length; i++ ){
                Qos[i] = 0;
            }
            client.subscribe(topics, Qos);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}