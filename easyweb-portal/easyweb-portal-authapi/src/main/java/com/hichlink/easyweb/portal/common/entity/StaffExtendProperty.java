package com.hichlink.easyweb.portal.common.entity;

import java.io.Serializable;

public class StaffExtendProperty implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2589343753540793700L;
	private Long staffId;
	private String property;
	private String value;
	
	public Long getStaffId() {
		return staffId;
	}
	public void setStaffId(Long staffId) {
		this.staffId = staffId;
	}
	public String getProperty() {
		return property;
	}
	public void setProperty(String property) {
		this.property = property;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
