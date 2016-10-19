package com.scot.permissions.core.dao;

import com.scot.permissions.core.entity.CorePermission;
import com.scot.permissions.core.entity.CorePermissionExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository("corePermissionDao")
public interface ICorePermissionDao {
    int countByExample(CorePermissionExample example);

    int deleteByExample(CorePermissionExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CorePermission record);

    int insertSelective(CorePermission record);

    List<CorePermission> selectByExample(CorePermissionExample example);

    CorePermission selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CorePermission record, @Param("example") CorePermissionExample example);

    int updateByExample(@Param("record") CorePermission record, @Param("example") CorePermissionExample example);

    int updateByPrimaryKeySelective(CorePermission record);

    int updateByPrimaryKey(CorePermission record);
}