package com.hichlink.easyweb.portal.common.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hichlink.easyweb.portal.common.auth.session.SessionContext;
import com.hichlink.easyweb.portal.common.dao.DepartmentDao;
import com.hichlink.easyweb.portal.common.dao.StaffDao;
import com.hichlink.easyweb.portal.common.entity.Department;
import com.hichlink.easyweb.portal.common.entity.Staff;
import com.hichlink.easyweb.portal.common.entity.StaffExtendProperty;
import com.hichlink.easyweb.portal.common.service.LoginService;
import com.hichlink.easyweb.portal.common.util.PasswordAdapter;


@Service("loginService")
public class LoginServiceImpl implements LoginService{

	private static final Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);
	private static final String CONTRACT_AGREEMENT_KEY = "contractAgreement";
	private static final String CONTRACT_AGREEMENT_VALUE = "1";
	@Autowired
	private StaffDao staffDao;
	
	@Autowired
	private DepartmentDao departmentDao;
	private Staff findStaffByKeyword(String keyword){
		Map<String,Object> queryParams = new HashMap<String,Object>();
		queryParams.put("keyword", keyword);
		List<Staff> staffs = staffDao.findStaffByMap(queryParams);
		if (null != staffs && !staffs.isEmpty()){
			return staffs.get(0);
		}
		return null;
	}
	public void login(String name, String password, HttpSession session) throws Exception{
	
	    	Staff loginStaff = null;
//	        logger.debug("用户["+name+"]进行登录...");
	        
		loginStaff = staffDao.findStaffByLoginName(name);
	    	
	    	if (loginStaff == null) {
	        	//为了避免用户穷举，不明确提示错误信息。
	        	logger.info("用户["+name+"]登录失败,没有找到对应的用户。");
	            throw new Exception("登录名或者密码不正确");
	        }
	    	
	    	// 判断用户状态是否正常
	    	if( !Staff.Status.NORMAL.equals(loginStaff.getStatus()) 
	    			&& !Staff.Status.INITIAL.equals(loginStaff.getStatus())){
	    		throw new Exception("用户状态异常");
	    	}

	    	
	    	//密码过期时要把staff放到sessin中
	        Staff paramStaff = new Staff();
	        paramStaff.setStaffId(loginStaff.getStaffId());
	        paramStaff.setLoginName(name);
	        paramStaff.setPassword(password);
	        
	    	
	        PasswordAdapter loginPwd = new PasswordAdapter(paramStaff);
	        
	        // 登录密码和数据库中的密码不匹配
	        if( !loginStaff.getPassword().equals(loginPwd.encryptPassword()) ){
	        	logger.info("用户["+name+"]登录失败,登录密码不正确。");
	        	throw new Exception("登录名或者密码不正确");
	        }
	        
	    	
	        if(loginStaff.getDepartmentId() != null){
		        Department department = 
		        		departmentDao.findDepartment(loginStaff.getDepartmentId());
	
		        // 把Department放入loginStaff中
		        loginStaff.setDepartment(department);
	        }

	        // 在session中保存登录用户名
	        session.setAttribute(LOGIN_NAME, loginStaff.getLoginName());
	        //如果登录成功，查询用户基本信息，保存在SESSION中
	        session.setAttribute(LOGIN_STAFF, loginStaff);
	        
	        SessionContext.getContext().addSession(session);
	        
	        // 记录日志
	}
	public boolean isReadContractAgreement(List<StaffExtendProperty> staffExtendProperties){
		for (StaffExtendProperty staffExtendProperty : staffExtendProperties){
			if (CONTRACT_AGREEMENT_KEY.equals(staffExtendProperty.getProperty()) && CONTRACT_AGREEMENT_VALUE.equals(staffExtendProperty.getValue())){
				return true;
			}
		}
		return false;
	}
	public StaffExtendProperty setReadContractAgreement(Staff staff){
		StaffExtendProperty staffExtendProperty = new StaffExtendProperty();
		staffExtendProperty.setStaffId(staff.getStaffId());
		staffExtendProperty.setProperty(CONTRACT_AGREEMENT_KEY);
		staffExtendProperty.setValue(CONTRACT_AGREEMENT_VALUE);
		staffDao.insertStaffExtendProperties(staffExtendProperty);
		return staffExtendProperty;
	}
	public void logout(HttpSession session) throws Exception{
		
		System.out.println("注销session");
		
		SessionContext.getContext().deleteSession(session.getId());
		
		// 先注销本容器的session
		session.invalidate();
		
	}
}
