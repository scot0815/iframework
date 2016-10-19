package com.scot.permissions.core.dao;

import com.scot.permissions.core.entity.CoreGroupPermissionExample;
import com.scot.permissions.core.entity.CorePermission;
import com.scot.permissions.core.entity.CoreGroupPermission;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("coreGroupPermissionDao")
public interface ICoreGroupPermissionDao {
    int countByExample(CoreGroupPermissionExample example);

    int deleteByExample(CoreGroupPermissionExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CoreGroupPermission record);

    int insertSelective(CoreGroupPermission record);

    List<CoreGroupPermission> selectByExample(CoreGroupPermissionExample example);

    CoreGroupPermission selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CoreGroupPermission record, @Param("example") CoreGroupPermissionExample example);

    int updateByExample(@Param("record") CoreGroupPermission record, @Param("example") CoreGroupPermissionExample example);

    int updateByPrimaryKeySelective(CoreGroupPermission record);

    int updateByPrimaryKey(CoreGroupPermission record);

    /**
     * 根据组id、用户id获取用户权限关系数据.
     * @param groupId    组id
     * @param permissionId  权限id
     * @return  关系数据
     */
    CoreGroupPermission selectByGroupIdAndPermissionId(@Param("groupId") Long groupId,
                                                       @Param("permissionId") Long permissionId);

    /**
     * 获取groupId包含permissionIds中权限的数量.
     * @param groupId    组id
     * @param permissionIds 权限id数组
     * @return  数量
     */
    int selectCountIsContain(@Param("groupId") Long groupId, @Param("permissionIds") Long[] permissionIds);

    /**
     * 根据groupId删除PermissionIds中的所有权限.
     * @param groupId    组id
     * @param permissionIds 权限id数组
     * @return  数量
     */
    int deleteByGroupIdAndPermissionIds(@Param("groupId") Long groupId, @Param("permissionIds") Long[] permissionIds);

    /**
     * 批量插入组权限.
     * @param userPermissions   组权限列表
     * @return  插入数量
     */
    int insertList(List<CoreGroupPermission> userPermissions);

    /**
     * 获取用户所有组的权限.
     * @param coreUserId    权限用户id
     * @return  权限列表
     */
    List<CorePermission> selectUserGroupsPermissions(Long coreUserId);

    /**
     * 获取组自身的权限.
     * @param groupId   组id
     * @return  权限列表
     */
    List<CorePermission> selectGroupPermissions(Long groupId);

    /**
     * 获取组全部的权限
     * @param groupId
     * @return
     */
    List<CorePermission> selectGroupAllPermissions(Long groupId);
}