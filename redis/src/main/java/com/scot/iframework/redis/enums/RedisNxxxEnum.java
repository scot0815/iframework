package com.scot.iframework.redis.enums;

/**
 * redis枚举类
 * Created by shengke on 2016/11/30.
 */
public enum RedisNxxxEnum {

    /**
     * Only set the key if it does not already exist.
     */
    NX("NX"),
    /**
     * Only set the key if it already exist.
     */
    XX("XX");

    /**
     * 枚举对应值.
     */
    private String value;

    /**
     * 构造函数.
     * @param value 枚举对应值
     */
    private RedisNxxxEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }


}
