package com.scot.permissions.core.utils;

/**
 * 数据基本验证工具类
 * Created by shengke on 2016/10/12.
 */
public class DataValidationUtil {

    /**
     * 验证数组是否为空.
     * @param objects   数组.
     * @return  是否为空
     */
    public static boolean validationArrayEmpty(Object [] objects) {
        if (objects == null || objects.length < 1) {
            return true;
        }
        return false;
    }
}
