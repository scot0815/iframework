package com.scot.iframework.permission.service;

import com.scot.iframework.permission.entity.*;
import com.scot.iframework.permission.constant.BaseConstant;

import java.util.List;

/**
 * 组service接口
 * Created by shengke on 2016/10/13.
 */
public interface ICoreGroupService {


    /**
     * 添加组信息.
     * @param coreGroup 组信息
     * @return  组id
     */
    Long addCoreGroup(CoreGroup coreGroup);

    /**
     * 删除组信息.
     * @param id    组id
     * @param operator  操作人
     */
    void delCoreGroup(Long id, Long operator);

    /**
     * 更新组信息.
     * @param coreGroup 组信息
     */
    void updateCoreGroup(CoreGroup coreGroup);

    /**
     * 根据id获取组信息.
     * @param id    组id
     * @return  组信息
     */
    CoreGroup queryCoreGroupById(Long id);

    /**
     * 赋予组 角色.
     * @param id    组id
     * @param roleId 角色id
     * @param operator  操作人
     */
    void addRole(Long id, Long roleId, Long operator);

    /**
     * 批量赋予组 角色.
     * @param id    组id
     * @param roleIds   角色数组
     * @param operator  操作人
     */
    void addRoles(Long id, Long[] roleIds, Long operator);

    /**
     * 取消组赋予的角色.
     * @param id    组id
     * @param roleId    角色id
     * @param operator  操作人
     */
    void delRole(Long id, Long roleId, Long operator);

    /**
     * 批量取消组赋予的角色.
     * @param id    组id
     * @param roleIds   角色数组
     * @param operator  操作人
     */
    void delRoles(Long id, Long[] roleIds, Long operator);

    /**
     * 组是否属于角色.
     * @param id    组id
     * @param roleId    角色id
     * @return 是否属于
     */
    boolean isBelongRole(Long id, Long roleId);

    /**
     * 组是否属于roleIds中角色.
     * @param id    组id
     * @param roleIds   角色数组
     * @param isBelongType
     * @return  是否属于
     */
    boolean isBelongRoles(Long id, Long[] roleIds, BaseConstant.IsBelongType isBelongType);

    /**
     * 权限组添加权限.
     * @param id    角色id
     * @param permissionId  权限id
     * @param operator  操作人
     */
    void addPermission(Long id, Long permissionId, Long operator);

    /**
     * 权限组批量添加权限.
     * @param id    角色id
     * @param permissionIds 权限id数组
     * @param operator  操作人
     */
    void addPermissions(Long id, Long[] permissionIds, Long operator);

    /**
     * 权限组删除权限.
     * @param id    组id
     * @param permissionId  权限id
     * @param operator  操作人
     */
    void delPermission(Long id, Long permissionId, Long operator);

    /**
     * 权限组批量删除权限.
     * @param id    组id
     * @param permissionIds 权限id数组
     * @param operator  操作人
     */
    void delPermissions(Long id, Long[] permissionIds, Long operator);

    /**
     * 权限组是否有此权限.
     * @param id    组id
     * @param permissionId  权限id
     * @return 是否包含
     */
    boolean isContainPermission(Long id, Long permissionId);

    /**
     * 权限组是否有数组中的权限.
     * @param id    组id
     * @param permissionIds 权限id数组
     * @param isContainType  ALL：全部，ONE_MORE：至少有一个
     * @return  是否包含
     */
    boolean isContainPermissions(Long id, Long[] permissionIds, BaseConstant.IsContainType isContainType);

    /**
     * 根据组id、权限id获取关系表数据.
     * @param id    组id
     * @param permissionId  权限id
     * @return  组、权限关系
     */
    CoreGroupPermission selectByGroupIdAndPermission(Long id, Long permissionId);

    /**
     * 获取组下的用户列表.
     * @param id    组id
     * @return  用户列表
     */
    List<CoreUser> queryContainUsers(Long id);

    /**
     * 组添加用户.
     * @param id    组id
     * @param coreUserId    用户id
     * @param operator  操作人
     */
    void addCoreUser(Long id, Long coreUserId, Long operator);

    /**
     * 组批量添加用户.
     * @param id    组id
     * @param coreUserIds   用户id数组
     * @param operator  操作人
     */
    void addCoreUsers(Long id, Long[] coreUserIds, Long operator);

    /**
     * 组删除用户.
     * @param id    组id
     * @param coreUserId    用户id
     * @param operator  操作人
     */
    void delCoreUser(Long id, Long coreUserId, Long operator);

    /**
     * 组批量删除用户.
     * @param id    组id
     * @param coreUserIds   用户id数组
     * @param operator  操作人
     */
    void delCoreUsers(Long id, Long[] coreUserIds, Long operator);

    /**
     * 组是否包含用户.
     * @param id    组id
     * @param coreUserId    用户id
     * @return  是否包含
     */
    boolean isContainCoreUser(Long id, Long coreUserId);

    /**
     * 组是否包含数组中的用户
     * @param id    组id
     * @param coreUserIds   用户id数组
     * @param isContainType 是否包含类型
     * @return  是否包含
     */
    boolean isContainCoreUsers(Long id, Long[] coreUserIds, BaseConstant.IsContainType isContainType);

    /**
     * 查询组所有角色.
     * @param id    组id
     * @return  角色列表
     */
    List<CoreRole> queryRoles(Long id);

    /**
     * 获取组自身权限.
     * @param id    组id
     * @return  权限列表
     */
    List<CorePermission> queryPermission(Long id);

    /**
     * 获取组的所有权限.
     * @param id    组id
     * @return  权限列表
     */
    List<CorePermission> queryAllPermission(Long id);
}
