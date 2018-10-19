package com.welltech.waterAffair.domain.entity;

import java.io.Serializable;

/**
 * 公司实体
 * @author WangXin
 *
 */
public class Company implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 公司id
	 */
    private Integer companyId;

    /**
     * 公司名称
     */
    private String companyName;

    /**
     * 上级公司id
     */
    private Integer parentCompanyId;

    /**
     * 公司级别
     */
    private String companyLevel;
    
    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName == null ? null : companyName.trim();
    }

    public Integer getParentCompanyId() {
        return parentCompanyId;
    }

    public void setParentCompanyId(Integer parentCompanyId) {
        this.parentCompanyId = parentCompanyId;
    }

	public String getCompanyLevel() {
		return companyLevel;
	}

	public void setCompanyLevel(String companyLevel) {
		this.companyLevel = companyLevel;
	}
}