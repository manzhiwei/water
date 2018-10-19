package com.welltech.waterAffair.domain.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 每小时电磁水表实时数据
 * @author WangXin
 *
 */
public class Ndata implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 腔体温度
	 */
	private Float temp;

	/**
	 * 正向累计流量
	 */
    private Float totalflow;

    /**
     * 反向累计流量
     */
    private Float ftotalflow;

    /**
     * 瞬时流量
     */
    private Float flow;

    /**
     * 
     */
    private Integer flowerror;

    /**
     * 电极信号
     */
    private Integer esignal;

    /**
     * 空管测量值
     */
    private Integer depdata;

    /**
     * 电池电量
     */
    private Float me;

    /**
     * 信号强度
     */
    private Integer signalStrength;

    /**
     * 数据记录时间
     */
    private Date iTime;

    /**
     * 压力
     */
    private Float press;

    /**
     * 供电电压
     */
    private Float currentv;

    /**
     * 供电电流
     */
    private Float currenti;

    private Integer presserror;

    /**
     * 净累计流量
     */
    private Float ntotalflow;

    /**
     * 链接持续时间
     */
    private Integer lastconnecting;

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

    public Float getFtotalflow() {
        return ftotalflow;
    }

    public void setFtotalflow(Float ftotalflow) {
        this.ftotalflow = ftotalflow;
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

    public Integer getEsignal() {
        return esignal;
    }

    public void setEsignal(Integer esignal) {
        this.esignal = esignal;
    }

    public Integer getDepdata() {
        return depdata;
    }

    public void setDepdata(Integer depdata) {
        this.depdata = depdata;
    }

    public Float getMe() {
        return me;
    }

    public void setMe(Float me) {
        this.me = me;
    }

    public Integer getSignalStrength() {
        return signalStrength;
    }

    public void setSignalStrength(Integer signalStrength) {
        this.signalStrength = signalStrength;
    }

    public Date getiTime() {
        return iTime;
    }

    public void setiTime(Date iTime) {
        this.iTime = iTime;
    }

    public Float getPress() {
        return press;
    }

    public void setPress(Float press) {
        this.press = press;
    }

    public Float getCurrentv() {
        return currentv;
    }

    public void setCurrentv(Float currentv) {
        this.currentv = currentv;
    }

    public Float getCurrenti() {
        return currenti;
    }

    public void setCurrenti(Float currenti) {
        this.currenti = currenti;
    }

    public Integer getPresserror() {
        return presserror;
    }

    public void setPresserror(Integer presserror) {
        this.presserror = presserror;
    }

    public Float getNtotalflow() {
        return ntotalflow;
    }

    public void setNtotalflow(Float ntotalflow) {
        this.ntotalflow = ntotalflow;
    }

    public Integer getLastconnecting() {
        return lastconnecting;
    }

    public void setLastconnecting(Integer lastconnecting) {
        this.lastconnecting = lastconnecting;
    }

	@Override
	public String toString() {
		return "Ndata [temp=" + temp + ", totalflow=" + totalflow + ", ftotalflow=" + ftotalflow + ", flow=" + flow
				+ ", flowerror=" + flowerror + ", esignal=" + esignal + ", depdata=" + depdata + ", me=" + me
				+ ", signalStrength=" + signalStrength + ", iTime=" + iTime + ", press=" + press + ", currentv="
				+ currentv + ", currenti=" + currenti + ", presserror=" + presserror + ", ntotalflow=" + ntotalflow
				+ ", lastconnecting=" + lastconnecting + "]";
	}
    
}