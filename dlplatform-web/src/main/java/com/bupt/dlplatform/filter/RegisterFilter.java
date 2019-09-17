//package com.bupt.dlplatform.filter;
//
//import com.bupt.dlplatform.util.RequestIdUtils;
//import lombok.extern.slf4j.Slf4j;
//import org.slf4j.MDC;
//import org.springframework.context.annotation.Configuration;
//
//import javax.servlet.*;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.http.HttpServletRequest;
//import java.io.IOException;
//
//@Slf4j
//@WebFilter(urlPatterns = "/**")
//@Configuration
//public class RegisterFilter implements Filter {
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//
//    }
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
//
//        RequestIdUtils tt = new RequestIdUtils();
//        String requestId = tt.logRequestId(httpServletRequest);
//        MDC.put("requestId", requestId);
//        log.info("=================>>>filtering requestId================={}", requestId);
//
//        request.setAttribute("Content-requestId", requestId);
//
//        chain.doFilter(httpServletRequest, response);
//        // LocalProvider.destroy();
//    }
//    @Override
//    public void destroy() {
//
//    }
//
//}
