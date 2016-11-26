package com.scot.iframework.permission.constant;

/**
 * 权限常量
 * Created by shengke on 2016/10/9.
 */
public class PermissionConstant {

    /**
     * 功能树.
     */
    public static final byte FUNCTION_TREE = 0;

    /**
     * 权限按钮.
     */
    public static final byte PERMISSION_BUTTON = 1;

    /**
     * 子节点.
     */
    public static final byte IS_CHILD = 1;

    /**
     * 非子节点.
     */
    public static final byte IS_NOT_CHILD = 0;

    /**
     * 访问权限Code.
     */
    public static final byte VISIT_PERMISSION_CODE = 1;

    /**
     * 控制权限Code.
     */
    public static final byte CONTROL_PERMISSION_CODE = 2;

    /**
     * 访问权限标示.
     */
    public static final String VISIT_PERMISSION = "V";

    /**
     * 控制权限标示.
     */
    public static final String CONTROL_PERMISSION = "C";


    /**
     * 权限类型.
     */
    public interface PermissionType {

        /**
         * 左侧树.
         */
        Integer LEFT_TREE = 0;

        /**
         * 资源.
         */
        Integer RESOURCE = 1;

    }

    /**
     * 权限类别.
     */
    public interface PermissionCategory {

        /**
         * 访问权限.
         */
        Integer VISIT_PERMISSION_CODE = 1;

        /**
         * 控制权限.
         */
        Integer CONTROL_PERMISSION_CODE = 2;
    }

}
