package com.welltech.waterAffair.domain.vo;

import java.io.Serializable;
import java.util.Date;


/**
 * The persistent class for the ndatass140 database table.
 * 
 */
public class NdatassVo implements Serializable {
	private static final long serialVersionUID = 1L;

	public NdatassVo(String flow, String ftotalflow, Date i_time, String press, Long time, String totalflow,
			String ntotalflow) {
		super();
		this.flow = flow;
		this.ftotalflow = ftotalflow;
		this.i_time = i_time;
		this.press = press;
		this.time = time;
		this.totalflow = totalflow;
		this.ntotalflow = ntotalflow;
	}

	private String flow;

	private String ftotalflow;

	private Date i_time;

	private String press;

	private Long time;

	private String totalflow;

	private String ntotalflow;

	public NdatassVo() {
	}

	public String getFlow() {
		return flow;
	}

	public void setFlow(String flow) {
		this.flow = flow;
	}

	public String getFtotalflow() {
		return ftotalflow;
	}

	public void setFtotalflow(String ftotalflow) {
		this.ftotalflow = ftotalflow;
	}

	public Date getI_time() {
		return i_time;
	}

	public void setI_time(Date i_time) {
		this.i_time = i_time;
	}

	public String getPress() {
		return press;
	}

	public void setPress(String press) {
		this.press = press;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	public String getTotalflow() {
		return totalflow;
	}

	public void setTotalflow(String totalflow) {
		this.totalflow = totalflow;
	}

	public String getNtotalflow() {
		return ntotalflow;
	}

	public void setNtotalflow(String ntotalflow) {
		this.ntotalflow = ntotalflow;
	}

}