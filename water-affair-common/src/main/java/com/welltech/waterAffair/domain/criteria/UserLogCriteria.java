package com.welltech.waterAffair.domain.criteria;

import java.util.Date;

import com.welltech.waterAffair.common.aop.pagination.annotation.Paging;
import com.welltech.waterAffair.common.aop.pagination.bean.Page;
import com.welltech.waterAffair.common.aop.pagination.bean.Pagination;

/**
 * 用户操作记录查询条件
 * @author WangXin
 *
 */
@Paging(field = "page")
public class UserLogCriteria{

	private Page page = new Pagination();
	
	/**
     * 开始时间
     */
    private Date startTime;
    
    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 值
     */
    private String eventValue;

    /**
     * 用户ids
     */
    private String userIds;

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

	public String getEventValue() {
		return eventValue;
	}

	public void setEventValue(String eventValue) {
		this.eventValue = eventValue;
	}

	public String getUserIds() {
		return userIds;
	}

	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}

}