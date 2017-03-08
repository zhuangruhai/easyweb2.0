package com.aspire.webbas.portal.service;

import java.util.ArrayList;
import java.util.List;

import com.aspire.webbas.core.pagination.mybatis.pager.Page;
import com.aspire.webbas.portal.common.entity.Department;
import com.aspire.webbas.portal.common.entity.Role;
import com.aspire.webbas.portal.common.service.DepartmentService;
import com.aspire.webbas.portal.common.tree.TreeNode;
import com.aspire.webbas.test.BaseTest;

public class DepartmentServiceTest extends BaseTest{
	
	private DepartmentService departmentService;
	
	public void test(){
		departmentService = (DepartmentService)getBean("departmentService");
		
		assertNotNull("departmentService is null", departmentService);
		
//		insertDepartment();
		
//		updateDepartment();
		
//		findDepartment();
		
//		findDepartmentInfo();
		
//		deleteDepartment();
		
//		listDepartment();
		
//		updateDepartmentRoles();
		
//		buildDepartmentTree();
		
		listDepartmentRoles();
	}
	
	public void insertDepartment(){
		
		Department dept = new Department();
		
		dept.setDepartmentName("组织3");
		dept.setDepartmentDesc("组织3");
		dept.setAddSub("1");
		dept.setCreateUser("admin");
		
		try {
			departmentService.insertDepartment(dept);
			
			System.out.println("insert department[id="+dept.getDepartmentId()+"] ok");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public void updateDepartment(){
		Long departmentId = new Long(2);
		
		try {
			Department dept = departmentService.findDepartment(departmentId);
			
			assertNotNull("find department[id="+dept.getDepartmentId()+"] null", dept);
			
			dept.setDepartmentDesc("部门说明");
			
			departmentService.updateDepartment(dept);
			
			System.out.println("update department[id="+dept.getDepartmentId()+"] ok");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void deleteDepartment(){
		Long departmentId = new Long(2);
		
		try {
			departmentService.deleteDepartment(departmentId);

			System.out.println("delete department[id="+departmentId+"] ok");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void findDepartment(){
		
		Long departmentId = new Long(-998);
		
		try {
			Department dept = departmentService.findDepartment(departmentId);
			
			assertNotNull("find department[id="+dept.getDepartmentId()+"] null", dept);
			
			System.out.println("find department[id="+dept.getDepartmentId()+"] ok");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void findDepartmentInfo(){
		Long departmentId = new Long(-998);
		
		try {
			Department dept = departmentService.findDepartment(departmentId);
			
			assertNotNull("find department[id="+dept.getDepartmentId()+"] null", dept);
			
			System.out.println("find department[id="+dept.getDepartmentId()+"] ok");
			
//			List<Role> roles = roleService.listDepartmentRoles(departmentId);
//			StringBuffer roleBuffer = new StringBuffer();
//			
//			for(Role r : roles){
//				roleBuffer.append(r.getRoleName())
//				      .append(",");
//			}
//			
//			d.addOtherField("roles", roleBuffer.toString());
//			
//			List<Staff> staffs = staffService.listDepartmentStaffs(departmentId);
//			StringBuffer staffBuffer = new StringBuffer();
//			
//			for(Staff s : staffs){
//				staffBuffer.append(s.getLoginName())
//				      .append(",");
//			}
//			
//			d.addOtherField("staffs", staffBuffer.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void listDepartment(){
		Department dept = new Department();
		
		List<Department> list;
		
		try {
			list = departmentService.listDepartment(dept);
			
			System.out.println("list Department ok!");
//			System.out.println("pageSize:"+ dept.getPageSize());
//			System.out.println("totalSize:"+ dept.getTotal());
			
			printDepartment(list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public void updateDepartmentRoles(){
		String departmentId = "1";
		
		List<String> roleIds = new ArrayList<String>();
		roleIds.add("100012");
		
		
		try {
//			departmentService.updateDepartmentRoles(departmentId, roleIds);
			
			System.out.println("update department Roles ok!");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void buildDepartmentTree(){
		
		try {
			List<TreeNode> tree = departmentService.buildDepartmentTree();
			
			toJson(tree);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void listDepartmentRoles(){
		Long deptId = new Long(-998);
		
		try {
			Page<Role> page = departmentService.listDepartmentRoles(deptId);
			
			System.out.println(page.getDatas().size());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void printDepartment(List<Department> list){
		System.out.println("list total size:" + list.size());
		
		for(Department d : list){
			System.out.print("departmentId=" + d.getDepartmentId() + "  ");
			System.out.print("deptName=" + d.getDepartmentName() + "  ");
			System.out.print("desc=" + d.getDepartmentDesc() + "  ");
			System.out.println();
		}
	}
	
	

}
