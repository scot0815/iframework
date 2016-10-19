package dao;

import com.scot.permissions.core.constant.BaseConstant;
import com.scot.permissions.core.dao.ICoreUserGroupDao;
import com.scot.permissions.core.entity.CoreGroup;
import com.scot.permissions.core.entity.CoreUser;
import com.scot.permissions.core.entity.CoreUserGroup;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 用户、组 测试dao
 * Created by shengke on 2016/10/12.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/applicationContext-base.xml"})
@ActiveProfiles("localhost")
public class CoreUserGroupDaoTest {

    /**
     * 用户、组Dao.
     */
    @Autowired
    private ICoreUserGroupDao userGroupDao;

    /**
     * 根据权限用户Id、组Id获取用户、组关系信息测试.
     */
    @Test
    public void selectByCoreUserIdAndGroupId() {
        System.out.println(userGroupDao.selectByCoreUserIdAndGroupId(1L, 1L));
    }

    /**
     * 获取权限用户id属于组groupIds的数量测试.
     */
    @Test
    public void selectCountIsBelong() {
        Long [] ids = {1L, 2L};
        System.out.println(userGroupDao.selectCountIsBelong(1L, ids));
    }

    /**
     * 根据权限用户id删除groupIds中的所有组测试.
     */
    @Test
    public void deleteByCoreUserIdAndRoleIds() {
        Long [] ids = {1L, 2L};
        System.out.println(userGroupDao.deleteByCoreUserIdAndGroupIds(1L, ids));
    }

    /**
     * 批量插入用户、组关系测试.
     */
    @Test
    public void insertList() {
        List<CoreUserGroup> userGroups = new ArrayList<CoreUserGroup>();
        Date now = new Date();
        for (int i = 0; i < 3 ; i++) {
            CoreUserGroup userGroup = new CoreUserGroup();
            userGroup.setCreator(1L);
            userGroup.setUpdateUser(1L);
            userGroup.setInserttime(now);
            userGroup.setUpdatetime(now);
            userGroup.setIsactive(BaseConstant.ABLE);
            userGroup.setCoreUserId(1L);
            userGroup.setGroupId(i+3L);
            userGroups.add(userGroup);
        }
        System.out.println(userGroupDao.insertList(userGroups));
    }

    /**
     * 查询用户所有组测试.
     */
    @Test
    public void selectUserGroups() {
        List<CoreGroup> groups = userGroupDao.selectUserGroups(1L);
        System.out.println(groups);
    }

    /**
     * 查询组下所有用户测试.
     */
    @Test
    public void selectGroupUsers() {
        List<CoreUser> users = userGroupDao.selectGroupUsers(1L);
        System.out.println(users);
    }

    /**
     * 组下用户的数量测试.
     */
    @Test
    public void selectCountIsContain() {
        Long [] ids = {1L, 2L};
        System.out.println(userGroupDao.selectCountIsContain(1L, ids));
    }

    /**
     * 删除组下用户测试.
     */
    @Test
    public void deleteByRoleIdAndCoreUserIds() {
        Long [] ids = {1L, 2L};
        System.out.println(userGroupDao.deleteByGroupIdAndCoreUserIds(1L, ids));
    }
}
