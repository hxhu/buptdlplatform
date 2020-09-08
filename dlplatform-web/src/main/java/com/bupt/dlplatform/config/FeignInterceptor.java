package com.bupt.dlplatform.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;


/*
 * web通过FeignClient 调用service,防止request head头部信息丢失
 * 重新透传调用者request head
 * 将用户的登录id放在了请求头中传递给内部服务。但是当内部服务之间存在feign调用时，那么请求头信息会在feign请求的时候传递吗？
 * 不会，请求的头信息和请求参数都不会进行传递。但是我们可以通过通过实现RequestInterceptor接口,完成对所有的Feign请求,传递请求头和请求参数
 * 原文：https://blog.csdn.net/AaronSimon/article/details/82711036 *
 * */
@Configuration
@Slf4j
public class FeignInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return;
        }
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        Enumeration<String> headerNames = request.getHeaderNames();
        if (headerNames != null) {
            while (headerNames.hasMoreElements()) {
                String name = headerNames.nextElement();
                Enumeration<String> values = request.getHeaders(name);
                while (values.hasMoreElements()) {
                    String value = values.nextElement();
                    template.header(name, value);
                }
            }
        }

        Enumeration<String> bodyNames = request.getParameterNames();
        StringBuffer body = new StringBuffer();
        if (bodyNames != null) {
            while (bodyNames.hasMoreElements()) {
                String name = bodyNames.nextElement();
                String values = request.getParameter(name);
                body.append(name).append("=").append(values).append("&");
            }
        }
        template.header("Content-Type","application/json");
        if (body.length() != 0) {
            body.deleteCharAt(body.length() - 1);
            template.body(body.toString());
        }
    }
}
