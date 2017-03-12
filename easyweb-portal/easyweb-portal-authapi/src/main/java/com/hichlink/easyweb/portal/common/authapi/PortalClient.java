package com.hichlink.easyweb.portal.common.authapi;

import java.util.List;
import java.util.Map;

import com.hichlink.easyweb.portal.common.entity.Department;
import com.hichlink.easyweb.portal.common.entity.Role;
import com.hichlink.easyweb.portal.common.entity.Staff;

public interface PortalClient {

	
	
	
	/**
	 * 认证接口， 认证通过返回staff，认证不通过返回null
	 * @param ticket 票据
	 * @return
	 */
	
	Staff auth(String ticket);
	
	
	/**
	 * 鉴权接口
	 * @param ticket 票据
	 * @param url  需要鉴权的url
	 *     此url对应的元数据配置的url
	 * @return
	 */
	AuthResult authUrl(String ticket, String url);
	
	
	/**
	 * 根据登陆名取得用户信息
	 * @param loginName
	 * @return
	 */
	Staff findStaffByLoginName(String loginName);
	
	/**
	 * 根据staffId取得用户信息
	 * @param staffId
	 * @return
	 */
	Staff findStaffById(Long staffId);
	
	/**
	 * 根据部门ID取得部门信息
	 * @param departmentId
	 * @return
	 */
	Department findDepartmentById(Long departmentId);
	
	
	/**
	 * 根据组织ID查询角色列表
	 * @param departmentId
	 * @return
	 */
	List<Role> listRoleByDepartmentId(Long departmentId);
	
	/**
	 * 查询指定员工被赋予了那些角色
	 * @param staffId
	 * @return
	 */
	List<Role> listStaffRoles(Long staffId);
	/**
	 * 对当前用户通过resKey,operKey进行签权
	 * @param staffId 登录用户staffId
	 * @param resKeys 资源ID组
	 * @param operKeys 操作ID组
	 * @return
	 */
	List<Map<String,Boolean>> authorize(Long staffId, String[] resKeys,String[] operKeys);
	/**
	 * 根据组织ID查询用户列表
	 * @param departmentId
	 * @return
	 */
	List<Staff> listStaffByDepartmentId(Long departmentId) throws Exception;
	
	/**
	 * 根据组织ID查询该组织以及下级所有组织的用户
	 * @param departmentId
	 * @return
	 */
	List<Staff> listAllStaffByDepartmentId(Long departmentId, String keyword) throws Exception;
	
	/**
	 * 根据组织ID查询该组织以及下级所有组织的用户
	 * @param departmentId 部门ID
	 * @param keyword 关键字，通过loginName和realName做模糊查询
	 * @param domain 管理域 SP或SYS_ADMIN
	 * @return
	 * @throws Exception
	 */
	List<Staff> listAllStaffByDepartmentId(Long departmentId, String keyword, String domain) throws Exception;
	
	/**
	 * 根据domain查询用户
	 * @param domain 管理域 SP或SYS_ADMIN
	 * @param keyword 关键字，通过loginName和realName做模糊查询
	 * @return
	 * @throws Exception
	 */
	List<Staff> listStaffsByDomain(String domain,String keyword) throws Exception;
	/**
	 * 根据关键词查询用户,keyword支持loginName和realName模糊查询
	 * @param keyword
	 * @param departmentId
	 * @return
	 * @throws Exception
	 */
	List<Staff> searchStaffs(String keyword) throws Exception;
	/**
	 * 根据部门ID关键词查询用户,keyword支持loginName和realName模糊查询
	 * @param keyword
	 * @param departmentId
	 * @return
	 * @throws Exception
	 */
	List<Staff> searchStaffs(String keyword,Long departmentId) throws Exception;
	/**
	 * 创建登录帐号
	 * @param staff
	 * @throws Exception
	 */
	Staff createStaff(Staff staff) throws Exception;
	/**
	 * 创建登录帐号,同时指定角色ID
	 * @param staff 用户信息
	 * @param roleIds 角色Id,多个通过逗号隔开
	 * @return
	 * @throws Exception
	 */
	Staff createStaff(Staff staff,String roleIds) throws Exception;
	/**
	 * 创建组织
	 * @param department
	 * @throws Exception
	 */
	void createDepartment(Department department) throws Exception;
	/**
	 * 将角色与组织关联
	 * @param departmentId
	 * @param roleId
	 * @throws Exception
	 */
	void insertDepartmentRole(Long departmentId,Long roleId) throws Exception;
	/**
	 * 将用户跟组织关联
	 * @param departmentId
	 * @param staffIds 帐号ID，多个以','隔开
	 * @throws Exception
	 */
	void updateStaffDepartment(Long departmentId, String staffIds) throws Exception;
	/**
	 * 对指定部门给指定用户分配权限
	 * @param departmentId
	 * @param staffIds 要操作帐号ID，多个以','隔开
	 * @param staffIdRoles staffId和roleId成对以'|'隔开，多个以','隔开
	 * @throws Exception
	 */
	void updateStaffRolesDepartment(Long departmentId,String staffIds,String staffIdRoles) throws Exception;
	/**
	 * 重置密码
	 * @param loginName 登录用户名
	 * @param newPassword 新的密码
	 * @throws Exception
	 */
	void resetPassword(String loginName, String newPassword) throws Exception;
	
	boolean authExclude(String addressUrl) throws Exception;
}
