package dao;

import com.scot.iframework.permission.constant.BaseConstant;
import com.scot.iframework.permission.dao.ICoreGroupRoleDao;
import com.scot.iframework.permission.entity.CoreGroup;
import com.scot.iframework.permission.entity.CoreGroupRole;
import com.scot.iframework.permission.entity.CoreRole;
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
 * 角色组测试Dao
 * Created by shengke on 2016/10/13.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/applicationContext-base.xml"})
@ActiveProfiles("localhost")
public class CoreGroupRoleDaoTest {

    /**
     * 角色组关系Dao.
     */
    @Autowired
    private ICoreGroupRoleDao groupRoleDao;

    /**
     * 根据roleId、groupId获取角色组关系测试.
     */
    @Test
    public void selectByRoleIdAndGroupId() {
        System.out.println(groupRoleDao.selectByRoleIdAndGroupId(1L, 1L));
    }


    /**
     * 获取roleId 包含groupIds中组的数量测试.
     */
    @Test
    public void selectCountIsContain() {
        Long [] groupIds = {1L, 2L};
        System.out.println(groupRoleDao.selectCountIsContain(1L, groupIds));
    }

    /**
     * 根据roleId删除groupIds中的所有组测试.
     */
    @Test
    public void deleteByRoleIdAndRoleIds() {
        Long [] groupIds = {1L, 2L};
        groupRoleDao.deleteByRoleIdAndGroupIds(1L, groupIds);
    }

    /**
     * 批量插入角色、组关系.
     */
    @Test
    public void insertList() {
        List<CoreGroupRole> groupRoles = new ArrayList<CoreGroupRole>();
        Date now = new Date();
        for (int i = 0; i < 3; i++) {
            CoreGroupRole groupRole = new CoreGroupRole();
            groupRole.setCreator(1L);
            groupRole.setUpdateUser(1L);
            groupRole.setInserttime(now);
            groupRole.setUpdatetime(now);
            groupRole.setIsactive(BaseConstant.ABLE);
            groupRole.setGroupId(1L);
            groupRole.setRoleId(i + 1L);
            groupRoles.add(groupRole);
        }
        groupRoleDao.insertList(groupRoles);
    }

    /**
     * 获取groupId 属于roleIds中角色的数量测试.
     */
    @Test
    public void selectCountIsBelong() {
        Long [] roleIds = {1L, 3L};
        System.out.println(groupRoleDao.selectCountIsBelong(1L, roleIds));
    }

    /**
     * 根据groupId删除roleIds中所有得角色测试.
     */
    @Test
    public void deleteByGroupIdAndRoleIds() {
        Long [] roleIds = {1L, 3L};
        System.out.println(groupRoleDao.deleteByGroupIdAndRoleIds(1L, roleIds));
    }

    /**
     * 获取角色下组列表测试.
     */
    @Test
    public void selectGroupsByRoleId() {
        List<CoreGroup> groups = groupRoleDao.selectGroupsByRoleId(1L);
        System.out.println(groups);
    }

    /**
     * 获取组下所有角色测试.
     */
    @Test
    public void selectRolesByGroupId() {
        List<CoreRole> coreRoles = groupRoleDao.selectRolesByGroupId(1L);
        System.out.println(coreRoles);
    }
}
