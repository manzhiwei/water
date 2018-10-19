package com.welltech.waterAffair.domain.vo;

import java.util.Date;

/**
 * 时用水统计分析前端显示对象
 * @author demoklis
 *
 */
public class WaterStatisticsHourVo {
	/**站点*/
	private String shortName="";
	/**抄表时间*/
	private Date iTime;
	/**瞬时流量值*/
	private String flow="";
	/**正向累计流量值*/
	private String totalflow="";
	/**反向累计流量值*/
	private String fTotalflow="";
	/**净累计流量值*/
	private String ntotalflow="";
	/**瞬时压力值*/
	private String press="";
	/**同比*/
	private String yearByYear="";
	/**环比*/
	private String mothByMonth="";
	/**明细*/
	private String detail="";
	/**公司*/
	private String companyName="";
	

	public WaterStatisticsHourVo(String shortName, Date iTime, String flow,
			String totalflow, String fTotalflow, String ntotalflow,
			String press,String detail) {
		super();
		this.shortName = shortName==null?"":shortName;
		this.iTime = iTime;
		this.flow = flow==null?"":flow;
		this.totalflow = totalflow==null?"":totalflow;
		this.fTotalflow = fTotalflow==null?"":fTotalflow;
		this.ntotalflow = ntotalflow==null?"":ntotalflow;
		this.press = press==null?"":press;
		this.detail=detail==null?"":detail;
	}
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	public Date getiTime() {
		return iTime;
	}
	public void setiTime(Date iTime) {
		this.iTime = iTime;
	}
	public String getFlow() {
		return flow;
	}
	public void setFlow(String flow) {
		this.flow = flow;
	}
	public String getTotalflow() {
		return totalflow;
	}
	public void setTotalflow(String totalflow) {
		this.totalflow = totalflow;
	}
	public String getfTotalflow() {
		return fTotalflow;
	}
	public void setfTotalflow(String fTotalflow) {
		this.fTotalflow = fTotalflow;
	}
	public String getNtotalflow() {
		return ntotalflow;
	}
	public void setNtotalflow(String ntotalflow) {
		this.ntotalflow = ntotalflow;
	}
	public String getPress() {
		return press;
	}
	public void setPress(String press) {
		this.press = press;
	}
	public String getYearByYear() {
		return yearByYear;
	}
	public void setYearByYear(String yearByYear) {
		this.yearByYear = yearByYear;
	}
	public String getMothByMonth() {
		return mothByMonth;
	}
	public void setMothByMonth(String mothByMonth) {
		this.mothByMonth = mothByMonth;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}	
	
}
