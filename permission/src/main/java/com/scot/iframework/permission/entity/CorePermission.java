package com.scot.iframework.permission.entity;

import java.util.Date;

public class CorePermission implements Comparable{
    private Long id;

    private Long creator;

    private Long updateUser;

    private Date inserttime;

    private Date updatetime;

    private Byte isactive;

    private String functionFlag;

    private String functionCode;

    private String parentFunctionCode;

    private String url;

    private Byte type;

    private Byte isChild;

    private Byte category;

    private Byte tier;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCreator() {
        return creator;
    }

    public void setCreator(Long creator) {
        this.creator = creator;
    }

    public Long getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Long updateUser) {
        this.updateUser = updateUser;
    }

    public Date getInserttime() {
        return inserttime;
    }

    public void setInserttime(Date inserttime) {
        this.inserttime = inserttime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public Byte getIsactive() {
        return isactive;
    }

    public void setIsactive(Byte isactive) {
        this.isactive = isactive;
    }

    public String getFunctionFlag() {
        return functionFlag;
    }

    public void setFunctionFlag(String functionFlag) {
        this.functionFlag = functionFlag == null ? null : functionFlag.trim();
    }

    public String getFunctionCode() {
        return functionCode;
    }

    public void setFunctionCode(String functionCode) {
        this.functionCode = functionCode == null ? null : functionCode.trim();
    }

    public String getParentFunctionCode() {
        return parentFunctionCode;
    }

    public void setParentFunctionCode(String parentFunctionCode) {
        this.parentFunctionCode = parentFunctionCode == null ? null : parentFunctionCode.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Byte getIsChild() {
        return isChild;
    }

    public void setIsChild(Byte isChild) {
        this.isChild = isChild;
    }

    public Byte getCategory() {
        return category;
    }

    public void setCategory(Byte category) {
        this.category = category;
    }

    public Byte getTier() {
        return tier;
    }

    public void setTier(Byte tier) {
        this.tier = tier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CorePermission that = (CorePermission) o;

        if (category != null ? !category.equals(that.category) : that.category != null) return false;
        if (functionCode != null ? !functionCode.equals(that.functionCode) : that.functionCode != null) return false;
        if (functionFlag != null ? !functionFlag.equals(that.functionFlag) : that.functionFlag != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (isChild != null ? !isChild.equals(that.isChild) : that.isChild != null) return false;
        if (isactive != null ? !isactive.equals(that.isactive) : that.isactive != null) return false;
        if (parentFunctionCode != null ? !parentFunctionCode.equals(that.parentFunctionCode) : that.parentFunctionCode != null)
            return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (isactive != null ? isactive.hashCode() : 0);
        result = 31 * result + (functionFlag != null ? functionFlag.hashCode() : 0);
        result = 31 * result + (functionCode != null ? functionCode.hashCode() : 0);
        result = 31 * result + (parentFunctionCode != null ? parentFunctionCode.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (isChild != null ? isChild.hashCode() : 0);
        result = 31 * result + (category != null ? category.hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo(Object o) {
        CorePermission permission = (CorePermission)o;
        int answer = 0;
        int thisLen = this.getFunctionCode().length();
        int len = permission.getFunctionCode().length();
        if(thisLen < 4) {
            return 1;
        }else if(len < 4) {
            return -1;
        }
        String thisCode = this.getFunctionCode().substring(0, 5);
        String code = permission.getFunctionCode().substring(0, 5);

        if (thisCode.equals(code)) {
            thisCode = this.getFunctionCode().substring(0, this.getFunctionCode().length() - 2);
            code = permission.getFunctionCode().substring(0, permission.getFunctionCode().length() - 2);
            if (thisCode.equals(code)) {
                answer = 0;
            } else if (thisCode.compareTo(code) > 0) {
                answer = 1;
            } else {
                answer = -1;
            }
        } else if (thisCode.compareTo(code) > 0) {
            answer = 1;
        } else {
            answer = -1;
        }
        return -1 * answer;
    }


}