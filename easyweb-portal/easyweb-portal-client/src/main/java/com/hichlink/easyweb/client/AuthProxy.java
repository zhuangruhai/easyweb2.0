package com.hichlink.easyweb.client;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import com.hichlink.easyweb.client.config.PortalClientConfig;
import com.hichlink.easyweb.portal.common.authapi.AuthResult;
import com.hichlink.easyweb.portal.common.authapi.PortalClient;
import com.hichlink.easyweb.portal.common.entity.Department;
import com.hichlink.easyweb.portal.common.entity.Role;
import com.hichlink.easyweb.portal.common.entity.Staff;
import com.caucho.hessian.client.HessianProxyFactory;

public class AuthProxy implements PortalClient{
	
	private static AuthProxy proxy;
	private PortalClient client;
	private String url;
	
	private AuthProxy(String url){
		this.url = url;
		repairClient();
	}
	
	public static AuthProxy getProxy(String url){
		if(proxy == null){
			proxy = new AuthProxy(url);
		}
		
		return proxy;
	}
	/**
	 * 请求的地址取portalclient.xml里的
	 * @return
	 */
	public static AuthProxy getProxy(){
		String url = PortalClientConfig.getInstance().getPortalAuthUrl();
		return getProxy(url);
	}
	public boolean repairClient(){
		HessianProxyFactory factory = new HessianProxyFactory();
		factory.setOverloadEnabled(true); 
		try {
			client = (PortalClient) factory.create(PortalClient.class, url);
			
			return true;
		} catch (MalformedURLException e) {
			e.printStackTrace();
			
			return false;
		}
	}
	/**
	 * 认证接口， 认证通过返回staff，认证不通过返回null
	 * @param ticket
	 * @return
	 */
	
	public Staff auth(String ticket){
		
		
		return getClient().auth(ticket);
	}
	
	
	/**
	 * 鉴权接口
	 * @param ticket 票据
	 * @param url  需要鉴权的url
	 *     此url对应的元数据配置的url
	 * @return
	 */
	public AuthResult authUrl(String ticket, String url){
		return getClient().authUrl(ticket, url);
	}
	
	
	/**
	 * 根据登陆名取得用户信息
	 * @param loginName
	 * @return
	 */
	public Staff findStaffByLoginName(String loginName){
		return getClient().findStaffByLoginName(loginName);
	}
	
	/**
	 * 根据staffId取得用户信息
	 * @param staffId
	 * @return
	 */
	public Staff findStaffById(Long staffId){
		return getClient().findStaffById(staffId);
	}
	
	/**
	 * 根据部门ID取得部门信息
	 * @param departmentId
	 * @return
	 */
	public Department findDepartmentById(Long departmentId){
		return getClient().findDepartmentById(departmentId);
	}
	
	
	/**
	 * 根据组织ID查询角色列表
	 * @param departmentId
	 * @return
	 */
	public List<Role> listRoleByDepartmentId(Long departmentId){
		return getClient().listRoleByDepartmentId(departmentId);
	}
	
	/**
	 * 查询指定员工被赋予了那些角色
	 * @param staffId
	 * @return
	 */
	public List<Role> listStaffRoles(Long staffId){
		return getClient().listStaffRoles(staffId);
	}
	
	private PortalClient getClient(){
		if(client == null){
			repairClient();
		}
		
		return client;
	}

	public List<Map<String, Boolean>> authorize(Long staffId,
			String[] resKeys, String[] operKeys) {
		return client.authorize(staffId, resKeys, operKeys);
	}

	public List<Staff> listStaffByDepartmentId(Long departmentId)
			throws Exception {
		return client.listStaffByDepartmentId(departmentId);
	}
	
	public List<Staff> listAllStaffByDepartmentId(Long departmentId, String keyword)
			throws Exception {
		return client.listAllStaffByDepartmentId(departmentId, keyword);
	}
	
	public List<Staff> listAllStaffByDepartmentId(Long departmentId, String keyword, String domain)
			throws Exception {
		return client.listAllStaffByDepartmentId(departmentId, keyword, domain);
	}

	public List<Staff> listStaffsByDomain(String domain,String keyword) throws Exception{
		return client.listStaffsByDomain(domain, keyword);
	}
	public List<Staff> searchStaffs(String keyword) throws Exception {
		return client.searchStaffs(keyword);
	}

	public List<Staff> searchStaffs(String keyword, Long departmentId)
			throws Exception {
		return client.searchStaffs(keyword, departmentId);
	}

	@Override
	public Staff createStaff(Staff staff) throws Exception {
		return client.createStaff(staff);
	}
	@Override
	public Staff createStaff(Staff staff,String roleIds) throws Exception {
		return client.createStaff(staff,roleIds);
	}
	@Override
	public void createDepartment(Department department) throws Exception {
		client.createDepartment(department);
	}

	@Override
	public void insertDepartmentRole(Long departmentId, Long roleId)
			throws Exception {
		client.insertDepartmentRole(departmentId, roleId);
	}

	@Override
	public void updateStaffDepartment(Long departmentId, String staffIds)
			throws Exception {
		client.updateStaffDepartment(departmentId, staffIds);
	}

	@Override
	public void updateStaffRolesDepartment(Long departmentId, String staffIds,
			String staffIdRoles) throws Exception {
		client.updateStaffRolesDepartment(departmentId, staffIds, staffIdRoles);
	}

	@Override
	public void resetPassword(String loginName, String newPassword)
			throws Exception {
		client.resetPassword(loginName, newPassword);
	}

	@Override
	public boolean authExclude(String addressUrl) throws Exception {
		return client.authExclude(addressUrl);
	}
}
