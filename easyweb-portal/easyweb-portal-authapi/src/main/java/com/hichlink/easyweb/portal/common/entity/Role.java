package com.aspire.webbas.portal.common.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;


public class Role extends BaseEntity{
	 /**
	 * 
	 */
	private static final long serialVersionUID = 3751599917198880271L;

	/** 可以修改. */
    public static final Integer CAN_MODIFY = 1;

    /** 不可以修改. */
    public static final Integer CAN_NOT_MODIFY = 0;
    
    /** 可以修改. */
    public static final Integer AUTO_ASSIGN = 1;

    /** 不可以修改. */
    public static final Integer AUTO_NOT_ASSIGN  = 0;
    
    /**
     * 可见
     */
    public static final Integer IS_VISIBLE = 1;
    
    /**
     * 不可见
     */
    public static final Integer IS_NOT_VISIBLE = 0;
    
    /**
     * 系统管理员
     */
    public static final String KEY_SYSTEM_ADMIN = "1001";
    /**
     * 组织管理员
     */
    public static final String KEY_DEPARTMENT_ADMIN = "1002";
    /**
     * ADMIN端初创用户
     */
    public static final String KEY_REGISTED_ADMIN = "101";
    /**
     * SP端初创用户
     */
    public static final String KEY_REGISTED_SP = "SP_ROLE_AUTH";
    
    
    /**
     * 角色ID.
     */
    private Long roleId;

    /**
     * 角色外码.
     */
    private String roleKey;

    /**
     * 角色名称.
     */
    private String roleName;

    /**
     * 角色描述.
     */
    private String roleDesc;

    /**
     * 角色创建者.
     */
    @JsonIgnore
    private String createUser;

    /**
     * 角色创建时间.
     */
    @JsonIgnore
    private Date createDate;
    
    /**
     * 是否允许修改 1：允许 0: 不充许 缺省=1.
     */
    private Integer canModify = CAN_MODIFY;

    /**
     * 最后修改时间.
     */
    @JsonIgnore
    private Date lastUpdateDate;
    
    /**
     * 自动分配(0:不自动分配；1：自动分配给所有注册成员 缺省为0)
     */
    @JsonIgnore
    private Integer autoAssign = 0;
    
    /**
     * 是否在界面上可见(0：不可见；1：可见)
     */
    @JsonIgnore
    private Integer visible = 1;

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getRoleKey() {
		return roleKey;
	}

	public void setRoleKey(String roleKey) {
		this.roleKey = roleKey;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleDesc() {
		return roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Integer getCanModify() {
		return canModify;
	}

	public void setCanModify(Integer canModify) {
		this.canModify = canModify;
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public Integer getAutoAssign() {
		return autoAssign;
	}

	public void setAutoAssign(Integer autoAssign) {
		this.autoAssign = autoAssign;
	}

	public Integer getVisible() {
		return visible;
	}

	public void setVisible(Integer visible) {
		this.visible = visible;
	}
}