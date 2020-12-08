package com.bupt.dlplatform;

import com.alipay.sofa.rpc.config.ProviderConfig;
import com.alipay.sofa.rpc.config.ServerConfig;
import com.bupt.dlplatform.rpc.DLPlatformService;
import com.bupt.dlplatform.rpc.DLPlatformServiceImpl;
import com.bupt.dlplatform.rpc.MQTTService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@EnableAsync
@EnableScheduling
@ServletComponentScan
@EnableEurekaClient
@SpringBootApplication
public class ServerApplication {
    // 先打开MQTT微服务，再打开本服务

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);

        // SofaRPC启动
        ServerConfig serverConfig = new ServerConfig()
                .setProtocol("bolt") // 设置一个协议，默认bolt
                .setPort(12200) // 设置一个端口，默认12200
                .setDaemon(false); // 非守护线程

        ProviderConfig<DLPlatformService> providerConfig = new ProviderConfig<DLPlatformService>()
                .setInterfaceId(DLPlatformService.class.getName()) // 指定接口
                .setRef(new DLPlatformServiceImpl()) // 指定实现
                .setServer(serverConfig); // 指定服务端

        providerConfig.export(); // 发布服务
    }

    @Bean
    ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        //如果池中的实际线程数小于corePoolSize,无论是否其中有空闲的线程,都会给新的任务产生新的线程
        threadPoolTaskExecutor.setCorePoolSize(5);
        //连接池中保留的最大连接数。Default: 15 maxPoolSize
        threadPoolTaskExecutor.setMaxPoolSize(10);
        //queueCapacity 线程池所使用的缓冲队列
        threadPoolTaskExecutor.setQueueCapacity(25);
        threadPoolTaskExecutor.initialize();
        return threadPoolTaskExecutor;
    }
}