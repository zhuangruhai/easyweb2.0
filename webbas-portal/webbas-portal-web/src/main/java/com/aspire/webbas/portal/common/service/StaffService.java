package com.aspire.webbas.portal.common.service;

import java.util.List;
import java.util.Map;

import com.aspire.webbas.core.pagination.mybatis.pager.Page;
import com.aspire.webbas.portal.common.entity.Role;
import com.aspire.webbas.portal.common.entity.SecStaffDepartmentRoleKey;
import com.aspire.webbas.portal.common.entity.Staff;
import com.aspire.webbas.portal.common.entity.StaffExtendProperty;

public interface StaffService {

	 void createStaff(Staff staff) throws Exception;
	 /**
	  * 注册用户同时指定角色权限
	  * @param staff
	  * @param roleIds
	  * @throws Exception
	  */
	 void createStaff(Staff staff,String roleIds) throws Exception;
	 /**
     * 更新成员.
     *
     * @param staff 成员对象
     */
     void updateStaff(Staff staff) throws Exception;
     
     void changePassword(String loginName, String oldPassword, String newPassword) throws Exception;

     void resetPassword(String loginName, String newPassword) throws Exception;

     /**
      * 锁定成员
      * @param staffId
      */
     void lockStaff(Long staffId) throws Exception;
     
     
     /**
      * 解锁成员.
      *
      * @param staffId
      */
     void unlockStaff(Long staffId) throws Exception;
     
     /**
      * 删除成员（逻辑删除，只修改成员状态）.
      *
      * @param staffId 成员ID
      */
     void deleteStaffs(Long[] staffIds) throws Exception;
     
     /**
      * 获取成员信息.
      *
      * @param staffId 成员ID
      * @return 成员对象
      */
     Staff findStaff(Long staffId);
     
     /**
      * 根据登录名获取成员，不包括已删除的
      *
      * @param loginName 登录名
      * @return 成员对象
      */
     Staff findStaffByLoginName(String loginName) throws Exception;
     
     /**
      * 获取成员列表.
      *
      * @param staff 成员查询条件
      * @return 成员列表
      */
     Page<Staff> listStaff(Page<Staff> page) throws Exception;
     
     
     void updateStaffRoles(Long staffId, List<Long> roleIds) throws Exception;
     
     void insertStaffRole(Long staffId, Long roleId) throws Exception;
     
     void deleteStaffRole(Long staffId, Long roleId) throws Exception;
     
     /**
      * 获取组织的成员列表.
      *
      * @param departmentId 组织ID
      * @return 成员列表
      */
     List<Staff> listDepartmentStaffs(Long departmentId) throws Exception;
     
     /**
      * 通过departmentId获取该部门以及下级部门的所有用户
      * @param departmentId
      * @return
      * @throws Exception
      */
     List<Staff> listDepartmentAllStaffs(Long departmentId,String keyword, String domain) throws Exception;
     /**
      * 获取组织的成员列表.
      *
      * @param departmentId 组织ID
      * @return 成员列表
      */
     List<Staff> listStaffs(Long departmentId,String keyword) throws Exception;
     
     List<Staff> listStaffsByDomain(String domain,String keyword) throws Exception;
     /**
      * 根据角色查用户
      *
      * @param role 角色
      * @return 成员列表
      */
     List<Staff> listStaffByRole(Long roleId) throws Exception;
     
     
     Page<Role> listStaffRoles(Long staffId) throws Exception;
     
     void updateStaffDepartment(Long departmentId,String staffIds) throws Exception;
     
     void updateStaffRolesDepartment(Long departmentId,String staffIds,String staffIdRoles) throws Exception;
     
     List<SecStaffDepartmentRoleKey> listRoleByStaffIds(Long departmentId, String staffIds) throws Exception;
     
     Staff findStaffByMap(Map<String,Object> params);
     /**
      * 根据staffId查出用户扩展属性
      * @param staffId
      * @return
      */
     List<StaffExtendProperty> listStaffExtendProperties(Long staffId);
     void insertStaffExtendProperty(StaffExtendProperty data);
}
