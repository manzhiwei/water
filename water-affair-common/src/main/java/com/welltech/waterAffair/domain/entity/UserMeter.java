package com.welltech.waterAffair.domain.entity;

import java.io.Serializable;

/**
 * 用户水表关系实体
 * @author WangXin
 *
 */
public class UserMeter implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 用户id
	 */
    private Integer userId;

    /**
     * 水表id
     */
    private Integer meterId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getMeterId() {
        return meterId;
    }

    public void setMeterId(Integer meterId) {
        this.meterId = meterId;
    }
}