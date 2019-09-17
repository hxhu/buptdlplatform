package com.bupt.dlplatform.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.type.ArrayType;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by zx
 */
public class Serializer{
    private static final Logger log = LoggerFactory.getLogger(Serializer.class);

    private static final ObjectMapper mapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);

    private static final ObjectMapper dtoMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public static String serialize(Object o) {
        if (o == null) {
            return null;
        }
        try {
            return mapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            log.warn("serialize error", e);
        }   return "";
    }

    public static String serializeDTO(Object o) {
        if (o == null) {
            return null;
        }
        try {
            return dtoMapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            log.warn("serialize dto error", e);
        }return "";
    }

    public static <T> T deserialize(String s, Class<T> clazz) {
        if ((s == null) || (s.trim().length() <= 0))
            return null;
        try {
            return mapper.readValue(s, clazz);
        } catch (IOException e) {
            log.warn("deserialize error", e);
        }return null;
    }

    private static TypeFactory getTypeFactory()
    {
        return mapper.getTypeFactory();
    }

    public static <T> T deserialize(String s, JavaType javaType) {
        if ((s == null) || (s.trim().length() <= 0)) {
            return null;
        }

        try
        {
            return mapper.readValue(s, javaType);
        } catch (IOException e) {
            log.warn("deserialize error", e);
        }return null;
    }

    public static <T> Object[] deserializeArray(String s, Class<T> elementType)
    {
        ArrayType type = getTypeFactory().constructArrayType(elementType);
        return (Object[])deserialize(s, type);
    }

    public static <T> Collection<T> deserializeCollection(String s, Class<T> elementType) {
        CollectionType type = getTypeFactory().constructCollectionType(Collection.class, elementType);
        return (Collection)deserialize(s, type);
    }

    public static <T> List<T> deserializeList(String s, Class<T> elementType) {
        JavaType type = getTypeFactory().constructParametricType(List.class, new Class[] { elementType });
        return (List)deserialize(s, type);
    }

    public static <T> Map<String, T> deserializeMap(String s, Class<T> valueType) {
        MapType type = getTypeFactory().constructMapType(Map.class, String.class, valueType);
        return (Map)deserialize(s, type);
    }

    static
    {
        SerializationConfig config = (SerializationConfig)dtoMapper
                .getSerializationConfig().without(new MapperFeature[] { MapperFeature.AUTO_DETECT_CREATORS, MapperFeature.AUTO_DETECT_GETTERS, MapperFeature.AUTO_DETECT_SETTERS, MapperFeature.AUTO_DETECT_IS_GETTERS });

        dtoMapper.setConfig(config);
    }
}
