package com.hichlink.easyweb.core.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class BaseEntity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3121771295441905966L;
	// 其他的参数我们把它分装成一个Map对象
	private Map<String, Object> others = new HashMap<String, Object>();
	
	public Map<String, Object> getOthers() {
		return others;
	}

	public void setOthers(Map<String, Object> params) {
		this.others = params;
	}
	public Object getOtherField(String key){
		return this.others.get(key);
	}
	public void addOtherField(String key, Object value){
		
		this.others.put(key, value);
	}
}
