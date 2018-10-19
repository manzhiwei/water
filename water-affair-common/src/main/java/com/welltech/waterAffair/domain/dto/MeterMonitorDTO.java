package com.welltech.waterAffair.domain.dto;

import java.io.Serializable;
import java.util.Date;


/**
 * The persistent class for the ndata171 database table.
 * 
 */
public class MeterMonitorDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	public MeterMonitorDTO(Float currentI, Float currentV, Integer depdata, Integer esignal, Float flow, Integer flowerror,
						   Float ftotalflow, Date i_time, Integer lastConnecting, Float me, Float ntotalflow, Float press,
						   Integer pressError, Integer signal_strength, Float temp, Float totalflow) {
		super();
		this.currentI = currentI;
		this.currentV = currentV;
		this.depdata = depdata;
		this.esignal = esignal;
		this.flow = flow;
		this.flowerror = flowerror;
		this.ftotalflow = ftotalflow;
		this.i_time = i_time;
		this.lastConnecting = lastConnecting;
		this.me = me;
		this.ntotalflow = ntotalflow;
		this.press = press;
		this.pressError = pressError;
		this.signal_strength = signal_strength;
		this.temp = temp;
		this.totalflow = totalflow;
	}

	private Float currentI;

	private Float currentV;

	private Integer depdata;

	private Integer esignal;

	private Float flow;

	private Integer flowerror;

	private Float ftotalflow;

	private Date i_time;

	private Integer lastConnecting;

	private Float me;

	private Float ntotalflow;

	private Float press;

	private Integer pressError;

	private Integer signal_strength;

	private Float temp;

	private Float totalflow;

	private String subUserName;//水表名

	private Integer num;//水表号

	private String companyName;//公司名

	private Integer companyId;//公司id

	public MeterMonitorDTO() {
	}

	public Float getCurrentI() {
		return currentI;
	}

	public void setCurrentI(Float currentI) {
		this.currentI = currentI;
	}

	public Float getCurrentV() {
		return currentV;
	}

	public void setCurrentV(Float currentV) {
		this.currentV = currentV;
	}

	public Integer getDepdata() {
		return depdata;
	}

	public void setDepdata(Integer depdata) {
		this.depdata = depdata;
	}

	public Integer getEsignal() {
		return esignal;
	}

	public void setEsignal(Integer esignal) {
		this.esignal = esignal;
	}

	public Float getFlow() {
		return flow;
	}

	public void setFlow(Float flow) {
		this.flow = flow;
	}

	public Integer getFlowerror() {
		return flowerror;
	}

	public void setFlowerror(Integer flowerror) {
		this.flowerror = flowerror;
	}

	public Float getFtotalflow() {
		return ftotalflow;
	}

	public void setFtotalflow(Float ftotalflow) {
		this.ftotalflow = ftotalflow;
	}

	public Date getI_time() {
		return i_time;
	}

	public void setI_time(Date i_time) {
		this.i_time = i_time;
	}

	public Integer getLastConnecting() {
		return lastConnecting;
	}

	public void setLastConnecting(Integer lastConnecting) {
		this.lastConnecting = lastConnecting;
	}

	public Float getMe() {
		return me;
	}

	public void setMe(Float me) {
		this.me = me;
	}

	public Float getNtotalflow() {
		return ntotalflow;
	}

	public void setNtotalflow(Float ntotalflow) {
		this.ntotalflow = ntotalflow;
	}

	public Float getPress() {
		return press;
	}

	public void setPress(Float press) {
		this.press = press;
	}

	public Integer getPressError() {
		return pressError;
	}

	public void setPressError(Integer pressError) {
		this.pressError = pressError;
	}

	public Integer getSignal_strength() {
		return signal_strength;
	}

	public void setSignal_strength(Integer signal_strength) {
		this.signal_strength = signal_strength;
	}

	public Float getTemp() {
		return temp;
	}

	public void setTemp(Float temp) {
		this.temp = temp;
	}

	public Float getTotalflow() {
		return totalflow;
	}

	public void setTotalflow(Float totalflow) {
		this.totalflow = totalflow;
	}

	public String getSubUserName() {
		return subUserName;
	}

	public void setSubUserName(String subUserName) {
		this.subUserName = subUserName;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

}