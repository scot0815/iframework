package com.scot.permissions.core.service.impl;

import com.scot.permissions.core.constant.BaseConstant;
import com.scot.permissions.core.dao.ICoreRoleDao;
import com.scot.permissions.core.dao.ICoreRolePermissionDao;
import com.scot.permissions.core.entity.*;
import com.scot.permissions.core.service.ICoreRoleService;
import com.scot.permissions.core.utils.DataValidationUtil;
import com.scot.permissions.core.dao.ICoreGroupRoleDao;
import com.scot.permissions.core.dao.ICoreUserRoleDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 角色service
 * Created by shengke on 2016/10/13.
 */
@Service("coreRoleService")
public class CoreRoleServiceImpl implements ICoreRoleService {

    /**
     * 角色Dao.
     */
    @Resource(name = "coreRoleDao")
    private ICoreRoleDao roleDao;

    /**
     * 角色、组关系Dao.
     */
    @Resource(name = "coreGroupRoleDao")
    private ICoreGroupRoleDao groupRoleDao;

    /**
     * 角色、权限关系Dao
     */
    @Resource(name = "coreRolePermissionDao")
    private ICoreRolePermissionDao rolePermissionDao;

    /**
     * 用户、角色关系Dao
     */
    @Resource(name = "coreUserRoleDao")
    private ICoreUserRoleDao userRoleDao;

    /**
     * 添加角色信息.
     *
     * @param coreRole 角色信息
     * @return 角色id
     */
    @Override
    public Long addCoreRole(CoreRole coreRole) {
        roleDao.insert(coreRole);
        return coreRole.getId();
    }

    /**
     * 删除角色信息.
     *
     * @param id       角色id
     * @param operator 操作人
     */
    @Override
    public void delCoreRole(Long id, Long operator) {
        Date now = new Date();
        CoreRole coreRole = roleDao.selectByPrimaryKey(id);
        coreRole.setIsactive(BaseConstant.DISABLE);
        coreRole.setUpdatetime(now);
        coreRole.setUpdateUser(operator);
        roleDao.updateByPrimaryKeySelective(coreRole);
    }

    /**
     * 更新角色信息.
     *
     * @param coreRole 角色信息
     */
    @Override
    public void updateCoreRole(CoreRole coreRole) {
        Date now = new Date();
        coreRole.setUpdatetime(now);
        roleDao.updateByPrimaryKeySelective(coreRole);
    }

    /**
     * 根据id查询角色信息.
     *
     * @param id 角色id
     * @return 角色信息
     */
    @Override
    public CoreRole queryCoreRoleById(Long id) {
        return roleDao.selectByPrimaryKey(id);
    }

    /**
     * 角色添加组.
     *
     * @param id       角色id
     * @param groupId  组id
     * @param operator 操作人
     */
    @Override
    public void addGroup(Long id, Long groupId, Long operator) {

        //1、查询角色是否包含此组
        boolean isContain = this.isContainGroup(id, groupId);
        if (isContain) {
            throw new RuntimeException("id为" + id + "的角色已经包含groupId为" + groupId + "的组");
        }

        //2、插入角色、组关系表数据
        CoreGroupRole groupRole = new CoreGroupRole();
        Date now = new Date();
        groupRole.setCreator(operator);
        groupRole.setUpdateUser(operator);
        groupRole.setInserttime(now);
        groupRole.setUpdatetime(now);
        groupRole.setIsactive(BaseConstant.ABLE);
        groupRole.setRoleId(id);
        groupRole.setGroupId(groupId);
        groupRoleDao.insert(groupRole);
    }

    /**
     * 角色批量添加组.
     *
     * @param id       角色id
     * @param groupIds 组id数组
     * @param operator 操作人
     */
    @Override
    public void addGroups(Long id, Long[] groupIds, Long operator) {

        if (DataValidationUtil.validationArrayEmpty(groupIds)) {
            throw new RuntimeException("groupIds不能为空");
        }

        //1、查询角色是否已经包含其中的组
        boolean isContain = this.isContainGroups(id, groupIds, BaseConstant.IsContainType.ONE_MORE);
        if (isContain) {
            throw new RuntimeException("id为" + id + "的角色有包含groupIds为"
                    + Arrays.toString(groupIds) + "中的组");
        }

        //2、插入角色、组关系表数据
        List<CoreGroupRole> groupRoles = new ArrayList<CoreGroupRole>();
        Date now = new Date();
        for (int i = 0; i < groupIds.length; i++) {
            CoreGroupRole groupRole = new CoreGroupRole();
            groupRole.setCreator(operator);
            groupRole.setUpdateUser(operator);
            groupRole.setInserttime(now);
            groupRole.setUpdatetime(now);
            groupRole.setIsactive(BaseConstant.ABLE);
            groupRole.setRoleId(id);
            groupRole.setGroupId(groupIds[i]);
            groupRoles.add(groupRole);
        }
        groupRoleDao.insertList(groupRoles);
    }

    /**
     * 角色删除组.
     *
     * @param id       角色id
     * @param groupId  组id
     * @param operator 操作人
     */
    @Override
    public void delGroup(Long id, Long groupId, Long operator) {

        //1、查询角色是否包含此组
        CoreGroupRole groupRole = groupRoleDao.selectByRoleIdAndGroupId(id, groupId);
        if (null == groupRole) {
            throw new RuntimeException("id为" + id + "的角色不包含groupId为" + groupId + "的组");
        }

        //2、删除关联信息
        groupRoleDao.deleteByPrimaryKey(groupRole.getId());
    }

    /**
     * 角色批量删除组.
     *
     * @param id       角色id
     * @param groupIds 组id数组
     * @param operator 操作人
     */
    @Override
    public void delGroups(Long id, Long[] groupIds, Long operator) {

        if (DataValidationUtil.validationArrayEmpty(groupIds)) {
            throw new RuntimeException("groupIds不能为空");
        }

        //1、查询角色是否已经包含所有的组
        boolean isContain = this.isContainGroups(id, groupIds, BaseConstant.IsContainType.ALL);
        if (!isContain) {
            throw new RuntimeException("id为" + id + "的角色有不包含groupIds为"
                    + Arrays.toString(groupIds) + "中的组");
        }

        //2、批量删除关联信息
        groupRoleDao.deleteByRoleIdAndGroupIds(id, groupIds);
    }

    /**
     * 角色是否包含此组.
     *
     * @param id      角色id
     * @param groupId 组id.
     * @return 是否包含组
     */
    @Override
    public boolean isContainGroup(Long id, Long groupId) {
        return null != groupRoleDao.selectByRoleIdAndGroupId(id, groupId);
    }

    /**
     * 角色是否包含数组中的组.
     *
     * @param id            角色id
     * @param groupIds      组id列表
     * @param isContainType ALL：全部包含，ONE_MORE：至少有一个包含
     * @return 是否包含
     */
    @Override
    public boolean isContainGroups(Long id, Long[] groupIds, BaseConstant.IsContainType isContainType) {

        if (DataValidationUtil.validationArrayEmpty(groupIds)) {
            throw new RuntimeException("groupIds不能为空");
        }
        Integer count = groupRoleDao.selectCountIsContain(id, groupIds);

        //如果ONE_MORE则count大于0返回true
        if (BaseConstant.IsContainType.ONE_MORE.equals(isContainType) && count > 0) {
            return true;
        }
        //如果ALL则count与groupIds的length相等返回true
        if (BaseConstant.IsContainType.ALL.equals(isContainType) && count == groupIds.length) {
            return true;
        }

        return false;
    }

    /**
     * 角色添加权限.
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
            throw new RuntimeException("id为" + id + "的角色已经包含permissionId为" + permissionId + "的权限");
        }

        CoreRolePermission rolePermission = new CoreRolePermission();
        Date now = new Date();
        rolePermission.setCreator(operator);
        rolePermission.setUpdateUser(operator);
        rolePermission.setInserttime(now);
        rolePermission.setUpdatetime(now);
        rolePermission.setIsactive(BaseConstant.ABLE);
        rolePermission.setRoleId(id);
        rolePermission.setPermissionId(permissionId);
        rolePermissionDao.insert(rolePermission);
    }

    /**
     * 角色批量添加权限.
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

        //1、查询角色是否已经拥有此权限组
        boolean isContain = this.isContainPermissions(id, permissionIds, BaseConstant.IsContainType.ONE_MORE);
        if (isContain) {
            throw new RuntimeException("id为" + id + "的权限已经包含permissionIds为"
                    + Arrays.toString(permissionIds) + "中的权限");
        }

        //2、插入角色、权限关系表
        List<CoreRolePermission> rolePermissions = new ArrayList<CoreRolePermission>();
        Date now = new Date();
        for (int i = 0; i < permissionIds.length; i++) {
            CoreRolePermission rolePermission = new CoreRolePermission();
            rolePermission.setCreator(operator);
            rolePermission.setUpdateUser(operator);
            rolePermission.setInserttime(now);
            rolePermission.setUpdatetime(now);
            rolePermission.setIsactive(BaseConstant.ABLE);
            rolePermission.setRoleId(id);
            rolePermission.setPermissionId(permissionIds[i]);
            rolePermissions.add(rolePermission);
        }
        rolePermissionDao.insertList(rolePermissions);
    }

    /**
     * 角色删除权限.
     *
     * @param id           角色id
     * @param permissionId 权限id
     * @param operator     操作人
     */
    @Override
    public void delPermission(Long id, Long permissionId, Long operator) {

        //1、查询角色是否已经有此权限
        CoreRolePermission rolePermission = this.selectByRoleIdAndPermission(id, permissionId);
        if (rolePermission == null) {
            throw new RuntimeException("id为" + id + "的角色不包含permissionId为" + permissionId + "的权限");
        }

        //2、删除关联信息
        rolePermissionDao.deleteByPrimaryKey(rolePermission.getId());
    }

    /**
     * 角色批量删除权限.
     *
     * @param id            角色id
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
            throw new RuntimeException("id为" + id + "的角色不包含permissionIds为"
                    + Arrays.toString(permissionIds) + "中的所有权限");
        }

        //2、批量删除关联信息
        rolePermissionDao.deleteByRoleIdAndPermissionIds(id, permissionIds);
    }

    /**
     * 角色是否有此权限.
     *
     * @param id           角色id
     * @param permissionId 权限id
     * @return 是否包含
     */
    @Override
    public boolean isContainPermission(Long id, Long permissionId) {
        return null != rolePermissionDao.selectByRoleIdAndPermissionId(id, permissionId);
    }

    /**
     * 用户是否有数组中的权限.
     *
     * @param id            角色id
     * @param permissionIds 权限id数组
     * @param isContainType ALL：全部，ONE_MORE：至少有一个
     * @return 是否包含
     */
    @Override
    public boolean isContainPermissions(Long id, Long[] permissionIds, BaseConstant.IsContainType isContainType) {

        if (DataValidationUtil.validationArrayEmpty(permissionIds)) {
            throw new RuntimeException("permissionIds不能为空");
        }

        Integer count = rolePermissionDao.selectCountIsContain(id, permissionIds);

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
     * 根据角色id、权限id获取关系表数据.
     *
     * @param id           角色id
     * @param permissionId 权限id
     * @return 角色、权限关系
     */
    @Override
    public CoreRolePermission selectByRoleIdAndPermission(Long id, Long permissionId) {
        return rolePermissionDao.selectByRoleIdAndPermissionId(id, permissionId);
    }

    /**
     * 获取角色权限.
     *
     * @param id 权限id
     * @return 权限列表
     */
    @Override
    public List<CorePermission> queryPermissions(Long id) {
        return rolePermissionDao.selectRolePermissions(id);
    }

    /**
     * 获取角色下的组列表.
     *
     * @param id 角色id
     * @return 组列表
     */
    @Override
    public List<CoreGroup> queryContainGroups(Long id) {
        return groupRoleDao.selectGroupsByRoleId(id);
    }

    /**
     * 获取角色下的用户列表.
     *
     * @param id 角色id
     * @return 用户列表
     */
    @Override
    public List<CoreUser> queryContainUsers(Long id) {
        return userRoleDao.selectRoleUsers(id);
    }

    /**
     * 角色添加用户.
     *
     * @param id         角色id
     * @param coreUserId 用户id
     * @param operator   操作人
     */
    @Override
    public void addCoreUser(Long id, Long coreUserId, Long operator) {
        //1、查询角色是否包含此用户
        boolean isContain = this.isContainCoreUser(id, coreUserId);
        if (isContain) {
            throw new RuntimeException("id为" + id + "的角色已经包含coreUserId为" + coreUserId + "的权限用户");
        }

        //2、插入用户、角色关系表
        Date now = new Date();
        CoreUserRole userRole = new CoreUserRole();
        userRole.setCreator(operator);
        userRole.setUpdateUser(operator);
        userRole.setInserttime(now);
        userRole.setUpdatetime(now);
        userRole.setIsactive(BaseConstant.ABLE);
        userRole.setCoreUserId(coreUserId);
        userRole.setRoleId(id);
        userRoleDao.insert(userRole);
    }

    /**
     * 角色批量添加用户.
     *
     * @param id          角色id
     * @param coreUserIds 用户id数组
     * @param operator    操作人
     */
    @Override
    public void addCoreUsers(Long id, Long[] coreUserIds, Long operator) {

        if (DataValidationUtil.validationArrayEmpty(coreUserIds)) {
            throw new RuntimeException("coreUserIds不能为空");
        }

        //1、查询角色是否已经包含其中的用户
        boolean isContain = this.isContainCoreUsers(id, coreUserIds, BaseConstant.IsContainType.ONE_MORE);
        if (isContain) {
            throw new RuntimeException("id为" + id + "的角色有包含coreUserIds为"
                    + Arrays.toString(coreUserIds) + "中的用户");
        }

        //2、插入角色、用户关系表数据
        List<CoreUserRole> userRoles = new ArrayList<CoreUserRole>();
        Date now = new Date();
        for (int i = 0; i < coreUserIds.length; i++) {
            CoreUserRole userRole = new CoreUserRole();
            userRole.setCreator(operator);
            userRole.setUpdateUser(operator);
            userRole.setInserttime(now);
            userRole.setUpdatetime(now);
            userRole.setIsactive(BaseConstant.ABLE);
            userRole.setCoreUserId(coreUserIds[i]);
            userRole.setRoleId(id);
            userRoles.add(userRole);
        }
        userRoleDao.insertList(userRoles);
    }

    /**
     * 角色删除用户.
     *
     * @param id         角色id
     * @param coreUserId 用户id
     * @param operator   操作人
     */
    @Override
    public void delCoreUser(Long id, Long coreUserId, Long operator) {

        //1、查询用户、角色关联信息
        CoreUserRole userRole = userRoleDao.selectByCoreUserIdAndRoleId(coreUserId, id);
        if (userRole == null) {
            throw new RuntimeException("id为" + id + "的角色不包含coreUserId为" + coreUserId + "的权限用户");
        }
        //2、删除关联信息
        userRoleDao.deleteByPrimaryKey(userRole.getId());
    }

    /**
     * 角色批量删除用户.
     *
     * @param id          角色id
     * @param coreUserIds 用户id数组
     * @param operator    操作人
     */
    @Override
    public void delCoreUsers(Long id, Long[] coreUserIds, Long operator) {

        if (DataValidationUtil.validationArrayEmpty(coreUserIds)) {
            throw new RuntimeException("coreUserIds不能为空");
        }

        //1、查询角色是否已经属于所有的用户
        boolean isBelong = this.isContainCoreUsers(id, coreUserIds, BaseConstant.IsContainType.ALL);
        if (!isBelong) {
            throw new RuntimeException("id为" + id + "的角色不包含coreUserIds为"
                    + Arrays.toString(coreUserIds) + "中的全部用户");
        }

        //2、批量删除关联信息
        userRoleDao.deleteByRoleIdAndCoreUserIds(id, coreUserIds);
    }

    /**
     * 角色是否包含用户.
     *
     * @param id         角色id
     * @param coreUserId 用户id
     * @return 是否包含
     */
    @Override
    public boolean isContainCoreUser(Long id, Long coreUserId) {
        return null != userRoleDao.selectByCoreUserIdAndRoleId(coreUserId, id);
    }

    /**
     * 角色是否包含数组中的用户
     *
     * @param id            角色id
     * @param coreUserIds   用户id数组
     * @param isContainType 是否包含类型
     * @return 是否包含
     */
    @Override
    public boolean isContainCoreUsers(Long id, Long[] coreUserIds, BaseConstant.IsContainType isContainType) {

        if (DataValidationUtil.validationArrayEmpty(coreUserIds)) {
            throw new RuntimeException("coreUserIds不能为空");
        }

        Integer count = userRoleDao.selectCountIsContain(id, coreUserIds);

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
}
