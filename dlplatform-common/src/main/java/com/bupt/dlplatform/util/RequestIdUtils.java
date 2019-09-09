package com.bupt.dlplatform.util;

import cn.hutool.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.slf4j.MDC;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Slf4j
public class RequestIdUtils {

    public String logRequestId(HttpServletRequest request) {
        String requestId = request.getHeader("Content-requestId");
        if(StringUtils.isBlank(requestId)) {
            requestId = getUniqueID();
        }
        MDC.put("requestId", requestId);
        log.info("===============begin requestId=========================={}", requestId);

        return requestId;
    }


    /**
     * 获取唯一标识符
     * @return
     */
    public static String getUUID(){
        UUID uuid = UUID.randomUUID();
        String id=uuid.toString();
        id=id.replaceAll("-", "");
        return id;
    }


    /**
     *
     *
     */
    public static String getUniqueID() {
        String id= String.valueOf(IdUtil.createSnowflake(0, 0).nextId());
        return id;
    }

}
