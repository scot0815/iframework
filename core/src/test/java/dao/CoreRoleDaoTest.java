package dao;

import com.scot.permissions.core.constant.BaseConstant;
import com.scot.permissions.core.dao.ICoreRoleDao;
import com.scot.permissions.core.entity.CoreRole;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

/**
 * 角色dao测试
 * Created by shengke on 2016/10/11.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/applicationContext-base.xml"})
@ActiveProfiles("localhost")
public class CoreRoleDaoTest {

    /**
     * 角色dao.
     */
    @Autowired
    private ICoreRoleDao roleDao;

    private Date now = new Date();

    /**
     * 角色插入测试.
     */
    @Test
    public void insert() {
        CoreRole role = new CoreRole();
        role.setCreator(1L);
        role.setUpdateUser(1L);
        role.setInserttime(now);
        role.setUpdatetime(now);
        role.setIsactive(BaseConstant.ABLE);
        role.setRoleName("财务总监");
        roleDao.insert(role);
        System.out.println(role.getId());

    }

}
