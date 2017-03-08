package com.aspire.webbas.portal.common.entity;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class ResourceCategory extends BaseEntity {
    /**
     * 
     */
    private static final long serialVersionUID = 8412644575707832273L;

    /**
     * 资源分类ID.
     */
    private Long categoryId;

    /**
     * 资源分类名称.
     */
    private String categoryName;

    /**
     * 资源分类描述.
     */
    private String categoryDesc;

    /**
     * 上级资源分类ID.
     */
    private Long parentId;

    /**
     * 资源分类外码.
     */
    private String categoryKey;

    /**
     * 元数据ID.
     */
    private String metadataId;
    
    /**
     * 管理域(admin,sp)
     */
    private String domain;

    /**
     * 上级资源分类对象.
     */
    private ResourceCategory parent;
    
    /**
     * 资源顺序
     */
    private int orderKey;
    
    /**
     * 资源分类是否被用户拥有
     */
    private boolean isOwned;
    
    public boolean isOwned() {
        return isOwned;
    }

    public void setOwned(boolean isOwned) {
        this.isOwned = isOwned;
    }

    /**
     * 资源分类索引
     */
    private Map<String,ResourceCategory> childrenMap = new HashMap<String,ResourceCategory>();
    
    /**
     * 资源索引
     */
    private Map<String,Resource> resourcesMap = new HashMap<String,Resource>();

    /**
     * 资源列表.
     */
    private List<Resource> resources = new LinkedList<Resource>();

    /**
     * 资源列表.
     */
    private List<ResourceCategory> children = new LinkedList<ResourceCategory>();

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ResourceCategory) {
            ResourceCategory category = (ResourceCategory) obj;
            if(category.getCategoryId() == null){
            	return category == this;
            }
            return category.getCategoryId().equals(this.categoryId);
        }
        return false;
    }

    @Override
    public int hashCode() {
        if (this.categoryId == null) {
            return 0;
        }
        return this.categoryId.hashCode();
    }

    @Override
    public String toString() {
        return this.categoryKey;
    }

    public void addResource(Resource resource) {
        this.resources.add(resource);
    }

    public String getCategoryDesc() {
        return categoryDesc;
    }

    public void setCategoryDesc(String categoryDesc) {
        this.categoryDesc = categoryDesc;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public ResourceCategory getParent() {
        return parent;
    }

    public void setParent(ResourceCategory parent) {
        this.parent = parent;
    }

    public Long getParentId() {
        return parentId;
    }

    public List<Resource> getResources() {
        return resources;
    }

    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }

    public String getCategoryKey() {
        return categoryKey;
    }

    public void setCategoryKey(String categoryKey) {
        this.categoryKey = categoryKey;
    }

    public List<ResourceCategory> getChildren() {
        return children;
    }

    public void setChildren(List<ResourceCategory> children) {
        this.children = children;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getMetadataId() {
        return metadataId;
    }

    public void setMetadataId(String metadataId) {
        this.metadataId = metadataId;
    }

	/**
	 * @return the childrenMap
	 */
	public Map<String, ResourceCategory> getChildrenMap() {
		return childrenMap;
	}

	/**
	 * @param childrenMap the childrenMap to set
	 */
	public void setChildrenMap(Map<String, ResourceCategory> childrenMap) {
		this.childrenMap = childrenMap;
	}

	/**
	 * @return the resourcesMap
	 */
	public Map<String, Resource> getResourcesMap() {
		return resourcesMap;
	}

	/**
	 * @param resourcesMap the resourcesMap to set
	 */
	public void setResourcesMap(Map<String, Resource> resourcesMap) {
		this.resourcesMap = resourcesMap;
	}

	/**
	 * @return the domain
	 */
	public String getDomain() {
		return domain;
	}

	/**
	 * @param domain the domain to set
	 */
	public void setDomain(String domain) {
		this.domain = domain;
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