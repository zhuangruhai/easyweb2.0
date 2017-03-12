package com.hichlink.easyweb.portal.modules.authmgt.web;

import java.util.ArrayList;
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

import com.hichlink.easyweb.core.pagination.mybatis.pager.Page;
import com.hichlink.easyweb.core.web.BaseController;
import com.hichlink.easyweb.portal.common.entity.Role;
import com.hichlink.easyweb.portal.common.entity.RoleResourceOperation;
import com.hichlink.easyweb.portal.common.service.RoleService;
import com.hichlink.easyweb.portal.common.tree.TreeNode;
import com.hichlink.easyweb.portal.common.util.StaffUtil;

@Controller
@RequestMapping("/role")
public class RoleController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(RoleController.class);
	@Autowired
	@Qualifier("roleService")
	private RoleService roleService;

	@RequestMapping(value = "/updateRole.ajax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, ? extends Object> updateRole(Role role) {

		try {
			String text = "";

			if (role == null) {
				throw new Exception("空对象错误");
			}

			if (isEmpty(role.getRoleId())) {
				// 新增

				// 为能否修改设属性置默认值，默认为可以修改
				if (role != null && (role.getCanModify() == null)) {
					role.setCanModify(1);
				}

				// 为角色设默认创建者为当前登录用户
				if (StaffUtil.getLoginStaff() == null) {
					role.setCreateUser("nouser");
				} else {
					role.setCreateUser(StaffUtil.getLoginStaff().getLoginName());
				}

				roleService.createRole(role);
				text = "创建角色成功";
			} else {
				// 修改
				roleService.updateRole(role);
				text = "修改角色成功";
			}

			return success(text);
		} catch (Exception e) {
			logger.error("修改角色失败", e);
			return fail(e.getMessage());
		}
	}

	@RequestMapping(value = "/deleteRole.ajax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, ? extends Object> deleteRole(Long roleId) {
		// String resourceIdAndOperationKey =
		// getRequest().getParameter("resourceIdAndOperationKey");
		try {
			roleService.deleteRole(roleId);

			return success("删除角色成功");
		} catch (Exception e) {
			logger.error("删除角色失败", e);
			return fail(e.getMessage());
		}
	}

	@RequestMapping(value = "/listRoles.ajax")
	@ResponseBody
	public Map<String, ? extends Object> listRoles(Page<Role> page) {
		try {

			return page(roleService.listRole(page));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			return fail(e.getMessage());
		}
	}

	@RequestMapping(value = "/findRole.ajax")
	@ResponseBody
	public Map<String, ? extends Object> findRoles(Long roleId) {
		try {

			if (roleId == null) {
				throw new Exception("查询角色ID不能为空");
			}

			Role r = roleService.findRole(roleId);

			if (r == null) {
				throw new Exception("找不到对应的角色[id=" + roleId + "]");
			}

			return success(r);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			return fail(e.getMessage());
		}
	}

	@RequestMapping(value = "/checkRoleKey.ajax")
	@ResponseBody
	public String checkRoleKey(Long roleId, String roleKey) {
		try {
			if (isEmpty(roleKey)) {
				return "参数为空";
			}

			Role role = roleService.findRoleByKey(roleKey);
			if (role != null) {
				if (isNotEmpty(roleId)) {
					Role role2 = roleService.findRole(roleId);
					if (null != role2 && role2.getRoleKey().equals(roleKey)) {
						return "true";
					}
				}
				return "角色助记码已存在";
			}

		} catch (Exception e) {
			logger.error("检查roleKey[" + roleKey + "]是否存在出错", e);
			return "发生系统异常";
		}
		return "true";
	}

	@RequestMapping(value = "/listRoleResource.ajax")
	@ResponseBody
	public Map<String, ? extends Object> listRoleResource(Long roleId) {
		try {

			List<TreeNode> tree = null;
			if (roleId == null) {
				tree = roleService.buildRoleResourceTree();
			} else {
				tree = roleService.buildRoleResourceTree(roleId);
			}

			return success(tree);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			return fail(e.getMessage());
		}
	}

	@RequestMapping(value = "/updateRoleResource.ajax", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, ? extends Object> updateRoleResource(@RequestParam Long roleId,
			@RequestParam String resourceIdAndOperationKey) {
		try {

			if (isEmpty(roleId)) {
				throw new Exception("角色ID为空");
			}

			roleService.updateRoleResource(roleId, createRoleResourceOperationList(resourceIdAndOperationKey));

			return success("成功更新角色权限");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			return fail(e.getMessage());
		}
	}

	/**
	 * 
	 * @param idAndKeys
	 * @return
	 */
	private List<RoleResourceOperation> createRoleResourceOperationList(String idAndKeys) {
		List<RoleResourceOperation> roleResourceOperations = new ArrayList<RoleResourceOperation>();

		if (isEmpty(idAndKeys)) {
			// logger.info("resourceIdAndOperationKey为空,新增或修改角色不带资源分配。");
			return roleResourceOperations;
		}
		if (isNotEmpty(idAndKeys)) {
			String[] resourceIdAndOperationKeys = idAndKeys.split(",");
			// HashMap resourceIdAndOperationMap = new HashMap();
			for (int i = 0; i < resourceIdAndOperationKeys.length; i++) {
				String[] idAndKey = resourceIdAndOperationKeys[i].split("-");
				if (idAndKey.length == 2) {
					// 和role绑定的资源ID
					String resourceId = idAndKey[0];
					// 此资源的操作KEY（一个资源可以有多个操作KEY）
					String operationKey = idAndKey[1];

					// 角色资源操作关联
					RoleResourceOperation roleResourceOperation = new RoleResourceOperation();
					roleResourceOperation.setResourceId(resourceId);
					roleResourceOperation.setOperationKey(operationKey);
					// 所有 角色资源操作关联
					roleResourceOperations.add(roleResourceOperation);
				}
			}
		}

		return roleResourceOperations;
	}

}
