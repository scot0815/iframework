package com.scot.iframework.permission.dao;

import com.scot.iframework.permission.entity.CorePermission;
import com.scot.iframework.permission.entity.CoreRolePermission;
import com.scot.iframework.permission.entity.CoreRolePermissionExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("coreRolePermissionDao")
public interface ICoreRolePermissionDao {
    int countByExample(CoreRolePermissionExample example);

    int deleteByExample(CoreRolePermissionExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CoreRolePermission record);

    int insertSelective(CoreRolePermission record);

    List<CoreRolePermission> selectByExample(CoreRolePermissionExample example);

    CoreRolePermission selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CoreRolePermission record, @Param("example") CoreRolePermissionExample example);

    int updateByExample(@Param("record") CoreRolePermission record, @Param("example") CoreRolePermissionExample example);

    int updateByPrimaryKeySelective(CoreRolePermission record);

    int updateByPrimaryKey(CoreRolePermission record);

    /**
     * 根据角色id、用户id获取用户权限关系数据.
     * @param roleId    角色id
     * @param permissionId  权限id
     * @return  关系数据
     */
    CoreRolePermission selectByRoleIdAndPermissionId(@Param("roleId") Long roleId,
                                                     @Param("permissionId") Long permissionId);

    /**
     * 获取roleId包含permissionIds中权限的数量.
     * @param roleId    角色id
     * @param permissionIds 权限id数组
     * @return  数量
     */
    int selectCountIsContain(@Param("roleId") Long roleId, @Param("permissionIds") Long[] permissionIds);

    /**
     * 根据roleId删除PermissionIds中的所有权限.
     * @param roleId    角色id
     * @param permissionIds 权限id数组
     * @return  数量
     */
    int deleteByRoleIdAndPermissionIds(@Param("roleId") Long roleId, @Param("permissionIds") Long[] permissionIds);

    /**
     * 批量插入角色权限.
     * @param userPermissions   角色权限列表
     * @return  插入数量
     */
    int insertList(List<CoreRolePermission> userPermissions);

    /**
     * 获取用户所有角色权限.
     * @param coreUserId    权限用户id
     * @return  权限列表
     */
    List<CorePermission> selectUserRolesPermissions(Long coreUserId);

    /**
     * 获取角色的所有权限.
     * @param roleId    角色id
     * @return  权限列表
     */
    List<CorePermission> selectRolePermissions(Long roleId);
}