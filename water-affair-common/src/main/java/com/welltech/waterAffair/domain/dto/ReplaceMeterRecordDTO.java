package com.welltech.waterAffair.domain.dto;

public class ReplaceMeterRecordDTO {
	private Integer id;
	private Integer num;
	private String checkPeople;
	private String checkTime;
	private String checkType;
	private String checkTypeValue;
	private String shortName;
	
	public ReplaceMeterRecordDTO(Integer id, Integer num, String checkPeople, String checkTime, String checkType,
			String checkTypeValue, String shortName) {
		this.id = id;
		this.num = num;
		this.checkPeople = checkPeople;
		this.checkTime = checkTime;
		this.checkType = checkType;
		this.checkTypeValue = checkTypeValue;
		this.shortName = shortName;
	}
	
	public ReplaceMeterRecordDTO() {
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public String getCheckPeople() {
		return checkPeople;
	}
	public void setCheckPeople(String checkPeople) {
		this.checkPeople = checkPeople;
	}
	public String getCheckTime() {
		return checkTime;
	}
	public void setCheckTime(String checkTime) {
		this.checkTime = checkTime;
	}
	public String getCheckType() {
		return checkType;
	}
	public void setCheckType(String checkType) {
		this.checkType = checkType;
	}
	public String getCheckTypeValue() {
		return checkTypeValue;
	}
	public void setCheckTypeValue(String checkTypeValue) {
		this.checkTypeValue = checkTypeValue;
	}
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	
}
