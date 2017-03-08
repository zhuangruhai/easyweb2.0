package com.aspire.webbas.portal.common.tree;

public class MenuTreeNode extends TreeNode{

	private String menuKey;
	private String icon;
	private String url;
	
	public MenuTreeNode(){
		
	}
	
//	public MenuTreeNode(String id, String parentId, String text,String url){
//		
//		super(id, parentId, text);
//		
//		this.url = url;
//	}

	public String getUrl() {
		return url;
	}

	public String getMenuKey() {
		return menuKey;
	}

	public void setMenuKey(String menuKey) {
		this.menuKey = menuKey;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
