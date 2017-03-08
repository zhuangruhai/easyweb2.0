package com.aspire.webbas.portal.common.service;

import java.util.List;

import com.aspire.webbas.core.pagination.mybatis.pager.Page;
import com.aspire.webbas.portal.common.entity.Department;
import com.aspire.webbas.portal.common.entity.Role;
import com.aspire.webbas.portal.common.tree.TreeNode;

public interface DepartmentService {

	/**
     * 插入组织.
     * @param department 组织对象
     * @return 组织ID
     */
    void insertDepartment(Department department) throws Exception;

    /**
     * 更新组织.
     * @param department 组织对象
     */
    void updateDepartment(Department department) throws Exception;

    /**
     * 删除组织.
     * @param departmentId 组织ID
     */
    void deleteDepartment(Long departmentId) throws Exception;

    /**
     * 获取组织信息.
     * @param departmentId 组织ID
     * @return 组织对象
     */
    Department findDepartment(Long departmentId) throws Exception;

    /**
     * 获取组织列表.
     * @param department 组织查询对象
     * @return 组织列表
     */
    List<Department> listDepartment(Department department) throws Exception;
    
    
    Page<Role> listDepartmentRoles(Long departmentId) throws Exception;
    
    void deleteDepartmentRoleByRoleIdAndDeptId(Long departmentId, Long roleId) throws Exception;
    
    void insertDepartmentRole(Long departmentId, Long roleId) throws Exception;
    /**
     * 更新部门角色
     * @param departmentId
     * @param roleIds
     */
//    void updateDepartmentRoles(String departmentId, List<String> roleIds) throws Exception;
    
    List<TreeNode> buildDepartmentTree() throws Exception;
    
    List<String> findMyDepartmentAndChildrenDeptIds() throws Exception;
    
    List<Department> listPathFromRootToCurrentDepartmentId(Long departmentId) throws Exception;
}

