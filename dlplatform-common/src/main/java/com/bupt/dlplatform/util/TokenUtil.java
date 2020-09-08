package com.bupt.dlplatform.util;

import com.bupt.dlplatform.model.common.TkGenerateParameter;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class TokenUtil {
    private static final String IAT = "iat";
    //private static final String EXP = "exp";
    private static final String CELL_PHONE = "cellPhone";
    private static final String USER_NAME = "userName";
    private static final String USER_TYPE = "userType";
    private static final String USER_ID="userId";
    private static final String SECRET = "71309bf6c811cc3ef4721321702c130d";

    /**
     * 7200秒
     */
    private static final Long EXPIRATION =7200L;

    private static String getString(Object v) {
        return v != null ? String.valueOf(v) : null;
    }

    /**
     * description
     *
     * @param token:令牌
     * @return io.jsonwebtoken.Claims
     */
    private static Claims getClaimsFromToken(String token, String secretKey) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(getSecretKey(secretKey))
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    public static SecretKey getSecretKey(String secretKey){

        byte[] apiKeySecretBytes = Base64.getEncoder().encode(secretKey.getBytes());
        //使用HmacSHA256签名算法生成一个HS256的签名秘钥Key
        SecretKey signingKey = new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS256.getJcaName());
        return signingKey;
    }

    /**
     * 生成Token
     *
     * @param info info
     * @return string token
     */
    public static String generateToken(TkGenerateParameter info, String secretKey) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(IAT, System.currentTimeMillis());
        claims.put(CELL_PHONE, info.getCellPhone());
        claims.put(USER_NAME, info.getUserName());
        claims.put(USER_TYPE, info.getUserType());
        claims.put(USER_ID,info.getUserId());
        return generateToken(claims,secretKey);
    }

    private static String generateToken(Map<String, Object> claims,String secretKey) {
        return Jwts.builder()
                .setClaims(claims)
                //设置过期时间
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS256,getSecretKey(secretKey))
                .compact();
    }


    /**
     * 解密token
     *
     * @param token token
     * @return obj
     */
    public static TkGenerateParameter getEntity(String token,String secretKey) {
        TkGenerateParameter obj = new TkGenerateParameter();
        try {
            final Claims claims = getClaimsFromToken(token,secretKey);
            if (claims != null) {
                obj.setIat(Long.parseLong(getString(claims.get(IAT))));
                //obj.setExp(Long.parseLong(getString(claims.get(EXP))));
                obj.setUserName(getString(claims.get(USER_NAME)));
                obj.setCellPhone(getString(claims.get(CELL_PHONE)));
                obj.setUserType(getString(claims.get(USER_TYPE)));
                obj.setUserId(getString(claims.get(USER_ID)));
            }
        } catch (Exception e) {
            log.error("token is error,token:{}", token, e);
        }
        return obj;
    }

    /**
     * 设置过期时间
     *
     * @return java.util.Date
     */
    private static Date generateExpirationDate() {
        Date d=new Date(System.currentTimeMillis() + EXPIRATION * 1000);
        log.info("set date:"+ DateFormatUtils.format(d,"yyyy-MM-dd HH:mm:ss"));
        return d;
    }


    /*  *//**
     * 判断token失效时间是否过期
     *
     * @param token 令牌
     * @return java.lang.Boolean
     */
    public static Boolean isTokenExpired(String token,String secretKey) {
        final Date expiration = getExpirationDateFromToken(token, secretKey);
        //String s = DateUtil.formatTime(expiration);
        if (expiration != null && expiration.after(new Date())) {
            return true;
        } else {
            log.info("token失效!token:{}", token);
            return false;
        }
    }

    /**
     * 获取设置的token失效时间
     *
     * @param token token
     * @return java.util.Date
     */
    private static Date getExpirationDateFromToken(String token,String secretKey) {
        Date expiration = null;
        try {
            final Claims claims = getClaimsFromToken(token,secretKey);
            if (claims != null) {
                expiration = claims.getExpiration();
            }
        } catch (Exception e) {
            log.error("claims", e);
        }
        return expiration;
    }


    /**
     * 测试
     * @param args
     */
    public static void main(String[] args) {
//        TkGenerateParameter info = new TkGenerateParameter();
//        info.setUserName("admin");
//        info.setCellPhone("18811707992");
//        info.setUserType("administrator");
//        System.out.println(generateToken(info,"71309bf6c811cc3ef4721321702c130d"));
        String token = "eyJ1c2VyVHlwZSI6bnVsbCwidXNlck5hbWUiOiJsaW4wMiIsImV4cCI6MTU2ODcxNzcxMSwiaWF0IjoxNTY4NzEwNTExNzAzLCJ1c2VySWQiOm51bGwsImNlbGxQaG9uZSI6IjEzMTY0Mjk0MzY4In0";
        TkGenerateParameter info1 = getEntity(token,"71309bf6c811cc3ef4721321702c130d");
        System.out.println(info1.getCellPhone());
        System.out.println(info1.getUserName());
        System.out.println(info1.getUserType());
        System.out.println(info1.getUserId());
    }
}
