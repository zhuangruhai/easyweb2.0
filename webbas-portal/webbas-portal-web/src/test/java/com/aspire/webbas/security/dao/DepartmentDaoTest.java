package com.aspire.webbas.security.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aspire.webbas.portal.common.dao.DepartmentDao;
import com.aspire.webbas.portal.common.entity.Department;
import com.aspire.webbas.test.BaseTest;

public class DepartmentDaoTest extends BaseTest{

	private DepartmentDao dao;
	
	public void test(){
		dao = (DepartmentDao)getBean("departmentDao");
		
		assertNotNull("DepartmentDao is null",dao);
		
//		insertDepartment();
		
//		updateDepartment();
		
//		deleteDepartment();
		
//		findDepartment();
		
//		listDepartment();
		
//		insertDepartmentRoles();
		
//		deleteDepartmentRoles();
		
		
		listSelfAndSubDepartmentByDepartmentId();
	}
	
	public void insertDepartment(){
		Department dept = new Department();
		
		dept.setDepartmentName("组织2");
		dept.setDepartmentDesc("组织2");
		dept.setAddSub("1");
		dept.setCreateUser("admin");
		
		dao.insertDepartment(dept);
		
		System.out.println("insert department[id="+dept.getDepartmentId()+"] ok");
	}
	
	public void updateDepartment(){
		Long departmentId = new Long(1010241);
		
		Department dept = dao.findDepartment(departmentId);
		
		assertNotNull("find department[id="+departmentId+"] is null", dept);
		
		dept.setDepartmentDesc("介绍");
		
		dao.updateDepartment(dept);
		
		System.out.println("update department[id="+departmentId+"] ok");
	}
	
	public void deleteDepartment(){
		Long departmentId = new Long(1010240);
		
		dao.deleteDepartment(departmentId);
		
		System.out.println("delete department[id="+departmentId+"] ok");
	}
	
	public void findDepartment(){
		
		Long departmentId = new Long(2);
		
		Department d = dao.findDepartment(departmentId);
		
		assertNotNull("find department[id="+departmentId+"] is null", d);
		
		System.out.println("find department[id="+departmentId+"] ok");
	}
	
	public void listDepartment(){
		Department d = new Department();
		
		
//		d.setDepartmentName("组织");
	    d.addOtherField("keyword", "组织");
		
		List<Department> list = dao.listDepartment(d);
		
		System.out.println("list Department ok!");
//		System.out.println("pageSize:"+ d.getPageSize());
//		System.out.println("totalSize:"+ d.getTotal());
		
		printDepartment(list);
	}
	
	public void insertDepartmentRoles(){
		Map<String, Object> map = new HashMap<String,Object>();
		
		map.put("departmentId", "1");
		map.put("roleId", "100021");
		
		dao.insertDepartmentRoles(map);
		
		System.out.println("insert DepartmentRoles ok");
	}
	
	public void deleteDepartmentRoles(){
		Long departmentId = new Long(2);
		
		dao.deleteDepartmentRoles(departmentId);
		
		System.out.println("delete DepartmentRoles ok");
	}
	
	public void listSelfAndSubDepartmentByDepartmentId(){
		Long departmentId = new Long(-998);
		
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("departmentId", departmentId);
		
		List<Department> list = dao.listSelfAndSubDepartmentByDepartmentId(param);
		
		printDepartment(list);
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
