package com.aspire.webbas.common.util;

import com.aspire.webbas.configuration.config.ConfigurationHelper;
import com.aspire.webbas.portal.common.dao.StaffDao;
import com.aspire.webbas.portal.common.entity.Staff;
import com.aspire.webbas.portal.common.util.PasswordAdapter;
import com.aspire.webbas.test.BaseTest;

public class PasswordAdapterTest extends BaseTest{


	private StaffDao dao;
	
	public void test(){
		
		dao = (StaffDao)getBean("staffDao");
		
		assertNotNull("staffDao is null",dao);
		
		
		encryptPassword();
	}
	
	public void encryptPassword(){
		
		ConfigurationHelper.setBasePath("C:\\hyb\\dev\\apache-tomcat-7.0.33\\bin\\config");
		
		String loginName = "admin";
		String password = "aaa111";
		
		Staff staff = dao.findStaffByLoginName(loginName);
		
		assertNotNull("find staff null", staff);
		
//		PasswordAdapter current = new PasswordAdapter(staff);
		
		Staff param = new Staff();
		param.setStaffId(staff.getStaffId());
		param.setLoginName(loginName);
		param.setPassword(password);
		
		PasswordAdapter login = new PasswordAdapter(param);
		
		
		System.out.println("current pwd:" + staff.getPassword());
		
		System.out.println("login pwd:" + login.encryptPassword());
		
		System.out.println("equal:" + staff.getPassword().equals(login.encryptPassword()));
	}
}
