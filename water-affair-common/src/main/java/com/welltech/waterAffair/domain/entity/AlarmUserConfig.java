package com.welltech.waterAffair.domain.entity;

import java.io.Serializable;

/**
 * 告警配置
 * @author WangXin
 *
 */
public class AlarmUserConfig implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

    private Integer mid;

    private String meterName;

    private String atype;

    private String mtype;

    private Float highHighValue;

    private Float highMiddleValue;

    private Float highLowValue;

    private Float lowLowValue;

    private Float lowMiddleValue;

    private Float lowHighValue;

    private Integer highHighType;

    private Integer highMiddleType;

    private Integer highLowType;

    private Integer lowLowType;

    private Integer lowMiddleType;

    private Integer lowHighType;

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

    public String getMeterName() {
        return meterName;
    }

    public void setMeterName(String meterName) {
        this.meterName = meterName == null ? null : meterName.trim();
    }

    public String getAtype() {
        return atype;
    }

    public void setAtype(String atype) {
        this.atype = atype == null ? null : atype.trim();
    }

    public String getMtype() {
        return mtype;
    }

    public void setMtype(String mtype) {
        this.mtype = mtype == null ? null : mtype.trim();
    }

    public Float getHighHighValue() {
        return highHighValue;
    }

    public void setHighHighValue(Float highHighValue) {
        this.highHighValue = highHighValue;
    }

    public Float getHighMiddleValue() {
        return highMiddleValue;
    }

    public void setHighMiddleValue(Float highMiddleValue) {
        this.highMiddleValue = highMiddleValue;
    }

    public Float getHighLowValue() {
        return highLowValue;
    }

    public void setHighLowValue(Float highLowValue) {
        this.highLowValue = highLowValue;
    }

    public Float getLowLowValue() {
        return lowLowValue;
    }

    public void setLowLowValue(Float lowLowValue) {
        this.lowLowValue = lowLowValue;
    }

    public Float getLowMiddleValue() {
        return lowMiddleValue;
    }

    public void setLowMiddleValue(Float lowMiddleValue) {
        this.lowMiddleValue = lowMiddleValue;
    }

    public Float getLowHighValue() {
        return lowHighValue;
    }

    public void setLowHighValue(Float lowHighValue) {
        this.lowHighValue = lowHighValue;
    }

    public Integer getHighHighType() {
        return highHighType;
    }

    public void setHighHighType(Integer highHighType) {
        this.highHighType = highHighType;
    }

    public Integer getHighMiddleType() {
        return highMiddleType;
    }

    public void setHighMiddleType(Integer highMiddleType) {
        this.highMiddleType = highMiddleType;
    }

    public Integer getHighLowType() {
        return highLowType;
    }

    public void setHighLowType(Integer highLowType) {
        this.highLowType = highLowType;
    }

    public Integer getLowLowType() {
        return lowLowType;
    }

    public void setLowLowType(Integer lowLowType) {
        this.lowLowType = lowLowType;
    }

    public Integer getLowMiddleType() {
        return lowMiddleType;
    }

    public void setLowMiddleType(Integer lowMiddleType) {
        this.lowMiddleType = lowMiddleType;
    }

    public Integer getLowHighType() {
        return lowHighType;
    }

    public void setLowHighType(Integer lowHighType) {
        this.lowHighType = lowHighType;
    }
}