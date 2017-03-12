package com.hichlink.easyweb.portal.modules.home.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hichlink.easyweb.core.web.BaseController;
import com.hichlink.easyweb.portal.common.entity.Staff;
import com.hichlink.easyweb.portal.common.service.MenuService;
import com.hichlink.easyweb.portal.common.tree.MenuTreeNode;
import com.hichlink.easyweb.portal.common.util.StaffUtil;

@Controller
@RequestMapping("/portal")
public class MenuController extends BaseController{

	@Autowired
	@Qualifier("menuService")
	private MenuService menuService;
	
	@RequestMapping(value = "/menu.ajax")
	@ResponseBody
	public Map<String, ? extends Object> menu() {
//        String loginName = "sims_admin";
        try {
        	
        	Staff loginStaff = StaffUtil.getLoginStaff();
        	
        	if(loginStaff == null){
        		throw new Exception("用户为登录");
        	}
        	
        	HttpSession session  = getSession();
        	
        	
        	List<MenuTreeNode> menuTree = 
        			menuService.buildMenuTree(loginStaff.getLoginName(), 
        					                  session.getId(),
        					                  this.getRequest().getContextPath());
        	
			
			return success(menuTree);
		} catch (Exception e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			
			return fail(e.getMessage());
		}
	}
}
