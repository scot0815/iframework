package dao;

import com.scot.iframework.permission.constant.BaseConstant;
import com.scot.iframework.permission.dao.ICoreUserRoleDao;
import com.scot.iframework.permission.entity.CoreRole;
import com.scot.iframework.permission.entity.CoreUser;
import com.scot.iframework.permission.entity.CoreUserRole;
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
 * 用户、角色 测试dao
 * Created by shengke on 2016/10/12.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/applicationContext-base.xml"})
@ActiveProfiles("localhost")
public class CoreUserRoleDaoTest {

    /**
     * 用户、角色Dao.
     */
    @Autowired
    private ICoreUserRoleDao userRoleDao;

    /**
     * 根据权限用户Id、角色Id获取用户、角色关系信息测试.
     */
    @Test
    public void selectByCoreUserIdAndGroupId() {
        System.out.println(userRoleDao.selectByCoreUserIdAndRoleId(1L, 1L));
    }

    /**
     * 获取权限用户id属于组groupIds的数量测试.
     */
    @Test
    public void selectCountIsBelong() {
        Long [] ids = {1L, 2L};
        System.out.println(userRoleDao.selectCountIsBelong(1L, ids));
    }

    /**
     * 根据权限用户id删除roleIds中的所有角色.
     */
    @Test
    public void deleteByCoreUserIdAndRoleIds() {
        Long [] ids = {1L, 2L};
        System.out.println(userRoleDao.deleteByCoreUserIdAndRoleIds(1L, ids));
    }

    /**
     * 批量插入用户、角色关系测试.
     */
    @Test
    public void insertList() {
        List<CoreUserRole> userRoles = new ArrayList<CoreUserRole>();
        for (int i = 0; i < 3; i++) {
            Date now = new Date();
            CoreUserRole userRole = new CoreUserRole();
            userRole.setCreator(1L);
            userRole.setUpdateUser(1L);
            userRole.setInserttime(now);
            userRole.setUpdatetime(now);
            userRole.setIsactive(BaseConstant.ABLE);
            userRole.setCoreUserId(1L);
            userRole.setRoleId(i+3L);
            userRoles.add(userRole);
        }
        System.out.println(userRoleDao.insertList(userRoles));
    }

    /**
     * 查询用户所有角色测试.
     */
    @Test
    public void selectUserRoles() {
        List<CoreRole> roles = userRoleDao.selectUserRoles(1L);
        System.out.println(roles);
    }

    /**
     * 查询角色下所有角色测试.
     */
    @Test
    public void selectRoleUsers() {
        List<CoreUser> users = userRoleDao.selectRoleUsers(1L);
        System.out.println(users);
    }

    /**
     * 获取角色下用户的数量测试.
     */
    @Test
    public void selectCountIsContain() {
        Long [] ids = {1L, 2L};
        System.out.println(userRoleDao.selectCountIsContain(1L, ids));
    }

    /**
     * 删除角色下用户测试.
     */
    @Test
    public void deleteByRoleIdAndCoreUserIds() {
        Long [] ids = {1L, 2L};
        userRoleDao.deleteByRoleIdAndCoreUserIds(1L, ids);
    }
}
