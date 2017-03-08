package com.aspire.webbas.portal.dao;

import java.util.List;

import com.aspire.webbas.portal.common.dao.MenuDao;
import com.aspire.webbas.portal.common.entity.Menu;
import com.aspire.webbas.test.BaseTest;


public class MenuDaoTest extends BaseTest{
	
	private MenuDao dao;
	
	public void test(){
		dao = (MenuDao)getBean("menuDao");
		
		assertNotNull("menuDao is null",dao);
	
		listMenu();
	}
	
	
	public void listMenu(){
		Menu menu = new Menu();
		
		List<Menu> menus = dao.listMenu(menu);
		
		
		System.out.println("list.size:" + menus.size());
	}
}
