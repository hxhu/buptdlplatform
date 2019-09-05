package com.bupt.dlplatform.util;

import org.apache.commons.lang.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationUtil {
    /**
     * 验证是否为正确的手机号
     */
    public static boolean assertMobile(String str) {
        Pattern pattern = null;
        Matcher matcher = null;
        boolean result = false;
        String template="^[1](([3|5|8][\\d])|([4][5,6,7,8,9])|([6][5,6])|([7][3,4,5,6,7,8])|([9][8,9]))[\\d]{8}$";//
        // 验证手机号
        if(StringUtils.isNotBlank(str)){
            pattern = Pattern.compile(template);
            matcher = pattern.matcher(str);
            result = matcher.matches();
        }
        return result;
    }
}
