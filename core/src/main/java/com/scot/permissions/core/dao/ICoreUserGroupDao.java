package com.scot.permissions.core.dao;

import com.scot.permissions.core.entity.CoreUser;
import com.scot.permissions.core.entity.CoreUserGroupExample;
import com.scot.permissions.core.entity.CoreGroup;
import com.scot.permissions.core.entity.CoreUserGroup;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("coreUserGroupDao")
public interface ICoreUserGroupDao {
    int countByExample(CoreUserGroupExample example);

    int deleteByExample(CoreUserGroupExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CoreUserGroup record);

    int insertSelective(CoreUserGroup record);

    List<CoreUserGroup> selectByExample(CoreUserGroupExample example);

    CoreUserGroup selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CoreUserGroup record, @Param("example") CoreUserGroupExample example);

    int updateByExample(@Param("record") CoreUserGroup record, @Param("example") CoreUserGroupExample example);

    int updateByPrimaryKeySelective(CoreUserGroup record);

    int updateByPrimaryKey(CoreUserGroup record);

    /**
     * 根据权限用户Id、组Id获取用户、组关系信息
     * @param coreUserId    用户Id
     * @param groupId   组Id
     * @return  用户、组关系信息
     */
    CoreUserGroup selectByCoreUserIdAndGroupId(@Param("coreUserId") Long coreUserId, @Param("groupId") Long groupId);

    /**
     * 获取权限用户id属于组groupIds的数量
     * @param coreUserId    用户Id
     * @param groupIds  组Ids
     * @return  数量
     */
    int selectCountIsBelong(@Param("coreUserId") Long coreUserId, @Param("groupIds") Long[] groupIds);

    /**
     * 根据权限用户id删除groupIds中的所有组.
     * @param coreUserId    用户Id
     * @param groupIds 组Ids
     * @return  数量
     */
    int deleteByCoreUserIdAndGroupIds(@Param("coreUserId") Long coreUserId, @Param("groupIds") Long[] groupIds);

    /**
     * 批量插入用户、组关系.
     * @param userGroups    用户、组关系类别
     * @return 插入数量
     */
    int insertList(List<CoreUserGroup> userGroups);

    /**
     * 查询用户所有组.
     * @param coreUserId    权限用户id
     * @return  组列表
     */
    List<CoreGroup> selectUserGroups(Long coreUserId);

    /**
     * 获取组下的所有用户.
     * @param groupId   组id
     * @return  用户列表
     */
    List<CoreUser> selectGroupUsers(Long groupId);

    /**
     * 组下用户的数量.
     * @param groupId   组id
     * @param coreUserIds   用户id数组
     * @return  数量
     */
    int selectCountIsContain(@Param("groupId") Long groupId, @Param("coreUserIds") Long[] coreUserIds);

    /**
     * 删除组下用户.
     * @param groupId   组id
     * @param coreUserIds   用户id数组
     * @return  数量
     */
    int deleteByGroupIdAndCoreUserIds(@Param("groupId") Long groupId, @Param("coreUserIds") Long[] coreUserIds);
}