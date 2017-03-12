package com.hichlink.easyweb.portal.common.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hichlink.easyweb.portal.common.dao.CityMapper;
import com.hichlink.easyweb.portal.common.entity.City;
import com.hichlink.easyweb.portal.common.service.CityService;

@Service("cityService")
public class CityServiceImpl implements CityService{
	@Autowired
	private CityMapper cityMapper;

	public List<City> list(City page) {
		return cityMapper.list(page);
	}

	public City get(Integer cityId) {
		return cityMapper.selectByPrimaryKey(cityId);
	}

}
