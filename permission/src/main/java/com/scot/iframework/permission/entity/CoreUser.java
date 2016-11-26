package com.scot.iframework.permission.entity;

import java.util.Date;

public class CoreUser {
    private Long id;

    private Long bzUserId;

    private Boolean status;

    private Long creator;

    private Long updateUser;

    private Date inserttime;

    private Date updatetime;

    private Byte isactive;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBzUserId() {
        return bzUserId;
    }

    public void setBzUserId(Long bzUserId) {
        this.bzUserId = bzUserId;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
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
}