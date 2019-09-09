package com.bupt.dlplatform.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.List;
import java.util.Map;

public class FastJsonUtil {
    public static String toJson(Object object) {
        return JSON.toJSONString(object, SerializerFeature.DisableCircularReferenceDetect);
    }

    public static <T> T toBean(String jsonString, Class<T> cls) {
        return JSON.parseObject(jsonString, cls);
    }

    public static <T> List<T> toList(String jsonString, Class<T> cls) {
        return JSON.parseArray(jsonString, cls);
    }

    public static <T> Map<String, T> toMap(String jsonString) {
        return JSON.parseObject(jsonString, new TypeReference<Map<String, T>>(){});
    }

    public static List<Map<String, Object>> toListMap(String jsonString) {
        return JSON.parseObject(jsonString, new TypeReference<List<Map<String, Object>>>(){});
    }

    public static String toJsonWithFormat(Object object, String dateFormat) {
        if (null == dateFormat || "".equals(dateFormat)) {
            return toJson(object);
        }
        return JSON.toJSONStringWithDateFormat(object, dateFormat);
    }
}
