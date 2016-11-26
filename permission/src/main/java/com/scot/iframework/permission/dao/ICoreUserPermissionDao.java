package com.scot.iframework.permission.dao;

import com.scot.iframework.permission.entity.CorePermission;
import com.scot.iframework.permission.entity.CoreUserPermission;
import com.scot.iframework.permission.entity.CoreUserPermissionExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("coreUserPermissionDao")
public interface ICoreUserPermissionDao {
    int countByExample(CoreUserPermissionExample example);

    int deleteByExample(CoreUserPermissionExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CoreUserPermission record);

    int insertSelective(CoreUserPermission record);

    List<CoreUserPermission> selectByExample(CoreUserPermissionExample example);

    CoreUserPermission selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CoreUserPermission record, @Param("example") CoreUserPermissionExample example);

    int updateByExample(@Param("record") CoreUserPermission record, @Param("example") CoreUserPermissionExample example);

    int updateByPrimaryKeySelective(CoreUserPermission record);

    int updateByPrimaryKey(CoreUserPermission record);

    /**
     * 根据权限id、用户id获取用户权限关系数据.
     * @param coreUserId    权限用户id
     * @param permissionId  权限id
     * @return  关系数据
     */
    CoreUserPermission selectByCoreUserIdAndPermissionId(@Param("coreUserId") Long coreUserId,
                                                         @Param("permissionId") Long permissionId);

    /**
     * 获取coreUserId包含permissionIds中权限的数量.
     * @param coreUserId    权限用户id
     * @param permissionIds 权限id数组
     * @return  数量
     */
    int selectCountIsContain(@Param("coreUserId") Long coreUserId, @Param("permissionIds") Long[] permissionIds);

    /**
     * 根据CoreUserId删除PermissionIds中的所有权限.
     * @param coreUserId    权限用户id
     * @param permissionIds 权限id数组
     * @return  数量
     */
    int deleteByCoreUserIdAndPermissionIds(@Param("coreUserId") Long coreUserId, @Param("permissionIds") Long[] permissionIds);

    /**
     * 批量插入用户权限.
     * @param userPermissions   用户权限列表
     * @return  插入数量
     */
    int insertList(List<CoreUserPermission> userPermissions);

    /**
     * 获取用户自身权限.
     * @param coreUserId    权限用户id
     * @return  权限列表
     */
    List<CorePermission> selectUserPermissions(Long coreUserId);
}