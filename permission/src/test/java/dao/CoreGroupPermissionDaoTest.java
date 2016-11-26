package dao;

import com.scot.iframework.permission.constant.BaseConstant;
import com.scot.iframework.permission.dao.ICoreGroupPermissionDao;
import com.scot.iframework.permission.entity.CoreGroupPermission;
import com.scot.iframework.permission.entity.CorePermission;
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
 * 组权限dao测试
 * Created by shengke on 2016/10/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/applicationContext-base.xml"})
@ActiveProfiles("localhost")
public class CoreGroupPermissionDaoTest {

    /**
     * 组权限dao.
     */
    @Autowired
    private ICoreGroupPermissionDao groupPermissionDao;

    private Date now = new Date();

    /**
     * 根据组id、用户id获取用户权限关系数据测试.
     */
    @Test
    public void selectByGroupIdAndPermissionId() {
        CoreGroupPermission groupPermission = groupPermissionDao.selectByGroupIdAndPermissionId(1L, 1L);
        System.out.println(groupPermission);
    }

    /**
     * 获取groupId包含permissionIds中权限的数量测试.
     */
    @Test
    public void selectCountIsContain() {
        Long [] permissionIds = {1L, 2L, 3L};
        System.out.println(groupPermissionDao.selectCountIsContain(1L, permissionIds));
    }

    /**
     * 根据groupId删除PermissionIds中的所有权限测试.
     */
    @Test
    public void deleteByGroupIdAndPermissionIds() {
        Long [] permissionIds = {1L, 2L};
        groupPermissionDao.deleteByGroupIdAndPermissionIds(1L, permissionIds);
    }

    /**
     * 批量插入测试.
     */
    @Test
    public void insertList() {
        List<CoreGroupPermission> groupPermissions = new ArrayList<CoreGroupPermission>();
        Date now = new Date();
        for (int i = 0; i < 3; i++) {
            CoreGroupPermission groupPermission = new CoreGroupPermission();
            groupPermission.setCreator(1L);
            groupPermission.setUpdateUser(1L);
            groupPermission.setInserttime(now);
            groupPermission.setUpdatetime(now);
            groupPermission.setIsactive(BaseConstant.ABLE);
            groupPermission.setGroupId(1L);
            groupPermission.setPermissionId(1L + i);
            groupPermissions.add(groupPermission);
        }
        groupPermissionDao.insertList(groupPermissions);
    }

    /**
     * 获取用户所有组的权限测试.
     */
    @Test
    public void selectUserGroupsPermissions() {
        List<CorePermission> permissions = groupPermissionDao.selectUserGroupsPermissions(1L);
        System.out.println(permissions);
    }

    /**
     * 获取组自身权限.
     */
    @Test
    public void selectRolePermissions() {
        List<CorePermission> permissions = groupPermissionDao.selectGroupPermissions(1L);
        System.out.println(permissions);
    }

    /**
     * 获取组全部权限.
     */
    @Test
    public void selectRoleAllPermissions() {
        List<CorePermission> permissions = groupPermissionDao.selectGroupAllPermissions(1L);
        System.out.println(permissions);
    }
}
