package com.scot.iframework.permission.dao;

import com.scot.iframework.permission.entity.CorePermissionExample;
import com.scot.iframework.permission.entity.CorePermission;

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

    /**
     * 根据类型获取权限列表.
     * @param typeCode  类型
     * @param category 类别
     * @return  权限列表
     */
    List<CorePermission> selectPermissionByType(@Param("typeCode") Integer typeCode, @Param("category") Integer category);

    /**
     * 获取父权限下最大code.
     * @param parentFunctionCode    父权限code
     * @return      父功能code下最大code
     */
    String selectMaxCodeByParentCode(String parentFunctionCode);

    /**
     * 根据权限code获取权限信息.
     * @param functionCode  权限code
     * @return  权限信息
     */
    CorePermission selectByFunctionCode(String functionCode);

    /**
     * 获取父权限下一级所有权限.
     * @param parentFunctionCode    父权限code
     * @return  权限信息列表
     */
    List<CorePermission> selectByParentFunctionCode(String parentFunctionCode);
}