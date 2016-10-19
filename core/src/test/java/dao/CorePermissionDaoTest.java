package dao;

import com.scot.permissions.core.constant.BaseConstant;
import com.scot.permissions.core.constant.PermissionConstant;
import com.scot.permissions.core.dao.ICorePermissionDao;
import com.scot.permissions.core.entity.CorePermission;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

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
        permission.setFunctionCode("001002002");
        permission.setParentFunctionCode("001002");
        permission.setUrl("/letter/replyList");
        permission.setType(PermissionConstant.FUNCTION_TREE);
        permission.setIsChild(PermissionConstant.IS_CHILD);
        permission.setCategory(PermissionConstant.VISIT_PERMISSION_CODE);
        permissionDao.insert(permission);
        System.out.println(permission.getId());
    }

}
