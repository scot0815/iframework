package com.scot.permissions.core.dao;

import com.scot.permissions.core.entity.CoreUser;
import com.scot.permissions.core.entity.CoreUserExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("coreUserDao")
public interface ICoreUserDao {
    int countByExample(CoreUserExample example);

    int deleteByExample(CoreUserExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CoreUser record);

    int insertSelective(CoreUser record);

    List<CoreUser> selectByExample(CoreUserExample example);

    CoreUser selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CoreUser record, @Param("example") CoreUserExample example);

    int updateByExample(@Param("record") CoreUser record, @Param("example") CoreUserExample example);

    int updateByPrimaryKeySelective(CoreUser record);

    int updateByPrimaryKey(CoreUser record);

    /**
     * 根据业务userId获取权限用户.
     * @param bizUserId 业务userId
     * @return  权限用户
     */
    CoreUser selectByBizUserId(Long bizUserId);
}