package com.scot.iframework.permission.constant;

/**
 * 基础常量类
 * Created by shengke on 2016/10/11.
 */
public class BaseConstant {

    /**
     * 禁用.
     */
    public static final byte DISABLE = 0;

    /**
     * 可用.
     */
    public static final byte ABLE = 1;

    /**
     * 是否属于分类 枚举类.
     */
    public static enum IsBelongType {
        /**
         * 全部属于.
         */
        ALL,
        /**
         * 至少有一个.
         */
        ONE_MORE
    }

    /**
     * 是否包含分类 枚举类.
     */
    public static enum IsContainType {
        /**
         * 全部属于.
         */
        ALL,
        /**
         * 至少有一个.
         */
        ONE_MORE
    }


}
