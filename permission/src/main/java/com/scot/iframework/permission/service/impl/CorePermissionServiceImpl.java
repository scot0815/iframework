package com.scot.iframework.permission.service.impl;

import com.scot.iframework.permission.constant.PermissionConstant;
import com.scot.iframework.permission.dao.ICorePermissionDao;
import com.scot.iframework.permission.enums.PermissionCategoryEnum;
import com.scot.iframework.permission.enums.PermissionTypeEnum;
import com.scot.iframework.permission.utils.AnnotationValidationUtil;
import com.scot.iframework.permission.vo.AddPermissionVo;
import com.scot.iframework.permission.vo.UpdatePermissionVo;
import com.scot.iframework.permission.constant.BaseConstant;
import com.scot.iframework.permission.entity.CorePermission;
import com.scot.iframework.permission.service.ICorePermissionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * 权限service实现
 * Created by shengke on 2016/10/21.
 */
@Service("corePermissionService")
public class CorePermissionServiceImpl implements ICorePermissionService{

    /**
     * 权限code左面补位.
     */
    private static final String  FUNCTION_CODE_LEFT_COVER = "00";

    /**
     * 权限Dao.
     */
    @Resource(name = "corePermissionDao")
    private ICorePermissionDao permissionDao;


    /**
     * 添加权限.
     *
     * @param corePermission 权限信息
     * @return 权限id
     */
    @Override
    public Long addCorePermission(CorePermission corePermission) {
        permissionDao.insert(corePermission);
        return corePermission.getId();
    }

    /**
     * 删除权限.
     *
     * @param id       权限id
     * @param operator 操作人
     */
    @Override
    public void delCorePermission(Long id, Long operator) {
        CorePermission permission = this.queryCorePermissionById(id);
        permission.setUpdatetime(new Date());
        permission.setUpdateUser(operator);
        permission.setIsactive(BaseConstant.DISABLE);
        permissionDao.updateByPrimaryKey(permission);
    }

    /**
     * 更新权限.
     *
     * @param corePermission 权限信息
     */
    @Override
    public void updateCorePermission(CorePermission corePermission) {
        corePermission.setUpdatetime(new Date());
        permissionDao.updateByPrimaryKey(corePermission);
    }

    /**
     * 获取权限列表.
     *
     * @return 权限列表
     */
    @Override
    public List<CorePermission> queryAllCorePermission() {
        return this.queryCorePermissionByType(null, null);
    }

    /**
     * 根据权限类型获取权限列表.
     *
     * @param permissionType 权限类型枚举
     * @return 权限列表
     */
    @Override
    public List<CorePermission> queryCorePermissionByType(PermissionTypeEnum permissionType,
                                                          PermissionCategoryEnum permissionCategory) {
        return permissionDao.selectPermissionByType(permissionType != null ? permissionType.getValue() : null,
                permissionCategory != null ? permissionCategory.getValue() : null);
    }

    /**
     * 根据id获取权限.
     *
     * @param id 主键id.
     * @return 权限信息
     */
    @Override
    public CorePermission queryCorePermissionById(Long id) {
        return permissionDao.selectByPrimaryKey(id);
    }

    /**
     * 新增节点权限.
     * 页面新增权限时使用，同时添加访问和控制权限.
     *
     * @param vo 权限vo
     * @return
     */
    @Override
    public Map<Byte, CorePermission> addBizPermission(AddPermissionVo vo) {

        //数据验证.
        AnnotationValidationUtil.validate(vo);

        Map<Byte, CorePermission> map = new HashMap<Byte, CorePermission>();
        String parentFunctionCode = vo.getParentFunctionCode();

        //查询父权限信息
        CorePermission parentPermission = permissionDao.selectByFunctionCode(parentFunctionCode);
        if(null != parentPermission) {

            //添加访问和控制权限
            String max = permissionDao.selectMaxCodeByParentCode(parentFunctionCode);
            Long maxCode = null;
            if(StringUtils.isEmpty(max)) {
                maxCode = Long.valueOf(parentFunctionCode.substring(0, parentFunctionCode.length() - 1) + "001");
            } else {
                maxCode = Long.valueOf(max.substring(0, max.length() - 1))  + 1L;
            }
            max = FUNCTION_CODE_LEFT_COVER + maxCode;
            CorePermission visitPermission = addPermissionForCategory(vo, PermissionConstant.VISIT_PERMISSION_CODE,
                    max + PermissionConstant.VISIT_PERMISSION, parentPermission.getTier() + 1);
            CorePermission controlPermission = addPermissionForCategory(vo, PermissionConstant.CONTROL_PERMISSION_CODE,
                    max + PermissionConstant.CONTROL_PERMISSION, parentPermission.getTier() + 1);
            map.put(PermissionConstant.VISIT_PERMISSION_CODE, visitPermission);
            map.put(PermissionConstant.CONTROL_PERMISSION_CODE, controlPermission);

            //如果父权限是子节点更新.
            if(PermissionConstant.IS_CHILD == parentPermission.getIsChild()) {
                parentPermission.setIsChild(PermissionConstant.IS_NOT_CHILD);
                parentPermission.setUpdatetime(new Date());
                parentPermission.setUpdateUser(vo.getOperator());
                this.updateCorePermission(parentPermission);

                //更新父权限对应的控制权限(默认进来的访问权限)
                String parentControlFunctionCode = parentFunctionCode.substring(0, parentFunctionCode.length() - 1)
                        + PermissionConstant.CONTROL_PERMISSION;
                CorePermission parentControlPermission = permissionDao.selectByFunctionCode(parentControlFunctionCode);
                parentControlPermission.setIsChild(PermissionConstant.IS_NOT_CHILD);
                parentControlPermission.setUpdatetime(new Date());
                parentControlPermission.setUpdateUser(vo.getOperator());
                this.updateCorePermission(parentControlPermission);
            }
            return map;
        } else {
            return null;
        }
    }

    /**
     * 更新节点权限信息.
     *
     * @param vo 更新节点vo.
     */
    @Override
    public void updateBizPermission(UpdatePermissionVo vo) {

        //数据验证.
        AnnotationValidationUtil.validate(vo);

        Date now = new Date();
        List<CorePermission> permissions = new ArrayList<CorePermission>();
        String functionCode = vo.getFunctionCode();
        permissions.add(permissionDao.selectByFunctionCode(functionCode));
        //获取对于的另一种类型权限.
        String functionNum = functionCode.substring(0, functionCode.length()-1);
        if (functionCode.endsWith(PermissionConstant.VISIT_PERMISSION)) {
            functionCode = functionNum + PermissionConstant.CONTROL_PERMISSION;
        } else if (functionCode.endsWith(PermissionConstant.CONTROL_PERMISSION)) {
            functionCode = functionNum + PermissionConstant.VISIT_PERMISSION;
        }
        permissions.add(permissionDao.selectByFunctionCode(functionCode));

        for (int i = 0; i < permissions.size(); i++) {
            CorePermission permission = permissions.get(i);
            if(null != permission) {
                permission.setFunctionFlag(vo.getFunctionFlag());
                permission.setUpdatetime(now);
                permission.setUpdateUser(vo.getOperator());
                permission.setUrl(vo.getUrl());
                permission.setType((byte)vo.getType());
                permissionDao.updateByPrimaryKey(permission);
            }else {
                throw new RuntimeException("找不到需要更新的权限数据。");
            }
        }

    }

    /**
     * 新增权限.
     * @param vo    权限vo
     * @param category  权限类别
     * @param functionCode  权限code
     * @param tier  权限等级
     * @return  权限
     */
    private CorePermission addPermissionForCategory(AddPermissionVo vo, byte category, String functionCode, int tier) {
        Date now = new Date();
        CorePermission permission = new CorePermission();
        permission.setCreator(vo.getOperator());
        permission.setUpdateUser(vo.getOperator());
        permission.setInserttime(now);
        permission.setUpdatetime(now);
        permission.setIsactive(BaseConstant.ABLE);
        permission.setFunctionFlag(vo.getFunctionFlag());
        permission.setFunctionCode(functionCode);
        permission.setParentFunctionCode(vo.getParentFunctionCode());
        permission.setUrl(vo.getUrl());
        permission.setType((byte)vo.getType());
        permission.setIsChild(vo.getIsChild() ? PermissionConstant.IS_CHILD : PermissionConstant.IS_NOT_CHILD);
        permission.setCategory(category);
        permission.setTier((byte)tier);
        this.addCorePermission(permission);
        return permission;
    }

}
