package com.scot.iframework.permission.dao;

import com.scot.iframework.permission.entity.CoreGroup;
import com.scot.iframework.permission.entity.CoreGroupRole;
import com.scot.iframework.permission.entity.CoreGroupRoleExample;
import com.scot.iframework.permission.entity.CoreRole;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("coreGroupRoleDao")
public interface ICoreGroupRoleDao {
    int countByExample(CoreGroupRoleExample example);

    int deleteByExample(CoreGroupRoleExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CoreGroupRole record);

    int insertSelective(CoreGroupRole record);

    List<CoreGroupRole> selectByExample(CoreGroupRoleExample example);

    CoreGroupRole selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CoreGroupRole record, @Param("example") CoreGroupRoleExample example);

    int updateByExample(@Param("record") CoreGroupRole record, @Param("example") CoreGroupRoleExample example);

    int updateByPrimaryKeySelective(CoreGroupRole record);

    int updateByPrimaryKey(CoreGroupRole record);

    /**
     * 根据roleId、groupId获取角色组关系.
     * @param roleId    角色id
     * @param groupId   组id
     * @return  角色组关系
     */
    CoreGroupRole selectByRoleIdAndGroupId(@Param("roleId") Long roleId, @Param("groupId") Long groupId);

    /**
     * 获取roleId 包含groupIds中组的数量
     * @param roleId    角色Id
     * @param groupIds  组Ids
     * @return  数量
     */
    int selectCountIsContain(@Param("roleId") Long roleId, @Param("groupIds") Long[] groupIds);

    /**
     * 根据roleId删除groupIds中的所有组.
     * @param roleId    角色Id
     * @param groupIds 组Ids
     * @return  数量
     */
    int deleteByRoleIdAndGroupIds(@Param("roleId") Long roleId, @Param("groupIds") Long[] groupIds);

    /**
     * 获取groupId 属于roleIds中角色的数量.
     * @param groupId   组Id
     * @param roleIds    角色数组
     * @return  数量
     */
    int selectCountIsBelong(@Param("groupId") Long groupId, @Param("roleIds") Long[] roleIds);

    /**
     * 根据groupId删除roleIds中所有角色.
     * @param groupId   组Id
     * @param roleIds   角色数组
     * @return 数量
     */
    int deleteByGroupIdAndRoleIds(@Param("groupId") Long groupId, @Param("roleIds") Long[] roleIds);

    /**
     * 批量插入角色、组关系.
     * @param groupRoles    角色、组关系
     * @return 插入数量
     */
    int insertList(List<CoreGroupRole> groupRoles);

    /**
     * 获取角色下组列表.
     * @param roleId    角色id
     * @return  组列表
     */
    List<CoreGroup> selectGroupsByRoleId(Long roleId);

    /**
     * 获取组下所有角色.
     * @param groupId   组id
     * @return  角色列表
     */
    List<CoreRole> selectRolesByGroupId(Long groupId);
}