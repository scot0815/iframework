package com.scot.iframework.rpc.utils;

import com.scot.iframework.rpc.constant.RpcConstant;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * 报文发送工具类
 * Created by scot on 2016/10/26.
 */
public class HttpUtil {

    /**
     * 默认http发送.
     * 请求：POST
     *
     * @param url url地址
     * @param data  数据
     * @return  url返回值
     */
    public static Map<String, Object> sendRequest(String url, String data) {
        return sendRequest(url, data, RpcConstant.HttpSendType.POST, RpcConstant.Encoding.UTF8,
                RpcConstant.DEFAULT_TIMEOUT_MILLISECOND);
    }

    /**
     * http发送(设置发送类型).
     * @param url   url地址
     * @param data  数据
     * @param sendType  发送类型
     * @return  url返回值
     */
    public static Map<String, Object> sendRequest(String url, String data, String sendType) {
        return sendRequest(url, data, sendType, RpcConstant.Encoding.UTF8, RpcConstant.DEFAULT_TIMEOUT_MILLISECOND);
    }

    /**
     * http发送(设置发送类型、编码类型).
     * @param urlStr    url地址
     * @param data  数据
     * @param sendType  发送类型
     * @param encoding  编码
     * @param timeout 超时时间（毫秒）
     * @return  url返回值
     */
    public static Map<String, Object> sendRequest(String urlStr, String data,
                                                  String sendType, String encoding, int timeout) {

        Map<String, Object> map = new HashMap<String, Object>();
        HttpURLConnection conn = null;
        OutputStream os = null;
        BufferedReader br = null;
        int statusCode = 0;
        try {
            StringBuffer result = new StringBuffer();
            URL url;
            url = new URL(urlStr);
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(timeout);
            conn.setRequestMethod(sendType);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            os = conn.getOutputStream();
            os.write(data.toString().getBytes(encoding));
            os.flush();
            os.close();
            statusCode = conn.getResponseCode();
             br = new BufferedReader(new InputStreamReader(conn
                    .getInputStream(), encoding));
            String line;
            while ((line = br.readLine()) != null) {
                result.append(line);
            }
            br.close();
            map.put(RpcConstant.HTTP_RESPONSE_CODE_FLAG, statusCode);
            map.put(RpcConstant.HTTP_RESPONSE_CONTENT_FLAG, result.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (os != null) {
                    os.close();
                }
                if (br != null) {
                    br.close();
                }
                conn.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return map;
    }
}
