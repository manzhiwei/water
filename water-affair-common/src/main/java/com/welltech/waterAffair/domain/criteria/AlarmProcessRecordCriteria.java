package com.welltech.waterAffair.domain.criteria;

import java.util.Date;
import java.util.List;

import com.welltech.waterAffair.common.aop.pagination.annotation.Paging;
import com.welltech.waterAffair.common.aop.pagination.bean.Page;
import com.welltech.waterAffair.common.aop.pagination.bean.Pagination;

/**
 * 分页查询警告
 * @author Peter
 *
 */
@Paging(field = "page")
public class AlarmProcessRecordCriteria {
	private Page page = new Pagination();
	
	private Date startTime;
	private Date endTime;
	private List<Integer> nums;
	private String alarmType;
	private String status;
	private List<String> alarmContents;
	
	public Page getPage() {
		return page;
	}
	public void setPage(Page page) {
		this.page = page;
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
	public List<Integer> getNums() {
		return nums;
	}
	public void setNums(List<Integer> nums) {
		this.nums = nums;
	}
	public String getAlarmType() {
		return alarmType;
	}
	public void setAlarmType(String alarmType) {
		this.alarmType = alarmType;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<String> getAlarmContents() {
		return alarmContents;
	}
	public void setAlarmContents(List<String> alarmContents) {
		this.alarmContents = alarmContents;
	}

}
