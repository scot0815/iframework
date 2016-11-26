package utils;

import com.scot.iframework.permission.utils.AnnotationValidationUtil;
import com.scot.iframework.permission.vo.AddPermissionVo;
import org.junit.Test;

/**
 * bean Validation校验测试
 * Created by shengke on 2016/10/31.
 */
public class AnnotationValidationUtilTest {

    @Test
    public void test() {
        AddPermissionVo vo = new AddPermissionVo();
        vo.setOperator(1L);
        AnnotationValidationUtil.validate(vo);
    }

}
