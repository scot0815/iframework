package service;

import com.scot.iframework.permission.constant.BaseConstant;
import com.scot.iframework.permission.entity.CoreGroup;
import com.scot.iframework.permission.entity.CorePermission;
import com.scot.iframework.permission.service.ICoreGroupService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;

/**
 * 权限组service测试类
 * Created by shengke on 2016/10/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/applicationContext-base.xml"})
@ActiveProfiles("localhost")
public class CoreGroupServiceTest {

    /**
     * 权限组service
     */
    @Autowired
    private ICoreGroupService groupService;

    /**
     * 添加组信息测试.
     */
    @Test
    public void addCoreGroup() {
        Date now = new Date();
        CoreGroup group = new CoreGroup();
        group.setCreator(1L);
        group.setUpdateUser(1L);
        group.setInserttime(now);
        group.setUpdatetime(now);
        group.setIsactive(BaseConstant.ABLE);
        group.setGroupName("IT组");
        groupService.addCoreGroup(group);
    }

    /**
     * 删除组信息测试.
     */
    @Test
    public void delCoreGroup() {
        groupService.delCoreGroup(3L, 1L);
    }

    /**
     * 更新组信息测试.
     */
    @Test
    public void updateCoreGroup() {
        CoreGroup group = groupService.queryCoreGroupById(3L);
        group.setIsactive(BaseConstant.ABLE);
        group.setUpdateUser(1L);
        group.setUpdatetime(new Date());
        groupService.updateCoreGroup(group);
    }

    /**
     * 赋予组 角色测试.
     */
    @Test
    public void addRole() {
        groupService.addRole(1L, 1L, 1L);
    }

    /**
     * 批量赋予组 角色测试.
     */
    @Test
    public void addRoles() {
        Long [] ids = {3L, 2L};
        groupService.addRoles(1L, ids, 1L);
    }

    /**
     * 取消组 角色测试.
     */
    @Test
    public void delRole() {
        groupService.delRole(1L, 1L, 1L);
    }

    /**
     * 取消组 角色测试.
     */
    @Test
    public void delRoles() {
        Long [] ids = {3L, 2L};
        groupService.delRoles(1L, ids, 1L);
    }

    /**
     * 组添加权限测试.
     */
    @Test
    public void addPermission() {
        groupService.addPermission(1L, 1L, 1L);
    }

    /**
     * 组批量添加权限测试.
     */
    @Test
    public void addPermissions() {
        Long [] ids = {3L, 2L, 1L};
        groupService.addPermissions(1L, ids, 1L);
    }

    /**
     * 组删除权限测试.
     */
    @Test
    public void delPermission() {
        groupService.delPermission(1L, 2L, 1L);
    }

    /**
     * 组批量删除权限测试.
     */
    @Test
    public void delPermissions() {
        Long [] ids = {3L, 1L};
        groupService.delPermissions(1L, ids, 1L);
    }

    /**
     * 组添加用户测试.
     */
    @Test
    public void addCoreUser() {
        groupService.addCoreUser(1L, 1L, 1L);
    }

    /**
     * 组批量添加用户测试.
     */
    @Test
    public void addCoreUsers() {
        Long [] ids = {2L, 3L};
        groupService.addCoreUsers(1L, ids, 1L);
    }

    /**
     * 组删除用户测试.
     */
    @Test
    public void delCoreUser() {
        groupService.delCoreUser(1L, 1L, 1L);
    }

    /**
     * 组批量删除用户测试.
     */
    @Test
    public void delCoreUsers() {
        Long [] ids = {2L, 3L};
        groupService.delCoreUsers(1L, ids, 1L);
    }

    /**
     * 组自身权限测试.
     */
    @Test
    public void queryPermission() {
        List<CorePermission> permissions = groupService.queryPermission(1L);
        System.out.println(permissions);
    }

    /**
     * 组所有权限测试.
     */
    @Test
    public void queryAllPermission() {
        List<CorePermission> permissions = groupService.queryAllPermission(1L);
        System.out.println(permissions);
    }
}
