package com.scot.iframework.rpc.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.scot.iframework.rpc.constant.RpcConstant;

import java.io.IOException;

/**
 * JSON工具类
 *
 * Created by shengke on 2016/10/26.
 */
public class JsonUtil {

    private static final ObjectMapper OBJECTMAPPER = new ObjectMapper();

    static {
        OBJECTMAPPER.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        OBJECTMAPPER.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        OBJECTMAPPER.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        OBJECTMAPPER.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    }


    /**
     * To json utf-8.
     *
     * @param obj the obj
     * @return the string
     */
    public static String toJson(Object obj) {
        return toJson(obj, RpcConstant.Encoding.UTF8);
    }

    /**
     * To json.
     *
     * @param obj the obj
     * @param   encoding 编码
     * @return the string
     */
    public static String toJson(Object obj, String encoding) {
        if (obj != null) {
            try {
                return new String(OBJECTMAPPER.writeValueAsString(obj).getBytes(encoding));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }


    /**
     * From json utf-8.
     * @param json        the json
     * @param targetClass the target class
     * @return the t
     */
    public static <T> T fromJson(String json, Class<T> targetClass) {
        return fromJson(json, targetClass, RpcConstant.Encoding.UTF8);
    }

    /**
     * From json.
     * @param json        the json
     * @param targetClass the target class
     * @param   encoding 编码
     * @return the t
     */
    public static <T> T fromJson(String json, Class<T> targetClass, String encoding) {
        T target = null;

        try {
            target = OBJECTMAPPER.readValue(json.getBytes(encoding), targetClass);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return target;
    }

}
