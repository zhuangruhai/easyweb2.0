package com.aspire.webbas.portal.common.dao;

import java.util.List;
import java.util.Map;

import com.aspire.webbas.portal.common.entity.Department;

public interface DepartmentDao {

	/**
     * 插入组织.
     * @param department 组织对象
     * @return 组织ID
     */
    void insertDepartment(Department department);
    
    /**
     * 更新组织.
     * @param department 组织对象
     */
    void updateDepartment(Department department);

    /**
     * 删除组织.
     * @param departmentId 组织ID
     */
    void deleteDepartment(Long departmentId);
    
	/**
     * 获取组织信息.
     * @param departmentId 组织ID
     * @return 组织对象
     */
    Department findDepartment(Long departmentId);
    
    /**
     * 获取组织列表.
     * @param department 组织查询对象
     * @return 组织列表
     */
    List<Department> listDepartment(Department department);
    
    /**
     * 插入组织角色.
     * map需要包含以下字段
     * departmentId 组织ID
     * roleId 角色ID列表
     */
    void insertDepartmentRoles(Map<String,Object> map);
    
    /**
     * 删除组织角色.
     * @param departmentId 组织ID
     */
    void deleteDepartmentRoles(Long departmentId);
    
    void deleteDepartmentRolesByRoleId(Long roleId);
    
    void deleteDepartmentRolesByRoleIdAndDeptId(Map<String,Object> map);
    
    List<Department> listPathFromRootToCurrentDepartmentId(Long departmentId);
    
    List<Department> listSelfAndSubDepartmentByDepartmentId(Map<String,Object> map);
}
