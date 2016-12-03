package com.scot.iframework.mongodb;

import com.scot.iframework.mongodb.dao.IMongoUserDao;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

/**
 * Created by shengke on 2016/11/28.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/applicationContext-mongodb.xml"})
@ActiveProfiles("localhost")
public class Test {

    /**
     * 用户dao.
     */
    @Autowired
    private IMongoUserDao userDao;

    @Autowired
    private IMongoFileService mongoFileService;

    /**
     * 测试插入.
     */
    @org.junit.Test
    @Ignore
    public void insert() {
        userDao.insert();
    }

    /**
     * 测试统计.
     */
    @org.junit.Test
    public void sum() {
        userDao.sum();
    }

    /**
     * 文件上传.
     */
    @org.junit.Test
    @Ignore
    public void upload() {
        userDao.upload("d:\\testupload.jpg","test_upload");
    }

    /**
     * 文件下载.
     */
    @org.junit.Test
    @Ignore
    public void download() {
        userDao.download("test_upload","d:\\test_download.jpg");
    }

    /**
     * 文件上传.
     */
    @org.junit.Test
    @Ignore
    public void uploadService() {
        try {
            mongoFileService.upload("d:\\test_upload.jpg","test_service_upload");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 文件下载.
     */
    @org.junit.Test
    @Ignore
    public void downloadService() {
        try {
            mongoFileService.download("test_service_upload","d:\\test_service_download.jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
