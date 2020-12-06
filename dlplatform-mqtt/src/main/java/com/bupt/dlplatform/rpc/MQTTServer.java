package com.bupt.dlplatform.rpc;

/**
 * Created by huhx on 2020/12/6
 */
import com.alipay.sofa.rpc.config.ProviderConfig;
import com.alipay.sofa.rpc.config.ServerConfig;

public class MQTTServer {

    public static void main(String[] args) {
        ServerConfig serverConfig = new ServerConfig()
                .setProtocol("bolt") // 设置一个协议，默认bolt
                .setPort(12400) // 设置一个端口，默认12200
                .setDaemon(false); // 非守护线程

        ProviderConfig<MQTTService> providerConfig = new ProviderConfig<MQTTService>()
                .setInterfaceId(MQTTService.class.getName()) // 指定接口
                .setRef(new MQTTServiceImpl()) // 指定实现
                .setServer(serverConfig); // 指定服务端

        providerConfig.export(); // 发布服务
    }
}
