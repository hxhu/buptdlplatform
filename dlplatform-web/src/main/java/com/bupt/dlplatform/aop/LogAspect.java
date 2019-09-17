package com.bupt.dlplatform.aop;

import com.bupt.dlplatform.config.SecretKeyConfig;
import com.bupt.dlplatform.data.ResponseCode;
import com.bupt.dlplatform.exception.dao.ApiException;
import com.bupt.dlplatform.model.common.TkGenerateParameter;
import com.bupt.dlplatform.util.Serializer;
import com.bupt.dlplatform.util.TokenUtil;
import com.bupt.dlplatform.vo.BaseInputVO;
import com.bupt.dlplatform.vo.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Component
@Aspect
public class LogAspect {

    @Pointcut("execution(* com.bupt.dlplatform.controller..*.*(..))")
    public void expenseController() {
    }

    @Before("expenseController()")
    public void expenseWebBefore(JoinPoint joinPoint) {
        HttpServletRequest httpServletRequest = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        /**
         * 提取token信息并给请求参数赋值
         */
        String token = httpServletRequest.getHeader("Content-token");
        if (!StringUtils.isBlank(token)) {
            TkGenerateParameter tokenEntity = TokenUtil.getEntity(token, SecretKeyConfig.secretKeySave());
            if (tokenEntity != null) {
                Object[] obj = joinPoint.getArgs();
                for (Object argItem : obj) {
                    if (argItem instanceof BaseInputVO) {
                        BaseInputVO paramVO = (BaseInputVO) argItem;
                        paramVO.setCellPhone(tokenEntity.getCellPhone());
                        paramVO.setUserType(tokenEntity.getUserType());
                        paramVO.setUserId(tokenEntity.getUserId());
                    }
                }
            }
        }
        log.info("【操作日志】【请求方法】:{}", joinPoint.getSignature());
     //   log.info("【操作日志】【输入参数】:{}", Serializer.serialize(joinPoint.getArgs()));
    }

    @AfterReturning(returning = "obj", pointcut = "expenseController()")
    public void expenseWebReturning(JoinPoint joinPoint, Object obj) {
        /**
         * 接口返回成功时更新token时间戳
         */

        HttpServletRequest httpServletRequest = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = httpServletRequest.getHeader("Content-token");
        if (!StringUtils.isBlank(token)) {
            TkGenerateParameter entity = TokenUtil.getEntity(token, SecretKeyConfig.secretKeySave());
            if (obj != null) {
                if (obj instanceof ResponseVO) {
                    ResponseVO responseVO = (ResponseVO) obj;
                    if (Objects.equals(ResponseCode.OK.value(), responseVO.getCode())) {
                        //重置token时间戳
                        token = TokenUtil.generateToken(entity, SecretKeyConfig.secretKeySave());
                    }
                }
            }
        }
        /**
         * 将token置于响应头的Access-token中
         */

        HttpServletResponse httpServletResponse = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        String resToken = httpServletResponse.getHeader("Access-token");
        if(StringUtils.isBlank(resToken)) {
            httpServletResponse.addHeader("Access-Control-Expose-Headers", "Access-token");
            httpServletResponse.addHeader("Access-token", token);
        }
    }

    @AfterThrowing(throwing = "ex", pointcut = "expenseController()")
    public void expenseWebAfterThrowing(JoinPoint joinPoint, Throwable ex) {
        /**
         * 接口返回成功时更新token时间戳
         */
        HttpServletRequest httpServletRequest = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = httpServletRequest.getHeader("Content-token");
        /**
         * 将token置于响应头的Access-token中
         */
        HttpServletResponse httpServletResponse = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        String resToken = httpServletResponse.getHeader("Access-token");
        if(StringUtils.isBlank(resToken)) {
            httpServletResponse.addHeader("Access-Control-Expose-Headers", "Access-token");
            httpServletResponse.addHeader("Access-token", token);
        }

        ApiException we = new ApiException();
        we.setCode(ResponseCode.SYSTEM_EXCEPTION.value());
        we.setMessage(ex.getMessage());

        ResponseVO result = new ResponseVO(we.getCode(), we.getMessage());

        log.info("【操作日志】【请求方法】:{}", joinPoint.getSignature());
        log.info("【操作日志】【异常信息】:{}", we.getMessage());
        log.info("【操作日志】【返回参数】:{}", Serializer.serialize(result));
        we.setResult(result);
        throw we;
    }

    /**
     * 序列化前过滤掉request和response，避免序列化异常
     *
     * @param args
     * @return
     */
    public List<Object> filterArgs(Object[] args) {
        Stream<?> stream = ArrayUtils.isEmpty(args) ? Stream.empty() : Arrays.asList(args).stream();
        List<Object> logArgs = stream
                .filter(arg -> (!(arg instanceof HttpServletRequest) && !(arg instanceof HttpServletResponse)))
                .collect(Collectors.toList());
        return logArgs;
    }

}

