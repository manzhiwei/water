package com.welltech.waterAffair.domain.criteria;

import java.util.Date;

import com.welltech.waterAffair.common.aop.pagination.annotation.Paging;
import com.welltech.waterAffair.common.aop.pagination.bean.Page;
import com.welltech.waterAffair.common.aop.pagination.bean.Pagination;

/**
 * 时用水分页查询
 * @author Peter
 *
 */
@Paging(field = "page")
public class WaterHourCriteria {

	private Page page = new Pagination();
	
	private Integer meterId;
	
	private Date startDate;
	
	private Date endDate;

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

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

}
