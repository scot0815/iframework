package com.scot.iframework.mail;

/**
 * 接收人信息.
 * Created by shengke on 2016/11/29.
 */
public class ReceiveInfo {

    /**
     * 接收人姓名.
     */
    private String receiveName;

    /**
     * 接收人邮件.
     */
    private String receiveMail;

    public String getReceiveName() {
        return receiveName;
    }

    public void setReceiveName(String receiveName) {
        this.receiveName = receiveName;
    }

    public String getReceiveMail() {
        return receiveMail;
    }

    public void setReceiveMail(String receiveMail) {
        this.receiveMail = receiveMail;
    }

    @Override
    public String toString() {
        return receiveName + "<" + receiveMail + ">";
    }
}
