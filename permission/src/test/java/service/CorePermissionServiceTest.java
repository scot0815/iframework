package service;

import com.scot.iframework.permission.constant.BaseConstant;
import com.scot.iframework.permission.constant.PermissionConstant;
import com.scot.iframework.permission.entity.CorePermission;
import com.scot.iframework.permission.enums.PermissionTypeEnum;
import com.scot.iframework.permission.enums.PermissionCategoryEnum;
import com.scot.iframework.permission.service.ICorePermissionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;

/**
 * 权限service测试类
 * Created by shengke on 2016/10/21.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/applicationContext-base.xml"})
@ActiveProfiles("localhost")
public class CorePermissionServiceTest {


    /**
     * 权限service.
     */
    @Autowired
    private ICorePermissionService permissionService;

    /**
     * 添加权限测试.
     */
    @Test
    public void addCorePermission() {
        Date now = new Date();
        CorePermission permission = new CorePermission();
        permission.setCreator(1L);
        permission.setUpdateUser(1L);
        permission.setInserttime(now);
        permission.setUpdatetime(now);
        permission.setIsactive(BaseConstant.ABLE);
        permission.setFunctionFlag("您的善意1");
        permission.setFunctionCode("001002003"+PermissionConstant.VISIT_PERMISSION);
        permission.setParentFunctionCode("001002"+PermissionConstant.VISIT_PERMISSION);
        permission.setUrl("/letter/replyList");
        permission.setType(PermissionConstant.FUNCTION_TREE);
        permission.setIsChild(PermissionConstant.IS_CHILD);
        permission.setCategory(PermissionConstant.VISIT_PERMISSION_CODE);
        permission.setTier(Byte.valueOf("5"));
        permissionService.addCorePermission(permission);
    }

    /**
     * 删除权限测试.
     */
    @Test
    public void delCorePermission() {
        permissionService.delCorePermission(21L, 1L);
    }

    /**
     * 更新权限测试.
     */
    @Test
    public void updateCorePermission() {
        CorePermission permission = permissionService.queryCorePermissionById(21L);
        permission.setFunctionFlag("您的善意----1");
        permissionService.updateCorePermission(permission);
    }

    /**
     * 获取权限列表测试.
     */
    @Test
    public void queryAllCorePermission() {
        List<CorePermission> permissions = permissionService.queryAllCorePermission();
        System.out.println(permissions);
    }

    /**
     * 根据权限类型获取权限列表测试.
     */
    @Test
    public void queryCorePermissionByType() {
        List<CorePermission> permissions = permissionService.queryCorePermissionByType(PermissionTypeEnum.LEFT_TREE,
                PermissionCategoryEnum.VISIT_PERMISSION_CODE);
        System.out.println(permissions);
    }

    /**
     * 根据id获取权限测试.
     */
    @Test
    public void queryCorePermissionById() {
        System.out.println(permissionService.queryCorePermissionById(21L));
    }
}
