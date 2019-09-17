package com.bupt.dlplatform.filter;


import com.bupt.dlplatform.config.SecretKeyConfig;
import com.bupt.dlplatform.constants.SystemData;
import com.bupt.dlplatform.data.ResponseCode;
import com.bupt.dlplatform.model.common.TkGenerateParameter;
import com.bupt.dlplatform.util.FastJsonUtil;
import com.bupt.dlplatform.util.TokenUtil;
import com.bupt.dlplatform.vo.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


@Slf4j
@WebFilter(urlPatterns = "/**")
@Configuration
public class AuthenticationFilter implements Filter {
    private static final Set<String> ALLOWED_PATHS = Collections.unmodifiableSet(new HashSet<>(
            Arrays.asList("/dlplatform/login", "/dlplatform/register","/v2/api-docs")));

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) {
        try {
            HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
            String servletPath = httpServletRequest.getServletPath();

            boolean allowedPath = ALLOWED_PATHS.contains(servletPath);
            boolean isSwaggerUrl = false;

            // 判断是否为swagger请求
            if (servletPath.indexOf(SystemData.swagger_url) > -1) {
                isSwaggerUrl = true;
            }

            boolean needContinue = true;
            if (!isSwaggerUrl && !allowedPath) {
                String token = httpServletRequest.getHeader("Content-token");
                if (StringUtils.isBlank(token)) {
                    needContinue = false;
                    tokenErrorResponse(servletResponse);
                } else {
                    TkGenerateParameter entity = TokenUtil.getEntity(token, SecretKeyConfig.secretKeySave());
                    if (entity == null) {
                        needContinue = false;
                        tokenErrorResponse(servletResponse);
                    } else {
                        boolean tokenExpired = TokenUtil.isTokenExpired(token, SecretKeyConfig.secretKeySave());
                        if (!tokenExpired) {
                            needContinue = false;
                            tokenErrorResponse(servletResponse);
                        }
                    }
                }
            }
            if (needContinue) {
                filterChain.doFilter(httpServletRequest, servletResponse);
            }
        } catch (Exception e) {
            try {
                exceptionResponse(servletResponse);
            } catch (IOException ex) {
                log.error("filter错误：{}", ex);
            }
        }
        // LocalProvider.destroy();
    }

    private void tokenErrorResponse(ServletResponse servletResponse) throws IOException {
        //解决json中文乱码
        servletResponse.setContentType("text/json;charset=UTF-8");
        servletResponse.setCharacterEncoding("UTF-8");
        ResponseVO res = new ResponseVO();
        res.setCode(ResponseCode.AUTH_INVALID.value());
        res.setMsg(ResponseCode.AUTH_INVALID.getDescription());
        byte[] resContent = FastJsonUtil.toJson(res).getBytes();
        OutputStream out = servletResponse.getOutputStream();
        out.write(resContent);
        out.flush();
    }

    private void exceptionResponse(ServletResponse servletResponse) throws IOException {
        //解决json中文乱码
        servletResponse.setContentType("text/json;charset=UTF-8");
        servletResponse.setCharacterEncoding("UTF-8");
        ResponseVO res = new ResponseVO();
        res.setCode(ResponseCode.SYSTEM_EXCEPTION.value());
        res.setMsg(ResponseCode.SYSTEM_EXCEPTION.getDescription());
        byte[] resContent = FastJsonUtil.toJson(res).getBytes();
        OutputStream out = servletResponse.getOutputStream();
        out.write(resContent);
        out.flush();
    }

    @Override
    public void destroy() {

    }

}
