package com.hichlink.easyweb.portal.common.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.hichlink.easyweb.core.pagination.mybatis.pager.Page;
import com.hichlink.easyweb.core.util.StringTools;
import com.hichlink.easyweb.portal.common.dao.DepartmentDao;
import com.hichlink.easyweb.portal.common.dao.RoleDao;
import com.hichlink.easyweb.portal.common.dao.StaffDao;
import com.hichlink.easyweb.portal.common.entity.Department;
import com.hichlink.easyweb.portal.common.entity.Role;
import com.hichlink.easyweb.portal.common.entity.SecStaffDepartmentRoleKey;
import com.hichlink.easyweb.portal.common.entity.Staff;
import com.hichlink.easyweb.portal.common.entity.StaffExtendProperty;
import com.hichlink.easyweb.portal.common.service.StaffService;
import com.hichlink.easyweb.portal.common.util.PasswordAdapter;

@Service("staffService")
public class StaffServiceImpl implements StaffService{

	@Autowired
	private StaffDao staffDao;
	
	@Autowired
	private RoleDao roleDao;
	
	@Autowired
	private DepartmentDao departmentDao;
	
	private boolean isStaffEmailExsit(String email){
		Map<String,Object> queryParams = new HashMap<String,Object>();
		queryParams.put("email", email);
    	return findStaffByMap(queryParams) != null;
	}
	private boolean isStaffMobileExsit(String mobile){
		Map<String,Object> queryParams = new HashMap<String,Object>();
		queryParams.put("mobile", mobile);
    	return findStaffByMap(queryParams) != null;
	}
	@Transactional(rollbackFor=Exception.class) 
	public void createStaff(Staff staff) throws Exception{
		_createStaff(staff);
	}
	private void _createStaff(Staff staff) throws Exception{

        	//是否已有同名帐号
            if(staffDao.findStaffByLoginName(staff.getLoginName()) != null){
            	throw new Exception("此帐号已注册，系统不允许重复注册!");
            }
            if (StringTools.isNotEmptyString(staff.getMobile()) && isStaffMobileExsit(staff.getMobile())){
            	throw new Exception("手机号码已被注册，系统不允许重复注册!");
            }
            if (StringTools.isNotEmptyString(staff.getEmail()) && isStaffEmailExsit(staff.getEmail())){
            	throw new Exception("邮箱地址已被注册，系统不允许重复注册!");
            }
	
            //成员信息入库
        	staffDao.insertStaff(staff);
        	
        	PasswordAdapter pa = new PasswordAdapter(staff);
        	//设置密码
        	staff.setPassword(pa.encryptPassword());
        	
        	// 为了和以前的密码加密方式兼容，必须要先入库，取得staffId之后再加密获得加密密码
        	// 然后再把加密密码更新到数据库表中
        	staffDao.updateStaffPassword(staff);
        	String roles = (String)staff.getOthers().get("roles");
        	if (StringTools.isNotEmptyString(roles)){
        		Map<String,Object> params = new HashMap<String,Object>();
        		params.put("departmentId", staff.getDepartmentId());
        		String[] roleList = roles.split(",");
        		for (int i = 0; i < roleList.length; i++){
        			if (StringTools.isNotEmptyString(roleList[i])){
        				params.put("staffId", staff.getStaffId());
        				params.put("roleId", roleList[i]);
        				staffDao.insertStaffRoles(params);
        			}
        		}
        	}
            // 创建成员扩展属性
        	for(Map.Entry<String, String> entry : staff.getExtendProperties().entrySet()){
        		StaffExtendProperty property = new StaffExtendProperty();
        		property.setStaffId(staff.getStaffId());
        		property.setProperty(entry.getKey());
        		property.setValue(entry.getValue());
        		
        		staffDao.insertStaffExtendProperties(property);
        	}
            
            // 记录密码历史         
	}
	@Override
	@Transactional(rollbackFor=Exception.class) 
	public void createStaff(Staff staff, String roleIds) throws Exception {
		if (staff.getDepartmentId() == null) {
			throw new IllegalArgumentException("组织ID不能为空。");
		}
		if (departmentDao.findDepartment(staff.getDepartmentId()) == null) {
			throw new IllegalArgumentException("通过departmentId=" + staff.getDepartmentId() + "，查询不到组织。");
		}
		_createStaff(staff);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("departmentId", staff.getDepartmentId());
		String[] roleIdArray = roleIds.split(",");
		for (String roleId : roleIdArray) {
			if (StringUtils.isEmpty(roleId)) {
				continue;
			}
			if (roleDao.findRole(Long.valueOf(roleId)) == null) {
				throw new IllegalArgumentException("通过roleId=" + roleId + "，查询不到角色信息。");
			}
			params.put("staffId", staff.getStaffId());
			params.put("roleId", roleId);
			staffDao.insertStaffRoles(params);
		}
	}
	/**
     * 更新成员.
     *
     * @param staff 成员对象
     */
	 @Transactional(rollbackFor=Exception.class) 
     public void updateStaff(Staff staff) throws Exception{
		 Staff oldStaff = staffDao.findStaff(staff.getStaffId());
		 if (null == oldStaff){
			 throw new Exception("修改的用户不存在!");
		 }
		 if (StringTools.isNotEmptyString(staff.getMobile()) && !staff.getMobile().equalsIgnoreCase(oldStaff.getMobile()) && isStaffMobileExsit(staff.getMobile())){
         	throw new Exception("手机号码已被注册，系统不允许重复注册!");
         }
         if (StringTools.isNotEmptyString(staff.getEmail()) && !staff.getEmail().equalsIgnoreCase(oldStaff.getEmail()) && isStaffEmailExsit(staff.getEmail())){
         	throw new Exception("邮箱地址已被注册，系统不允许重复注册!");
         }
    	 staffDao.updateStaff(staff);
     }
	 
	 /**
      * 删除成员（逻辑删除，只修改成员状态）.
      *
      * @param staffId 成员ID
      */
	 @Transactional(rollbackFor=Exception.class) 
     public void deleteStaffs(Long[] staffIds) throws Exception{
        
		if(staffIds == null){
			throw new Exception("参数非法，不能为空");
		}
        for(Long staffId : staffIds){
			// 删除用户的角色信息
			staffDao.deleteStaffRoles(staffId);
			
			staffDao.deleteStaff(staffId);
        }
		
		// 删除用户信息 与组织管理删除组织同时删除用户时统一，置department_id 为null
//		staffDao.deleteStaffCauseByDeleteDepartment(staffId);
	 }
	 
	 /**
      * 获取成员信息.
      *
      * @param staffId 成员ID
      * @return 成员对象
      */
	 @Transactional(rollbackFor=Exception.class) 
     public Staff findStaff(Long staffId){
    	 Staff staff = staffDao.findStaff(staffId);
    	 
    	 if(staff != null){
    		 List<StaffExtendProperty> properties = staffDao.listStaffExtendProperties(staffId);
    		 
    		 for(StaffExtendProperty p : properties){
    			 staff.addExtendProperty(p.getProperty(), p.getValue());
    		 }
    		 
    		 if(staff.getDepartmentId() != null){
    			 staff.setDepartment(departmentDao.findDepartment(staff.getDepartmentId()));
    		 }
    	 }
    	 
    	 return staff;
     }
	 
	 /**
      * 根据登录名获取成员，不包括已删除的
      *
      * @param loginName 登录名
      * @return 成员对象
      */
	 @Transactional(rollbackFor=Exception.class) 
     public Staff findStaffByLoginName(String loginName) throws Exception{
		 
		 if(loginName == null){
			 throw new Exception("登录名["+loginName+"]不能为空.");
		 }
		 
		 Staff staff = staffDao.findStaffByLoginName(loginName);
		 
		 if(staff != null){
    		 List<StaffExtendProperty> properties = staffDao.listStaffExtendProperties(staff.getStaffId());
    		 
    		 for(StaffExtendProperty p : properties){
    			 staff.addExtendProperty(p.getProperty(), p.getValue());
    		 }
    		 
    		 if(staff.getDepartmentId() != null){
    			 staff.setDepartment(departmentDao.findDepartment(staff.getDepartmentId()));
    		 }
    	 }
		 
		 return staff;
	 }
     
     /**
      * 获取成员列表.
      *
      * @param staff 成员查询条件
      * @return 成员列表
      */
	 @Transactional(rollbackFor=Exception.class) 
     public Page<Staff> listStaff(Page<Staff> page) throws Exception{
    	 
    	 if(page == null){
    		 throw new Exception("查询参数不能为空");
    	 }

//    	 Page<Staff> page = new Page<Staff>(staffDao.listStaff(staff), staff);
//    	 Page<Staff> page = new Page<Staff>(null, null);
    	 
    	 page.setDatas(staffDao.listStaff(page));
    	 
    	 return page;
     }
     
	 /**
	  * 修改用户密码
	  */
	 @Transactional(rollbackFor=Exception.class) 
     public void changePassword(String loginName, String oldPassword, String newPassword) 
    		 throws Exception
	 {
		 
		if (loginName == null || oldPassword == null || newPassword == null) {
			throw new IllegalArgumentException("用户名、旧密码或新密码没有设置！");
		}
		
		if (oldPassword.equals(newPassword)) {
            throw new Exception("旧密码和新密码不能相同！");
        }
 
		// 通过登录名找到原先的用户
		Staff staff = this.staffDao.findStaffByLoginName(loginName);
		if (staff == null) {
			throw new Exception("找不到对应的成员【loginName=" + loginName + "】");
		}
        
        if (!staff.getPassword().equals(buildStaffPassword(staff, oldPassword))) {
            throw new Exception("旧密码不正确！");
        }

        // 前N次中没有相同密码，允许修改
        staff.setPassword(buildStaffPassword(staff, newPassword));

        // 设置密码过期时间
//        staff.setPasswordExpireDate(getPasswordExpireDate());
        
        //如果是初创用户，第一次登陆时修改密码，需要修改用户的状态
        //如果是密码过期锁定，修改密码成功后需要修改用户的状态
        if (staff.getStatus() == Staff.Status.INITIAL
        		|| staff.getStatus() == Staff.Status.PASSWORD_EXPIRED) {
            staff.setStatus(Staff.Status.NORMAL);
        }

        staffDao.updateStaffPassword(staff);
	 }
	 
	 /**
	  * 
	  */
	 @Transactional(rollbackFor=Exception.class) 
	 public void resetPassword(String loginName, String newPassword) 
			 throws Exception
	 {
		 
		if (loginName == null || newPassword == null) {
			throw new IllegalArgumentException("用户名或新密码没有设置！");
		}

		Staff staff = this.staffDao.findStaffByLoginName(loginName);
		if (staff == null) {
			throw new Exception("找不到对应的成员【loginName=" + loginName + "】");
		}

		// 重置密码时 增加密码的校验
		staff.setPassword(buildStaffPassword(staff, newPassword));

		// 设置密码过期时间
		// staff.setPasswordExpireDate(getPasswordExpireDate());

		// 重置密码，同时将成员状态置为INITIAL，强制成员下次登录时修改密码
		staff.setStatus(Staff.Status.INITIAL);

		staffDao.updateStaffPassword(staff);
	        
	 }
	 
	 
	 /**
      * 锁定成员
      * @param staffId
      */
	 @Transactional(rollbackFor=Exception.class) 
     public void lockStaff(Long staffId) throws Exception{
    	 
    	 Staff staff = staffDao.findStaff(staffId);
         if (staff == null) {
             throw new Exception("用户["+staffId+"]没有找到");
         }
         
         // 不是正常或者初创状态不允许锁定
         if(staff.getStatus()==Staff.Status.INITIAL 
        		 || staff.getStatus()==Staff.Status.NORMAL){


        	 staff.setStatus(Staff.Status.LOCKED);
             
        	 staffDao.updateStaff(staff);
         }else{
             throw new Exception("用户状态不是初创或者正常状态,不允许锁定.");
         }
         
     }
     
     
     /**
      * 解锁成员.
      *
      * @param staffId
      */
	 @Transactional(rollbackFor=Exception.class) 
     public void unlockStaff(Long staffId) throws Exception{
    	 
    	 Staff staff = staffDao.findStaff(staffId);
         if (staff == null) {
             throw new Exception("用户["+staffId+"]没有找到");
         }
         
    	 staff.setStatus(Staff.Status.NORMAL);
         
         staffDao.updateStaff(staff);
     }
	 
	 /**
	  * 
	  */
	 @Transactional(rollbackFor=Exception.class) 
	 public void updateStaffRoles(Long staffId, List<Long> roleIds) throws Exception{
		 
		 Staff staff = staffDao.findStaff(staffId);
         if (staff == null) {
             throw new Exception("用户["+staffId+"]没有找到");
         }
		
         if(staff.getDepartmentId() == null){
        	 staff.setDepartmentId(new Long(-1));
         }
		// 先删除staff role
		staffDao.deleteStaffRoles(staff.getStaffId());

		for (Long roleId : roleIds) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("staffId", staff.getStaffId());
			map.put("departmentId", staff.getDepartmentId());
			map.put("roleId", roleId);

			staffDao.insertStaffRoles(map);
		}
	    	
	 }
	 
	 @Transactional(rollbackFor=Exception.class) 
	 public void insertStaffRole(Long staffId, Long roleId) throws Exception{
		 
		 Staff staff = staffDao.findStaff(staffId);
         if (staff == null) {
             throw new Exception("用户["+staffId+"]没有找到");
         }
		
         if(staff.getDepartmentId() == null){
        	 staff.setDepartmentId(new Long(-1));
         }

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("staffId", staff.getStaffId());
		map.put("departmentId", staff.getDepartmentId());
		map.put("roleId", roleId);

		staffDao.insertStaffRoles(map);	
	 }
	 
	 @Transactional(rollbackFor=Exception.class) 
	 public void deleteStaffRole(Long staffId, Long roleId) throws Exception{
		 
		 Staff staff = staffDao.findStaff(staffId);
         if (staff == null) {
             throw new Exception("用户["+staffId+"]没有找到");
         }
		
         if(staff.getDepartmentId() == null){
        	 staff.setDepartmentId(new Long(-1));
         }

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("staffId", staff.getStaffId());
		param.put("roleId", roleId);

		staffDao.deleteStaffRolesByStaffIdAndRoleId(param);
	 }
	 
	 /**
      * 获取组织的成员列表.
      *
      * @param departmentId 组织ID
      * @return 成员列表
      */
     public List<Staff> listDepartmentStaffs(Long departmentId) throws Exception{

		 Page<Staff> page = new Page<Staff>();
		 
		 page.addParam("departmentId", departmentId.toString());
		 page.addParam("noAdmin", "1");
		 
		 page.setRows(999999);
		 
		 
		 return staffDao.listStaff(page);
	 }
     
     
     /**
      * 通过departmentId获取该部门以及下级部门的所有用户
      * @param departmentId
      * @return
      * @throws Exception
      */
     public List<Staff> listDepartmentAllStaffs(Long departmentId,String keyword, String domain) throws Exception{
    	 
    	 if(departmentId == null)
    		 throw new Exception("departmentId不能为空");
    	 
    	 Page<Staff> page = new Page<Staff>();
    	 
    	 Map<String,Object> param = new HashMap<String,Object>();
    	 
    	 param.put("departmentId", departmentId);
    	 
    	 if(domain != null){
    		 param.put("domain", domain); 
    	 }
    	 
    	 List<Department> departs = departmentDao.listSelfAndSubDepartmentByDepartmentId(param);
     
    	 StringBuffer ids = new StringBuffer();
    	 for(Department d : departs){
    		 ids.append(d.getDepartmentId().toString()).append(",");
    	 }
    	 
    	 if(ids.length() > 0){
    		 ids.delete(ids.length() - 1, ids.length());
    	 }else{
    		 return new ArrayList<Staff>();
    	 }
    	 
    	 page.addParam("departmentIds", ids.toString());
    	 
    	 if(keyword != null && !"".equals(keyword.trim())){
    		 page.addParam("keyword", keyword);
    	 }
    	 page.setRows(999999);
    	 
    	 return staffDao.listStaff(page);
     }
     
     public List<Staff> listStaffsByDomain(String domain,String keyword) throws Exception{
    	 
    	 if(domain == null)
    		 throw new Exception("domain不能为空");
    	 
    	 Department dept = new Department();
    	 dept.setDomain(domain);
    	 
    	 List<Department> departs = departmentDao.listDepartment(dept);
     
    	 StringBuffer ids = new StringBuffer();
    	 for(Department d : departs){
    		 ids.append(d.getDepartmentId().toString()).append(",");
    	 }
    	 
    	 if(ids.length() > 0){
    		 ids.delete(ids.length() - 1, ids.length());
    	 }else{
    		 return new ArrayList<Staff>();
    	 }
    	 
    	 Page<Staff> page = new Page<Staff>();
    	 page.addParam("departmentIds", ids.toString());
    	 
    	 if(keyword != null && !"".equals(keyword.trim())){
    		 page.addParam("keyword", keyword);
    	 }
    	 
    	 page.setRows(999999);
    	 
    	 return staffDao.listStaff(page);
     }
	 
	 /**
      * 根据角色查用户
      *
      * @param role 角色
      * @return 成员列表
      */
	 @Transactional(rollbackFor=Exception.class) 
     public List<Staff> listStaffByRole(Long roleId)
    		 throws Exception{
		 
		 return staffDao.listStaffByRole(roleId);
     }
	 
	 /**
	  * 密码加密
	  * @param staff
	  * @param password
	  * @return
	  */
	 private String buildStaffPassword(Staff staff, String password){
		 
		 PasswordAdapter pa = new PasswordAdapter(staff);
		 
//		 return PasswordUtil.buildPasswordWithoutVerify(staff.getLoginName(), password);
		 
		 staff.setPassword(password);
		 
		 return pa.encryptPassword();
	 }
	 
	 
	 public Page<Role> listStaffRoles(Long staffId) throws Exception{
		 
		 Page<Role> page = new Page<Role>();
		 page.setRows(99999);
		 Staff staff = staffDao.findStaff(staffId);
		 if (null != staff && staff.getDepartmentId() != null){
			 page.addParam("departmentId", staff.getDepartmentId().toString());
		 }
		 List<Role> allRoles = roleDao.listRole(page);

		 //
		 Map<Long, Role> roleMap = createRoleMap(roleDao.listStaffRoles(new Long(staffId)));
		 
		 for(Role r: allRoles){
			 if(roleMap.containsKey(r.getRoleId())){
				 r.addOtherField("check", "true");
			 }else{
				 r.addOtherField("check", "false");
			 }
		 }
		 
//		 Page<Role> page = new Page<Role>();
		 page.setDatas(allRoles);
		 
		 return page;
//		 return allRoles;
	 }
	 
	 
	/* public void updateStaffDepartment(String departmentId, String staffids) throws Exception{
		 Map<String,String> map = new HashMap<String,String>();
			
		 map.put("departmentId", departmentId);
		 map.put("staffIds", staffids);
		
		 staffDao.updateStaffDepartment(map);
	 }*/
	 
	 private Map<Long, Role> createRoleMap(List<Role> list){
		 Map<Long, Role> roleMap = new HashMap<Long,Role>();
		 
		 for(Role r: list){
			 roleMap.put(r.getRoleId(), r);
		 }
		 
		 return roleMap;
	 }
	 @Transactional(rollbackFor=Exception.class) 
	public void updateStaffDepartment(Long departmentId, String staffIds)
			throws Exception {
		 staffDao.clearStaffDepartment(departmentId); 
		 Map<String,Object> params = new HashMap<String,Object>();
		params.put("departmentId", departmentId);
		 if (!StringUtils.isEmpty(staffIds)){
			 	String[] arr = staffIds.split(",");
				params.put("staffIds", arr);
				staffDao.updateStaffDepartmentByStaffIds(params);
		 }else{
			 staffDao.clearStaffDepartment(departmentId);
		 }
		 staffDao.deleteStaffRolesByDepartmentIdAndNotInStaffIds(params);
	}
	 
	 
	 /**
	     * 获取密码过期时间
	     * @return
	     */
//    private Date getPasswordExpireDate(){
//    	
////	    	int passwordExpirePeriod = SecurityConfig.getConfig().getInt("password-history.password-expire-period", 30);
//    	int passwordExpirePeriod = SecurityConfig.getSecurityConfig().getPasswordExpirePeriod();
//    	
//        // Date passwordExpireDate = DateUtils.addDays(nowDate, passwordExpirePeriod);
//        // 使用apache-common-lang2.3的DateUtils.addDays方法在OC4J环境出问题,改为原始方法计算日期
//        Calendar cal = Calendar.getInstance();  
//        cal.add(Calendar.DATE, passwordExpirePeriod);   
//        
//        return cal.getTime();   
//       
//    }

	public List<SecStaffDepartmentRoleKey> listRoleByStaffIds(Long departmentId, String staffIds) throws Exception {
		Map<String,Object> params = new HashMap<String,Object>();
    	params.put("departmentId", departmentId);
    	params.put("staffIds", staffIds.split(","));
		return staffDao.listRoleByStaffIds(params);
	}
	@Transactional(rollbackFor=Exception.class) 
	public void updateStaffRolesDepartment(Long departmentId,
			String staffIds, String staffIdRoles) throws Exception {
		String[] staffIdArr = staffIds.split(",");
		for (String staffId : staffIdArr){
			staffDao.deleteStaffRoles(Long.parseLong(staffId));
		}
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("departmentId", departmentId);
		String[] staffIdRoleArr = staffIdRoles.split(",");
		for (String staffIdRole : staffIdRoleArr){
			if (StringUtils.isEmpty(staffIdRole))continue;
			String[] roles = staffIdRole.split("\\|");
			if (roles.length == 2){
				params.put("staffId", roles[0]);
				params.put("roleId", roles[1]);
				staffDao.insertStaffRoles(params);
			}
		}
	}

	@Override
	public List<Staff> listStaffs(Long departmentId, String keyword)
			throws Exception {
		
		Page<Staff> page = new Page<Staff>();
		 if (null != departmentId){
			 page.addParam("departmentId", departmentId.toString());
		 }
		 page.setRows(999999);
		 page.addParam("keyword", keyword);
		 
		 return staffDao.listStaff(page);
	}
	public Staff findStaffByMap(Map<String,Object> params){
		List<Staff> staffs = staffDao.findStaffByMap(params);
		if (null != staffs && !staffs.isEmpty()){
			return staffs.get(0);
		}
		return null;
	}
	@Override
	public List<StaffExtendProperty> listStaffExtendProperties(Long staffId) {
		return staffDao.listStaffExtendProperties(staffId);
	}
	@Override
	public void insertStaffExtendProperty(StaffExtendProperty data) {
		staffDao.insertStaffExtendProperties(data);
	}
}
