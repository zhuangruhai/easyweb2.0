package com.hichlink.easyweb.portal.common.dao;

import java.util.List;
import java.util.Map;

import com.hichlink.easyweb.core.pagination.mybatis.pager.Page;
import com.hichlink.easyweb.portal.common.entity.SecStaffDepartmentRoleKey;
import com.hichlink.easyweb.portal.common.entity.Staff;
import com.hichlink.easyweb.portal.common.entity.StaffExtendProperty;

public interface StaffDao {

	/**
     * 插入成员.
     *
     * @param staff 成员对象
     * @return 成员ID
     */
    void insertStaff(Staff staff);
    
    /**
     * 更新成员.
     *
     * @param staff 成员对象
     */
    void updateStaff(Staff staff);
    
    /**
     * 更新密码
     * @param staff
     */
    void updateStaffPassword(Staff staff);
    
    /**
     * 删除成员（逻辑删除，只修改成员状态）.
     *
     * @param staffId 成员ID
     */
    void deleteStaff(Long staffId);
    
    /**
     * 彻底删除成员，只为调试时使用.
     *
     * @param staffId 成员ID
     */
    void removeStaff(Long staffId);
    
	/**
     * 获取成员信息.
     *
     * @param staffId 成员ID
     * @return 成员对象
     */
    Staff findStaff(Long staffId);
    /**
     * 根据登录名，手机号码，邮箱做为查询关键词查询用户信息
     * @param params
     * @return
     */
    List<Staff> findStaffByMap(Map<String,Object> params);
    /**
     * 根据登录名获取成员.
     *
     * @param loginName 登录名
     * @return 成员对象
     */
    Staff findStaffByLoginName(String loginName);
    
    /**
     * 
     * @param loginName
     * @param password
     * @return
     */
    Staff findStaffByNameAndPassword(Staff staff);
    
    
    void insertStaffExtendProperties(StaffExtendProperty property);
    
    void deleteStaffExtendProperties(Long staffId);
    
    List<StaffExtendProperty> listStaffExtendProperties(Long staffId);
    /**
     * 获取成员列表,支持分页.
     *
     * @param staff 成员查询条件
     * @return 成员列表
     */
//    List<Staff> listStaff(Staff staff);
    
    List<Staff> listStaff(Page<Staff> page);
    
    /**
     * 插入成员角色.
     *
     * staffId      成员ID
     * departmentId 组织ID
     * roleId      角色ID列表
     */
    void insertStaffRoles(Map<String, Object> map);
    
    void deleteStaffRoles(Long staffId);
    
    void deleteStaffRolesByRoleId(Long roleId);
    
    void deleteStaffRolesByStaffIdAndRoleId(Map<String, Object> param);
    
    void deleteStaffRolesByDepartmentIdAndRoleId(Map<String, Object> param);
    
    void deleteStaffRolesByDepartmentIdAndNotInStaffIds(Map<String, Object> param);
    
    /**
     * 插入成员角色(根据角色外码).
     *
     * staffId      成员ID
     * departmentId 组织ID
     * roleKey     角色外码列表
     */
    void insertStaffRolesByKey(Map<String, String> map);
    
    
    List<Staff> listStaffByRole(Long roleId);
    
    void clearStaffDepartment(Long departmentId);
    
    /**
     * 更新成员的部门属性值
     * @param map
     * departmentId
     * staffIds (staffId要用"," 分隔开)
     */
    void updateStaffDepartment(Map<String,String> map);
    /**
     * 更新成员的部门属性值
     * @param map
     * departmentId
     * staffIds 数组orList
     */
    void updateStaffDepartmentByStaffIds(Map<String,Object> map);
    
    List<SecStaffDepartmentRoleKey> listRoleByStaffIds(Map<String,Object> map);
}
