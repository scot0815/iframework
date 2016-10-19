package com.scot.permissions.core.service;

import com.scot.permissions.core.constant.BaseConstant;
import com.scot.permissions.core.entity.*;

import java.util.List;

/**
 * 角色service接口
 * Created by shengke on 2016/10/13.
 */
public interface ICoreRoleService {

    /**
     * 添加角色信息.
     * @param coreRole  角色信息
     * @return  角色id
     */
    Long addCoreRole(CoreRole coreRole);

    /**
     * 删除角色信息.
     * @param id    角色id
     * @param operator  操作人
     */
    void delCoreRole(Long id, Long operator);

    /**
     * 更新角色信息.
     * @param coreRole  角色信息
     */
    void updateCoreRole(CoreRole coreRole);

    /**
     * 根据id查询角色信息.
     * @param id    角色id
     * @return  角色信息
     */
    CoreRole queryCoreRoleById(Long id);

    /**
     * 角色添加组.
     * @param id    角色id
     * @param groupId   组id
     * @param operator  操作人
     */
    void addGroup(Long id, Long groupId, Long operator);

    /**
     * 角色批量添加组.
     * @param id    角色id
     * @param groupIds  组id数组
     * @param operator  操作人
     */
    void addGroups(Long id, Long[] groupIds, Long operator);

    /**
     * 角色删除组.
     * @param id    角色id
     * @param groupId   组id
     * @param operator  操作人
     */
    void delGroup(Long id, Long groupId, Long operator);

    /**
     * 角色批量删除组.
     * @param id    角色id
     * @param groupIds   组id数组
     * @param operator  操作人
     */
    void delGroups(Long id, Long[] groupIds, Long operator);

    /**
     * 角色是否包含此组.
     * @param id    角色id
     * @param groupId   组id.
     * @return  是否包含组
     */
    boolean isContainGroup(Long id, Long groupId);

    /**
     * 角色是否包含数组中的组.
     * @param id    角色id
     * @param groupIds  组id列表
     * @param isContainType ALL：全部包含，ONE_MORE：至少有一个包含
     * @return  是否包含
     */
    boolean isContainGroups(Long id, Long[] groupIds, BaseConstant.IsContainType isContainType);

    /**
     * 角色添加权限.
     * @param id    角色id
     * @param permissionId  权限id
     * @param operator  操作人
     */
    void addPermission(Long id, Long permissionId, Long operator);

    /**
     * 角色批量添加权限.
     * @param id    角色id
     * @param permissionIds 权限id数组
     * @param operator  操作人
     */
    void addPermissions(Long id, Long[] permissionIds, Long operator);

    /**
     * 角色删除权限.
     * @param id    角色id
     * @param permissionId  权限id
     * @param operator  操作人
     */
    void delPermission(Long id, Long permissionId, Long operator);

    /**
     * 角色批量删除权限.
     * @param id    角色id
     * @param permissionIds 权限id数组
     * @param operator  操作人
     */
    void delPermissions(Long id, Long[] permissionIds, Long operator);

    /**
     * 角色是否有此权限.
     * @param id    角色id
     * @param permissionId  权限id
     * @return 是否包含
     */
    boolean isContainPermission(Long id, Long permissionId);

    /**
     * 用户是否有数组中的权限.
     * @param id    角色id
     * @param permissionIds 权限id数组
     * @param isContainType  ALL：全部，ONE_MORE：至少有一个
     * @return  是否包含
     */
    boolean isContainPermissions(Long id, Long[] permissionIds, BaseConstant.IsContainType isContainType);

    /**
     * 根据角色id、权限id获取关系表数据.
     * @param id    角色id
     * @param permissionId  权限id
     * @return  角色、权限关系
     */
    CoreRolePermission selectByRoleIdAndPermission(Long id, Long permissionId);

    /**
     * 获取角色权限.
     * @param id    权限id
     * @return  权限列表
     */
    List<CorePermission> queryPermissions(Long id);

    /**
     * 获取角色下的组列表.
     * @param id    角色id
     * @return  组列表
     */
    List<CoreGroup> queryContainGroups(Long id);

    /**
     * 获取角色下的用户列表.
     * @param id    角色id
     * @return  用户列表
     */
    List<CoreUser> queryContainUsers(Long id);

    /**
     * 角色添加用户.
     * @param id    角色id
     * @param coreUserId    用户id
     * @param operator  操作人
     */
    void addCoreUser(Long id, Long coreUserId, Long operator);

    /**
     * 角色批量添加用户.
     * @param id    角色id
     * @param coreUserIds   用户id数组
     * @param operator  操作人
     */
    void addCoreUsers(Long id, Long[] coreUserIds, Long operator);

    /**
     * 角色删除用户.
     * @param id    角色id
     * @param coreUserId    用户id
     * @param operator  操作人
     */
    void delCoreUser(Long id, Long coreUserId, Long operator);

    /**
     * 角色批量删除用户.
     * @param id    角色id
     * @param coreUserIds   用户id数组
     * @param operator  操作人
     */
    void delCoreUsers(Long id, Long[] coreUserIds, Long operator);

    /**
     * 角色是否包含用户.
     * @param id    角色id
     * @param coreUserId    用户id
     * @return  是否包含
     */
    boolean isContainCoreUser(Long id, Long coreUserId);

    /**
     * 角色是否包含数组中的用户
     * @param id    角色id
     * @param coreUserIds   用户id数组
     * @param isContainType 是否包含类型
     * @return  是否包含
     */
    boolean isContainCoreUsers(Long id, Long[] coreUserIds, BaseConstant.IsContainType isContainType);
}
