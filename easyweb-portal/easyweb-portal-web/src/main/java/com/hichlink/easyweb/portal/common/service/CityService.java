package com.hichlink.easyweb.portal.common.service;

import java.util.List;

import com.hichlink.easyweb.portal.common.entity.City;


/**
 * 
 * <b>Title：</b>CityService.java<br/>
 * <b>Description：</b> <br/>
 * <b>@author： </b>zhuangruhai<br/>
 * <b>@date：</b>2014-12-04 17:39:49<br/>
 * <b>Copyright (c) 2014 ASPire Tech.</b>
 * 
 */
public interface CityService {
	City get(Integer cityId);
	List<City> list(City page);
}