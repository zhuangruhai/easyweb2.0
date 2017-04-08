package com.hichlink.easyweb.portal.modules.authmgt.web;

import java.util.Date;
import java.util.HashMap;
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
import org.springframework.web.servlet.ModelAndView;

import com.hichlink.easyweb.core.pagination.mybatis.pager.Page;
import com.hichlink.easyweb.core.web.BaseController;
import com.hichlink.easyweb.portal.common.entity.City;
import com.hichlink.easyweb.portal.common.entity.Department;
import com.hichlink.easyweb.portal.common.entity.Role;
import com.hichlink.easyweb.portal.common.entity.Staff;
import com.hichlink.easyweb.portal.common.service.CityService;
import com.hichlink.easyweb.portal.common.service.DepartmentService;
import com.hichlink.easyweb.portal.common.service.RoleService;
import com.hichlink.easyweb.portal.common.service.StaffService;
import com.hichlink.easyweb.portal.common.util.RSAUtil;
import com.hichlink.easyweb.portal.common.util.StaffUtil;

@Controller
@RequestMapping("/staff")
public class StaffController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(StaffController.class);

	@Autowired
	@Qualifier("staffService")
	private StaffService staffService;
	@Autowired
	@Qualifier("departmentService")
	private DepartmentService departmentService;
	@Autowired
	@Qualifier("roleService")
	private RoleService roleService;
	@Autowired
	@Qualifier("cityService")
	private CityService cityService;

	@RequestMapping(value = "/view")
	public ModelAndView view() {
		Map<String, Object> result = new HashMap<String, Object>();
		String modulus = RSAUtil.bigIntToHexStr(RSAUtil.getDefaultPublicKey().getModulus());
		String exponent = RSAUtil.bigIntToHexStr(RSAUtil.getDefaultPublicKey().getPublicExponent());
		result.put("modulus", modulus);
		result.put("exponent", exponent);
		return new ModelAndView("staff", "result", result);
	}

	@RequestMapping(value = "/viewModifyPwd")
	public ModelAndView viewModifyPwd() {
		return new ModelAndView("modifyPwd");
	}

	@RequestMapping(value = "/viewModifyInfo")
	public ModelAndView viewModifyInfo() {
		return new ModelAndView("modifyInfo");
	}

	@RequestMapping(value = "/viewSelfConfigSetting")
	public ModelAndView viewSelfConfigSetting() {
		return new ModelAndView("selfConfigSetting");
	}

	@RequestMapping(value = "/createStaff.ajax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, ? extends Object> createStaff(Staff staff) throws Exception {
		try {
			if (isNotEmpty(staff.getStaffId())) {
				staff.setLastUpdateDate(new Date());
				staffService.updateStaff(staff);
			} else {
				if (staff.getStatus() == null) {
					staff.setStatus(Staff.Status.INITIAL);
				}

				if (staff.getCreateUser() == null) {
					if (StaffUtil.getLoginStaff() == null) {
						staff.setCreateUser("nouser");
					} else {
						// staff.setDepartmentId(StaffUtil.getLoginStaff().getDepartmentId());
						staff.setCreateUser(StaffUtil.getLoginStaff().getLoginName());
					}
				}
				staffService.createStaff(staff);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			logger.error("新增用户信息出错", e);
			return fail(e.getMessage());
		}

		return success("创建用户成功。");
	}

	// @ModelAttribute("staff")
	// public void getForUpdate(@RequestParam(value = "updId",required=false)
	// String updId,
	// Model model) {
	// if (null != updId) {
	// model.addAttribute("staff",staffService.findStaff(updId));
	// }
	// }

	@RequestMapping(value = "/findStaff.ajax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, ? extends Object> findStaff(Long staffId) {

		try {
			if (isEmpty(staffId)) {
				throw new Exception("用户Id参数不能为空");
			}

			Staff staff = staffService.findStaff(staffId);

			if (staff == null) {
				throw new Exception("没有找到用户[id=" + staffId + "]");
			}
			staff.setOthers(packStaffOthers(staff));
			return success(staff);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			logger.error("根据staffId=" + staffId + ",查询用户信息出错", e);
			return fail(e.getMessage());
		}
	}

	@RequestMapping(value = "/deleteStaff.ajax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, ? extends Object> deleteStaff(Long staffId) {

		try {
			if (isEmpty(staffId)) {
				throw new Exception("staffId参数为空");
			}

			// String[] staffs = staffIds.split(",");
			Long[] staffs = { staffId };

			staffService.deleteStaffs(staffs);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("根据staffId=" + staffId + ",删除用户信息出错", e);
			return fail(e.getMessage());
		}

		return success("删除用户成功。");
	}

	@RequestMapping(value = "/listStaff.ajax")
	@ResponseBody
	public Map<String, ? extends Object> listStaff(Page<Staff> page) {

		try {
			page.getParams().put("departmentIds",
					transferList2QueryStr(departmentService.findMyDepartmentAndChildrenDeptIds()));
			return page(staffService.listStaff(page));

		} catch (Exception e) {
			logger.error("查询用户信息出错", e);
			return fail(e.getMessage());
		}
	}

	private String transferList2QueryStr(List<String> deptIds) {
		StringBuilder sb = new StringBuilder();
		for (String id : deptIds) {
			if (!sb.toString().isEmpty()) {
				sb.append(",");
			}
			sb.append(id);
		}
		return sb.toString();
	}

	private Map<String, Object> packStaffOthers(Staff staff) throws Exception {
		Map<String, Object> others = new HashMap<String, Object>();
		if (isNotEmpty(staff.getDepartmentId())) {
			List<Department> list = departmentService.listPathFromRootToCurrentDepartmentId(staff.getDepartmentId());
			if (null != list && list.size() > 0) {
				StringBuilder sb = new StringBuilder();
				for (int i = list.size() - 1; i >= 0; i--) {
					if (list.get(i).getDepartmentId() == Department.ROOT_DEPARTMENT_ID) {
						continue;
					}
					if (!sb.toString().isEmpty()) {
						sb.append("/");
					}
					sb.append(list.get(i).getDepartmentName());
				}
				others.put("departmentName", sb.toString());
			}
		}
		if (null != staff.getCityId()) {
			City city = cityService.get(staff.getCityId());
			if (null != city) {
				others.put("city", city);
			}
		}
		List<Role> roles = roleService.listStaffRoles(staff.getStaffId());
		others.put("roles", roles);
		return others;
	}

	@RequestMapping(value = "/lockStaff.ajax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, ? extends Object> lockStaff(@RequestParam String operation, @RequestParam Long staffId) {

		try {
			if (isEmpty(operation) || isEmpty(staffId)) {
				throw new Exception("参数为空");
			}

			// System.out.println("operation="+operation+",staffId="+staffId);

			if ("lock".equalsIgnoreCase(operation)) {
				staffService.lockStaff(staffId);

				return success("锁定用户成功。");
			} else {
				staffService.unlockStaff(staffId);

				return success("解锁用户成功。");
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("锁定/解锁用户出错", e);
			return fail(e.getMessage());
		}
	}

	@RequestMapping(value = "/updateStaffRole.ajax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, ? extends Object> updateStaffRole(@RequestParam String operation, @RequestParam Long staffId,
			@RequestParam Long roleId) {

		try {
			if (isEmpty(operation) || isEmpty(staffId) || isEmpty(roleId)) {
				throw new Exception("参数为空");
			}

			// System.out.println("operation="+operation+",staffId="+staffId);

			if ("add".equalsIgnoreCase(operation)) {
				staffService.insertStaffRole(staffId, roleId);

				return success("添加角色成功。");
			} else {

				// 对于超级管理员不能删除自己所拥有的“系统管理”角色
				Role role = roleService.findRole(roleId);

				Staff staff = StaffUtil.getLoginStaff();

				// System.out.println("staffId:" + staff.getStaffId() +
				// " roleKey:" + role.getRoleKey());

				if (staffId.equals(staff.getStaffId()) && "1001".equals(role.getRoleKey())) {
					throw new Exception("超级管理员不能删除自己拥有的系统管理角色");
				}
				staffService.deleteStaffRole(staffId, roleId);

				return success("删除角色成功。");
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("删除角色出错", e);
			return fail(e.getMessage());
		}
	}

	@RequestMapping(value = "/listStaffRoles.ajax")
	@ResponseBody
	public Map<String, ? extends Object> listStaffRoles(Long staffId) {

		try {

			if (staffId == null) {
				throw new Exception("用户id为空");
			}

			return page(staffService.listStaffRoles(staffId));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("错误:", e);
			return fail(e.getMessage());
		}
	}

	@RequestMapping(value = "/checkStaffLoginName.ajax")
	@ResponseBody
	public boolean checkStaffLoginName(String loginName) {

		try {
			if (isEmpty(loginName)) {
				return true;
			}

			Staff staff = staffService.findStaffByLoginName(loginName);

			if (staff != null) {
				return false;
			}

		} catch (Exception e) {
			logger.error("检查用户名[" + loginName + "]是否存在出错", e);
			return true;
		}

		return true;
	}

	@RequestMapping(value = "/checkStaffMobile.ajax")
	@ResponseBody
	public boolean checkStaffMobile(String mobile) {
		try {
			if (isEmpty(mobile)) {
				return true;
			}
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("mobile", mobile);
			Staff staff = staffService.findStaffByMap(params);

			if (staff != null) {
				return false;
			}

		} catch (Exception e) {
			logger.error("检查手机号码[" + mobile + "]是否存在出错", e);
			return false;
		}
		return true;
	}

	@RequestMapping(value = "/checkStaffEmail.ajax")
	@ResponseBody
	public boolean checkStaffEmail(String email) {
		try {
			if (isEmpty(email)) {
				return true;
			}
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("email", email);
			Staff staff = staffService.findStaffByMap(params);

			if (staff != null) {
				return false;
			}

		} catch (Exception e) {
			logger.error("检查邮箱[" + email + "]是否存在出错", e);
			return false;
		}
		return true;
	}

	@RequestMapping(value = "/findLoginStaff.ajax")
	@ResponseBody
	public Map<String, ? extends Object> findLoginStaff() throws Exception {
		try {
			Staff staff = StaffUtil.getLoginStaff();
			if (staff == null) {
				throw new Exception("用户没有登录");
			}
			Staff staff2 = staffService.findStaffByLoginName(staff.getLoginName());
			staff2.setOthers(packStaffOthers(staff2));
			return success(staff2);
		} catch (Exception e) {
			logger.error("获取登录用户信息出错=>", e);
			return fail(e.getMessage());
		}
	}

	@RequestMapping(value = "/changePwd.ajax")
	@ResponseBody
	public Map<String, ? extends Object> changePwd(String oldPassword, String newPassword) throws Exception {
		try {
			Staff staff = StaffUtil.getLoginStaff();
			if (staff == null) {
				throw new Exception("用户没有登录");
			}
			if (isEmpty(oldPassword) || isEmpty(newPassword)) {
				throw new IllegalArgumentException("旧密码或新密码没有设置！");
			}
			String oldPasswordDecrypt = RSAUtil.decryptString(oldPassword);
			String newPasswordDecrypt = RSAUtil.decryptString(newPassword);
			staffService.changePassword(staff.getLoginName(), oldPasswordDecrypt, newPasswordDecrypt);
			return success("修改成功");
		} catch (Exception e) {
			logger.error("获取登录用户信息出错=>", e);
			return fail(e.getMessage());
		}
	}

	@RequestMapping(value = "/resetPwd.ajax")
	@ResponseBody
	public Map<String, ? extends Object> resetPwd(String loginName, String password) throws Exception {
		try {
			if (isEmpty(password)) {
				throw new IllegalArgumentException("新密码没有设置！");
			}
			String passwordDecrypt = RSAUtil.decryptString(password);
			staffService.resetPassword(loginName, passwordDecrypt);
			return success("重置成功");
		} catch (Exception e) {
			logger.error("获取登录用户信息出错=>", e);
			return fail(e.getMessage());
		}
	}

	@RequestMapping(value = "/updateStaff.ajax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, ? extends Object> updateStaff(Staff staff) throws Exception {
		try {

			if (isEmpty(staff.getLoginName())) {
				throw new Exception("参数错误");
			}
			Staff staff2 = staffService.findStaffByLoginName(staff.getLoginName());
			staff2.setRealName(staff.getRealName());
			staff2.setSex(staff.getSex());
			staff2.setMobile(staff.getMobile());
			staff2.setTelephone(staff.getTelephone());
			staff2.setEmail(staff.getEmail());
			staffService.updateStaff(staff2);

			// 要通过staffUtil来更新登录用户信息
			StaffUtil.updateLoginStaff(staff2);
			return success("修改成功");
		} catch (Exception e) {
			logger.error("获取登录用户信息出错=>", e);
			return fail(e.getMessage());
		}
	}

	@RequestMapping(value = "/updateStaffDepartment.ajax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, ? extends Object> updateStaffDepartment(Long departmentId, String staffIds) throws Exception {
		try {

			if (isEmpty(departmentId)) {
				throw new Exception("参数错误");
			}
			staffService.updateStaffDepartment(departmentId, staffIds);
			return success("添加成功");
		} catch (Exception e) {
			logger.error("添加用户到组织出错=>", e);
			return fail(e.getMessage());
		}
	}

	@RequestMapping(value = "/listRoleByStaffIds.ajax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, ? extends Object> listRoleByStaffIds(Long departmentId, String staffIds) throws Exception {
		try {
			return success(staffService.listRoleByStaffIds(departmentId, staffIds));
		} catch (Exception e) {
			logger.error("添加用户到组织出错=>", e);
			return fail(e.getMessage());
		}
	}

	@RequestMapping(value = "/updateStaffRolesDepartment.ajax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, ? extends Object> updateStaffRolesDepartment(Long departmentId, String staffIds,
			String staffIdRoles) throws Exception {
		try {

			if (isEmpty(departmentId) || isEmpty(staffIds)) {
				throw new Exception("参数错误");
			}
			staffService.updateStaffRolesDepartment(departmentId, staffIds, staffIdRoles);
			return success("修改成功");
		} catch (Exception e) {
			logger.error("添加用户到组织出错=>", e);
			return fail(e.getMessage());
		}
	}
}
