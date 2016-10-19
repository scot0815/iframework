package com.scot.permissions.core.dao;

import com.scot.permissions.core.entity.CoreRole;
import com.scot.permissions.core.entity.CoreRoleExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("coreRoleDao")
public interface ICoreRoleDao {
    int countByExample(CoreRoleExample example);

    int deleteByExample(CoreRoleExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CoreRole record);

    int insertSelective(CoreRole record);

    List<CoreRole> selectByExample(CoreRoleExample example);

    CoreRole selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CoreRole record, @Param("example") CoreRoleExample example);

    int updateByExample(@Param("record") CoreRole record, @Param("example") CoreRoleExample example);

    int updateByPrimaryKeySelective(CoreRole record);

    int updateByPrimaryKey(CoreRole record);
}