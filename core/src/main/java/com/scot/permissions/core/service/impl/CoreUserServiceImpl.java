package com.scot.permissions.core.service.impl;

import com.scot.permissions.core.constant.BaseConstant;
import com.scot.permissions.core.service.ICoreUserService;
import com.scot.permissions.core.utils.DataValidationUtil;
import com.scot.permissions.core.dao.*;
import com.scot.permissions.core.entity.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 权限用户service
 * Created by shengke on 2016/10/11.
 */
@Service("coreUserService")
public class CoreUserServiceImpl implements ICoreUserService {

    /**
     * 权限用户Dao.
     */
    @Resource(name = "coreUserDao")
    private ICoreUserDao userDao;

    /**
     * 用户、组关系Dao.
     */
    @Resource(name = "coreUserGroupDao")
    private ICoreUserGroupDao userGroupDao;

    /**
     * 用户、角色关系Dao.
     */
    @Resource(name = "coreUserRoleDao")
    private ICoreUserRoleDao userRoleDao;

    /**
     * 用户、权限关系Dao.
     */
    @Resource(name = "coreUserPermissionDao")
    private ICoreUserPermissionDao userPermissionDao;

    /**
     * 组权限dao.
     */
    @Resource(name = "coreGroupPermissionDao")
    private ICoreGroupPermissionDao groupPermissionDao;

    /**
     * 角色权限dao.
     */
    @Resource(name = "coreRolePermissionDao")
    private ICoreRolePermissionDao rolePermissionDao;

    /**
     * 添加权限用户.
     *
     * @param coreUser 权限用户信息
     * @return 主键ID
     */
    @Override
    public Long addCoreUser(CoreUser coreUser) {
        userDao.insert(coreUser);
        return coreUser.getId();
    }

    /**
     * 删除权限用户.
     *
     * @param id       权限用户id
     * @param operator 操作人
     */
    @Override
    public void delCoreUser(Long id, Long operator) {
        Date now = new Date();
        CoreUser coreUser =  this.queryCoreUserById(id);
        coreUser.setIsactive(BaseConstant.DISABLE);
        coreUser.setUpdatetime(now);
        coreUser.setUpdateUser(operator);
        userDao.updateByPrimaryKeySelective(coreUser);
    }

    /**
     * 更新权限用户.
     *
     * @param coreUser 权限用户信息
     */
    @Override
    public void updateCoreUser(CoreUser coreUser) {
        Date now = new Date();
        coreUser.setUpdatetime(now);
        userDao.updateByPrimaryKeySelective(coreUser);
    }

    /**
     * 根据id查询权限用户.
     *
     * @param id 权限id
     * @return 权限用户信息
     */
    @Override
    public CoreUser queryCoreUserById(Long id) {
        return userDao.selectByPrimaryKey(id);
    }

    /**
     * 根据系统userId获取权限用户.
     *
     * @param bizUserId 系统userId
     * @return 权限用户信息
     */
    @Override
    public CoreUser queryCoreUserByBizUserId(Long bizUserId) {
        return userDao.selectByBizUserId(bizUserId);
    }

    /**
     * 用户添加组.
     *
     * @param id       权限用户id
     * @param groupId  组id
     * @param operator 操作人
     */
    @Override
    public void addGroup(Long id, Long groupId, Long operator) {

        //1、查询用户是否已经属于此组
        boolean isBelong = this.isBelongGroup(id, groupId);
        if (isBelong) {
            throw new RuntimeException("id为" + id + "的权限用户已经属于groupId为" + groupId + "的组");
        }

        //2、插入用户、组关系表数据
        CoreUserGroup userGroup = new CoreUserGroup();
        Date now = new Date();
        userGroup.setCreator(operator);
        userGroup.setUpdateUser(operator);
        userGroup.setInserttime(now);
        userGroup.setUpdatetime(now);
        userGroup.setIsactive(BaseConstant.ABLE);
        userGroup.setCoreUserId(id);
        userGroup.setGroupId(groupId);
        userGroupDao.insert(userGroup);
    }

    /**
     * 用户批量添加组.
     *
     * @param id       权限用户id
     * @param groupIds 组id数组
     * @param operator 操作人
     */
    @Override
    @Transactional
    public void addGroups(Long id, Long[] groupIds, Long operator) {

        if (DataValidationUtil.validationArrayEmpty(groupIds)) {
            throw new RuntimeException("groupIds不能为空");
        }

        //1、查询用户是否已经属于其中的组
        boolean isBelong = this.isBelongGroups(id, groupIds, BaseConstant.IsBelongType.ONE_MORE);
        if (isBelong) {
            throw new RuntimeException("id为" + id + "的权限用户有属于groupIds为"
                    + Arrays.toString(groupIds) + "中的组");
        }

        //2、插入用户、组关系表数据
        List<CoreUserGroup> userGroups = new ArrayList<CoreUserGroup>();
        for (int i = 0; i < groupIds.length; i++) {
            CoreUserGroup userGroup = new CoreUserGroup();
            Date now = new Date();
            userGroup.setCreator(operator);
            userGroup.setUpdateUser(operator);
            userGroup.setInserttime(now);
            userGroup.setUpdatetime(now);
            userGroup.setIsactive(BaseConstant.ABLE);
            userGroup.setCoreUserId(id);
            userGroup.setGroupId(groupIds[i]);
            userGroups.add(userGroup);
        }
        userGroupDao.insertList(userGroups);
    }

    /**
     * 用户删除组.
     *
     * @param id       权限用户id
     * @param groupId  组id
     * @param operator 操作人
     */
    @Override
    public void delGroup(Long id, Long groupId, Long operator) {
        //1、获取用户组关联信息
        CoreUserGroup userGroup = userGroupDao.selectByCoreUserIdAndGroupId(id, groupId);
        if (userGroup == null) {
            throw new RuntimeException("id为" + id + "的权限用户不属于groupId为" + groupId + "的组");
        }

        //2、删除关联信息
        userGroupDao.deleteByPrimaryKey(userGroup.getId());
    }

    /**
     * 用户批量删除组.
     *
     * @param id       权限用户id
     * @param groupIds 组id数组
     * @param operator 操作人
     */
    @Override
    @Transactional
    public void delGroups(Long id, Long[] groupIds, Long operator) {

        if (DataValidationUtil.validationArrayEmpty(groupIds)) {
            throw new RuntimeException("groupIds不能为空");
        }

        //1、查询用户是否已经属于所有的组
        boolean isBelong = this.isBelongGroups(id, groupIds, BaseConstant.IsBelongType.ALL);
        if (!isBelong) {
            throw new RuntimeException("id为" + id + "的权限用户有不属于groupIds为"
                    + Arrays.toString(groupIds) + "中所有的组");
        }

        //2、批量删除关联信息
        userGroupDao.deleteByCoreUserIdAndGroupIds(id, groupIds);
    }

    /**
     * 用户是否属于此组.
     *
     * @param id      权限用户id
     * @param groupId 组id.
     * @return 是否属于组
     */
    @Override
    public boolean isBelongGroup(Long id, Long groupId) {
        return null != userGroupDao.selectByCoreUserIdAndGroupId(id, groupId);
    }

    /**
     * 用户是否属于数组中的组
     *
     * @param id       权限用户id
     * @param groupIds 组id列表
     * @param isBelongType ALL：全部，ONE_MORE：至少有一个
     * @return 是否属于
     */
    @Override
    @Transactional
    public boolean isBelongGroups(Long id, Long[] groupIds, BaseConstant.IsBelongType isBelongType) {

        if (DataValidationUtil.validationArrayEmpty(groupIds)) {
            throw new RuntimeException("groupIds不能为空");
        }
        Integer count = userGroupDao.selectCountIsBelong(id, groupIds);

        //如果ONE_MORE则count大于0返回true
        if (BaseConstant.IsBelongType.ONE_MORE.equals(isBelongType) && count > 0) {
            return true;
        }
        //如果ALL则count与groupIds的length相等返回true
        if (BaseConstant.IsBelongType.ALL.equals(isBelongType) && count == groupIds.length) {
            return true;
        }

        return false;
    }

    /**
     * 用户添加角色.
     *
     * @param id       权限用户id
     * @param roleId   角色id
     * @param operator 操作人
     */
    @Override
    public void addRole(Long id, Long roleId, Long operator) {

        //1、查询用户是否已经属于此角色
        boolean isBelong = this.isBelongRole(id, roleId);
        if (isBelong) {
            throw new RuntimeException("id为" + id + "的权限用户已经属于roleId为" + roleId + "的角色");
        }

        //2、插入用户、角色关系表
        Date now = new Date();
        CoreUserRole userRole = new CoreUserRole();
        userRole.setCreator(operator);
        userRole.setUpdateUser(operator);
        userRole.setInserttime(now);
        userRole.setUpdatetime(now);
        userRole.setIsactive(BaseConstant.ABLE);
        userRole.setCoreUserId(id);
        userRole.setRoleId(roleId);
        userRoleDao.insert(userRole);
    }

    /**
     * 用户批量添加角色.
     *
     * @param id       权限用户id
     * @param roleIds  角色id数组
     * @param operator 操作人
     */
    @Override
    public void addRoles(Long id, Long[] roleIds, Long operator) {

        if (DataValidationUtil.validationArrayEmpty(roleIds)) {
            throw new RuntimeException("roleIds不能为空");
        }

        //1、查询用户是否已经属于此角色组
        boolean isBelong = this.isBelongRoles(id, roleIds, BaseConstant.IsBelongType.ONE_MORE);
        if (isBelong) {
            throw new RuntimeException("id为" + id + "的权限用户已经属于roleIds为"
                    + Arrays.toString(roleIds) + "中的角色");
        }
        //2、插入用户、角色关系表
        List<CoreUserRole> userRoles = new ArrayList<CoreUserRole>();
        Date now = new Date();
        for (int i = 0; i < roleIds.length; i++) {
            CoreUserRole userRole = new CoreUserRole();
            userRole.setCreator(operator);
            userRole.setUpdateUser(operator);
            userRole.setInserttime(now);
            userRole.setUpdatetime(now);
            userRole.setIsactive(BaseConstant.ABLE);
            userRole.setCoreUserId(id);
            userRole.setRoleId(roleIds[i]);
            userRoles.add(userRole);
        }
        userRoleDao.insertList(userRoles);
    }

    /**
     * 用户删除角色.
     *
     * @param id       权限用户id
     * @param roleId   角色id
     * @param operator 操作人
     */
    @Override
    public void delRole(Long id, Long roleId, Long operator) {

        //1、查询用户、角色关联信息
        CoreUserRole userRole = userRoleDao.selectByCoreUserIdAndRoleId(id, roleId);
        if (userRole == null) {
            throw new RuntimeException("id为" + id + "的权限用户不属于roleId为" + roleId + "的角色");
        }
        //2、删除关联信息
        userRoleDao.deleteByPrimaryKey(userRole.getId());
    }

    /**
     * 用户批量删除角色.
     *
     * @param id       权限用户id
     * @param roleIds  角色id数组
     * @param operator 操作人
     */
    @Override
    public void delRoles(Long id, Long[] roleIds, Long operator) {

        if (DataValidationUtil.validationArrayEmpty(roleIds)) {
            throw new RuntimeException("roleIds不能为空");
        }

        //1、查询用户是否已经属于所有的角色
        boolean isBelong = this.isBelongRoles(id, roleIds, BaseConstant.IsBelongType.ALL);
        if (!isBelong) {
            throw new RuntimeException("id为" + id + "的权限用户不属于roleIds为"
                    + Arrays.toString(roleIds) + "中的全部角色");
        }

        //2、批量删除关联信息
        userRoleDao.deleteByCoreUserIdAndRoleIds(id, roleIds);
    }

    /**
     * 用户是否属于角色.
     *
     * @param id     权限用户id
     * @param roleId 角色id
     * @return 是否包含角色
     */
    @Override
    public boolean isBelongRole(Long id, Long roleId) {
        return null != userRoleDao.selectByCoreUserIdAndRoleId(id, roleId);
    }

    /**
     * 用户是否属于数组里的每个角色.
     *
     * @param id    权限用户id
     * @param roleIds 角色id数组
     * @param isBelongType ALL：全部，ONE_MORE：至少有一个
     * @return 是否属于每个角色
     */
    @Override
    public boolean isBelongRoles(Long id, Long[] roleIds, BaseConstant.IsBelongType isBelongType) {

        if (DataValidationUtil.validationArrayEmpty(roleIds)) {
            throw new RuntimeException("roleIds不能为空");
        }
        Integer count = userRoleDao.selectCountIsBelong(id, roleIds);

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
     * 用户添加权限.
     *
     * @param id           权限用户id
     * @param permissionId 权限id
     * @param operator     操作人
     */
    @Override
    public void addPermission(Long id, Long permissionId, Long operator) {

        //1、查询用户是否已经有此权限
        boolean isContain = this.isContainPermission(id, permissionId);
        if (isContain) {
            throw new RuntimeException("id为" + id + "的权限用户已经包含permissionId为" + permissionId + "的权限");
        }

        //2、插入用户、权限关系表
        CoreUserPermission userPermission = new CoreUserPermission();
        Date now = new Date();
        userPermission.setCreator(operator);
        userPermission.setUpdateUser(operator);
        userPermission.setInserttime(now);
        userPermission.setUpdatetime(now);
        userPermission.setIsactive(BaseConstant.ABLE);
        userPermission.setCoreUserId(id);
        userPermission.setPermissionId(permissionId);
        userPermissionDao.insert(userPermission);
    }

    /**
     * 用户批量添加权限.
     *
     * @param id            权限用户id
     * @param permissionIds 权限id数组
     * @param operator      操作人
     */
    @Override
    public void addPermissions(Long id, Long[] permissionIds, Long operator) {

        if (DataValidationUtil.validationArrayEmpty(permissionIds)) {
            throw new RuntimeException("permissionIds不能为空");
        }

        //1、查询用户是否已经拥有此权限组
        boolean isContain = this.isContainPermissions(id, permissionIds, BaseConstant.IsContainType.ONE_MORE);
        if (isContain) {
            throw new RuntimeException("id为" + id + "的权限用户已经包含permissionIds为"
                    + Arrays.toString(permissionIds) + "中的权限");
        }

        //2、插入用户、权限关系表
        List<CoreUserPermission> userPermissions = new ArrayList<CoreUserPermission>();
        Date now = new Date();
        for (int i = 0; i < permissionIds.length; i++) {
            CoreUserPermission userPermission = new CoreUserPermission();
            userPermission.setCreator(operator);
            userPermission.setUpdateUser(operator);
            userPermission.setInserttime(now);
            userPermission.setUpdatetime(now);
            userPermission.setIsactive(BaseConstant.ABLE);
            userPermission.setCoreUserId(id);
            userPermission.setPermissionId(permissionIds[i]);
            userPermissions.add(userPermission);
        }
        userPermissionDao.insertList(userPermissions);
    }

    /**
     * 用户删除权限.
     *
     * @param id           权限用户id
     * @param permissionId 权限id
     * @param operator     操作人
     */
    @Override
    public void delPermission(Long id, Long permissionId, Long operator) {

        //1、查询用户是否已经有此权限
        CoreUserPermission userPermission = this.selectByCoreUserIdAndPermission(id, permissionId);
        if (userPermission == null) {
            throw new RuntimeException("id为" + id + "的权限用户不包含permissionId为" + permissionId + "的权限");
        }

        //2、删除关联信息
        userPermissionDao.deleteByPrimaryKey(userPermission.getId());
    }

    /**
     * 用户批量删除权限.
     *
     * @param id            权限用户id
     * @param permissionIds 权限id数组
     * @param operator      操作人
     */
    @Override
    public void delPermissions(Long id, Long[] permissionIds, Long operator) {

        if (DataValidationUtil.validationArrayEmpty(permissionIds)) {
            throw new RuntimeException("permissionIds不能为空");
        }

        //1、查询用户是否已经拥有此权限组
        boolean isContain = this.isContainPermissions(id, permissionIds, BaseConstant.IsContainType.ALL);
        if (!isContain) {
            throw new RuntimeException("id为" + id + "的权限用户不包含permissionIds为"
                    + Arrays.toString(permissionIds) + "中的所有权限");
        }

        //2、批量删除关联信息
        userPermissionDao.deleteByCoreUserIdAndPermissionIds(id, permissionIds);
    }

    /**
     * 用户是否有此权限.
     *
     * @param id           权限用户id
     * @param permissionId 权限id
     * @return 是否包含
     */
    @Override
    public boolean isContainPermission(Long id, Long permissionId) {
        return null != userPermissionDao.selectByCoreUserIdAndPermissionId(id, permissionId);
    }

    /**
     * 用户是否有数组中的权限.
     *
     * @param id            权限用户id
     * @param permissionIds 权限id数组
     * @param isContainType ALL：全部，ONE_MORE：至少有一个
     * @return 是否包含
     */
    @Override
    public boolean isContainPermissions(Long id, Long[] permissionIds, BaseConstant.IsContainType isContainType) {

        if (DataValidationUtil.validationArrayEmpty(permissionIds)) {
            throw new RuntimeException("permissionIds不能为空");
        }
        Integer count = userPermissionDao.selectCountIsContain(id, permissionIds);

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
     * 根据用户id、权限id获取关系表数据.
     *
     * @param id           用户id
     * @param permissionId 权限id
     * @return 用户、权限关系
     */
    @Override
    public CoreUserPermission selectByCoreUserIdAndPermission(Long id, Long permissionId) {
        return userPermissionDao.selectByCoreUserIdAndPermissionId(id, permissionId);
    }

    /**
     * 查询用户自身权限.
     *
     * @param id    权限用户id
     * @return  权限列表
     */
    @Override
    public List<CorePermission> queryPermissions(Long id) {
        return userPermissionDao.selectUserPermissions(id);
    }

    /**
     * 查询用户所有角色.
     *
     * @param id 权限用户id
     * @return 角色列表
     */
    @Override
    public List<CoreRole> queryRoles(Long id) {
        return userRoleDao.selectUserRoles(id);
    }

    /**
     * 查询用户所有组.
     *
     * @param id 权限用户id
     * @return 组列表.
     */
    @Override
    public List<CoreGroup> queryGroups(Long id) {
        return userGroupDao.selectUserGroups(id);
    }

    /**
     * 获取用户所有角色权限.
     *
     * @param id 权限用户id
     * @return 权限列表
     */
    @Override
    public List<CorePermission> queryRolesPermissions(Long id) {
        return rolePermissionDao.selectUserRolesPermissions(id);
    }

    /**
     * 获取用户所有组权限.
     *
     * @param id 权限用户id
     * @return 权限列表
     */
    @Override
    public List<CorePermission> queryGroupsPermissions(Long id) {
        return groupPermissionDao.selectUserGroupsPermissions(id);
    }

    /**
     * 获取用户的所有权限.
     *
     * @param id 权限用户id
     * @return 权限列表
     */
    @Override
    public List<CorePermission> queryAllPermissionsById(Long id) {

        //用户自身权限
        List<CorePermission> userPermissions = this.queryPermissions(id);
        //用户所有角色权限
        List<CorePermission> rolesPermissions = this.queryRolesPermissions(id);
        //用户所有组权限（包含组所属的角色的权限）
        List<CorePermission> groupsPermissions = this.queryGroupsPermissions(id);

        List<CorePermission> allPermissions = new ArrayList<CorePermission>();
        allPermissions.addAll(userPermissions);
        allPermissions.addAll(rolesPermissions);
        allPermissions.addAll(groupsPermissions);
        return allPermissions;
    }

}
