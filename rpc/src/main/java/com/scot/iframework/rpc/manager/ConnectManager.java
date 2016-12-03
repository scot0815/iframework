package com.scot.iframework.rpc.manager;

import com.scot.iframework.rpc.constant.RpcConstant;
import com.scot.iframework.rpc.utils.HttpUtil;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * 连接管理.
 * Created by shengke on 2016/12/2.
 */
public class ConnectManager {

    /**
     * 连接类型.
     */
    private int protocol;

    /**
     * 发送类型.
     */
    private String sendType;

    /**
     * 基础构造函数.
     * @param protocol  协议
     */
    public ConnectManager(int protocol) {
        this.setProtocol(protocol);
    }

    /**
     * 构造函数.
     * @param protocol  协议.
     * @param sendType  发送类型.
     */
    public ConnectManager(int protocol, String sendType) {
        this.setProtocol(protocol);
        this.setSendType(sendType);
    }


    /**
     * 发送请求.
     * @param url   请求地址
     * @param data  数据
     * @param encoding  编码
     * @param timeoutMillisecond 超时时间（毫秒）
     * @return  请求返回数据
     */
    public Object request(String url, String data, String encoding, int timeoutMillisecond) {
        switch (this.protocol) {
            case RpcConstant.Protocol.HTTP:
                return httpRequest(url, data, encoding, timeoutMillisecond);
            default:
                throw new RuntimeException("暂不支持此连接协议。");
        }
    }

    /**
     * http请求.
     * @param url   请求地址
     * @param data  数据
     * @param encoding  编码
     * @param timeoutMillisecond 超时时间（毫秒）
     * @return  请求返回数据
     */
    private Map<String, Object> httpRequest(String url, String data, String encoding, int timeoutMillisecond) {
        //http发送类型缺省值post
        if (StringUtils.isEmpty(this.sendType)) {
            this.setSendType(RpcConstant.HttpSendType.POST);
        }
        return HttpUtil.sendRequest(url, data, this.sendType, encoding, timeoutMillisecond);
    }

    public int getProtocol() {
        return protocol;
    }

    public void setProtocol(int protocol) {
        this.protocol = protocol;
    }

    public String getSendType() {
        return sendType;
    }

    public void setSendType(String sendType) {
        this.sendType = sendType;
    }
}
