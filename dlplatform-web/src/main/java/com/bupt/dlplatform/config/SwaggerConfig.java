package com.bupt.dlplatform.config;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyName;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.configuration.ObjectMapperConfigured;
import springfox.documentation.service.ApiInfo;
//import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.PostConstruct;
import java.lang.annotation.Annotation;

@Configuration
@EnableSwagger2
public class SwaggerConfig extends WebMvcConfigurerAdapter {

    @Value("${swagger.show}")
    private boolean enableSwagger;


    @Autowired
    private ApplicationContext applicationContext;


    @PostConstruct
    public void setObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        objectMapper.registerModule(module);

        JacksonAnnotationIntrospector jacksonAnnotationIntrospector=  new JacksonAnnotationIntrospector();

        objectMapper.setAnnotationIntrospector(new JacksonAnnotationIntrospector() {

            @Override
            public boolean isAnnotationBundle(Annotation ann) {
                if (ann.annotationType() == JSONField.class) {
                    return true;
                }
                return super.isAnnotationBundle(ann);
            }

            @Override
            public PropertyName findNameForSerialization(Annotated a) {
                PropertyName nameForSerialization = super.findNameForSerialization(a);
                if (nameForSerialization == null || nameForSerialization == PropertyName.USE_DEFAULT) {
                    JSONField jsonField = _findAnnotation(a, JSONField.class);
                    if (jsonField != null) {
                        return PropertyName.construct(jsonField.name());
                    }
                }
                return nameForSerialization;
            }

            @Override
            public PropertyName findNameForDeserialization(Annotated a) {
                PropertyName nameForDeserialization = super.findNameForDeserialization(a);
                if (nameForDeserialization == null || nameForDeserialization == PropertyName.USE_DEFAULT) {
                    JSONField jsonField = _findAnnotation(a, JSONField.class);
                    if (jsonField != null) {
                        return PropertyName.construct(jsonField.name());
                    }
                }
                return nameForDeserialization;
            }
        });

        ObjectMapperConfigured objectMapperConfigured = new ObjectMapperConfigured(applicationContext, objectMapper);
        applicationContext.publishEvent(objectMapperConfigured);
    }


    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).groupName("framework").enable(enableSwagger)
                .genericModelSubstitutes(DeferredResult.class).useDefaultResponseMessages(false)
                .forCodeGeneration(false).pathMapping("/").apiInfo(apiInfo()).select()
                .apis(RequestHandlerSelectors.basePackage("com.bupt.dlplatform.controller")).build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("应用框架").description("平台接口文档").termsOfServiceUrl("")
                .contact("zhongling").version("1.0").build();
    }


}
