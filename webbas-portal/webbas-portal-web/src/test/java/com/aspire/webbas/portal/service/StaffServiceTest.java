package com.aspire.webbas.portal.service;

import java.util.ArrayList;
import java.util.List;

import com.aspire.webbas.core.pagination.mybatis.pager.Page;
import com.aspire.webbas.portal.common.entity.Role;
import com.aspire.webbas.portal.common.entity.Staff;
import com.aspire.webbas.portal.common.service.StaffService;
import com.aspire.webbas.test.BaseTest;

public class StaffServiceTest extends BaseTest{

	private StaffService staffService;
	
	public void test(){
		staffService = (StaffService)getBean("staffService");
		
		assertNotNull("staffService is null", staffService);
		
//		createStaff();
		
//		deleteStaff();
		
//		findStaff();
		
		findStaffByLoginName();
		
//		listStaff();
		
//		changePassword();
		
//		resetPassword();
		
//		lockStaff();
		
//		unlockStaff();
		
//		updateStaffRoles();
		
//		listDepartmentStaffs();
		
//		listDepartmentAllStaffs();
		
//		listStaffByRole();
		
//		listStaffRoles();
		
	}
	
	public void createStaff(){
		
		Staff s = new Staff();
		
		s.setLoginName("abc2");
		s.setRealName("abc2");
		s.setPassword("aaa111");
		s.setStatus(Staff.Status.NORMAL);
		s.setSex(Staff.Sex.MALE);
		s.setCreateUser("system");
		
		
		try {
			staffService.createStaff(s);
			
			System.out.println("create staff ok!");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void deleteStaff(){
		Long[] staffIds = {new Long(41)};
		
		try {
			staffService.deleteStaffs(staffIds);
			
			System.out.println("delete staff[id="+staffIds+"] ok");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void findStaff(){
		Long staffId = new Long(-999);
		
		try {
			Staff staff = staffService.findStaff(staffId);
			
			System.out.println("delete staff[id="+staffId+"] ok");
			
			System.out.println("extend properties:" + staff.getExtendProperties().size());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void findStaffByLoginName(){
		String loginName = "admin";
		
		try {
			Staff staff = staffService.findStaffByLoginName(loginName);
			
			System.out.println("delete staff[name="+loginName+"] ok");
			
			System.out.println("extend properties:" + staff.getExtendProperties().size());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void listStaff(){
		Staff s = new Staff();
		
		Page<Staff> page = new Page<Staff>();
		
		try {
			page = staffService.listStaff(page);
			
			System.out.println("list staff ok!");
			System.out.println("pageNO:" + page.getPageNo());
			System.out.println("pageSize:"+ page.getPageSize());
			System.out.println("totalSize:"+ page.getTotal());
			System.out.println("records:" + page.getTotal());
			
			printStaff(page.getDatas());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void listStaffRoles(){
		Long staffId = new Long(-999);
		
		try {
			Page<Role> roles = staffService.listStaffRoles(staffId);
			
			for(Role r : roles.getDatas()){
				System.out.println(r.getOthers().get("check"));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void changePassword(){
		String loginName = "abc2",
			   oldPwd = "aaa123",
		       newPwd = "aaa111";
		
		try {
			staffService.changePassword(loginName, oldPwd, newPwd);
			
			System.out.println("change staff[loginName="+loginName+"] password ok!");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void lockStaff(){
		Long staffId = new Long(41);
		
		try {
			staffService.lockStaff(staffId);
			
			System.out.println("lock staff["+staffId+"] ok!");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void unlockStaff(){
		Long staffId = new Long(41);
		
		try {
			staffService.unlockStaff(staffId);
			
			System.out.println("lock staff["+staffId+"] ok!");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void resetPassword(){
		String loginName = "abc2",
		       newPwd = "aaa111";
		
		try {
			staffService.resetPassword(loginName, newPwd);
			
			System.out.println("resect password ok!");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void updateStaffRoles(){
		Long staffId = new Long(41);
		
		List<Long> roleIds = new ArrayList<Long>();
		
		roleIds.add(new Long(100011));
		
		try {
			staffService.updateStaffRoles(staffId, roleIds);
			
			System.out.println("update staff[id="+staffId+"] roles ok");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void listDepartmentStaffs(){
		Long departmentId = new Long(-999);
		
		try {
			List<Staff> list = staffService.listDepartmentStaffs(departmentId);
			
			System.out.println("list department staff");
			
			printStaff(list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void listDepartmentAllStaffs(){
		Long departmentId = new Long(-998);
		String keyword = "admin2";
		
		try {
			List<Staff> list = staffService.listDepartmentAllStaffs(departmentId, keyword, null);
			
			System.out.println("list department all staff");
			
			printStaff(list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void listStaffByRole(){
		Long roleId = new Long(100000);
		
		try {
			List<Staff> list = staffService.listStaffByRole(roleId);
			
			System.out.println("list staff by role:");
			printStaff(list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	private void printStaff(List<Staff> list){
		System.out.println("total list size:" + list.size());
		for(Staff s : list){
			System.out.print("staffId=" + s.getStaffId() + "  ");
			System.out.print("loginName=" + s.getLoginName() + "  ");
			System.out.print("deptId=" + s.getDepartmentId() + "  ");
			System.out.print("sex=" + s.getSexName() + "  ");
			System.out.println("status=" + s.getStatusName());
			System.out.println();
		}
	}
}
