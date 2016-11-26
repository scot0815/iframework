package com.scot.iframework.permission.vo;

import javax.validation.constraints.NotNull;

/**
 * 新增权限Vo
 * Created by shengke on 2016/10/28.
 */
public class AddPermissionVo {

    /**
     * 权限标记.
     */
    private String functionFlag;

    /**
     * 操作人.
     */
    @NotNull(message = "操作人不能为空")
    private Long operator;

    /**
     * 父权限code.
     */
    @NotNull(message = "父权限code不能为空")
    private String parentFunctionCode;

    /**
     * 资源url.
     */
    private String url;

    /**
     * 资源类型.
     */
    private int type;

    /**
     * 是否子节点.
     */
    @NotNull(message = "是否子节点不能为空")
    private Boolean isChild;

    public String getFunctionFlag() {
        return functionFlag;
    }

    public void setFunctionFlag(String functionFlag) {
        this.functionFlag = functionFlag;
    }

    public Long getOperator() {
        return operator;
    }

    public void setOperator(Long operator) {
        this.operator = operator;
    }

    public String getParentFunctionCode() {
        return parentFunctionCode;
    }

    public void setParentFunctionCode(String parentFunctionCode) {
        this.parentFunctionCode = parentFunctionCode;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Boolean getIsChild() {
        return isChild;
    }

    public void setIsChild(Boolean isChild) {
        this.isChild = isChild;
    }

}
