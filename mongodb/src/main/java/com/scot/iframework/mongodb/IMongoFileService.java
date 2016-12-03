package com.scot.iframework.mongodb;

import java.io.IOException;

/**
 * mongodb文件工具类
 * Created by shengke on 2016/11/30.
 */
public interface IMongoFileService {

    /**
     * 上传位置.
     * @param fileUrl	文件路径
     * @param uploadFileName	mongodb上文件名
     */
    void upload(String fileUrl, String uploadFileName) throws IOException;

    /**
     * 下载文件.
     * @param filename	mongodb上文件名称
     * @param downloadUrl	下载路径
     */
    void download(String filename, String downloadUrl) throws IOException;
}
