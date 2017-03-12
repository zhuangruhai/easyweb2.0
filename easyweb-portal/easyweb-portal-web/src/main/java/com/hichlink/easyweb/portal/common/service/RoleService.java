package com.hichlink.easyweb.portal.common.service;

import java.util.List;

import com.hichlink.easyweb.core.pagination.mybatis.pager.Page;
import com.hichlink.easyweb.portal.common.entity.Role;
import com.hichlink.easyweb.portal.common.entity.RoleResourceOperation;
import com.hichlink.easyweb.portal.common.tree.TreeNode;

public interface RoleService {

	 void createRole(Role role) throws Exception;
	/**
     * 创建角色，已经角色和资源的绑定，其中的资源的操作权限
     * @param role                      角色基本信息
     * @param roleResourceOperations    角色资源操作关联
     * @return
     */
//    void createRole(Role role, List<RoleResourceOperation> roleResourceOperations) throws Exception;
    
    /**
     * 更新角色.  重新绑定角色和资源的关联，资源的操作权限
     * @param role                      角色基本信息
     * @param roleResourceOperations    角色资源操作关联
     * @return
     */
//    void updateRole(Role role, List<RoleResourceOperation> roleResourceOperations) throws Exception;
    
    
    void updateRole(Role role) throws Exception;
    
	void deleteRole(Long roleId) throws Exception;
	
	Role findRole(Long roleId) throws Exception;
	
	/**
     * 获取角色列表.
     * @param role 角色查询条件
     * @return 角色列表
     */
    Page<Role> listRole(Page<Role> page) throws Exception;
    
    
    void updateRoleResource(Long roleId, List<RoleResourceOperation> roleResourceOperations) throws Exception;
    /**
     * 获取成员角色列表.
     *
     * @param staffId      成员ID
     * @param departmentId 组织ID
     * @return 成员角色列表
     */
    List<Role> listStaffRoles(Long staffId) throws Exception;
    
    /**
     * 获取组织角色列表.
     *
     * @param departmentId 组织ID
     * @return 组织角色列表
     */
    List<Role> listDepartmentRoles(Long departmentId) throws Exception;
    
    
    /**
     * 构造角色资源分配树
     * @return
     */
    List<TreeNode> buildRoleResourceTree();
    
    /**
     * 按roleId已经分配的资源权限构造资源树
     * @param roleId
     * @return
     */
    List<TreeNode> buildRoleResourceTree(Long roleId);
    /**
     * 根据roleKey查询角色
     * @param roleKey
     * @return
     */
    Role findRoleByKey(String roleKey);
}
