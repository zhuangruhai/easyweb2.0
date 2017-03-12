package com.hichlink.easyweb.portal.common.entity;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;



public class Resource extends BaseEntity  {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 2000294270349307314L;

    /** 鉴权. */
    public static final String AUTH_TYPE_AUTH = "AUTH";    
    
    /** 不鉴权. */
    public static final String AUTH_TYPE_UNAUTH = "UNAUTH";
    
    /** 登录鉴权. */
    public static final String AUTH_TYPE_LOGIN_AUTH = "LOGIN_AUTH";
    
    /**
     * 资源ID.
     */
    private Long resourceId;

    /**
     * 资源外码.
     */
    private String resourceKey;

    /**
     * 资源名称.
     */
    private String resourceName;

    /**
     * 资源描述.
     */
    private String resourceDesc;

    /**
     * 资源分类.
     */
    private Long categoryId;

    /**
     * 元数据ID.
     */
    private String metadataId;
    
    /**
     * 鉴权类型（UNAUTH、LOGIN_AUTH、空表示鉴权）.
     */
    private String authType;

    /**
     * 管理域：SP、SYSADMIN.
     */
    private String domain;
    
    /**
     * 资源顺序
     */
    private int orderKey;
    
    private String entryUrl;
    
    public String getEntryUrl() {
		return entryUrl;
	}

	public void setEntryUrl(String entryUrl) {
		this.entryUrl = entryUrl;
	}

	/**
     * 操作索引
     */
    private Map<String,Operation> operationsMap = new HashMap<String,Operation>();

    /**
     * 操作.
     */
    private List<Operation> operations = new LinkedList<Operation>();

    private ResourceCategory resourceCategory;

    public ResourceCategory getResourceCategory() {
        return resourceCategory;
    }

    public void setResourceCategory(ResourceCategory resourceCategory) {
        this.resourceCategory = resourceCategory;
    }

    /**
     * 角色
     */
    private List<Role> roles = new LinkedList<Role>();
    
    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    /*
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Resource) {
            Resource resource = (Resource) obj;
            if(resource.getResourceId() == null){
            	if(resource.getResourceKey() != null){
            		return resource.getResourceKey().equals(this.resourceKey);
            	}
            	return false;
            }
            return resource.getResourceId().equals(this.resourceId);
        }
        return false;
    }

    /*
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        if (this.resourceId == null) {
            return 0;
        }
        return this.resourceId.hashCode();
    }

    @Override
    public String toString() {
        return this.categoryId + " --> " + this.resourceId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getMetadataId() {
        return metadataId;
    }

    public void setMetadataId(String metadataId) {
        this.metadataId = metadataId;
    }

    public List<Operation> getOperations() {
        return operations;
    }

    public void setOperations(List<Operation> operations) {
        this.operations = operations;
    }

    public String getResourceDesc() {
        return resourceDesc;
    }

    public void setResourceDesc(String resourceDesc) {
        this.resourceDesc = resourceDesc;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public String getResourceKey() {
        return resourceKey;
    }

    public void setResourceKey(String resourceKey) {
        this.resourceKey = resourceKey;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getAuthType() {
        return authType;
    }

    public void setAuthType(String authType) {
        this.authType = authType;
    }

	/**
	 * @return the operationsMap
	 */
	public Map<String, Operation> getOperationsMap() {
		return operationsMap;
	}

	/**
	 * @param operationsMap the operationsMap to set
	 */
	public void setOperationsMap(Map<String, Operation> operationsMap) {
		this.operationsMap = operationsMap;
	}

	/**
	 * @return the orderKey
	 */
	public int getOrderKey() {
		return orderKey;
	}

	/**
	 * @param orderKey the orderKey to set
	 */
	public void setOrderKey(int orderKey) {
		this.orderKey = orderKey;
	}
}