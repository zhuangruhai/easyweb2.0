package com.aspire.webbas.portal.modules.authmgt.web;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aspire.webbas.core.web.BaseController;
import com.aspire.webbas.portal.common.entity.Department;
import com.aspire.webbas.portal.common.entity.Role;
import com.aspire.webbas.portal.common.entity.Staff;
import com.aspire.webbas.portal.common.service.DepartmentService;
import com.aspire.webbas.portal.common.service.RoleService;
import com.aspire.webbas.portal.common.service.StaffService;
import com.aspire.webbas.portal.common.util.StaffUtil;

@Controller
@RequestMapping("/department")
public class DepartmentController extends BaseController{
	private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentController.class);
	@Autowired
	@Qualifier("departmentService")
	private DepartmentService departmentService;
	
	@Autowired
	@Qualifier("roleService")
	private RoleService roleService;
	
	@Autowired
	@Qualifier("staffService")
	private StaffService staffService;
	
	
	@RequestMapping(value = "/findDepartment.ajax")
	@ResponseBody
	public Map<String, ? extends Object> findRoles(Long departmentId) {
		try {

			if(departmentId == null){
				throw new Exception("查询部门ID不能为空");
			}
			
			Department d = departmentService.findDepartment(departmentId);
			
			if(d == null){
				throw new Exception("找不到对应的部门[id="+departmentId+"]");
			}
			
			return success(d);
			
		} catch (Exception e) {
			LOGGER.error("根据[id="+departmentId+"]查询角色出错", e);
			return fail(e.getMessage());
		}
	}
	
	@RequestMapping(value = "/updateDepartment.ajax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, ? extends Object> updateDepartment(Department department) {
		try {
			
			String text = "";
			
        	if(department == null){
        		throw new Exception("空对象错误");
        	}
        	
        	if(isEmpty(department.getDepartmentId())){
        		// 新增
                
        		// 设置addSub属性，兼容以前
        		department.setAddSub("1");
                //为角色设默认创建者为当前登录用户
            	if(StaffUtil.getLoginStaff() == null){
            		department.setCreateUser("system");
            	}else{
            		department.setCreateUser(StaffUtil.getLoginStaff().getLoginName());
            	}

            	departmentService.insertDepartment(department);
        		
        		text = "创建部门成功";
        	}else{
        		// 修改
        		departmentService.updateDepartment(department);
        		
        		text = "修改部门成功";
        	}
			
			return success(department);
		} catch (Exception e) {
			LOGGER.error("修改部门信息出错", e);
			return fail(e.getMessage());
		}
	}
	
	@RequestMapping(value = "/delDepartment.ajax")
	@ResponseBody
	public Map<String, ? extends Object> delDepartment(Long departmentId) {
		try {

			if(departmentId == null){
				throw new Exception("部门ID不能为空");
			}
			Staff staff = StaffUtil.getLoginStaff();
			if (null != staff && staff.getDepartmentId() == departmentId){
				throw new Exception("你没有权限删除当前组织!");
			}
			departmentService.deleteDepartment(departmentId);
		
			return success("删除部门成功");	
		} catch (Exception e) {
			LOGGER.error("根据[departmentId]="+departmentId+",删除部门出错", e);
			return fail(e.getMessage());
		}
	}
	
	@RequestMapping(value = "/listDepartmentTree.ajax")
	@ResponseBody
	public Map<String, ? extends Object> listDepartmentTree() {
		try {

			return success(departmentService.buildDepartmentTree());
			
		} catch (Exception e) {
			LOGGER.error("获取组织列表出错", e);
			return fail(e.getMessage());
		}
	}
	
	@RequestMapping(value = "/findDepartmentInfo.ajax")
	@ResponseBody
	public Map<String, ? extends Object> findDepartmentInfo(Long departmentId) {
		try {

			if(departmentId == null){
				throw new Exception("查询部门ID不能为空");
			}
			
			Department d = departmentService.findDepartment(departmentId);
			
			if(d == null){
				throw new Exception("找不到对应的部门[id="+departmentId+"]");
			}
			
			List<Role> roles = roleService.listDepartmentRoles(departmentId);
			/*StringBuffer roleBuffer = new StringBuffer();
			
			for(Role r : roles){
				roleBuffer.append(r.getRoleName())
				      .append(",");
			}*/
			
			d.addOtherField("roles", roles);
			
			List<Staff> staffs = staffService.listDepartmentStaffs(departmentId);
			/*StringBuffer staffBuffer = new StringBuffer();
			
			for(Staff s : staffs){
				staffBuffer.append(s.getLoginName())
				      .append(",");
			}*/
			
			d.addOtherField("staffs", staffs);
			
			return success(d);
			
		} catch (Exception e) {
			LOGGER.error("根据[departmentId]="+departmentId+",查询部门出错", e);
			return fail(e.getMessage());
		}
	}
	
	
	@RequestMapping(value = "/listDepartmentRoles.ajax")
	@ResponseBody
	public Map<String, ? extends Object> listDepartmentRoles(Long departmentId) {
		try {
			
			return page(departmentService.listDepartmentRoles(departmentId));
			
		} catch (Exception e) {
			LOGGER.error("根据[departmentId]="+departmentId+",查询角色出错", e);
			return fail(e.getMessage());
		}
	}
	
	@RequestMapping(value = "/updateDepartmentRole.ajax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, ? extends Object> updateStaffRole(@RequestParam String operation,
			                                             @RequestParam Long departmentId,
			                                             @RequestParam Long roleId) 
	{
		
		try {
			if(isEmpty(operation) || isEmpty(departmentId) || isEmpty(roleId)){
				throw new Exception("参数为空");
			}
			
			//System.out.println("operation="+operation+",staffId="+staffId);
			
			if("add".equalsIgnoreCase(operation)){
				departmentService.insertDepartmentRole(departmentId, roleId);
				
				return success("添加角色成功。");
			}else{
				departmentService.deleteDepartmentRoleByRoleIdAndDeptId(departmentId, roleId);
				
				return success("删除角色成功。");
			}
		    
		} catch (Exception e) {
			LOGGER.error("根据[][departmentId]="+departmentId+",查询角色出错", e);
			return fail(e.getMessage());
		}
	}
	
	@RequestMapping(value = "/updateStaffDepartment.ajax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, ? extends Object> updateStaffDepartment( @RequestParam Long departmentId,
            													@RequestParam String staffIds,
            													@RequestParam String oraginalIds) {
		try {
			
			if(isEmpty(departmentId)){
				throw new Exception("部门Id不能为空");
			}
			
			if(isEmpty(staffIds)){
				//throw new Exception("部门Id不能为空");
				staffIds = "''";
			}
			
			if(isEmpty(oraginalIds)){
				//throw new Exception("部门Id不能为空");
				oraginalIds = "''";
			}
			
			// 先原来的用户的departmentId设置为空
			staffService.updateStaffDepartment(null, wrapIds(oraginalIds));
			
			// 再更新新用户的departmentId
			staffService.updateStaffDepartment(departmentId, wrapIds(staffIds));
			
			return success("更新部门用户成功");
		} catch (Exception e) {
			LOGGER.error("根据[staffIds]="+staffIds+",[oraginalIds]="+oraginalIds+",[departmentId]="+departmentId+",更新部门用户出错", e);
			return fail(e.getMessage());
		}
	}
	
	private String wrapIds(String ids){
		if(ids.indexOf(",") > 0){
			return "'" + ids.replace(",", "','") + "'";
		}
		
		return ids;
	}
//	
//	@RequestMapping(value = "/listStaffsInDepartment.ajax")
//	@ResponseBody
//	public Map<String, ? extends Object> listStaffsInDepartment(String departmentId) {
//		try {
//
//			
//			List<Staff> staffs = staffService.listDepartmentStaffs(departmentId);
//			
//			StringBuffer buffer = new StringBuffer();
//			
//			for(Staff s : staffs){
//				buffer.append(s.getLoginName())
//				      .append(",");
//			}
//			
//			return success(buffer.toString());
//			
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
////			e.printStackTrace();
//			return fail(e.getMessage());
//		}
//	}
}
