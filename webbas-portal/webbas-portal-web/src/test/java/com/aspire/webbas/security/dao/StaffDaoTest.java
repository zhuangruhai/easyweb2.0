package com.aspire.webbas.security.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aspire.webbas.core.pagination.mybatis.pager.Page;
import com.aspire.webbas.portal.common.dao.StaffDao;
import com.aspire.webbas.portal.common.entity.Staff;
import com.aspire.webbas.portal.common.entity.StaffExtendProperty;
import com.aspire.webbas.test.BaseTest;

public class StaffDaoTest extends BaseTest{
	
	private StaffDao dao;
	
	public void test(){
		dao = (StaffDao)getBean("staffDao");
		
		assertNotNull("staffDao is null",dao);
		
//		insertStaff();
		
//		updateStaff();
		
//		deleteStaff();
		
//		removeStaff();
		
//		findStaff();
		
//		findStaffByLoginName();
		
//		findStaffByNameAndPassword();
		
		insertStaffExtendProperties();
		
//		deleteStaffExtendProperties();
		
//		listStaffExtendProperties();
		
//		listStaff();
		
//		insertStaffRoles();
		
//		insertStaffRolesByKey();
		
//		deleteStaffRoles();
		
		
//		listStaffByRole();
		
//		clearStaffDepartment();
		
//		updateStaffDepartment();
		
	}
	
	public void insertStaff(){
		Staff s = new Staff();
		
		s.setLoginName("abc");
		s.setRealName("abc");
		s.setPassword("abc");
		s.setStatus(Staff.Status.NORMAL);
		s.setSex(Staff.Sex.MALE);
		s.setCreateUser("system");
		s.setMobile("1234");
		
		dao.insertStaff(s);
		
		System.out.println("insert staff[id="+s.getStaffId()+"] ok");
	}
	
	public void updateStaff(){
		Long staffId = new Long(1010240);
		
		Staff s = dao.findStaff(staffId);
		
		assertNotNull("find staff[id="+staffId+"] is null", s);
		
		s.setRealName("张三管理");
//		s.setDepartmentId("-999");
		s.setPassword("abc");
		s.setEmail("kfdsjal");
		
		dao.updateStaff(s);
		
		System.out.println("update staff[id="+s.getStaffId()+"] ok");
	}
	
	public void deleteStaff(){
		Long staffId = new Long(41);
		
		dao.deleteStaff(staffId);
		
		System.out.println("delete staff[id="+staffId+"] ok");
	}
	
	public void removeStaff(){
		Long staffId = new Long(41);
		
		dao.removeStaff(staffId);
		
		System.out.println("remove staff[id="+staffId+"] ok");
	}
	
	public void findStaff(){
		Long staffId = new Long(41);
		
		Staff s = dao.findStaff(staffId);
		
		assertNotNull("find staff[id="+staffId+"] is null", s);
		
		System.out.println("find staff[id="+staffId+"] ok");
	}
	
	public void findStaffByLoginName(){
		String loginName = "abc2";
		
		Staff s = dao.findStaffByLoginName(loginName);
		
		assertNotNull("find staff[loginName="+loginName+"] is null", s);
		
		System.out.println("find staff[loginName="+loginName+"] ok");
	}
	
	public void findStaffByNameAndPassword(){
		Staff staff = new Staff();
		
		staff.setLoginName("13688888880");
		staff.setPassword("lL7M+BVeQNwTmwi/mbf4kw==");
		
		Staff s = dao.findStaffByNameAndPassword(staff);
		
		assertNotNull("find staff[loginName="+staff.getLoginName()+"] is null", s);
		
		System.out.println("find staff[loginName="+staff.getLoginName()+"] ok");
	}
	
	public void insertStaffExtendProperties(){
		
		StaffExtendProperty p = new StaffExtendProperty();
		p.setStaffId(new Long(-999));
		p.setProperty("p1");
		p.setValue("1");
		
		dao.insertStaffExtendProperties(p);
		
		System.out.println("insert staff extend property ok.");
	}
	
	public void deleteStaffExtendProperties(){
		Long staffId = new Long(-999);
		
		dao.deleteStaffExtendProperties(staffId);
		
		System.out.println("delete staff extend properties ok.");
	}
	
	public void listStaffExtendProperties(){
		Long staffId = new Long(-999);
		
		List<StaffExtendProperty> properties = dao.listStaffExtendProperties(staffId);
		
		System.out.println("size:" + properties.size());
		
		for(StaffExtendProperty se : properties){
			System.out.println(se.getProperty() + " : " + se.getValue());
		}
	}
	
	public void listStaff(){
//		Staff s = new Staff();
		
//		s.openPagination();
		
//		s.setRealName("管理");
//		s.addOtherField("keyword", "sims");
//		s.setDepartmentId("1");
//		
//		List<Staff> list = dao.listStaff(s);
//		
//		System.out.println("list staff ok!");
//		System.out.println("pageSize:"+ s.getPageSize());
//		System.out.println("totalSize:"+ s.getTotal());
//		
//		printStaff(list);
		Map<String,Object> params = new HashMap<String,Object>();
		
//		params.put("status", "NORMAL");
//		params.put("keyword", "张");
		params.put("departmentIds", "-998,1010243");
		
		Page<Staff> page = new Page<Staff>();
		page.setParams(params);
		
		List<Staff> pages = dao.listStaff(page);
		
		System.out.println("list staff ok!");
		System.out.println("pageSize:"+ page.getPageSize());
		System.out.println("totalSize:"+ page.getTotal());
		
		printStaff(pages);
	}
	
	public void insertStaffRoles(){
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("staffId", "1");
		map.put("departmentId", "-999");
		map.put("roleId", "100011");
		
		dao.insertStaffRoles(map);
		
		System.out.println("insert staff roles ok");
	}
	
	public void insertStaffRolesByKey(){
		Map<String,String> map = new HashMap<String,String>();
		map.put("staffId", "1");
		map.put("departmentId", "-999");
		map.put("roleKey", "1002");
		
		dao.insertStaffRolesByKey(map);
		
		System.out.println("insert staff roles ok");
	}
	
	public void deleteStaffRoles(){
		Long staffId = new Long(41);
		
		dao.deleteStaffRoles(staffId);
		
		System.out.println("deleteStaffRoles[staffId="+staffId+"] ok");
	}
	
	public void listStaffByRole(){
		Long roleId = new Long(100000);
		
		List<Staff> list = dao.listStaffByRole(roleId);
		
		printStaff(list);
	}
	
	public void clearStaffDepartment(){
		Long departmentId = new Long(1);
		
		dao.clearStaffDepartment(departmentId);
		
		System.out.println("clearStaffDepartment ok!");
	}
	
	public void updateStaffDepartment(){
		Map<String,String> map = new HashMap<String,String>();
		
		map.put("departmentId", "-998");
		map.put("staffIds", "'1010252'");
		
		dao.updateStaffDepartment(map);
		
		System.out.println("updateStaffDepartment ok");
	}
	
	private void printStaff(List<Staff> list){
		System.out.println("total list size:" + list.size());
		for(Staff s : list){
			System.out.print("staffId=" + s.getStaffId() + "  ");
			System.out.print("loginName=" + s.getLoginName() + "  ");
			System.out.print("deptId=" + s.getDepartmentId() + "  ");
			System.out.println();
		}
	}

}
