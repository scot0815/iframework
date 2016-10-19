package com.scot.permissions.core.service.impl;

import com.scot.permissions.core.constant.BaseConstant;
import com.scot.permissions.core.dao.ICoreGroupDao;
import com.scot.permissions.core.dao.ICoreGroupPermissionDao;
import com.scot.permissions.core.dao.ICoreGroupRoleDao;
import com.scot.permissions.core.dao.ICoreUserGroupDao;
import com.scot.permissions.core.service.ICoreGroupService;
import com.scot.permissions.core.utils.DataValidationUtil;
import com.scot.permissions.core.entity.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 组service实现
 * Created by shengke on 2016/10/13.
 */
@Service("coreGroupService")
public class CoreGroupServiceImpl implements ICoreGroupService {

    /**
     * 权限组Dao.
     */
    @Resource(name = "coreGroupDao")
    private ICoreGroupDao groupDao;

    /**
     * 角色、组关系Dao.
     */
    @Resource(name = "coreGroupRoleDao")
    private ICoreGroupRoleDao groupRoleDao;

    /**
     * 组、权限关系Dao.
     */
    @Resource(name = "coreGroupPermissionDao")
    private ICoreGroupPermissionDao groupPermissionDao;

    /**
     * 组、用户dao.
     */
    @Resource(name = "coreUserGroupDao")
    private ICoreUserGroupDao userGroupDao;

    /**
     * 添加组信息.
     *
     * @param coreGroup 组信息
     * @return 组id
     */
    @Override
    public Long addCoreGroup(CoreGroup coreGroup) {
        groupDao.insert(coreGroup);
        return coreGroup.getId();
    }

    /**
     * 删除组信息.
     *
     * @param id       组id
     * @param operator 操作人
     */
    @Override
    public void delCoreGroup(Long id, Long operator) {
        CoreGroup group = this.queryCoreGroupById(id);
        Date now = new Date();
        group.setUpdatetime(now);
        group.setIsactive(BaseConstant.DISABLE);
        group.setUpdateUser(operator);
        groupDao.updateByPrimaryKey(group);
    }

    /**
     * 更新组信息.
     *
     * @param coreGroup 组信息
     */
    @Override
    public void updateCoreGroup(CoreGroup coreGroup) {
        Date now = new Date();
        coreGroup.setUpdatetime(now);
        groupDao.updateByPrimaryKey(coreGroup);
    }

    /**
     * 根据id获取组信息.
     *
     * @param id 组id
     * @return 组信息
     */
    @Override
    public CoreGroup queryCoreGroupById(Long id) {
        return groupDao.selectByPrimaryKey(id);
    }

    /**
     * 赋予组 角色.
     *
     * @param id       组id
     * @param roleId   角色id
     * @param operator 操作人
     */
    @Override
    public void addRole(Long id, Long roleId, Long operator) {

        //1、查看组是否属于角色
        boolean isBelong = this.isBelongRole(id, roleId);
        if (isBelong) {
            throw new RuntimeException("id为" + id + "的组已经属于roleId为" + roleId + "的角色");
        }

        //2、插入组、角色关系表数据
        CoreGroupRole groupRole = new CoreGroupRole();
        Date now = new Date();
        groupRole.setCreator(operator);
        groupRole.setUpdateUser(operator);
        groupRole.setInserttime(now);
        groupRole.setUpdatetime(now);
        groupRole.setIsactive(BaseConstant.ABLE);
        groupRole.setRoleId(roleId);
        groupRole.setGroupId(id);
        groupRoleDao.insert(groupRole);
    }

    /**
     * 批量赋予组 角色.
     *
     * @param id       组id
     * @param roleIds  角色数组
     * @param operator 操作人
     */
    @Override
    public void addRoles(Long id, Long[] roleIds, Long operator) {

        if (DataValidationUtil.validationArrayEmpty(roleIds)) {
            throw new RuntimeException("roleIds不能为空");
        }

        //1、查询权限组是否已经属于此角色数组
        boolean isBelong = this.isBelongRoles(id, roleIds, BaseConstant.IsBelongType.ONE_MORE);
        if (isBelong) {
            throw new RuntimeException("id为" + id + "的权限用户已经属于roleIds为"
                    + Arrays.toString(roleIds) + "中的角色");
        }
        //2、插入用户、角色关系表
        List<CoreGroupRole> coreGroupRoles = new ArrayList<CoreGroupRole>();
        Date now = new Date();
        for (int i = 0; i < roleIds.length; i++) {
            CoreGroupRole groupRole = new CoreGroupRole();
            groupRole.setCreator(operator);
            groupRole.setUpdateUser(operator);
            groupRole.setInserttime(now);
            groupRole.setUpdatetime(now);
            groupRole.setIsactive(BaseConstant.ABLE);
            groupRole.setRoleId(roleIds[i]);
            groupRole.setGroupId(id);
            coreGroupRoles.add(groupRole);
        }
        groupRoleDao.insertList(coreGroupRoles);
    }

    /**
     * 取消组赋予的角色.
     *
     * @param id       组id
     * @param roleId   角色id
     * @param operator 操作人
     */
    @Override
    public void delRole(Long id, Long roleId, Long operator) {
        //1、查询角色是否包含此组
        CoreGroupRole groupRole = groupRoleDao.selectByRoleIdAndGroupId(id, roleId);
        if (null == groupRole) {
            throw new RuntimeException("id为" + id + "的组不包含roleId为" + roleId + "的角色");
        }

        //2、删除关系数据
        groupRoleDao.deleteByPrimaryKey(groupRole.getId());
    }

    /**
     * 批量取消组赋予的角色.
     *
     * @param id       组id
     * @param roleIds  角色数组
     * @param operator 操作人
     */
    @Override
    public void delRoles(Long id, Long[] roleIds, Long operator) {

        if (DataValidationUtil.validationArrayEmpty(roleIds)) {
            throw new RuntimeException("roleIds不能为空");
        }

        //1、查询角色是否已经全部属于此权限组
        boolean isBelong = this.isBelongRoles(id, roleIds, BaseConstant.IsBelongType.ALL);
        if (!isBelong) {
            throw new RuntimeException("id为" + id + "的权限组不属于roleIds为"
                    + Arrays.toString(roleIds) + "中的全部角色");
        }

        //2、批量删除关联信息
        groupRoleDao.deleteByGroupIdAndRoleIds(id, roleIds);

    }

    /**
     * 组是否属于角色.
     *
     * @param id     组id
     * @param roleId 角色id
     * @return 是否属于
     */
    @Override
    public boolean isBelongRole(Long id, Long roleId) {
        return null != groupRoleDao.selectByRoleIdAndGroupId(roleId, id);
    }

    /**
     * 组是否属于roleIds中角色.
     *
     * @param id           组id
     * @param roleIds      角色数组
     * @param isBelongType
     * @return 是否属于
     */
    @Override
    public boolean isBelongRoles(Long id, Long[] roleIds, BaseConstant.IsBelongType isBelongType) {

        if (DataValidationUtil.validationArrayEmpty(roleIds)) {
            throw new RuntimeException("roleIds不能为空");
        }
        Integer count = groupRoleDao.selectCountIsBelong(id, roleIds);

        //如果ONE_MORE则count大于0返回true
        if (BaseConstant.IsBelongType.ONE_MORE.equals(isBelongType) && count > 0) {
            return true;
        }
        //如果ALL则count与roleIds的length相等返回true
        if (BaseConstant.IsBelongType.ALL.equals(isBelongType) && count == roleIds.length) {
            return true;
        }

        return false;
    }

    /**
     * 权限组添加权限.
     *
     * @param id           角色id
     * @param permissionId 权限id
     * @param operator     操作人
     */
    @Override
    public void addPermission(Long id, Long permissionId, Long operator) {

        //1、查询角色是否已经有此权限
        boolean isContain = this.isContainPermission(id, permissionId);
        if (isContain) {
            throw new RuntimeException("id为" + id + "的组已经包含permissionId为" + permissionId + "的权限");
        }

        CoreGroupPermission groupPermission = new CoreGroupPermission();
        Date now = new Date();
        groupPermission.setCreator(operator);
        groupPermission.setUpdateUser(operator);
        groupPermission.setInserttime(now);
        groupPermission.setUpdatetime(now);
        groupPermission.setIsactive(BaseConstant.ABLE);
        groupPermission.setGroupId(id);
        groupPermission.setPermissionId(permissionId);
        groupPermissionDao.insert(groupPermission);
    }

    /**
     * 权限组批量添加权限.
     *
     * @param id            角色id
     * @param permissionIds 权限id数组
     * @param operator      操作人
     */
    @Override
    public void addPermissions(Long id, Long[] permissionIds, Long operator) {

        if (DataValidationUtil.validationArrayEmpty(permissionIds)) {
            throw new RuntimeException("permissionIds不能为空");
        }

        //1、查询组是否已经拥有此权限组
        boolean isContain = this.isContainPermissions(id, permissionIds, BaseConstant.IsContainType.ONE_MORE);
        if (isContain) {
            throw new RuntimeException("id为" + id + "的组已经包含permissionIds为"
                    + Arrays.toString(permissionIds) + "中的权限");
        }

        //2、插入组、权限关系表
        List<CoreGroupPermission> groupPermissions = new ArrayList<CoreGroupPermission>();
        Date now = new Date();
        for (int i = 0; i < permissionIds.length; i++) {
            CoreGroupPermission groupPermission = new CoreGroupPermission();
            groupPermission.setCreator(operator);
            groupPermission.setUpdateUser(operator);
            groupPermission.setInserttime(now);
            groupPermission.setUpdatetime(now);
            groupPermission.setIsactive(BaseConstant.ABLE);
            groupPermission.setGroupId(id);
            groupPermission.setPermissionId(permissionIds[i]);
            groupPermissions.add(groupPermission);
        }
        groupPermissionDao.insertList(groupPermissions);
    }

    /**
     * 权限组删除权限.
     *
     * @param id           组id
     * @param permissionId 权限id
     * @param operator     操作人
     */
    @Override
    public void delPermission(Long id, Long permissionId, Long operator) {

        //1、查询组是否已经有此权限
        CoreGroupPermission groupPermission = this.selectByGroupIdAndPermission(id, permissionId);
        if(null == groupPermission) {
            throw new RuntimeException("id为" + id + "的组不包含permissionId为" + permissionId + "的权限");
        }

        //2、删除关联信息
        groupPermissionDao.deleteByPrimaryKey(groupPermission.getId());
    }

    /**
     * 权限组批量删除权限.
     *
     * @param id            组id
     * @param permissionIds 权限id数组
     * @param operator      操作人
     */
    @Override
    public void delPermissions(Long id, Long[] permissionIds, Long operator) {
        if (DataValidationUtil.validationArrayEmpty(permissionIds)) {
            throw new RuntimeException("permissionIds不能为空");
        }

        //1、查询组是否已经拥有此权限数组
        boolean isContain = this.isContainPermissions(id, permissionIds, BaseConstant.IsContainType.ALL);
        if (!isContain) {
            throw new RuntimeException("id为" + id + "的组不包含permissionIds为"
                    + Arrays.toString(permissionIds) + "中的所有权限");
        }

        //2、批量删除关联信息
        groupPermissionDao.deleteByGroupIdAndPermissionIds(id, permissionIds);
    }

    /**
     * 权限组是否有此权限.
     *
     * @param id           组id
     * @param permissionId 权限id
     * @return 是否包含
     */
    @Override
    public boolean isContainPermission(Long id, Long permissionId) {
        return null != groupPermissionDao.selectByGroupIdAndPermissionId(id, permissionId);
    }

    /**
     * 权限组是否有数组中的权限.
     *
     * @param id            组id
     * @param permissionIds 权限id数组
     * @param isContainType ALL：全部，ONE_MORE：至少有一个
     * @return 是否包含
     */
    @Override
    public boolean isContainPermissions(Long id, Long[] permissionIds, BaseConstant.IsContainType isContainType) {

        if (DataValidationUtil.validationArrayEmpty(permissionIds)) {
            throw new RuntimeException("permissionIds不能为空");
        }

        Integer count = groupPermissionDao.selectCountIsContain(id, permissionIds);

        //如果ONE_MORE则count大于0返回true
        if (BaseConstant.IsContainType.ONE_MORE.equals(isContainType) && count > 0) {
            return true;
        }
        //如果ALL则count与permissionIds的length相等返回true
        if (BaseConstant.IsContainType.ALL.equals(isContainType) && count == permissionIds.length) {
            return true;
        }

        return false;
    }

    /**
     * 根据组id、权限id获取关系表数据.
     *
     * @param id           组id
     * @param permissionId 权限id
     * @return 组、权限关系
     */
    @Override
    public CoreGroupPermission selectByGroupIdAndPermission(Long id, Long permissionId) {
        return groupPermissionDao.selectByGroupIdAndPermissionId(id, permissionId);
    }

    /**
     * 获取组下的用户列表.
     *
     * @param id 组id
     * @return 用户列表
     */
    @Override
    public List<CoreUser> queryContainUsers(Long id) {
        return null;
    }

    /**
     * 组添加用户.
     *
     * @param id         组id
     * @param coreUserId 用户id
     * @param operator   操作人
     */
    @Override
    public void addCoreUser(Long id, Long coreUserId, Long operator) {
        //1、查询角色是否包含此用户
        boolean isContain = this.isContainCoreUser(id, coreUserId);
        if (isContain) {
            throw new RuntimeException("id为" + id + "的组已经包含coreUserId为" + coreUserId + "的权限用户");
        }

        //2、插入用户、角色关系表
        Date now = new Date();
        CoreUserGroup userGroup = new CoreUserGroup();
        userGroup.setCreator(operator);
        userGroup.setUpdateUser(operator);
        userGroup.setInserttime(now);
        userGroup.setUpdatetime(now);
        userGroup.setIsactive(BaseConstant.ABLE);
        userGroup.setCoreUserId(coreUserId);
        userGroup.setGroupId(id);
        userGroupDao.insert(userGroup);
    }

    /**
     * 组批量添加用户.
     *
     * @param id          组id
     * @param coreUserIds 用户id数组
     * @param operator    操作人
     */
    @Override
    public void addCoreUsers(Long id, Long[] coreUserIds, Long operator) {

        if (DataValidationUtil.validationArrayEmpty(coreUserIds)) {
            throw new RuntimeException("coreUserIds不能为空");
        }

        //1、查询组是否已经包含其中的用户
        boolean isContain = this.isContainCoreUsers(id, coreUserIds, BaseConstant.IsContainType.ONE_MORE);
        if (isContain) {
            throw new RuntimeException("id为" + id + "的组有包含coreUserIds为"
                    + Arrays.toString(coreUserIds) + "中的用户");
        }

        //2、插入组、用户关系表数据
        List<CoreUserGroup> coreUserGroups = new ArrayList<CoreUserGroup>();
        Date now = new Date();
        for (int i = 0; i < coreUserIds.length; i++) {
            CoreUserGroup userGroup = new CoreUserGroup();
            userGroup.setCreator(operator);
            userGroup.setUpdateUser(operator);
            userGroup.setInserttime(now);
            userGroup.setUpdatetime(now);
            userGroup.setIsactive(BaseConstant.ABLE);
            userGroup.setCoreUserId(coreUserIds[i]);
            userGroup.setGroupId(id);
            coreUserGroups.add(userGroup);
        }
        userGroupDao.insertList(coreUserGroups);
    }

    /**
     * 组删除用户.
     *
     * @param id         组id
     * @param coreUserId 用户id
     * @param operator   操作人
     */
    @Override
    public void delCoreUser(Long id, Long coreUserId, Long operator) {
        //1、查询用户、组关联信息
        CoreUserGroup userGroup = userGroupDao.selectByCoreUserIdAndGroupId(coreUserId, id);
        if (userGroup == null) {
            throw new RuntimeException("id为" + id + "的组不包含coreUserId为" + coreUserId + "的权限用户");
        }
        //2、删除关联信息
        userGroupDao.deleteByPrimaryKey(userGroup.getId());
    }

    /**
     * 组批量删除用户.
     *
     * @param id          组id
     * @param coreUserIds 用户id数组
     * @param operator    操作人
     */
    @Override
    public void delCoreUsers(Long id, Long[] coreUserIds, Long operator) {

        if (DataValidationUtil.validationArrayEmpty(coreUserIds)) {
            throw new RuntimeException("coreUserIds不能为空");
        }

        //1、查询组是否已经属于所有的用户
        boolean isBelong = this.isContainCoreUsers(id, coreUserIds, BaseConstant.IsContainType.ALL);
        if (!isBelong) {
            throw new RuntimeException("id为" + id + "的组不包含coreUserIds为"
                    + Arrays.toString(coreUserIds) + "中的全部用户");
        }

        //2、批量删除关联信息
        userGroupDao.deleteByGroupIdAndCoreUserIds(id, coreUserIds);
    }

    /**
     * 组是否包含用户.
     *
     * @param id         组id
     * @param coreUserId 用户id
     * @return 是否包含
     */
    @Override
    public boolean isContainCoreUser(Long id, Long coreUserId) {
        return null != userGroupDao.selectByCoreUserIdAndGroupId(coreUserId, id);
    }

    /**
     * 组是否包含数组中的用户
     *
     * @param id            组id
     * @param coreUserIds   用户id数组
     * @param isContainType 是否包含类型
     * @return 是否包含
     */
    @Override
    public boolean isContainCoreUsers(Long id, Long[] coreUserIds, BaseConstant.IsContainType isContainType) {

        if (DataValidationUtil.validationArrayEmpty(coreUserIds)) {
            throw new RuntimeException("coreUserIds不能为空");
        }

        Integer count = userGroupDao.selectCountIsContain(id, coreUserIds);

        //如果ONE_MORE则count大于0返回true
        if (BaseConstant.IsContainType.ONE_MORE.equals(isContainType) && count > 0) {
            return true;
        }
        //如果ALL则count与coreUserIds的length相等返回true
        if (BaseConstant.IsContainType.ALL.equals(isContainType) && count == coreUserIds.length) {
            return true;
        }

        return false;
    }

    /**
     * 查询组所有角色.
     *
     * @param id 组id
     * @return 角色列表
     */
    @Override
    public List<CoreRole> queryRoles(Long id) {
        return groupRoleDao.selectRolesByGroupId(id);
    }

    /**
     * 获取组自身权限.
     *
     * @param id 组id
     * @return 权限列表
     */
    @Override
    public List<CorePermission> queryPermission(Long id) {
        return groupPermissionDao.selectGroupPermissions(id);
    }

    /**
     * 获取组的所有权限.
     *
     * @param id 组id
     * @return 权限列表
     */
    @Override
    public List<CorePermission> queryAllPermission(Long id) {
        return groupPermissionDao.selectGroupAllPermissions(id);
    }
}
