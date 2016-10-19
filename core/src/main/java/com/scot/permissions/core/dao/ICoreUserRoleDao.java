package com.scot.permissions.core.dao;

import com.scot.permissions.core.entity.CoreRole;
import com.scot.permissions.core.entity.CoreUser;
import com.scot.permissions.core.entity.CoreUserRole;
import com.scot.permissions.core.entity.CoreUserRoleExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("coreUserRoleDao")
public interface ICoreUserRoleDao {
    int countByExample(CoreUserRoleExample example);

    int deleteByExample(CoreUserRoleExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CoreUserRole record);

    int insertSelective(CoreUserRole record);

    List<CoreUserRole> selectByExample(CoreUserRoleExample example);

    CoreUserRole selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CoreUserRole record, @Param("example") CoreUserRoleExample example);

    int updateByExample(@Param("record") CoreUserRole record, @Param("example") CoreUserRoleExample example);

    int updateByPrimaryKeySelective(CoreUserRole record);

    int updateByPrimaryKey(CoreUserRole record);

    /**
     * 根据用户、角色获取用户、角色关系表.
     * @param coreUserId    用户id
     * @param roleId    角色id
     * @return  用户、角色关系信息
     */
    CoreUserRole selectByCoreUserIdAndRoleId(@Param("coreUserId") Long coreUserId, @Param("roleId") Long roleId);

    /**
     * 获取权限用户id属于角色roleIds的数量.
     * @param coreUserId    用户Id
     * @param roleIds 角色Ids
     * @return  数量
     */
    int selectCountIsBelong(@Param("coreUserId") Long coreUserId, @Param("roleIds") Long[] roleIds);

    /**
     * 根据权限用户id删除roleIds中的所有角色.
     * @param coreUserId    用户Id
     * @param roleIds 角色Ids
     * @return  数量
     */
    int deleteByCoreUserIdAndRoleIds(@Param("coreUserId") Long coreUserId, @Param("roleIds") Long[] roleIds);

    /**
     * 批量插入用户、角色关系.
     * @param userRoles 用户、角色关系类别
     * @return  插入数量
     */
    int insertList(List<CoreUserRole> userRoles);

    /**
     * 获取用户所有角色.
     * @param coreUserId    权限用户id
     * @return  权限列表
     */
    List<CoreRole> selectUserRoles(Long coreUserId);

    /**
     * 获取角色下所有用户.
     * @param roleId    角色id
     * @return  用户列表
     */
    List<CoreUser> selectRoleUsers(Long roleId);

    /**
     * 获取角色下用户的数量.
     * @param roleId    角色id
     * @param coreUserIds   权限用户id数组
     * @return  包含数量
     */
    int selectCountIsContain(@Param("roleId") Long roleId, @Param("coreUserIds") Long[] coreUserIds);

    /**
     * 批量删除角色下的用户.
     * @param roleId    角色id
     * @param coreUserIds   权限用户id数组
     * @return  包含数量
     */
    int deleteByRoleIdAndCoreUserIds(@Param("roleId") Long roleId, @Param("coreUserIds") Long[] coreUserIds);
}