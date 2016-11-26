package com.scot.iframework.permission.service;

import com.scot.iframework.permission.entity.CorePermission;
import com.scot.iframework.permission.enums.PermissionCategoryEnum;
import com.scot.iframework.permission.enums.PermissionTypeEnum;
import com.scot.iframework.permission.vo.AddPermissionVo;
import com.scot.iframework.permission.vo.UpdatePermissionVo;

import java.util.List;
import java.util.Map;

/**
 * 权限service接口
 * Created by shengke on 2016/10/21.
 */
public interface ICorePermissionService {

    /**
     * 添加权限.
     * @param corePermission    权限信息
     * @return  权限id
     */
    Long addCorePermission(CorePermission corePermission);

    /**
     * 删除权限.
     * @param id    权限id
     * @param operator  操作人
     */
    void delCorePermission(Long id, Long operator);

    /**
     * 更新权限.
     * @param corePermission    权限信息
     */
    void updateCorePermission(CorePermission corePermission);

    /**
     * 获取权限列表.
     * @return  权限列表
     */
    List<CorePermission> queryAllCorePermission();

    /**
     * 根据权限类型获取权限列表.
     * @param permissionType    权限类型枚举
     * @param permissionType    权限类别枚举
     * @return  权限列表
     */
    List<CorePermission> queryCorePermissionByType(PermissionTypeEnum permissionCategory,
                                                   PermissionCategoryEnum permissionType);

    /**
     * 根据id获取权限.
     * @param id    主键id.
     * @return  权限信息
     */
    CorePermission queryCorePermissionById(Long id);

    /**
     * 新增节点权限.
     * 页面新增权限时使用，同时添加访问和控制权限.
     * @param vo 新增权限vo
     * @return  新增权限MAP
     */
    Map<Byte, CorePermission> addBizPermission(AddPermissionVo vo);

    /**
     * 更新节点权限信息.
     * @param vo    更新节点vo.
     */
    void updateBizPermission(UpdatePermissionVo vo);
}
