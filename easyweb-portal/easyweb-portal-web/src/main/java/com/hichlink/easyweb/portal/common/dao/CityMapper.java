/*** Auto generator by codegenerator 2014-12-04 17:53:44*/
package com.aspire.webbas.portal.common.dao;

import java.util.List;

import com.aspire.webbas.portal.common.entity.City;

public interface CityMapper {
    int deleteByPrimaryKey(Integer cityId);

    int insert(City record);

    int insertSelective(City record);

    List<City> list(City page);

    City selectByPrimaryKey(Integer cityId);

    int updateByPrimaryKeySelective(City record);

    int updateByPrimaryKey(City record);
}
