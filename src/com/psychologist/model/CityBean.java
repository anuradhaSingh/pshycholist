package com.psychologist.model;

import java.io.Serializable;

public class CityBean {
	
	private Integer cityId;
    private String cityName;

	/**
	 * @return the cityId
	 */
	public Integer getCityId() {
		return cityId;
	}


	/**
	 * @param cityId the cityId to set
	 */
	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}


	/**
	 * @return the cityName
	 */
	public String getCityName() {
		return cityName;
	}


	/**
	 * @param cityName the cityName to set
	 */
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}



}
