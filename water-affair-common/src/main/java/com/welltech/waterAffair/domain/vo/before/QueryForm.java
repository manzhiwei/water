package com.welltech.waterAffair.domain.vo.before;

import java.util.List;

public class QueryForm {
	
	private String startTime;
	
	private String endTime;
	
	private List<String> stations;

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public List<String> getStations() {
		return stations;
	}

	public void setStations(List<String> stations) {
		this.stations = stations;
	}

}
