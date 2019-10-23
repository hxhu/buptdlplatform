package com.bupt.dlplatform.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SecretKeyConfig {
    private static final String SECRET = "71309bf6c811cc3ef4721321702c130d";

    public static String secretKeySave(){
        String secretKey = new String();
        secretKey= SECRET;
        return secretKey;
    }

}
