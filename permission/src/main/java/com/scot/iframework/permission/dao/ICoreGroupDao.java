package com.scot.iframework.permission.dao;

import com.scot.iframework.permission.entity.CoreGroup;
import com.scot.iframework.permission.entity.CoreGroupExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("coreGroupDao")
public interface ICoreGroupDao {
    int countByExample(CoreGroupExample example);

    int deleteByExample(CoreGroupExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CoreGroup record);

    int insertSelective(CoreGroup record);

    List<CoreGroup> selectByExample(CoreGroupExample example);

    CoreGroup selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CoreGroup record, @Param("example") CoreGroupExample example);

    int updateByExample(@Param("record") CoreGroup record, @Param("example") CoreGroupExample example);

    int updateByPrimaryKeySelective(CoreGroup record);

    int updateByPrimaryKey(CoreGroup record);
}