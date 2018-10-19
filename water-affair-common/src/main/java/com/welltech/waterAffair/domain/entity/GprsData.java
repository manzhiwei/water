package com.welltech.waterAffair.domain.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 电磁流量计数据
 * @author WangXin
 *
 */
public class GprsData implements Serializable{
	
    /**
	 * 
	 */
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
     * 瞬时流量单位
     */
    private Integer flowunit;

    /**
     * 累计流量单位
     */
    private Integer totalunit;

    /**
     * 信号强度
     */
    private Byte signalIntensity;

    private Integer bz25State;

    private Float qmax;

    private Float qpercent;

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

    public Integer getFlowunit() {
        return flowunit;
    }

    public void setFlowunit(Integer flowunit) {
        this.flowunit = flowunit;
    }

    public Integer getTotalunit() {
        return totalunit;
    }

    public void setTotalunit(Integer totalunit) {
        this.totalunit = totalunit;
    }

    public Byte getSignalIntensity() {
        return signalIntensity;
    }

    public void setSignalIntensity(Byte signalIntensity) {
        this.signalIntensity = signalIntensity;
    }

    public Integer getBz25State() {
        return bz25State;
    }

    public void setBz25State(Integer bz25State) {
        this.bz25State = bz25State;
    }

    public Float getQmax() {
        return qmax;
    }

    public void setQmax(Float qmax) {
        this.qmax = qmax;
    }

    public Float getQpercent() {
        return qpercent;
    }

    public void setQpercent(Float qpercent) {
        this.qpercent = qpercent;
    }
}