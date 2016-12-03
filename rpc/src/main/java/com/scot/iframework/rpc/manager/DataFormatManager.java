package com.scot.iframework.rpc.manager;

import com.scot.iframework.rpc.constant.RpcConstant;
import com.scot.iframework.rpc.utils.JsonUtil;

/**
 * 数据转换管理类.
 * Created by shengke on 2016/10/26.
 */
public class DataFormatManager {

    /**
     * 编码.
     */
    private String encoding;

    /**
     * 数据类型.
     */
    private int dataFormat;

    /**
     * 基础构造函数.
     * @param dataFormat  数据类型
     */
    public DataFormatManager(int dataFormat) {
        this.dataFormat = dataFormat;
    }

    /**
     * 构造函数.
     * @param dataFormat  数据类型
     * @param encoding  编码
     */
    public DataFormatManager(int dataFormat, String encoding) {
        this.dataFormat = dataFormat;
        this.encoding = encoding;
    }

    /**
     * 转换为对象
     * @param data  数据
     * @param targetClass   class
     * @param <T>   泛型
     * @return  对象
     */
    public <T> T fromData(String data, Class<T> targetClass) {
        switch (this.dataFormat) {
            case RpcConstant.DataFormat.JSON:
                return JsonUtil.fromJson(data, targetClass, encoding);
            default:
                throw new RuntimeException("暂不支持此数据类型。");
        }
    }

    /**
     * 转为字符串
     * @param t 需转换对象
     * @param <T>   泛型
     * @return  转换后字符串
     */
    public <T> String toStr(T t) {
        switch (this.dataFormat) {
            case RpcConstant.DataFormat.JSON:
                return JsonUtil.toJson(t, encoding);
            default:
                throw new RuntimeException("暂不支持此数据类型。");
        }
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public int getDataFormat() {
        return dataFormat;
    }

    public void setDataFormat(int dataFormat) {
        this.dataFormat = dataFormat;
    }
}
