package service;

import com.scot.permissions.core.constant.BaseConstant;
import com.scot.permissions.core.entity.CoreRole;
import com.scot.permissions.core.service.ICoreRoleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

/**
 * 权限service测试类
 * Created by shengke on 2016/10/12.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/applicationContext-base.xml"})
@ActiveProfiles("localhost")
public class CoreRoleServiceTest {

    /**
     * 权限server.
     */
    @Autowired
    private ICoreRoleService roleService;
    private Date now = new Date();

    /**
     * 添加角色测试.
     */
    @Test
    public void addCoreRole() {
        CoreRole role = new CoreRole();
        role.setCreator(1L);
        role.setUpdateUser(1L);
        role.setInserttime(now);
        role.setUpdatetime(now);
        role.setIsactive(BaseConstant.ABLE);
        role.setRoleName("销售总监");
        System.out.println(roleService.addCoreRole(role));

    }

    /**
     * 删除角色测试.
     */
    @Test
    public void delCoreRole() {
        roleService.delCoreRole(3L, 1L);
    }

    /**
     * 更新角色测试.
     */
    @Test
    public void updateCoreRole() {
        CoreRole coreRole = roleService.queryCoreRoleById(3L);
        coreRole.setUpdatetime(new Date());
        coreRole.setRoleName("销售总监");
        roleService.updateCoreRole(coreRole);
    }

    /**
     * 角色添加组测试.
     */
    @Test
    public void addGroup() {
        roleService.addGroup(1L, 1L, 1L);
    }

    /**
     * 角色删除组测试.
     */
    @Test
    public void delGroup() {
        roleService.delGroup(1L, 1L, 1L);
    }


    /**
     * 角色批量添加组测试.
     */
    @Test
    public void addGroups() {
        Long [] groupIds = {1L, 2L};
        roleService.addGroups(1L, groupIds, 1L);
    }

    /**
     * 角色批量删除组测试.
     */
    @Test
    public void delGroups() {
        Long [] groupIds = {1L, 2L};
        roleService.delGroups(1L, groupIds, 1L);
    }

    /**
     * 角色添加权限测试.
     */
    @Test
    public void addPermission() {
        roleService.addPermission(1L, 4L, 1L);
    }

    /**
     * 角色批量添加权限测试.
     */
    @Test
    public void addPermissions() {
        Long [] ids = {1L, 2L};
        roleService.addPermissions(1L, ids, 1L);
    }

    /**
     * 角色删除权限测试.
     */
    @Test
    public void delPermission() {
        roleService.delPermission(1L, 2L, 1L);
    }

    /**
     * 角色批量删除权限测试.
     */
    @Test
    public void delPermissions() {
        Long [] ids = {3L, 2L, 1L};
        roleService.delPermissions(1L, ids, 1L);
    }

    /**
     * 获取角色下的组列表测试.
     */
    @Test
    public void queryContainGroups() {
        System.out.println(roleService.queryContainGroups(1L));
    }

    /**
     * 角色添加用户测试.
     */
    @Test
    public void addCoreUser() {
        roleService.addCoreUser(1L, 1L, 1L);
    }

    /**
     * 角色批量添加用户测试.
     */
    @Test
    public void addCoreUsers() {
        Long [] ids = {2L, 3L};
        roleService.addCoreUsers(1L, ids, 1L);
    }

    /**
     * 角色删除用户测试.
     */
    @Test
    public void delCoreUser() {
        roleService.delCoreUser(1L, 1L, 1L);
    }

    /**
     * 角色批量删除用户测试.
     */
    @Test
    public void delCoreUsers() {
        Long [] ids = {2L, 3L};
        roleService.delCoreUsers(1L, ids, 1L);
    }
}
