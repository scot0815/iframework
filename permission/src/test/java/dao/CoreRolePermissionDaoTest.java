package dao;

import com.scot.iframework.permission.constant.BaseConstant;
import com.scot.iframework.permission.dao.ICoreRolePermissionDao;
import com.scot.iframework.permission.entity.CorePermission;
import com.scot.iframework.permission.entity.CoreRolePermission;
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
 * 角色权限dao测试
 * Created by shengke on 2016/10/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/applicationContext-base.xml"})
@ActiveProfiles("localhost")
public class CoreRolePermissionDaoTest {

    /**
     * 角色权限dao.
     */
    @Autowired
    private ICoreRolePermissionDao rolePermissionDao;

    private Date now = new Date();

    /**
     * 根据角色id、用户id获取用户权限关系数据测试.
     */
    @Test
    public void selectByRoleIdAndPermissionId() {
        CoreRolePermission rolePermission = rolePermissionDao.selectByRoleIdAndPermissionId(1L, 1L);
        System.out.println(rolePermission);
    }

    /**
     * 获取roleId包含permissionIds中权限的数量测试.
     */
    @Test
    public void selectCountIsContain() {
        Long [] permissionIds = {1L, 2L, 3L};
        System.out.println(rolePermissionDao.selectCountIsContain(1L, permissionIds));
    }

    /**
     * 根据roleId删除PermissionIds中的所有权限测试.
     */
    @Test
    public void deleteByRoleIdAndPermissionIds() {
        Long [] permissionIds = {1L, 2L};
        rolePermissionDao.deleteByRoleIdAndPermissionIds(1L, permissionIds);
    }

    /**
     * 批量插入测试.
     */
    @Test
    public void insertList() {
        List<CoreRolePermission> rolePermissions = new ArrayList<CoreRolePermission>();
        Date now = new Date();
        for (int i = 0; i < 3; i++) {
            CoreRolePermission rolePermission = new CoreRolePermission();
            rolePermission.setCreator(1L);
            rolePermission.setUpdateUser(1L);
            rolePermission.setInserttime(now);
            rolePermission.setUpdatetime(now);
            rolePermission.setIsactive(BaseConstant.ABLE);
            rolePermission.setRoleId(1L);
            rolePermission.setPermissionId(1L + i);
            rolePermissions.add(rolePermission);
        }
        rolePermissionDao.insertList(rolePermissions);
    }

    /**
     * 获取用户所有角色权限测试.
     */
    @Test
    public void selectUserRolesPermissions() {
        List<CorePermission> permissions = rolePermissionDao.selectUserRolesPermissions(1L);
        System.out.println(permissions);
    }

    /**
     * 获取角色权限测试.
     */
    @Test
    public void selectRolePermissions() {
        List<CorePermission> permissions = rolePermissionDao.selectRolePermissions(1L);
        System.out.println(permissions);
    }

}
