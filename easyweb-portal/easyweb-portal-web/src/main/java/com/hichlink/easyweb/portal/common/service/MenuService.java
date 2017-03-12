package com.hichlink.easyweb.portal.common.service;

import java.util.List;

import com.hichlink.easyweb.portal.common.tree.MenuTreeNode;

public interface MenuService {

	List<MenuTreeNode> buildMenuTree(String loginName,String ticket, String defaultPath) throws Exception;
}
