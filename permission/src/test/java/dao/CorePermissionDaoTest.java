package dao;

import com.scot.iframework.permission.constant.BaseConstant;
import com.scot.iframework.permission.constant.PermissionConstant;
import com.scot.iframework.permission.dao.ICorePermissionDao;
import com.scot.iframework.permission.entity.CorePermission;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;

/**
 * 权限dao测试
 * Created by shengke on 2016/10/11.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/applicationContext-base.xml"})
@ActiveProfiles("localhost")
public class CorePermissionDaoTest {

    /**
     * 权限dao.
     */
    @Autowired
    private ICorePermissionDao permissionDao;

    private Date now = new Date();

    /**
     * 权限插入测试.
     */
    @Test
    public void insert() {
        CorePermission permission = new CorePermission();
        permission.setCreator(1L);
        permission.setUpdateUser(1L);
        permission.setInserttime(now);
        permission.setUpdatetime(now);
        permission.setIsactive(BaseConstant.ABLE);
        permission.setFunctionFlag("您的善意...........");
        permission.setFunctionCode("001002002" + PermissionConstant.VISIT_PERMISSION);
        permission.setParentFunctionCode("001002"+PermissionConstant.VISIT_PERMISSION);
        permission.setUrl("/letter/replyList");
        permission.setType(PermissionConstant.FUNCTION_TREE);
        permission.setIsChild(PermissionConstant.IS_CHILD);
        permission.setCategory(PermissionConstant.VISIT_PERMISSION_CODE);
        permission.setTier(Byte.valueOf("5"));
        permissionDao.insert(permission);
        System.out.println(permission.getId());
    }

    /**
     * 根据类型获取权限列表.
     */
    @Test
    public void selectPermissionByType() {
        permissionDao.selectPermissionByType(1,1);
    }

    /**
     * 获取父功能code下最大code.
     */
    @Test
    public void selectMaxCodeByParentCode() {
        System.out.println(permissionDao.selectMaxCodeByParentCode("001001V"));
    }

    /**
     * 获取父功能code下最大code.
     */
    @Test
    public void selectByFunctionCode() {
        CorePermission corePermission = permissionDao.selectByFunctionCode("001001V");
        System.out.println(corePermission);
    }

    /**
     * 获取父权限下一级所有权限.
     */
    @Test
    public void selectByParentFunctionCode() {
        List<CorePermission> corePermissionList = permissionDao.selectByParentFunctionCode("001001V");
        System.out.println(corePermissionList);
    }

}
