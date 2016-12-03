package com.scot.iframework.mongodb.impl;

import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;
import com.scot.iframework.mongodb.IMongoFileService;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.io.*;

/**
 * Created by shengke on 2016/11/30.
 */
public class MongoFileServiceImpl implements IMongoFileService {

    /**
     * mongodb连接模板.
     */
    private MongoTemplate mongoUploadTemplate;

    public MongoTemplate getMongoUploadTemplate() {
        return mongoUploadTemplate;
    }

    public void setMongoUploadTemplate(MongoTemplate mongoUploadTemplate) {
        this.mongoUploadTemplate = mongoUploadTemplate;
    }

    /**
     * 上传位置.
     *
     * @param fileUrl        文件路径
     * @param uploadFileName mongodb上文件名
     */
    @Override
    public void upload(String fileUrl, String uploadFileName) throws IOException {
        GridFS fs = new GridFS(mongoUploadTemplate.getDb());
        GridFSInputFile gfsif = fs.createFile(new File(fileUrl));
        gfsif.put("filename", uploadFileName);
        gfsif.save();
    }

    /**
     * 下载文件.
     *
     * @param filename    mongodb上文件名称
     * @param downloadUrl 下载路径
     */
    @Override
    public void download(String filename, String downloadUrl) throws IOException {
        GridFS fs = new GridFS(mongoUploadTemplate.getDb());
        GridFSDBFile gridFSDBFile = fs.findOne(filename);
        InputStream inputStream = gridFSDBFile.getInputStream();
        BufferedInputStream bf = new BufferedInputStream(inputStream);
        OutputStream outputStream = new FileOutputStream(downloadUrl);
        int len;
        byte[] b = new byte[1024];
        while((len=bf.read(b))!=-1){
            outputStream.write(b);
        }
        outputStream.flush();
        if(null != bf) {
            bf.close();
        }
        if(null != inputStream) {
            inputStream.close();
        }
        if(null != outputStream) {
            outputStream.close();
        }
    }
}
