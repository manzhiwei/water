package com.welltech.waterAffair.domain.vo;

import java.io.Serializable;
import java.util.Date;


/**
 * The persistent class for the ndata171 database table.
 * 
 */
public class NdataVo implements Serializable {
	private static final long serialVersionUID = 1L;

	public NdataVo(Float currentI, Float currentV, Integer depdata, Integer esignal, Float flow, Integer flowerror,
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



	private Float increaseTotalflow;//今日用量

	private Double increaseTotalflowMonth;//本月用量

	//18-10-20 add
	private String instrNo;

	public NdataVo() {
	}

	public NdataVo(String subUserName, Integer num, Date i_time) {
		this.subUserName = subUserName;
		this.num = num;
		this.currentI = 0f;
		this.currentV = 0f;
		this.depdata = 0;
		this.esignal = 0;
		this.flow = 0f;
		this.flowerror = 0;
		this.ftotalflow = 0f;
		this.i_time = i_time;
		this.lastConnecting = 0;
		this.me = 0f;
		this.ntotalflow = 0f;
		this.press = 0f;
		this.pressError = 0;
		this.signal_strength = 0;
		this.temp = 0f;
		this.totalflow = 0f;
	}
	
	public NdataVo(String subUserName, Integer num, Date i_time,Float flow,Float press,Float totalflow) {
		this.subUserName = subUserName;
		this.num = num;
		this.currentI = 0f;
		this.currentV = 0f;
		this.depdata = 0;
		this.esignal = 0;
		this.flow = flow;
		this.flowerror = 0;
		this.ftotalflow = 0f;
		this.i_time = i_time;
		this.lastConnecting = 0;
		this.me = 0f;
		this.ntotalflow = 0f;
		this.press = press;//初始化为null
		this.pressError = 0;
		this.signal_strength = 0;
		this.temp = 0f;
		this.totalflow = totalflow;//初始化为null
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
	public Float getIncreaseTotalflow() {
		return increaseTotalflow;
	}

	public void setIncreaseTotalflow(Float increaseTotalflow) {
		this.increaseTotalflow = increaseTotalflow;
	}

	public Double getIncreaseTotalflowMonth() {
		return increaseTotalflowMonth;
	}

	public void setIncreaseTotalflowMonth(Double increaseTotalflowMonth) {
		this.increaseTotalflowMonth = increaseTotalflowMonth;
	}

	public String getInstrNo() {
		return instrNo;
	}

	public void setInstrNo(String instrNo) {
		this.instrNo = instrNo;
	}
}