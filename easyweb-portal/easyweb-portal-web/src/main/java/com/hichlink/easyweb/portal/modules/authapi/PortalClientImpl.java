package com.hichlink.easyweb.portal.modules.authapi;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hichlink.easyweb.core.util.SpringContextHolder;
import com.hichlink.easyweb.portal.common.auth.session.SessionContext;
import com.hichlink.easyweb.portal.common.authapi.AuthConstant;
import com.hichlink.easyweb.portal.common.authapi.AuthResult;
import com.hichlink.easyweb.portal.common.authapi.PortalClient;
import com.hichlink.easyweb.portal.common.entity.Department;
import com.hichlink.easyweb.portal.common.entity.Role;
import com.hichlink.easyweb.portal.common.entity.Staff;
import com.hichlink.easyweb.portal.common.service.AuthService;
import com.hichlink.easyweb.portal.common.service.DepartmentService;
import com.hichlink.easyweb.portal.common.service.RoleService;
import com.hichlink.easyweb.portal.common.service.StaffService;
import com.hichlink.easyweb.portal.common.service.impl.AuthServiceImpl;
import com.hichlink.easyweb.portal.common.service.impl.DepartmentServiceImpl;
import com.hichlink.easyweb.portal.common.service.impl.RoleServiceImpl;
import com.hichlink.easyweb.portal.common.service.impl.StaffServiceImpl;


public class PortalClientImpl implements PortalClient{

	private static String SUCCESS   = "0";
	private static String FAIL      = "1";
	private static String INVALID   = "2";
	private static String NOT_LOGIN = "3";
	
	private static Logger logger = LoggerFactory.getLogger(PortalClientImpl.class);
	/**
	 * ticket认证， 认证通过返回staff，认证不通过返回null
	 * @param ticket
	 * @return
	 */
	
	public Staff auth(String ticket){
		
		// 通过ticket获取session
		HttpSession session = SessionContext.getContext().getSession(ticket);
		
		if(session == null){
			return null;
		}
		
		Staff staff = (Staff)session.getAttribute(SessionContext.LOGIN_STAFF);
		
		return staff;
	}
	
	public AuthResult authUrl(String ticket, String url){
		try {
			
			// 通过ticket获取session
			HttpSession session = SessionContext.getContext().getSession(ticket);
			
			
			if(session == null){
				return authFail(INVALID, "ticket无效", AuthConstant.DEFAULT_LOGIN_PAGE);
			}
			
			AuthService authService = SpringContextHolder.getBean(AuthServiceImpl.class);
			
			// 如果属于例外鉴权的url，直接返回成功
			if(authService.authExclude(url)){
				return authSuccess();
			}
			
			Staff staff = (Staff)session.getAttribute(SessionContext.LOGIN_STAFF);
			
			if(staff == null){
				return authFail(NOT_LOGIN, "用户未登录", AuthConstant.DEFAULT_LOGIN_PAGE);
			}

			// 如果属于登录后不需要鉴权的url,直接返回成功
			if(authService.notNeedAuthAfterLogin(url)){
				return authSuccess();
			}
			
			// 用户url鉴权
			if(authService.authorize(staff.getStaffId(), url)){
				return authSuccess();
			}else{
				return authFail(FAIL, "鉴权失败", AuthConstant.DEFAULT_NOGRANTAUTHORITY_PAGE);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();

			return  authFail(FAIL, e.getMessage(), AuthConstant.DEFAULT_NOGRANTAUTHORITY_PAGE);
		}
	}
	/**
	 * 根据登陆名取得用户信息
	 * @param loginName
	 * @return
	 */
	public Staff findStaffByLoginName(String loginName){
		
		StaffService staffService = SpringContextHolder.getBean(StaffServiceImpl.class);
		
		try {
			return staffService.findStaffByLoginName(loginName);
		} catch (Exception e) {
			logger.error("根据loginName=" + loginName + ",获取用户信息出错", e);
		}
		
		return null;
	}
	
	/**
	 * 根据staffId取得用户信息
	 * @param staffId
	 * @return
	 */
	public Staff findStaffById(Long staffId){
		StaffService staffService = SpringContextHolder.getBean(StaffServiceImpl.class);
		
		try {
			return staffService.findStaff(staffId);
		} catch (Exception e) {
			logger.error("根据staffId="+staffId+",获取用户信息出错", e);
		}
		
		return null;
	}
	
	/**
	 * 根据部门ID取得部门信息
	 * @param departmentId
	 * @return
	 */
	public Department findDepartmentById(Long departmentId){
		
		DepartmentService deptService = SpringContextHolder.getBean(DepartmentServiceImpl.class);
		
		try {
			return deptService.findDepartment(departmentId);
		} catch (Exception e) {
			logger.error("根据departmentId="+departmentId+",获取部门信息出错", e);
		}
		
		return null;
	}
	
	
	/**
	 * 根据组织ID查询角色列表
	 * @param departmentId
	 * @return
	 */
	public List<Role> listRoleByDepartmentId(Long departmentId){
		
		RoleService roleService = SpringContextHolder.getBean(RoleServiceImpl.class);
		
		try {
			return roleService.listDepartmentRoles(departmentId);
		} catch (Exception e) {
			logger.error("根据departmentId="+departmentId+",获取角色列表出错", e);
		}
		
		return new ArrayList<Role>();
	}
	
	/**
	 * 查询指定员工被赋予了那些角色
	 * @param loginName
	 * @return
	 */
	public List<Role> listStaffRoles(Long staffId){
		
		RoleService roleService = SpringContextHolder.getBean(RoleServiceImpl.class);
		
		try {
			return roleService.listStaffRoles(staffId);
		} catch (Exception e) {
			logger.error("根据staffId="+staffId+",查询指定员工被赋予了那些角色出错", e);
		}
		
		return new ArrayList<Role>();
	}
	
	private AuthResult authFail(String code,String message, String url){
		AuthResult result = new AuthResult();
		
		result.setReturnCode(code);
		result.setMessage(message);
		result.setRedirectUrl(url);
		
		return result;
	}
	
	private AuthResult authSuccess(){
		AuthResult result = new AuthResult();
		
		result.setReturnCode(SUCCESS);
		result.setMessage("鉴权成功");

		return result;
	}

	public List<Map<String, Boolean>> authorize(Long staffId,
			String[] resKeys, String[] operKeys) {
		AuthService authService = SpringContextHolder.getBean(AuthServiceImpl.class);
		return authService.authorize(staffId, resKeys, operKeys);
	}

	@Override
	public List<Staff> listStaffByDepartmentId(Long departmentId) throws Exception {
		StaffService staffService = SpringContextHolder.getBean(StaffServiceImpl.class);
		return staffService.listDepartmentStaffs(departmentId);
	}
	
	/**
	 * 根据组织ID查询该组织以及下级所有组织的用户
	 * @param departmentId
	 * @return
	 */
	public List<Staff> listAllStaffByDepartmentId(Long departmentId, String keyword) throws Exception{
		StaffService staffService = SpringContextHolder.getBean(StaffServiceImpl.class);
		return staffService.listDepartmentAllStaffs(departmentId,keyword, null);
	}
	
	/**
	 * 根据组织ID查询该组织以及下级所有组织的用户
	 * @param departmentId
	 * @return
	 */
	public List<Staff> listAllStaffByDepartmentId(Long departmentId, String keyword, String domain) throws Exception{
		StaffService staffService = SpringContextHolder.getBean(StaffServiceImpl.class);
		return staffService.listDepartmentAllStaffs(departmentId,keyword, domain);
	}
	
	public List<Staff> listStaffsByDomain(String domain,String keyword) throws Exception{
		StaffService staffService = SpringContextHolder.getBean(StaffServiceImpl.class);
		return staffService.listStaffsByDomain(domain, keyword);
	}

	@Override
	public List<Staff> searchStaffs(String keyword) throws Exception {
		StaffService staffService = SpringContextHolder.getBean(StaffServiceImpl.class);
		return staffService.listStaffs(null, keyword);
	}

	@Override
	public List<Staff> searchStaffs(String keyword, Long departmentId)
			throws Exception {
		StaffService staffService = SpringContextHolder.getBean(StaffServiceImpl.class);
		return staffService.listStaffs(departmentId, keyword);
	}

	@Override
	public Staff createStaff(Staff staff) throws Exception {
		StaffService staffService = SpringContextHolder.getBean(StaffServiceImpl.class);
		staffService.createStaff(staff);
		return staff;
	}
	@Override
	public Staff createStaff(Staff staff,String roleIds) throws Exception {
		StaffService staffService = SpringContextHolder.getBean(StaffServiceImpl.class);
		staffService.createStaff(staff,roleIds);
		return staff;
	}
	@Override
	public void createDepartment(Department department) throws Exception {
		DepartmentService departmentService = SpringContextHolder.getBean(DepartmentServiceImpl.class);
		departmentService.insertDepartment(department);
	}
	@Override
	public void insertDepartmentRole(Long departmentId,Long roleId) throws Exception {
		DepartmentService departmentService = SpringContextHolder.getBean(DepartmentServiceImpl.class);
		departmentService.insertDepartmentRole(departmentId, roleId);
	}
	@Override
	public void updateStaffDepartment(Long departmentId, String staffIds)
			throws Exception {
		StaffService staffService = SpringContextHolder.getBean(StaffServiceImpl.class);
		staffService.updateStaffDepartment(departmentId, staffIds);
	}

	@Override
	public void updateStaffRolesDepartment(Long departmentId, String staffIds,
			String staffIdRoles) throws Exception {
		StaffService staffService = SpringContextHolder.getBean(StaffServiceImpl.class);
		staffService.updateStaffRolesDepartment(departmentId, staffIds, staffIdRoles);
	}

	@Override
	public void resetPassword(String loginName, String newPassword)
			throws Exception {
		StaffService staffService = SpringContextHolder.getBean(StaffServiceImpl.class);
		staffService.resetPassword(loginName, newPassword);
	}

	@Override
	public boolean authExclude(String addressUrl) throws Exception {
		AuthService authService = SpringContextHolder.getBean(AuthServiceImpl.class);
		return authService.authExclude(addressUrl);
	}
}
