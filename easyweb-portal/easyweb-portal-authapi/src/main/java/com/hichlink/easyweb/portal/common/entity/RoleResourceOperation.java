package com.hichlink.easyweb.portal.common.entity;


public class RoleResourceOperation extends BaseEntity{
    /**
     * 
     */
    private static final long serialVersionUID = 2482302361798722810L;
    /**
     * 角色ID
     */
	private Long roleId;
	/**
	 * 资源ID
	 */
    private String resourceId;
    /**
     * 资源操作关键字
     */
    private String operationKey;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getOperationKey() {
        return operationKey;
    }

    public void setOperationKey(String operationKey) {
        this.operationKey = operationKey;
    }


    public String toString() {
        return "RoleResourceOperation{" +
                "roleId='" + roleId + '\'' +
                ", resourceId='" + resourceId + '\'' +
                ", operationKey='" + (operationKey==null?"":operationKey) + '\'' +
                '}';
    }
    
}