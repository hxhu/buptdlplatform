package com.bupt.dlplatform.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by dragonlai on 2019/4/9.
 * 是因为swagger-ui.html 是在springfox-swagger-ui.jar里的，因为修改了路径Spring Boot不会自动把/swagger-ui.html这个路径映射到对应的目录META-INF/resources/下面。
 *所以我们修改springboot配置类，为swagger建立新的静态文件路径映射就可以了
 *原文：https://blog.csdn.net/weixin_39168678/article/details/80563402
 */
@Configuration
public class WebMVCConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");

    }

}
