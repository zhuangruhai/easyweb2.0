package com.aspire.webbas.portal.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aspire.webbas.portal.common.dao.MenuDao;
import com.aspire.webbas.portal.common.entity.Menu;
import com.aspire.webbas.portal.common.service.MenuService;
import com.aspire.webbas.portal.common.tree.MenuTreeNode;
import com.aspire.webbas.test.BaseTest;
import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
public class MenuServiceTest extends BaseTest{

	private MenuDao dao;
	
	private MenuService service;
	
	public void test(){
		dao = (MenuDao)getBean("menuDao");
		
		service = (MenuService)getBean("menuService");
		
		assertNotNull("menuDao is null",dao);
		
		assertNotNull("menuService is null",service);
		
//		buildTree();
		
		buildMenuTree();
		
		
	}
	
	public void buildMenuTree(){
		String loginName = "admin";
		
		List<MenuTreeNode> tree;
		
		try {
			tree = service.buildMenuTree(loginName, "lksjdflkjsl", "/webbas");

			
			toJson(tree);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	
	public void buildTree(){
		
		List<Menu> menus = dao.listMenu(new Menu());
		
		List<MenuTreeNode> list = new ArrayList();
		
		HashMap<String, MenuTreeNode> map = new HashMap<String, MenuTreeNode>();
		
		List<MenuTreeNode> roots = new ArrayList<MenuTreeNode>();
		
		for(Menu m : menus){
			MenuTreeNode node = new MenuTreeNode();
			node.setId(m.getMenuId().toString());
			node.setParentId(m.getParentId().toString());
			node.setText(m.getMenuName());
			node.setMenuKey(m.getMenuKey());
			node.setIcon(m.getImageUrl());
			node.setUrl(m.getUrl());
			
			list.add(node);
			map.put(node.getId(), node);
			
			if(m.getParentId() == 0){
				roots.add(node);
			}
		}
		
		
		for(MenuTreeNode node : list){
			MenuTreeNode parent = map.get(node.getParentId());
			
			if(parent != null){
				parent.addChild(node);
			}
		}
		
		// print
		System.out.println("root = " + roots.get(0).getText());
		System.out.println("root children: " + roots.get(0).getChildren().size());
		
		toJson(roots);
	}
	
	
	public void toJson(Object obj){
	  JsonGenerator jsonGenerator = null;
	  ObjectMapper objectMapper = null;

	  Map<String, Object> map = new HashMap<String,Object>();
	  
	  map.put("data", obj);
	  map.put("success", true);
	  
	  objectMapper = new ObjectMapper();
        try {
            jsonGenerator = objectMapper.getJsonFactory().createJsonGenerator(System.out, JsonEncoding.UTF8);
        
            
            System.out.println("json object ---->");
            jsonGenerator.writeObject(map);
            
            
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
}
