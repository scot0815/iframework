package com.scot.iframework.permission.service;

import com.scot.iframework.permission.entity.*;
import com.scot.iframework.permission.constant.BaseConstant;

import java.util.List;

/**
 * 用户service接口
 * Created by shengke on 2016/10/11.
 */
public interface ICoreUserService {

    /**
     * 添加权限用户.
     * @param coreUser  权限用户信息
     * @return  主键ID
     */
    Long addCoreUser(CoreUser coreUser);

    /**
     * 删除权限用户.
     * @param id    权限用户id
     * @param operator  操作人
     */
    void delCoreUser(Long id, Long operator);

    /**
     * 更新权限用户.
     * @param coreUser  权限用户信息
     */
    void updateCoreUser(CoreUser coreUser);

    /**
     * 根据id查询权限用户.
     * @param id    权限用户id
     * @return  权限用户信息
     */
    CoreUser queryCoreUserById(Long id);

    /**
     * 根据系统userId获取权限用户.
     * @param bizUserId 系统userId
     * @return 权限用户信息
     */
    CoreUser queryCoreUserByBizUserId(Long bizUserId);

    /**
     * 用户添加组.
     * @param id    权限用户id
     * @param groupId   组id
     * @param operator  操作人
     */
    void addGroup(Long id, Long groupId, Long operator);

    /**
     * 用户批量添加组.
     * @param id    权限用户id
     * @param groupIds  组id数组
     * @param operator  操作人
     */
    void addGroups(Long id, Long[] groupIds, Long operator);

    /**
     * 用户删除组.
     * @param id    权限用户id
     * @param groupId   组id
     * @param operator  操作人
     */
    void delGroup(Long id, Long groupId, Long operator);

    /**
     * 用户批量删除组.
     * @param id    权限用户id
     * @param groupIds   组id数组
     * @param operator  操作人
     */
    void delGroups(Long id, Long[] groupIds, Long operator);

    /**
     * 用户是否属于此组.
     * @param id    权限用户id
     * @param groupId   组id.
     * @return  是否属于组
     */
    boolean isBelongGroup(Long id, Long groupId);

    /**
     * 用户是否属于数组中的组.
     * @param id    权限用户id
     * @param groupIds  组id列表
     * @param isBelongType ALL：全部，ONE_MORE：至少有一个
     * @return  是否属于
     */
    boolean isBelongGroups(Long id, Long[] groupIds, BaseConstant.IsBelongType isBelongType);

    /**
     * 用户添加角色.
     * @param id    权限用户id
     * @param roleId    角色id
     * @param operator  操作人
     */
    void addRole(Long id, Long roleId, Long operator);

    /**
     * 用户批量添加角色.
     * @param id    权限用户id
     * @param roleIds   角色id数组
     * @param operator  操作人
     */
    void addRoles(Long id, Long[] roleIds, Long operator);

    /**
     * 用户删除角色.
     * @param id    权限用户id
     * @param roleId    角色id
     * @param operator  操作人
     */
    void delRole(Long id, Long roleId, Long operator);

    /**
     * 用户批量删除角色.
     * @param id    权限用户id
     * @param roleIds   角色id数组
     * @param operator  操作人
     */
    void delRoles(Long id, Long[] roleIds, Long operator);

    /**
     * 用户是否属于角色.
     * @param id    权限用户id
     * @param roleId    角色id
     * @return  是否包含角色
     */
    boolean isBelongRole(Long id, Long roleId);

    /**
     * 用户是否属于数组里的角色.
     * @param id    权限用户id
     * @param roleIds 角色id数组
     * @param isBelongType ALL：全部，ONE_MORE：至少有一个
     * @return  是否属于
     */
    boolean isBelongRoles(Long id, Long[] roleIds, BaseConstant.IsBelongType isBelongType);

    /**
     * 用户添加权限.
     * @param id    权限用户id
     * @param permissionId  权限id
     * @param operator  操作人
     */
    void addPermission(Long id, Long permissionId, Long operator);

    /**
     * 用户批量添加权限.
     * @param id    权限用户id
     * @param permissionIds 权限id数组
     * @param operator  操作人
     */
    void addPermissions(Long id, Long[] permissionIds, Long operator);

    /**
     * 用户删除权限.
     * @param id    权限用户id
     * @param permissionId  权限id
     * @param operator  操作人
     */
    void delPermission(Long id, Long permissionId, Long operator);

    /**
     * 用户批量删除权限.
     * @param id    权限用户id
     * @param permissionIds 权限id数组
     * @param operator  操作人
     */
    void delPermissions(Long id, Long[] permissionIds, Long operator);

    /**
     * 用户是否有此权限.
     * @param id    权限用户id
     * @param permissionId  权限id
     * @return 是否包含
     */
    boolean isContainPermission(Long id, Long permissionId);

    /**
     * 用户是否有数组中的权限.
     * @param id    权限用户id
     * @param permissionIds 权限id数组
     * @param isContainType  ALL：全部，ONE_MORE：至少有一个
     * @return  是否包含
     */
    boolean isContainPermissions(Long id, Long[] permissionIds, BaseConstant.IsContainType isContainType);

    /**
     * 根据用户id、权限id获取关系表数据.
     * @param id    用户id
     * @param permissionId  权限id
     * @return  用户、权限关系
     */
    CoreUserPermission selectByCoreUserIdAndPermission(Long id, Long permissionId);

    /**
     * 查询用户自身权限.
     * @param id    权限用户id
     * @return  权限列表
     */
    List<CorePermission> queryPermissions(Long id);

    /**
     * 查询用户所有角色.
     * @param id    权限用户id
     * @return  角色列表
     */
    List<CoreRole> queryRoles(Long id);

    /**
     * 查询用户所有组.
     * @param id    权限用户id
     * @return  组列表.
     */
    List<CoreGroup> queryGroups(Long id);

    /**
     * 获取用户所有角色权限.
     * @param id    权限用户id
     * @return  权限列表
     */
    List<CorePermission> queryRolesPermissions(Long id);

    /**
     * 获取用户所有组权限.
     * @param id    权限用户id
     * @return  权限列表
     */
    List<CorePermission> queryGroupsPermissions(Long id);

    /**
     * 获取用户的所有权限.
     * @param id    权限用户id
     * @return  权限列表
     */
    List<CorePermission> queryAllPermissionsById(Long id);
}
