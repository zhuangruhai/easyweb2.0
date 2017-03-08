package com.aspire.webbas.security.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aspire.webbas.core.pagination.mybatis.pager.Page;
import com.aspire.webbas.portal.common.dao.RoleDao;
import com.aspire.webbas.portal.common.entity.Role;
import com.aspire.webbas.portal.common.entity.RoleResourceOperation;
import com.aspire.webbas.test.BaseTest;

public class RoleDaoTest extends BaseTest{

	private RoleDao dao;
	
	public void test(){
		
		dao = (RoleDao)getBean("roleDao");
		
		assertNotNull("roleDao is null",dao);
		
		
//		insertRole();
		
		deleteRole();
		
//		updateRole();
		
//		findRole(); 
		
//		findRoleByKey();
		
//		findRoleByName();
		
//		listRole();
		
//		listStaffRoles();
		
//		listDepartmentRoles();
		
//		insertRoleResourceOperation();
		
//		deleteRoleResourceOperation();
		
//		deleteRoleResourceOperationById();
	}

	public void insertRole(){
		Role r = new Role();
		
//		r.setRoleId("200010");
		r.setRoleKey("123");
		r.setRoleName("测试角色");
		r.setRoleDesc("测试角色");
		r.setCreateUser("test");
		r.setCreateDate(new Date());
		r.setLastUpdateDate(new Date());
		r.setCanModify(1);
		r.setAutoAssign(0);
		r.setVisible(1);
		
		
		dao.insertRole(r);
		
		System.out.println("insert role[id="+r.getRoleId()+"]  ok");
	}
	
	public void deleteRole(){
		Long roleId = new Long(100001);
		
		dao.deleteRole(roleId);
		
		System.out.println("delete role[id="+roleId+"] ok!");
	}
	
	public void updateRole(){
		Role r = new Role();
		
		r.setRoleId(new Long(100001));
		r.setRoleKey("123");
		r.setRoleName("测试角色2");
		r.setRoleDesc("测试角色2");
		r.setCreateUser("test");
		r.setCreateDate(new Date());
		r.setLastUpdateDate(new Date());
		r.setCanModify(1);
		r.setAutoAssign(0);
		r.setVisible(1);
		
		
		dao.updateRole(r);
		
		System.out.println("update role[id="+r.getRoleId()+"]  ok");
	}
	
	public void findRole(){

		Long roleId = new Long(100001);
		
		Role result = dao.findRole(roleId);
		
		assertNotNull(result);
		
		System.out.println("get role ok.");
		
		System.out.println("role.getDesc:" + result.getRoleDesc());
		
		//com.aspire.webbas.security.dao.RoleDao.getRole
		//com.aspire.webbas.security.dao.RoleDao
	}
	
	public void findRoleByKey(){
		String roleKey = "123";
		
		Role result = dao.findRoleByKey(roleKey);
		
		assertNotNull(result);
		
		System.out.println("find role by key["+roleKey+"] ok");
	}
	
	public void findRoleByName(){
		String roleName = "测试角色2";
		
		Role result = dao.findRoleByName(roleName);
		
		assertNotNull(result);
		
		System.out.println("find role by name["+roleName+"] ok");
	}
	
	public void listRole(){
		Role r = new Role();
		
//		r.setRoleName("管理");
		
//		r.openPagination();
		
//		r.setSort("role_name");
//		r.setDir(Role.DESC);
		Page<Role> page = new Page<Role>();
		
		List<Role> list =  dao.listRole(page);
		
		System.out.println("list role ok!");
		System.out.println("pageSize:"+ page.getPageSize());
		System.out.println("totalSize:"+ page.getTotal());
		
		
		printRole(list);
	}
	
	public void listStaffRoles(){
		Long staffId = new Long(-999);
		
		List<Role> list =  dao.listStaffRoles(staffId);
		
		printRole(list);
	}
	
	public void listDepartmentRoles(){
		Long departmentId = new Long(-999);
		
		List<Role> list =  dao.listDepartmentRoles(departmentId);
		
		printRole(list);
	}
	
	public void insertRoleResourceOperation(){
	
		RoleResourceOperation ro = new RoleResourceOperation();
		ro.setRoleId(new Long(100021));
		ro.setResourceId("10050");
		ro.setOperationKey("TEST");
		
		dao.insertRoleResourceOperation(ro);
		
		System.out.println("insertRoleResourceOperation ok!");
	}
	
	public void deleteRoleResourceOperation(){
		Map<String,Object> map = new HashMap<String,Object>();
		
		map.put("roleId", "100021");
//		map.put("resourceId", "10050");
//		map.put("operationKey", "TEST");
		
		dao.deleteRoleResourceOperation(map);
		
		System.out.println("deleteRoleResourceOperation ok!");
	}
	
	public void deleteRoleResourceOperationById(){
		Long roleId = new Long(100021);
		
		dao.deleteRoleResourceOperationById(roleId);
		
		System.out.println("deleteRoleResourceOperation ok!");
	}
	
	
	private void printRole(List<Role> list){
		System.out.println("[list.size:"+list.size()+"]");
		
		for(Role role : list){
			System.out.println("roleId="+role.getRoleId() + "   roleName=" + role.getRoleName());
		}
	}
	
}
