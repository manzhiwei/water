package com.welltech.waterAffair.domain.criteria;

import java.util.Date;

public class NdataCriteria {
	
	private Integer meterId;
	
	private Date startDate;
	
	private Date endDate;

	private Date currentTime;

	private String currentDate;

	public Integer getMeterId() {
		return meterId;
	}

	public void setMeterId(Integer meterId) {
		this.meterId = meterId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getCurrentTime() {
		return currentTime;
	}

	public void setCurrentTime(Date currentTime) {
		this.currentTime = currentTime;
	}

	public String getCurrentDate() {
		return currentDate;
	}

	public void setCurrentDate(String currentDate) {
		this.currentDate = currentDate;
	}
}
