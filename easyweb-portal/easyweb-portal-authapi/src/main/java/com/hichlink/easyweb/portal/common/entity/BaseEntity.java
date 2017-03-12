package com.hichlink.easyweb.portal.common.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class BaseEntity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5869181224979219137L;
	// 其他的参数我们把它分装成一个Map对象
	private Map<String, Object> others = new HashMap<String, Object>();
	
	public Map<String, Object> getOthers() {
		return others;
	}

	public void setOthers(Map<String, Object> params) {
		this.others = params;
	}
	
	public void addOtherField(String key, Object value){
		
		this.others.put(key, value);
	}
}
