package com.bupt.dlplatform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);

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