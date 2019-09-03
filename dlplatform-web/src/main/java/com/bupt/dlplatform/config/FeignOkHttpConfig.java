package com.bupt.dlplatform.config;

import feign.Feign;
import feign.Logger;
import lombok.extern.slf4j.Slf4j;
import okhttp3.ConnectionPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * Created by dragonlai on 2019/6/12.
 * okhttp3组件会把原始Get请求通过fegin会改成POST,导致提示Method Get must not have body
 */
@Configuration
@ConditionalOnClass(Feign.class)
@AutoConfigureBefore(FeignAutoConfiguration.class)
@Slf4j
public class FeignOkHttpConfig {
    @Autowired
    public OkHttp3LoggingInterceptor okHttp3LoggingInterceptor;

    /**
     * 日志级别
     * @return
     */
    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    @Bean
    public okhttp3.OkHttpClient okHttpClient() {
        return new okhttp3.OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)  //设置读取超时时间
                .connectTimeout(60, TimeUnit.SECONDS) //设置连接超时时间
                .writeTimeout(120, TimeUnit.SECONDS)
                .connectionPool(new ConnectionPool())
                //.addInterceptor();  //添加请求拦截器
                .addNetworkInterceptor(okHttp3LoggingInterceptor)
                .build();
    }
}
