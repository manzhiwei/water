package com.welltech.waterAffair.domain.entity;

import java.io.Serializable;

/**
 * 用户实体
 * @author WangXin
 *
 */
public class User implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 用户id
	 */
    private Integer userId;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 公司id
     */
    private Integer companyId;
    
    /**
     * 权限
     */
    private Integer modifyPermission;

    public User() {
	}
    
	public User(Integer userId, String userName, String password, Integer companyId, Integer modifyPermission) {
		this.userId = userId;
		this.userName = userName;
		this.password = password;
		this.companyId = companyId;
		this.modifyPermission = modifyPermission;
	}

	public User(User user){
		this.userId = user.getUserId();
		this.userName = user.getUserName();
		this.password = user.getPassword();
		this.companyId = user.getCompanyId();
		this.modifyPermission = user.modifyPermission;
	}

	public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

	public Integer getModifyPermission() {
		return modifyPermission;
	}

	public void setModifyPermission(Integer modifyPermission) {
		this.modifyPermission = modifyPermission;
	}
}