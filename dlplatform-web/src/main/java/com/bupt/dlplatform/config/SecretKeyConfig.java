package com.bupt.dlplatform.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SecretKeyConfig {
    public static String  encrypt_decrypt_key=null;//全局变量：kms接口返回密钥

}
