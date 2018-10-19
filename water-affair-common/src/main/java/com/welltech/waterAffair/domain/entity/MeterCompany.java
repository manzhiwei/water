package com.welltech.waterAffair.domain.entity;

import java.io.Serializable;

/**
 * 公司水表关系实体
 * @author WangXin
 *
 */
public class MeterCompany implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 公司id
	 */
    private Integer companyId;

    /**
     * 水表id
     */
    private Integer meterId;

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getMeterId() {
        return meterId;
    }

    public void setMeterId(Integer meterId) {
        this.meterId = meterId;
    }
}