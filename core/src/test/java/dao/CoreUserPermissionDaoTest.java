package dao;

import com.scot.permissions.core.constant.BaseConstant;
import com.scot.permissions.core.dao.ICoreUserPermissionDao;
import com.scot.permissions.core.entity.CorePermission;
import com.scot.permissions.core.entity.CoreUserPermission;
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
 * 用户权限dao测试
 * Created by shengke on 2016/10/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/applicationContext-base.xml"})
@ActiveProfiles("localhost")
public class CoreUserPermissionDaoTest {

    /**
     * 用户权限dao.
     */
    @Autowired
    private ICoreUserPermissionDao userPermissionDao;

    private Date now = new Date();

    /**
     * 根据权限id、用户id获取用户权限关系数据测试.
     */
    @Test
    public void selectByCoreUserIdAndPermissionId() {
        CoreUserPermission userPermission = userPermissionDao.selectByCoreUserIdAndPermissionId(1L, 1L);
        System.out.println(userPermission);
    }

    /**
     *获取coreUserId包含permissionIds中权限的数量测试.
     */
    @Test
    public void selectCountIsContain() {
        Long [] permissionIds = {1L, 2L, 3L};
        System.out.println(userPermissionDao.selectCountIsContain(1L, permissionIds));
    }

    /**
     * 根据CoreUserId删除PermissionIds中的所有权限测试.
     */
    @Test
    public void deleteByCoreUserIdAndGroupIds() {
        Long [] permissionIds = {1L, 2L};
        userPermissionDao.deleteByCoreUserIdAndPermissionIds(1L, permissionIds);
    }

    /**
     * 批量插入测试.
     */
    @Test
    public void insertList() {
        List<CoreUserPermission> userPermissions = new ArrayList<CoreUserPermission>();
        Date now = new Date();
        for (int i = 0; i < 3; i++) {
            CoreUserPermission userPermission = new CoreUserPermission();
            userPermission.setCreator(1L);
            userPermission.setUpdateUser(1L);
            userPermission.setInserttime(now);
            userPermission.setUpdatetime(now);
            userPermission.setIsactive(BaseConstant.ABLE);
            userPermission.setCoreUserId(1L);
            userPermission.setPermissionId(1L+i);
            userPermissions.add(userPermission);
        }
        userPermissionDao.insertList(userPermissions);
    }

    /**
     * 获取用户自身权限测试.
     */
    @Test
    public void selectUserPermission() {
        List<CorePermission> corePermissions = userPermissionDao.selectUserPermissions(1L);
        System.out.println(corePermissions);
    }
}
