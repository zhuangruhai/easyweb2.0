package com.hichlink.easyweb.portal.modules.login.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hichlink.easyweb.core.web.BaseController;
import com.hichlink.easyweb.portal.common.config.Config;
import com.hichlink.easyweb.portal.common.entity.Staff;
import com.hichlink.easyweb.portal.common.service.MenuService;
import com.hichlink.easyweb.portal.common.tree.MenuTreeNode;
import com.hichlink.easyweb.portal.common.tree.TreeNode;
import com.hichlink.easyweb.portal.common.util.StaffUtil;

@Controller
public class IndexController extends BaseController {

	@Autowired
	@Qualifier("menuService")
	private MenuService menuService;
	private String[] menuLevelClazz = new String[] { "nav-second-level", "nav-third-level", "nav-third-level",
			"nav-third-level" };
	@Autowired
	private Config config;

	@RequestMapping(value = "/")
	public ModelAndView view() {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("title", config.getTitle());
		try {
			if (null != StaffUtil.getLoginStaff()) {
				Staff loginStaff = StaffUtil.getLoginStaff();
				result.put("staff", loginStaff);
				
				result.put("menuHtml", initMenu(loginStaff));
				return new ModelAndView("main", "result", result);
			}
		} catch (Exception e) {
		}
		return new ModelAndView("login", "result", result);
	}

	private String initMenu(Staff loginStaff) {
		try {

			HttpSession session = getSession();

			List<MenuTreeNode> menuTree = menuService.buildMenuTree(loginStaff.getLoginName(), session.getId(),
					this.getRequest().getContextPath());
			StringBuilder sb = new StringBuilder();
			buildMenu(menuTree, sb);
			return sb.toString();
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		return "";
	}

	private void buildMenu(List<MenuTreeNode> menuTree, StringBuilder sb) {
		for (MenuTreeNode menu : menuTree) {
			sb.append("<li>");
			sb.append("<a href=\"#\"><i class=\"" + menu.getIcon() + "\"></i><span class=\"nav-label\">"
					+ menu.getText() + "</span>");
			if (!menu.isLeaf()) {
				sb.append("<span class=\"fa arrow\"></span>");
			}
			sb.append("</a>");
			int level = 0;
			if (!menu.isLeaf()) {
				buildSubMenu(menu.getChildren(), sb, level);
			}
			sb.append("</li>");
		}
	}

	private void buildSubMenu(Set<TreeNode> childrens, StringBuilder sb, int level) {
		if (childrens.size() <= 0) {
			return;
		}
		sb.append("<ul class=\"nav " + menuLevelClazz[level] + " collapse\" aria-expanded=\"false\">");
		for (TreeNode treeNode : childrens) {
			MenuTreeNode children = (MenuTreeNode) treeNode;
			sb.append("<li>");
			if (StringUtils.isNotBlank(children.getUrl())) {
				sb.append("<a class=\"J_menuItem\" href=\"" + children.getUrl() + "\">" + children.getText() + "</a>");
			} else {
				sb.append("<a href=\"#\">" + children.getText() + "<span class=\"fa arrow\"></span></a>");
			}
			buildSubMenu(children.getChildren(), sb, level++);
			sb.append("</li>");

		}
		sb.append("</ul>");
	}
}
