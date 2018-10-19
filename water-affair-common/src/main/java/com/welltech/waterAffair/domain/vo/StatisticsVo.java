package com.welltech.waterAffair.domain.vo;

public class StatisticsVo {
	
	private String ssll;//瞬时流量
	private String jrll;//今日累计流量
	private String zrtq;//昨日同期瞬时
	private String zrll;//昨日瞬时流量
	
	private String receiveTime;//接收时间
	private String deciverType;//设备类型
	private String addressInstall;//安装地址
	
	private String flow;//瞬时流量
	private String press;//压力
	private String totalflow;//正向累计
	private String ftotalflow;//反向累计
	private String voltage;//电压
	private String current;//电流
	private String signal;//信号强度
	
	public String getJrll() {
		return jrll;
	}
	public void setJrll(String jrll) {
		this.jrll = jrll;
	}
	public String getZrtq() {
		return zrtq;
	}
	public void setZrtq(String zrtq) {
		this.zrtq = zrtq;
	}
	public String getZrll() {
		return zrll;
	}
	public void setZrll(String zrll) {
		this.zrll = zrll;
	}
	public String getReceiveTime() {
		return receiveTime;
	}
	public void setReceiveTime(String receiveTime) {
		this.receiveTime = receiveTime;
	}
	public String getDeciverType() {
		return deciverType;
	}
	public void setDeciverType(String deciverType) {
		this.deciverType = deciverType;
	}
	public String getAddressInstall() {
		return addressInstall;
	}
	public void setAddressInstall(String addressInstall) {
		this.addressInstall = addressInstall;
	}
	public String getSsll() {
		return ssll;
	}
	public void setSsll(String ssll) {
		this.ssll = ssll;
	}
	public String getFlow() {
		return flow;
	}
	public void setFlow(String flow) {
		this.flow = flow;
	}
	public String getPress() {
		return press;
	}
	public void setPress(String press) {
		this.press = press;
	}
	public String getTotalflow() {
		return totalflow;
	}
	public void setTotalflow(String totalflow) {
		this.totalflow = totalflow;
	}
	public String getFtotalflow() {
		return ftotalflow;
	}
	public void setFtotalflow(String ftotalflow) {
		this.ftotalflow = ftotalflow;
	}
	public String getVoltage() {
		return voltage;
	}
	public void setVoltage(String voltage) {
		this.voltage = voltage;
	}
	public String getCurrent() {
		return current;
	}
	public void setCurrent(String current) {
		this.current = current;
	}
	public String getSignal() {
		return signal;
	}
	public void setSignal(String signal) {
		this.signal = signal;
	}
	
}
