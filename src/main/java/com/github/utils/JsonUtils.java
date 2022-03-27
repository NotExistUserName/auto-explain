package com.github.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;

/**
 * @author coffe enginner
 * @date 2022/3/25 20:47
 * @description JSON序列化工具
 */
public final class JsonUtils {

    public static final Logger LOGGER = LoggerFactory.getLogger(JsonUtils.class);

    private JsonUtils(){}

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        // 序列化所有字段
        OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        // 取消默认转换timestamps形式
        OBJECT_MAPPER.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        // 忽略空Bean转json的错误
        OBJECT_MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        // 所有的日期格式都统一为以下的样式，即yyyy-MM-dd HH:mm:ss
        OBJECT_MAPPER.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        // 忽略 在json字符串中存在，但是在java对象中不存在对应属性的情况。
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 加载SPI插件
        OBJECT_MAPPER.findAndRegisterModules();
    }

    /**
     * 对象转Json格式字符串
     *
     * @param object 目标对象
     * @return Json格式字符串
     */
    public static <T> String object2String(T object) throws JsonProcessingException {
        if (object == null) {
            return null;
        }

        return object instanceof String ? (String) object : OBJECT_MAPPER.writeValueAsString(object);
    }

    /**
     * 对象转Json格式字符串
     *
     * @param object 目标对象
     * @return Json格式字符串
     */
    public static <T> String object2StringQuietly(T object) {
        try {
            return object2String(object);
        } catch (JsonProcessingException e) {
            LOGGER.error(String.format("object2StringQuietly error，err msg：%s",e.getMessage()),e);
            return null;
        }
    }
}
