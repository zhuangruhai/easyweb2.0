package com.hichlink.easyweb.portal.common.entity;


public class Menu extends BaseEntity {

	 /**
	 * 
	 */
	private static final long serialVersionUID = -6380192975770195471L;

	/**
     * 菜单ID.
     */
    private Long menuId;
    
    /**
     * 菜单名称.
     */
    private String menuName;
    
    /**
     * 菜单外键
     */
    private String menuKey;
    
    /**
     * 父菜单ID.
     */
    private Long parentId;
    
    /**
     * 图标url.
     */
    private String imageUrl;
    
    /**
     * 资源url地址.
     */
    private String url;
    
    /**
     * 菜单顺序.
     */
    private String menuOrder;
    
    public Long getMenuId() {
		return menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuKey() {
		return menuKey;
	}

	public void setMenuKey(String menuKey) {
		this.menuKey = menuKey;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMenuOrder() {
		return menuOrder;
	}

	public void setMenuOrder(String menuOrder) {
		this.menuOrder = menuOrder;
	}

	public String getSubsystem() {
		return subsystem;
	}

	public void setSubsystem(String subsystem) {
		this.subsystem = subsystem;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	/**
     * 子系统名称.
     */
    private String subsystem;
    
    /**
     * 菜单所属域(admin或sp).
     */
    private String domain;
}
