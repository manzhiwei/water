package com.welltech.waterAffair.domain.dto;

/**
 * The persistent class for the usermeterauthority database table.
 * 
 */
public class UserMeterAuthorityDTO {
	private Integer meterId;
	private Integer userNameId;

	public UserMeterAuthorityDTO() {
	}

	public Integer getMeterId() {
		return this.meterId;
	}

	public void setMeterId(Integer meterId) {
		this.meterId = meterId;
	}

	public Integer getUserNameId() {
		return this.userNameId;
	}

	public void setUserNameId(Integer userNameId) {
		this.userNameId = userNameId;
	}

}