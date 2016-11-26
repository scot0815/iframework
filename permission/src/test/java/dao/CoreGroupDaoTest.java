package dao;

import com.scot.iframework.permission.constant.BaseConstant;
import com.scot.iframework.permission.dao.ICoreGroupDao;
import com.scot.iframework.permission.entity.CoreGroup;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

/**
 * 权限组dao测试
 * Created by shengke on 2016/10/11.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/applicationContext-base.xml"})
@ActiveProfiles("localhost")
public class CoreGroupDaoTest {

    /**
     * 权限组dao.
     */
    @Autowired
    private ICoreGroupDao groupDao;

    private Date now = new Date();

    /**
     * 权限组插入测试.
     */
    @Test
    public void insert() {
        CoreGroup group = new CoreGroup();
        group.setCreator(1L);
        group.setUpdateUser(1L);
        group.setInserttime(now);
        group.setUpdatetime(now);
        group.setIsactive(BaseConstant.ABLE);
        group.setGroupName("财务组");
        groupDao.insert(group);
        System.out.println(group.getId());
    }

}
