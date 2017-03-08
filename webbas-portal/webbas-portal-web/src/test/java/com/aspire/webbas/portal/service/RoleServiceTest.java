package com.aspire.webbas.portal.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aspire.webbas.core.pagination.mybatis.pager.Page;
import com.aspire.webbas.portal.common.entity.Role;
import com.aspire.webbas.portal.common.entity.RoleResourceOperation;
import com.aspire.webbas.portal.common.service.RoleService;
import com.aspire.webbas.portal.common.tree.TreeNode;
import com.aspire.webbas.test.BaseTest;

public class RoleServiceTest extends BaseTest{

	private RoleService roleService;
	
	public void test(){
		
		roleService = (RoleService)getBean("roleService");
		
		assertNotNull(roleService);
		
//		System.out.println("get roleService ok"); 
		
//		insertRole();
		
//		createRole();
		
//		updateRole();
		
//		deleteRole();
		
//		getRole(); 
		
//		listRoles();
		
//		listDepartmentRoles();
		
		buildRoleResourceTree();
		
	}
	
	public void insertRole(){
		Role r = buildRole();

//		roleService.insertRole(r);
		
		System.out.println("insert ok");
	}
	
	public void createRole(){
		Role r = buildRole();
		
		List<RoleResourceOperation> list = buildRoleResourceOperations(r.getRoleId());

		
		try {
			roleService.createRole(r);
			
			System.out.println("create role ok!");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void updateRole(){
		Long roleId = new Long(100031);
		
		try {
			Role r = roleService.findRole(roleId);
			
			assertNotNull("update role[id="+roleId+"] is null", r);
			
			r.setRoleName("测试角色2");
			r.setRoleDesc("测试角色2");
			
			roleService.updateRole(r);
			
			System.out.println("update role[id="+roleId+"] ok");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void deleteRole(){
		try {
			roleService.deleteRole(new Long(100031));
			
			System.out.println("delete ok!");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public void getRole(){
			
		RoleService roleService = (RoleService)getBean("roleService");
		
		assertNotNull(roleService);
		
		System.out.println("get roleService ok");
		
		Role r = new Role();
		
		r.setRoleId(new Long(100031));
		Long roleId = new Long(100031);
		
//		Role result = roleService.findRole(roleId);
		
//		assertNotNull(result);
//		
//		System.out.println("get role ok.");
//		
//		System.out.println("role.getDesc:" + result.getRoleDesc());
		
		//com.aspire.webbas.security.dao.RoleDao.getRole
		//com.aspire.webbas.security.dao.RoleDao
	}
	
	private void listRoles(){
//		Role role = new Role();
		
//		role.openPagination();

//		role.setRoleName("管理");
		Page<Role> page = new Page<Role>();
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("roleName", "管理");
		page.setParams(params);
		
		try {
			page = roleService.listRole(page);
			
			System.out.println("total:" + page.getTotal());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void listDepartmentRoles(){
		Long departmentId = new Long(-998);
		
		try {
			List<Role> roles = roleService.listDepartmentRoles(departmentId);
			
			StringBuffer roleBuffer = new StringBuffer();
			
			for(Role r : roles){
				roleBuffer.append(r.getRoleName())
				      .append(",");
			}
			
//			d.addOtherField("roles", roleBuffer.toString());
			
			System.out.println("" + roleBuffer.toString());
			
//			System.out.println("total:" + page.getTotal());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void buildRoleResourceTree(){
		List<TreeNode> list = roleService.buildRoleResourceTree();
		
		System.out.println("list.size=" + list.size());
		
		this.toJson(list);
	}
	
	private Role buildRole(){
		Role r = new Role();
		
		
		r.setRoleId(new Long(200010));
		r.setRoleKey("123");
		r.setRoleName("测试角色");
		r.setRoleDesc("测试角色");
		r.setCreateUser("test");
		r.setCreateDate(new Date());
		r.setLastUpdateDate(new Date());
		r.setCanModify(1);
		r.setAutoAssign(0);
		r.setVisible(1);
		
		return r;
	}
	
	private List<RoleResourceOperation> buildRoleResourceOperations(Long roleId){
		
		List<RoleResourceOperation> list = new ArrayList<RoleResourceOperation>();
		
		RoleResourceOperation ro = new RoleResourceOperation();
		ro.setRoleId(roleId);
		ro.setResourceId("10050");
		ro.setOperationKey("TEST");
		
		
		list.add(ro);
		
		return list;
	}
}
