package com.hichlink.easyweb.portal.common.entity;

import java.io.Serializable;

public class SubSystem implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7218954853841966736L;
	private String subSystemId;
	private String subSystemName;
	private String subSystemDesc;
	private String hopDomain;
	private String interfaceDomain;
	private String deployMode;
	private String domain;
	
	public String getSubSystemId() {
		return subSystemId;
	}
	public void setSubSystemId(String subSystemId) {
		this.subSystemId = subSystemId;
	}
	public String getSubSystemName() {
		return subSystemName;
	}
	public void setSubSystemName(String subSystemName) {
		this.subSystemName = subSystemName;
	}
	public String getSubSystemDesc() {
		return subSystemDesc;
	}
	public void setSubSystemDesc(String subSystemDesc) {
		this.subSystemDesc = subSystemDesc;
	}
	public String getHopDomain() {
		return hopDomain;
	}
	public void setHopDomain(String hopDomain) {
		this.hopDomain = hopDomain;
	}
	public String getInterfaceDomain() {
		return interfaceDomain;
	}
	public void setInterfaceDomain(String interfaceDomain) {
		this.interfaceDomain = interfaceDomain;
	}
	public String getDeployMode() {
		return deployMode;
	}
	public void setDeployMode(String deployMode) {
		this.deployMode = deployMode;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
}
