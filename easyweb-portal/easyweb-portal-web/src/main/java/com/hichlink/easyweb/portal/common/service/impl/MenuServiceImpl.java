package com.hichlink.easyweb.portal.common.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hichlink.easyweb.portal.common.dao.MenuDao;
import com.hichlink.easyweb.portal.common.dao.SubSystemDao;
import com.hichlink.easyweb.portal.common.entity.Menu;
import com.hichlink.easyweb.portal.common.entity.SubSystem;
import com.hichlink.easyweb.portal.common.service.AuthService;
import com.hichlink.easyweb.portal.common.service.MenuService;
import com.hichlink.easyweb.portal.common.tree.MenuTreeNode;
import com.hichlink.easyweb.portal.common.tree.TreeNode;

@Service("menuService")
public class MenuServiceImpl implements MenuService {

	private static Map<String, SubSystem> subSystemMap = null;

	@Autowired
	private MenuDao menuDao;

	@Autowired
	private SubSystemDao subSystemDao;

	@Autowired
	private AuthService authService;

	public List<MenuTreeNode> buildMenuTree(String loginName, String ticket, String defaultPath) throws Exception {

		List<Menu> menus = menuDao.listMenu(new Menu());

		List<Menu> targetMenus = new ArrayList<Menu>();

		Map<String, String> urlMap = authService.listAddressUrlByLoginName(loginName);

		// 把鉴权不通过的菜单去掉
		for (Menu m : menus) {
			// 空url的菜单可能是父节点
			if (StringUtils.isEmpty(m.getUrl())) {
				targetMenus.add(m);
				continue;
			}

			if (authService.authorizeSuccess(m.getUrl(), urlMap)) {
				targetMenus.add(m);
			}
		}

		// System.out.println("targetMenus.size=" + targetMenus.size());

		List<MenuTreeNode> list = new ArrayList<MenuTreeNode>();

		Map<String, MenuTreeNode> map = new LinkedHashMap<String, MenuTreeNode>();

		List<MenuTreeNode> roots = new ArrayList<MenuTreeNode>();

		for (Menu m : targetMenus) {
			MenuTreeNode node = toMenuNode(m, ticket, defaultPath);

			list.add(node);
			map.put(node.getId(), node);
		}

		// 构建父子关系
		for (MenuTreeNode node : list) {
			MenuTreeNode parent = map.get(node.getParentId());

			if (parent != null) {
				parent.addChild(node);
			}
		}

		// 删除空节点
		for (MenuTreeNode node : list) {
			if (node.isLeaf() && node.getUrl() == null) {
				removeNode(map, node);
			}
		}

		// System.out.println("map.size:" + map.size());

		for (MenuTreeNode node : map.values()) {
			if ("0".equals(node.getParentId())) {
				roots.add(node);
			}
		}

		return roots;
	}

	private Map<String, SubSystem> getSubSystem() {
		if (subSystemMap == null) {
			subSystemMap = loadSubSystem();
		}

		return subSystemMap;
	}

	private Map<String, SubSystem> loadSubSystem() {
		Map<String, SubSystem> map = new HashMap<String, SubSystem>();

		List<SubSystem> list = subSystemDao.listSubSystem();

		for (SubSystem sys : list) {
			map.put(sys.getSubSystemId(), sys);
		}

		return map;
	}

	private void removeNode(Map<String, MenuTreeNode> treeNodes, TreeNode node) {
		treeNodes.remove(node.getId());

		if (treeNodes.containsKey(node.getParentId())) {
			TreeNode parent = treeNodes.get(node.getParentId());
			parent.removeChild(node);
			// 父节点删除child之后如果没有children了，继续删除父节点
			if (parent.isLeaf()) {
				removeNode(treeNodes, parent);
			}
		}

	}

	private MenuTreeNode toMenuNode(Menu m, String ticket, String defaultPath) {
		MenuTreeNode node = new MenuTreeNode();
		node.setId(m.getMenuId().toString());
		node.setParentId(m.getParentId().toString());
		node.setText(m.getMenuName());
		node.setMenuKey(m.getMenuKey());
		node.setIcon(m.getImageUrl());

		// node.setUrl(m.getUrl());

		// 对于每个菜单跟节点，需要拼装访问子系统的路径
		if (m.getUrl() != null && !"".equals(m.getUrl())) {
			String contextPath = getSubSystemContext(m.getSubsystem());

			if (contextPath == null || "".equals(contextPath)) {
				contextPath = defaultPath;
			}
			node.setUrl(contextPath + m.getUrl() + "?ticket=" + ticket);
		}

		return node;
	}

	private String getSubSystemContext(String subSystemId) {

		Map<String, SubSystem> subSystems = getSubSystem();

		if (subSystems.containsKey(subSystemId)) {
			return subSystems.get(subSystemId).getHopDomain();
		}

		return "";
	}
}
