package com.scot.iframework.permission.enums;

import com.scot.iframework.permission.constant.PermissionConstant;

/**
 * 账户类型枚举类
 * Created by shengke on 2016/10/21.
 */
public enum PermissionTypeEnum {
    /**
     * 个人.
     */
    LEFT_TREE(PermissionConstant.PermissionType.LEFT_TREE, "左侧树"),
    /**
     * 企业.
     */
    RESOURCE(PermissionConstant.PermissionType.RESOURCE, "资源");

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
    private PermissionTypeEnum(int value, String typeName) {
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
