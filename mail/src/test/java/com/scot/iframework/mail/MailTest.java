package com.scot.iframework.mail;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 邮件测试
 * Created by shengke.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/application/applicationContext-mail.xml"})
@ActiveProfiles("localhost")
public class MailTest {

    @Resource(name="biz1Template")
    private VelocityMail mailUtil;

    @Test
    @Ignore
    public void test() {

        String nameOfT = "竞争力";
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("username",nameOfT);
        model.put("bizname","业务1");
        model.put("bizResult","成功");
        try {
            mailUtil.send(model);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
