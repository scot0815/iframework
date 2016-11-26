package com.scot.iframework.permission.vo;

import javax.validation.constraints.NotNull;

/**
 * 更新权限Vo
 * Created by shengke on 2016/10/31.
 */
public class UpdatePermissionVo {

    /**
     * 权限code.
     */
    @NotNull(message = "权限code不能为空")
    private String functionCode;

    /**
     * 权限标记.
     */
    @NotNull(message = "权限标记不能为空")
    private String functionFlag;

    /**
     * 操作人.
     */
    @NotNull(message = "操作人不能为空")
    private Long operator;

    /**
     * 资源url.
     */
    private String url;

    /**
     * 资源类型.
     */
    private int type;

    public String getFunctionCode() {
        return functionCode;
    }

    public void setFunctionCode(String functionCode) {
        this.functionCode = functionCode;
    }

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
}
