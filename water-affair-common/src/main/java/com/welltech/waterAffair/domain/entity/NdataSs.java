package com.welltech.waterAffair.domain.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 电磁水表5分钟采集数据
 * @author WangXin
 *
 */
public class NdataSs implements Serializable{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 采样时间
	 */
	private Date time;

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
	 * 数据记录时间
	 */
    private Date iTime;

    /**
	 * 压力测量值
	 */
    private Float press;

    /**
	 * 净累计流量
	 */
    private Float totalflown;



    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
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

    public Float getTotalflown() {
        return totalflown;
    }

    public void setTotalflown(Float totalflown) {
        this.totalflown = totalflown;
    }

	@Override
	public String toString() {
		return "NdataSs [time=" + time + ", totalflow=" + totalflow + ", ftotalflow=" + ftotalflow + ", flow=" + flow
				+ ", iTime=" + iTime + ", press=" + press + ", totalflown=" + totalflown + "]";
	}
    
}