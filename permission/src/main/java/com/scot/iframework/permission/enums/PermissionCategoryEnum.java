package com.scot.iframework.permission.enums;

import com.scot.iframework.permission.constant.PermissionConstant;

/**
 * 账户类别枚举类
 * Created by shengke on 2016/10/21.
 */
public enum PermissionCategoryEnum {
    /**
     * 访问权限.
     */
    VISIT_PERMISSION_CODE(PermissionConstant.PermissionCategory.VISIT_PERMISSION_CODE, "访问权限"),
    /**
     * 控制权限.
     */
    CONTROL_PERMISSION_CODE(PermissionConstant.PermissionCategory.CONTROL_PERMISSION_CODE, "控制权限");

    /**
     * 枚举对应值.
     */
    private int value;
    /**
     * 枚举类型名称.
     */
    private String typeName;

    /**
     * 构造函数.
     * @param value 枚举对应值
     * @param typeName  枚举类型名称.
     */
    private PermissionCategoryEnum(int value, String typeName) {
        this.value = value;
        this.typeName = typeName;
    }

    public int getValue() {
        return value;
    }

    public String getTypeName() {
        return typeName;
    }

}
