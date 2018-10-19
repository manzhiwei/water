package com.welltech.waterAffair.domain.entity;

import java.io.Serializable;
import java.util.Date;

public class GprsDataFor4200 implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 仪表id
     */
    private Integer flowid;

    /**
     * 采样时间
     */
    private Date time;

    /**
     * 顺时流量
     */
    private Float flow;

    /**
     * 正向累计流量
     */
    private Float ftotal;

    /**
     * 反向累计流量
     */
    private Float rtotal;

    /**
     * 信号强度
     */
    private Integer signalIntensity;

    private Integer alarm;

    private Float speed;

    private String tub;

    public Integer getFlowid() {
        return flowid;
    }

    public void setFlowid(Integer flowid) {
        this.flowid = flowid;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Float getFlow() {
        return flow;
    }

    public void setFlow(Float flow) {
        this.flow = flow;
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

    public Integer getSignalIntensity() {
        return signalIntensity;
    }

    public void setSignalIntensity(Integer signalIntensity) {
        this.signalIntensity = signalIntensity;
    }

    public Integer getAlarm() {
        return alarm;
    }

    public void setAlarm(Integer alarm) {
        this.alarm = alarm;
    }

    public Float getSpeed() {
        return speed;
    }

    public void setSpeed(Float speed) {
        this.speed = speed;
    }

    public String getTub() {
        return tub;
    }

    public void setTub(String tub) {
        this.tub = tub;
    }
}
