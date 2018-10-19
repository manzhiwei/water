package com.welltech.waterAffair.domain.vo;

import java.util.List;

/**
 * 前端页面对象专门给datatable写的一个分页对象
 * 
 * @author zhoupei
 *
 */
public class PageByDataTableVo<T> {

	private String sEcho;
	private int iTotalRecords;
	private int iTotalDisplayRecords;
	private int iRecordsDisplay;
	/** 具体记录 */
	private List<T> aaData;
	public String getsEcho() {
		return sEcho;
	}
	public void setsEcho(String sEcho) {
		this.sEcho = sEcho;
	}
	public int getiTotalRecords() {
		return iTotalRecords;
	}
	public void setiTotalRecords(int iTotalRecords) {
		this.iTotalRecords = iTotalRecords;
	}
	public int getiTotalDisplayRecords() {
		return iTotalDisplayRecords;
	}
	public void setiTotalDisplayRecords(int iTotalDisplayRecords) {
		this.iTotalDisplayRecords = iTotalDisplayRecords;
	}
	public List<T> getAaData() {
		return aaData;
	}
	public void setAaData(List<T> aaData) {
		this.aaData = aaData;
	}
	public PageByDataTableVo(String sEcho, int iTotalRecords,
			int iTotalDisplayRecords, List<T> aaData) {
		super();
		this.sEcho = sEcho;
		this.iTotalRecords = iTotalRecords;
		this.iTotalDisplayRecords = iTotalRecords;
		this.aaData = aaData;
	}
	public int getiRecordsDisplay() {
		return iRecordsDisplay;
	}
	public void setiRecordsDisplay(int iRecordsDisplay) {
		this.iRecordsDisplay = iRecordsDisplay;
	}

}
