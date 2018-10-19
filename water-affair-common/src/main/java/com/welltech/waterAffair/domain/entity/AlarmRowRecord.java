package com.welltech.waterAffair.domain.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 告警原始记录
 * @author WangXin
 *
 */
public class AlarmRowRecord implements Serializable{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

    private Integer mid;

    private Integer metertype;

    private Float flow;

    private Float press;

    private Float me;

    private Float currentv;

    private Float currenti;

    private Integer esignal;

    private Integer depdata;

    private Float temp;

    private Integer flowerror;

    private Integer presserror;

    private Float totalf;

    private Float totalr;

    private Float totaln;

    private Integer signalstrength;

    private Integer lastconnecting;

    private Integer flowunit;

    private Date createtime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getMid() {
        return mid;
    }

    public void setMid(Integer mid) {
        this.mid = mid;
    }

    public Integer getMetertype() {
        return metertype;
    }

    public void setMetertype(Integer metertype) {
        this.metertype = metertype;
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

    public Float getMe() {
        return me;
    }

    public void setMe(Float me) {
        this.me = me;
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

    public Float getTemp() {
        return temp;
    }

    public void setTemp(Float temp) {
        this.temp = temp;
    }

    public Integer getFlowerror() {
        return flowerror;
    }

    public void setFlowerror(Integer flowerror) {
        this.flowerror = flowerror;
    }

    public Integer getPresserror() {
        return presserror;
    }

    public void setPresserror(Integer presserror) {
        this.presserror = presserror;
    }

    public Float getTotalf() {
        return totalf;
    }

    public void setTotalf(Float totalf) {
        this.totalf = totalf;
    }

    public Float getTotalr() {
        return totalr;
    }

    public void setTotalr(Float totalr) {
        this.totalr = totalr;
    }

    public Float getTotaln() {
        return totaln;
    }

    public void setTotaln(Float totaln) {
        this.totaln = totaln;
    }

    public Integer getSignalstrength() {
        return signalstrength;
    }

    public void setSignalstrength(Integer signalstrength) {
        this.signalstrength = signalstrength;
    }

    public Integer getLastconnecting() {
        return lastconnecting;
    }

    public void setLastconnecting(Integer lastconnecting) {
        this.lastconnecting = lastconnecting;
    }

    public Integer getFlowunit() {
        return flowunit;
    }

    public void setFlowunit(Integer flowunit) {
        this.flowunit = flowunit;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
}