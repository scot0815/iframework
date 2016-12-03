package com.scot.iframework.redis.enums;

/**
 * redis枚举类
 * Created by shengke on 2016/11/30.
 */
public enum RedisExpxEnum {

    /**
     * EX = seconds.
     */
    EX("EX"),
    /**
     * PX = milliseconds.
     */
    PX("PX");

    /**
     * 枚举对应值.
     */
    private String value;

    /**
     * 构造函数.
     * @param value 枚举对应值
     */
    private RedisExpxEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }


}
