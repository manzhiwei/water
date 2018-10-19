package com.welltech.waterAffair.domain.criteria;

import java.util.Date;

import com.welltech.waterAffair.common.aop.pagination.annotation.Paging;
import com.welltech.waterAffair.common.aop.pagination.bean.Page;
import com.welltech.waterAffair.common.aop.pagination.bean.Pagination;

/**
 * 仪表更换记录查询条件
 * @author WangXin
 *
 */
@Paging(field = "page")
public class MeterReplaceCriteria{

	private Page page = new Pagination();
	
	private String stations;

	private Date startTime;
	
	private Date endTime;

	private String checkType;
	
	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public String getStations() {
		return stations;
	}

	public void setStations(String stations) {
		this.stations = stations;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getCheckType() {
		return checkType;
	}

	public void setCheckType(String checkType) {
		this.checkType = checkType;
	}
	 
}