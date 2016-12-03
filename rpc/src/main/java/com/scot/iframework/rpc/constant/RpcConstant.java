package com.scot.iframework.rpc.constant;

/**
 * 常量类.
 * Created by shengke on 2016/10/26.
 */
public class RpcConstant {


    /**
     * http协议返回代码.
     */
    public static final String HTTP_RESPONSE_CODE_FLAG = "responseCode";

    /**
     * http协议返回内容.
     */
    public static final String HTTP_RESPONSE_CONTENT_FLAG = "responseContent";

    /**
     * http返回代码200.
     */
    public static final Integer HTTP_RESPONSE_SUCCESS_CODE = 200;

    /**
     * 默认超时毫秒数.
     */
    public static final Integer DEFAULT_TIMEOUT_MILLISECOND = 2000;

    /**
     * 协议.
     */
    public interface Protocol {

        /**
         * http;
         */
        int HTTP = 1;

        /**
         * socket.
         */
        int SOCKET = 2;
    }

    /**
     * 编码
     */
    public interface Encoding {

        /**
         * UTF-8.
         */
        String UTF8 = "UTF-8";

        /**
         * GBK.
         */
        String GBK = "GBK";

        /**
         * GB2312.
         */
        String GB2312 = "GB2312";
    }

    /**
     * 数据类型.
     */
    public interface DataFormat {

        /**
         * json.
         */
        int JSON = 1;

        /**
         * xml
         */
        int XML = 2;
    }

    /**
     * http发送类型.
     */
    public interface HttpSendType {

        /**
         * POST.
         */
        String POST = "POST";

        /**
         * GET.
         */
        String GET = "GET";

        /**
         * PUT.
         */
        String PUT = "PUT";
    }

}
