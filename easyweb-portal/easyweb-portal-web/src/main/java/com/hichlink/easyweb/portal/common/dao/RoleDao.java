package com.aspire.webbas.portal.common.dao;

import java.util.List;

import java.util.Map;

import com.aspire.webbas.core.pagination.mybatis.pager.Page;
import com.aspire.webbas.portal.common.entity.Role;
import com.aspire.webbas.portal.common.entity.RoleResourceOperation;

public interface RoleDao {

	/**
	 * 新增角色
	 * @param r
	 */
	void insertRole(Role r);
	
	/**
     * 删除角色.
     * @param roleId 角色ID
     */
    void deleteRole(Long roleId);
	
    
    /**
     * 更新角色.
     * @param role 角色对象
     */
    void updateRole(Role role);
    
    /**
     * 查找角色
     * @param roleId
     * @return
     */
	Role findRole(Long roleId);
	
	/**
     * 获取角色信息.
     * @param roleKey 角色Key
     * @return 角色对象
     */
    Role findRoleByKey(String roleKey);
    
    /**
     * 得到角色通过角色名称
     * @param roleName
     * @return role 
     */
    Role findRoleByName(String roleName);
    
    
    /**
     * 获取角色列表.
     * @param role 角色查询条件
     * @return 角色列表
     */
    List<Role> listRole(Page<Role> page);
    
    /**
     * 获取成员角色
     * @param staffId
     * @return
     */
    List<Role> listStaffRoles(Long staffId);
    
    /**
     * 获取组织角色列表.
     * @param departmentId 组织ID
     * @return 组织角色列表
     */
    List<Role> listDepartmentRoles(Long departmentId);
    
    /**
     * 插入角色资源操作关联.
     * @param ro
     */
    void insertRoleResourceOperation(RoleResourceOperation ro);
    
    /**
     * 删除角色资源操作关联.
     * @param map
     * roleId 角色ID
     * resourceId 资源Id (可为空)
     * operationKey 操作外码列表 (可为空)
     */
    void deleteRoleResourceOperation(Map<String, Object> map);
    
    
    /**
     * 删除角色资源操作关联.
     * @param roleId 角色ID
     */
    void deleteRoleResourceOperationById(Long roleId);
    
}

