package com.bupt.dlplatform;

/**
 * Created by huhx on 2020/12/3
 * <p>
 * <p>
 * Description:
 *
 * @author admin
 * 2017年2月10日下午18:04:07
 * <p>
 * <p>
 * Description:
 * @author admin
 * 2017年2月10日下午18:04:07
 * <p>
 * <p>
 * Description:
 * @author admin
 * 2017年2月10日下午18:04:07
 * <p>
 * <p>
 * Description:
 * @author admin
 * 2017年2月10日下午18:04:07
 * <p>
 * <p>
 * Description:
 * @author admin
 * 2017年2月10日下午18:04:07
 * <p>
 * <p>
 * Description:
 * @author admin
 * 2017年2月10日下午18:04:07
 */
/**
 *
 * Description:
 * @author admin
 * 2017年2月10日下午18:04:07
 */

import com.alipay.sofa.rpc.config.ConsumerConfig;
import com.bupt.dlplatform.Entity.UpEntity;
import com.bupt.dlplatform.rpc.DLPlatformService;
import com.bupt.dlplatform.rpc.MQTTService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.Date;
import java.text.SimpleDateFormat;

/**
 * 发布消息的回调类
 *
 * 必须实现MqttCallback的接口并实现对应的相关接口方法CallBack 类将实现 MqttCallBack。
 * 每个客户机标识都需要一个回调实例。在此示例中，构造函数传递客户机标识以另存为实例数据。
 * 在回调中，将它用来标识已经启动了该回调的哪个实例。
 * 必须在回调类中实现三个方法：
 *
 *  public void messageArrived(MqttTopic topic, MqttMessage message)接收已经预订的发布。
 *
 *  public void connectionLost(Throwable cause)在断开连接时调用。
 *
 *  public void deliveryComplete(MqttDeliveryToken token))
 *  接收到已经发布的 QoS 1 或 QoS 2 消息的传递令牌时调用。
 *  由 MqttClient.connect 激活此回调。
 *
 */
public class PushCallback implements MqttCallback {
    private volatile ConsumerConfig<DLPlatformService> consumerConfig = null;
    private DLPlatformService dlPlatformService = null;

    public void connectionLost(Throwable cause) {
        // 连接丢失后，一般在这里面进行重连
        System.out.println("连接断开，可以做重连");
    }

    public void deliveryComplete(IMqttDeliveryToken token) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        if (token.isComplete()) {
            System.out.println(df.format(new Date()) + " 发送成功");
        } else {
            System.out.println(df.format(new Date()) + " 发送失败");
        }
    }

    public void messageArrived(String topic, MqttMessage message) throws Exception {
        try {
            // 双重检验锁
            if (dlPlatformService == null) {
                synchronized (MQTTService.class) {
                    if (dlPlatformService == null) {
                        // PRC调用服务
                        consumerConfig = new ConsumerConfig<DLPlatformService>()
                                .setInterfaceId(DLPlatformService.class.getName()) // 指定接口
                                .setProtocol("bolt") // 指定协议
                                .setDirectUrl("bolt://127.0.0.1:12200"); // 指定直连地址
                        // 生成代理类
                        dlPlatformService = consumerConfig.refer();
                    }
                }
            }

            // 数据处理
            ObjectMapper objectMapper = new ObjectMapper();
            String recieve = new String(message.getPayload());
            UpEntity upEntity = objectMapper.readValue(recieve, UpEntity.class);

            String messageType = upEntity.getMessage();

            switch (messageType) {
                case "heartbeat":    // 心跳上传
                    if (dlPlatformService.updateERHeartbeat(
                            upEntity.getDeviceId(),
                            upEntity.getMessage(),
                            upEntity.getData(),      // "0" , "1", "2"
                            upEntity.getTimestamp()
                    ).equals("OK")) {
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
                        System.out.println(df.format(new Date()) + " 心跳上传成功");
                    }
                    break;
                case "videoMessage":  // 视频信息上传
                    if (dlPlatformService.updateVideoMessage(
                            upEntity.getDeviceId(),
                            upEntity.getMessage(),
                            upEntity.getData(),   // 视频信息
                            upEntity.getTimestamp()
                    ).equals("OK")) {
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
                        System.out.println(df.format(new Date()) + " 视频信息上传成功");
                    }
                    break;
                case "modelUpdate":   //  模型更新结果
                    if (dlPlatformService.updateModelMessage(
                            upEntity.getDeviceId(),
                            upEntity.getMessage(),
                            upEntity.getData(),   // modelId
                            upEntity.getTimestamp()
                    ).equals("OK")) {
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
                        System.out.println(df.format(new Date()) + " 模型更新上传成功");
                    }
                    break;
            }
        } catch (Exception e) {

        }
    }
}
