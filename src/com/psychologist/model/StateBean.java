package com.psychologist.model;

import java.io.Serializable;

public class StateBean {
	private Integer stateId;

	
	private String stateName;


	/**
	 * @return the stateId
	 */
	public Integer getStateId() {
		return stateId;
	}


	/**
	 * @param stateId the stateId to set
	 */
	public void setStateId(Integer stateId) {
		this.stateId = stateId;
	}


	/**
	 * @return the stateName
	 */
	public String getStateName() {
		return stateName;
	}


	/**
	 * @param stateName the stateName to set
	 */
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

}
