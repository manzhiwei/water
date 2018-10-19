package com.welltech.waterAffair.domain.vo.before;

import java.util.Date;


public class ArchivesRecordVo {
	private Integer id;
	private Integer num;
	
	private String checkPeople;

	private String checkTime;

	private String checkType;

	private Integer checkTypeValue;
	private Integer resourceImgId;
	
	private String fileName;

	public ArchivesRecordVo(Integer id, Integer num, String checkPeople,
			String checkTime, String checkType, Integer checkTypeValue,
			 String fileName,Integer resourceImgId) {
		this.id = id;
		this.num = num;
		this.checkPeople = checkPeople;
		this.checkTime = checkTime;
		this.checkType = checkType;
		this.checkTypeValue = checkTypeValue;
		this.resourceImgId = resourceImgId;
		this.fileName = fileName;
	}

	public Integer getResourceImgId() {
		return resourceImgId;
	}

	public void setResourceImgId(Integer resourceImgId) {
		this.resourceImgId = resourceImgId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
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

	public Integer getCheckTypeValue() {
		return checkTypeValue;
	}

	public void setCheckTypeValue(Integer checkTypeValue) {
		this.checkTypeValue = checkTypeValue;
	}
	
}