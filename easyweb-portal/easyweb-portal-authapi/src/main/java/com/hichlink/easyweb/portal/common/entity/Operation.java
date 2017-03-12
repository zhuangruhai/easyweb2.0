package com.hichlink.easyweb.portal.common.entity;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class Operation extends BaseEntity{
    /**
     * 
     */
    private static final long serialVersionUID = -7387368957838961357L;

    private Long operationId;

	/**
     * 资源ID.
     */
    private Long resourceId;
    /**
     * 资源外码.
     */
    private String resourceKey;

    /**
     * 操作外码.
     */
    private String operationKey;

    /**
     * 操作名称.
     */
    private String operationName;

    /**
     * 操作描述.
     */
    private String operationDesc;

    /**
     * 依赖操作key.
     */
    private String dependKey;
    
    /**
     * 被依赖操作key.
     */
    private String dependByKey;
    
    /**
     * 元数据ID.
     */
    private String metadataId;
    
    /**
     * 管理域(admin,sp)
     */
    private String domain;
    
    /**
     * 操作顺序
     */
    private int orderKey;

    /**
     * 操作地址.
     */
    private List<OperationAddress> operationAddresses = new LinkedList<OperationAddress>();
    
    /**
     *  操作地址索引
     */
    private Map<String,OperationAddress> operationAddressesMap = new HashMap<String,OperationAddress>();
    /*
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Operation) {
            Operation operation = (Operation) obj;
            if(operation.getResourceId() == null || operation.getOperationKey() == null){
            	return operation == this;
            }
            return operation.getResourceId().equals(this.resourceId)
                    && operation.getOperationKey().equals(this.operationKey);
        }
        return false;
    }

    /*
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        if (this.resourceId == null && this.operationKey == null) {
            return 0;
        }
        return (this.resourceId + this.operationKey).hashCode();
    }

    @Override
    public String toString() {
        return this.resourceId + " --> " + this.operationKey;
    }

    public Long getOperationId() {
		return operationId;
	}

	public void setOperationId(Long operationId) {
		this.operationId = operationId;
	}
	
    public String getMetadataId() {
        return metadataId;
    }

    public void setMetadataId(String metadataId) {
        this.metadataId = metadataId;
    }

    public String getOperationDesc() {
        return operationDesc;
    }

    public void setOperationDesc(String operationDesc) {
        this.operationDesc = operationDesc;
    }

    public String getOperationKey() {
        return operationKey;
    }

    public void setOperationKey(String operationKey) {
        this.operationKey = operationKey;
    }

    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public List<OperationAddress> getOperationAddresses() {
        return operationAddresses;
    }

    public void setOperationAddresses(List<OperationAddress> operationAddresses) {
        this.operationAddresses = operationAddresses;
    }

    public String getDependKey() {
        return dependKey;
    }

    public void setDependKey(String dependKey) {
        this.dependKey = dependKey;
    }

	public String getDependByKey() {
		return dependByKey;
	}

	public void setDependByKey(String dependByKey) {
		this.dependByKey = dependByKey;
	}

	/**
	 * @return the operationAddressesMap
	 */
	public Map<String, OperationAddress> getOperationAddressesMap() {
		return operationAddressesMap;
	}

	/**
	 * @param operationAddressesMap the operationAddressesMap to set
	 */
	public void setOperationAddressesMap(
			Map<String, OperationAddress> operationAddressesMap) {
		this.operationAddressesMap = operationAddressesMap;
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

	public String getResourceKey() {
		return resourceKey;
	}

	public void setResourceKey(String resourceKey) {
		this.resourceKey = resourceKey;
	}
	
}
