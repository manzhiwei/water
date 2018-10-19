package com.welltech.waterAffair.domain.dto;

import java.util.Date;

/**
 * 流量压力趋势
 * @author zhoupei
 *
 */
public class FlowAndPressTrendDTO {
	/**统计时间*/
	private Date time;
	/**仪表Id*/
	private String num;
	/**站点*/
	private String station;
	/**瞬时流量*/
	private Float flow;
	/**瞬时压力*/
	private Float press;
	/**正向累计流量*/
	private Float ftotal;
	/**反向累计流量*/
	private Float rtotal;

	public FlowAndPressTrendDTO(Date time, String num, Float flow, Float press,
                                Float ftotal, Float rtotal, String station) {
		super();
		this.time = time;
		this.num = num;
		this.flow = flow;
		this.press = press;
		this.ftotal = ftotal;
		this.rtotal = rtotal;
		this.station = station;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public Float getFlow() {
		return flow;
	}
	public void setFlow(Float flow) {
		this.flow = flow;
	}
	public Float getPress() {
		return press;
	}
	public void setPress(Float press) {
		this.press = press;
	}
	public Float getFtotal() {
		return ftotal;
	}
	public void setFtotal(Float ftotal) {
		this.ftotal = ftotal;
	}
	public Float getRtotal() {
		return rtotal;
	}
	public void setRtotal(Float rtotal) {
		this.rtotal = rtotal;
	}
	public String getStation() {
		return station;
	}
	public void setStation(String station) {
		this.station = station;
	}
	
}
