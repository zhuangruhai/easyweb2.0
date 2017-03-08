package com.aspire.webbas.portal.common.entity;


/**
 * 操作地址域对象.
 */
public class OperationAddress extends BaseEntity {
    /**
     * 
     */
    private static final long serialVersionUID = 748718941228442377L;

    /**
     * 访问地址ID.
     */
    private Long addressId;

    /**
     * 资源ID.
     */
    private Long resourceId;

    /**
     * 资源操作外码.
     */
    private String operationKey;

    /**
     * 访问地址名称.
     */
    private String operationAddressName;

    /**
     * 访问地址URL（不包含协议、IP、端口、contextpath）.
     */
    private String operationAddressUrl;

    /**
     * 参数名一.
     */
    private String parameterName1;

    /**
     * 参数值一.
     */
    private String parameterValue1;

    /**
     * 参数名二.
     */
    private String parameterName2;

    /**
     * 参数值二.
     */
    private String parameterValue2;

    /**
     * 参数名三.
     */
    private String parameterName3;

    /**
     * 参数值三.
     */
    private String parameterValue3;

    /**
     * 元数据ID.
     */
    private String metadataId;
    
    /**
     * 管理域(admin,sp)
     */
    private String domain;

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

	/*
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof OperationAddress) {
            OperationAddress operationAddress = (OperationAddress) obj;
            if(operationAddress.getAddressId() == null){
            	return operationAddress == this;
            }
            return operationAddress.getAddressId().equals(this.addressId);
        }
        return false;
    }

    /*
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        if (this.addressId == null) {
            return 0;
        }
        return this.addressId.hashCode();
    }

    @Override
    public String toString() {
        return this.resourceId + " --> " + this.operationKey + " --> " + this.addressId;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public String getMetadataId() {
        return metadataId;
    }

    public void setMetadataId(String metadataId) {
        this.metadataId = metadataId;
    }

    public String getOperationAddressName() {
        return operationAddressName;
    }

    public void setOperationAddressName(String operationAddressName) {
        this.operationAddressName = operationAddressName;
    }

    public String getOperationAddressUrl() {
        return operationAddressUrl;
    }

    public void setOperationAddressUrl(String operationAddressUrl) {
        this.operationAddressUrl = operationAddressUrl;
    }

    public String getOperationKey() {
        return operationKey;
    }

    public void setOperationKey(String operationKey) {
        this.operationKey = operationKey;
    }

    public String getParameterName1() {
        return parameterName1;
    }

    public void setParameterName1(String parameterName1) {
        this.parameterName1 = parameterName1;
    }

    public String getParameterName2() {
        return parameterName2;
    }

    public void setParameterName2(String parameterName2) {
        this.parameterName2 = parameterName2;
    }

    public String getParameterName3() {
        return parameterName3;
    }

    public void setParameterName3(String parameterName3) {
        this.parameterName3 = parameterName3;
    }

    public String getParameterValue1() {
        return parameterValue1;
    }

    public void setParameterValue1(String parameterValue1) {
        this.parameterValue1 = parameterValue1;
    }

    public String getParameterValue2() {
        return parameterValue2;
    }

    public void setParameterValue2(String parameterValue2) {
        this.parameterValue2 = parameterValue2;
    }

    public String getParameterValue3() {
        return parameterValue3;
    }

    public void setParameterValue3(String parameterValue3) {
        this.parameterValue3 = parameterValue3;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

}
