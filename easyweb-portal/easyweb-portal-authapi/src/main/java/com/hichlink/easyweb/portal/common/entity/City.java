/*** Auto generator by codegenerator 2014-12-04 17:53:30*/
package com.aspire.webbas.portal.common.entity;


public class City {
    /**
    市ID
     */
    private Integer cityId;

    /**
    城市名称
     */
    private String cityName;

    /**
    省份ID
     */
    private Integer provinceId;

    /**
    区号
     */
    private String areaCode;

    /**
    市ID
     * @return the value of city.CITY_ID
     */
    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    /**
    城市名称
     * @return the value of city.CITY_NAME
     */
    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName == null ? null : cityName.trim();
    }

    /**
    省份ID
     * @return the value of city.PROVINCE_ID
     */
    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    /**
    区号
     * @return the value of city.AREA_CODE
     */
    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode == null ? null : areaCode.trim();
    }
}
