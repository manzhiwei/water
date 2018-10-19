package com.welltech.waterAffair.domain.entity;

import java.io.Serializable;

/**
 * 公司水表关系实体
 * @author WangXin
 *
 */
public class MeterType implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 水表类型id
	 */
    private Integer id;

    /**
     * 水表类型名称
     */
    private String meterType;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMeterType() {
		return meterType;
	}

	public void setMeterType(String meterType) {
		this.meterType = meterType;
	}

}