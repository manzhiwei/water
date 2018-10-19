package com.welltech.waterAffair.domain.dto;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class ArchivesRecord implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	private Integer num;
	
	private String checkPeople;

	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm") 
	private Date checkTime;

	private String checkType;

	private Integer checkTypeValue;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCheckPeople() {
		return checkPeople;
	}

	public void setCheckPeople(String checkPeople) {
		this.checkPeople = checkPeople;
	}

	public Date getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}

	public String getCheckType() {
		return checkType;
	}

	public void setCheckType(String checkType) {
		this.checkType = checkType;
	}

	public Integer getCheckTypeValue() {
		return checkTypeValue;
	}

	public void setCheckTypeValue(Integer checkTypeValue) {
		this.checkTypeValue = checkTypeValue;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}
	
}