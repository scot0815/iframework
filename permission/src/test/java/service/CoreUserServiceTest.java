package service;

import com.scot.iframework.permission.constant.BaseConstant;
import com.scot.iframework.permission.constant.UserConstant;
import com.scot.iframework.permission.entity.CorePermission;
import com.scot.iframework.permission.entity.CoreUser;
import com.scot.iframework.permission.service.ICoreUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 用户service测试类
 * Created by shengke on 2016/10/12.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/applicationContext-base.xml"})
@ActiveProfiles("localhost")
public class CoreUserServiceTest {

    /**
     * 用户server.
     */
    @Autowired
    private ICoreUserService userService;

    private Date now = new Date();

    /**
     * 添加权限用户测试.
     */
    @Test
    public void addCoreUser() {
        CoreUser user = new CoreUser();
        user.setBzUserId(2L);
        user.setStatus(UserConstant.NORMAL);
        user.setCreator(1L);
        user.setUpdateUser(1L);
        user.setInserttime(now);
        user.setUpdatetime(now);
        user.setIsactive(BaseConstant.ABLE);
        userService.addCoreUser(user);
    }

    /**
     * 更新权限用户测试.
     */
    @Test
    public void updateCoreUser() {
        CoreUser coreUser = userService.queryCoreUserById(2L);
        coreUser.setBzUserId(20L);
        userService.updateCoreUser(coreUser);
    }

    /**
     * 删除权限用户测试.
     */
    @Test
    public void delCoreUser() {
        userService.delCoreUser(2L, 1L);
    }

    /**
     * 用户添加组测试.
     */
    @Test
    public void addGroup() {
        userService.addGroup(1L, 1L, 1L);
    }

    /**
     * 用户批量添加组测试.
     */
    @Test
    public void addGroups() {
        Long [] ids = {1L, 2L};
        userService.addGroups(1L, ids, 1L);
    }

    /**
     * 用户删除组测试.
     */
    @Test
    public void delGroup() {
        userService.delGroup(1L,1L,1L);
    }

    /**
     * 用户批量删除组测试.
     */
    @Test
    public void delGroups() {
        Long [] ids = {3L, 2L};
        userService.delGroups(1L, ids, 1L);
    }

    /**
     * 用户添加角色测试.
     */
    @Test
    public void addRole() {
        userService.addRole(1L, 1L, 1L);
    }

    /**
     * 用户批量添加角色测试.
     */
    @Test
    public void addRoles() {
        Long [] ids = {1L, 2L};
        userService.addRoles(1L, ids, 1L);
    }

    /**
     * 用户删除角色测试.
     */
    @Test
    public void delRole() {
        userService.delRole(1L, 1L, 1L);
    }

    /**
     * 用户批量删除角色测试.
     */
    @Test
    public void delRoles() {
        Long [] ids = {3L, 2L};
        userService.delRoles(1L, ids, 1L);
    }

    /**
     * 用户添加权限测试.
     */
    @Test
    public void addPermission() {
        userService.addPermission(1L, 2L, 1L);
    }

    /**
     * 用户批量添加权限测试.
     */
    @Test
    public void addPermissions() {
        Long [] ids = {3L, 2L};
        userService.addPermissions(1L, ids, 1L);
    }

    /**
     * 用户删除权限测试.
     */
    @Test
    public void delPermission() {
        userService.delPermission(1L, 2L, 1L);
    }

    /**
     * 用户批量删除权限测试.
     */
    @Test
    public void delPermissions() {
        Long [] ids = {3L, 2L, 1L};
        userService.delPermissions(1L, ids, 1L);
    }

    /**
     * 查询用户自身权限测试.
     */
    @Test
    public void queryPermissions() {
        System.out.println(userService.queryPermissions(1L));
    }

    /**
     * 查询用户角色测试.
     */
    @Test
    public void queryRoles() {
        System.out.println(userService.queryRoles(1L));
    }

    /**
     * 查询用户组测试.
     */
    @Test
    public void queryGroups() {
        System.out.println(userService.queryGroups(1L));
    }

    /**
     * 获取用户所有角色权限测试.
     */
    @Test
    public void queryRolesPermissions() {
        System.out.println(userService.queryRolesPermissions(1L));
    }

    /**
     * 获取用户所有组权限测试.
     */
    @Test
    public void queryGroupsPermissions() {
        List<CorePermission> permissions = userService.queryGroupsPermissions(1L);
        Map<String, CorePermission> maps = permissions.stream().collect(
                Collectors.toMap(CorePermission:: getFunctionCode, p -> p));
        System.out.println(maps);
    }

    /**
     * 获取用户的所有权限.
     */
    @Test
    public void queryAllPermissionsById() {
        List<CorePermission> permissions = userService.queryAllPermissionsById(1L);
        //permissions.sort((c1, c2) -> c1.getId().compareTo(c2.getId()));
        permissions.stream().distinct();
        Collections.sort(permissions, (a, b) -> b.compareTo(a));
        Map<String, CorePermission> maps = permissions.stream()
                /* .sorted(new Comparator<CorePermission>() {
                    @Override
                    public int compare(CorePermission o1, CorePermission o2) {
                        int answer;
                        if (o1.getFunctionCode().equals(o2.getFunctionCode())) {
                            answer = 0;
                        } else if (o2.getFunctionCode().compareTo(o1.getFunctionCode()) < 0) {
                            answer = 1;
                        } else {
                            answer = -1;
                        }
                        return -1 * answer;
                    }
                })*/
                .distinct()
                /*.sorted((f1, f2) -> Long.compare(f2.getId(), f1.getId()))*/
                .collect(Collectors.toMap(CorePermission::getFunctionCode, p -> p));
        Map<String, CorePermission> result = new LinkedHashMap<>();
        maps.entrySet().stream()
                .sorted(Map.Entry.<String, CorePermission>comparingByKey())
                .forEachOrdered(x -> result.put(x.getKey(), x.getValue()));
        System.out.println(maps);
    }

}
