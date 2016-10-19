package dao;

import com.scot.permissions.core.constant.BaseConstant;
import com.scot.permissions.core.constant.UserConstant;
import com.scot.permissions.core.dao.ICoreUserDao;
import com.scot.permissions.core.entity.CoreUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

/**
 * 权限用户dao测试
 * Created by shengke on 2016/10/11.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/applicationContext-base.xml"})
@ActiveProfiles("localhost")
public class CoreUserDaoTest {

    /**
     * 权限用户dao.
     */
    @Autowired
    private ICoreUserDao coreUserDao;

    private Date now = new Date();

    /**
     * 权限用户插入测试.
     */
    @Test
    public void insert() {
        CoreUser user = new CoreUser();
        user.setBzUserId(1L);
        user.setStatus(UserConstant.NORMAL);
        user.setCreator(1L);
        user.setUpdateUser(1L);
        user.setInserttime(now);
        user.setUpdatetime(now);
        user.setIsactive(BaseConstant.ABLE);
        coreUserDao.insert(user);
        System.out.println(user.getId());
    }

}
