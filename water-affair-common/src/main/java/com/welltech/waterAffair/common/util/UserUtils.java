package com.welltech.waterAffair.common.util;

import com.welltech.waterAffair.domain.entity.User;

/**
 * 用户工具类
 * @author wangxin
 *
 */
public class UserUtils {
	
	private static final String USERID = "aid";
	
	/**
	 * 是否为admin用户
	 * @param user
	 * @return
	 */
	public static boolean isAdmin(User user){
		if(user == null){
			throw new IllegalArgumentException("null object");
		}
		if("admin".equals(user.getUserName())){
			return true;
		}
		if(user.getCompanyId() == null){
			return true;
		}
		return false;
	}
	
	/**
	 * 保存userId
	 * @param userId
	 */
	public static void saveUserId(Integer userId){
		if(userId == null){
			throw new IllegalArgumentException("null object");
		}
		SpringWebUtils.getSession().setAttribute(USERID, userId);
	}
	
	/**
	 * 获取userId
	 * @return
	 */
	public static Integer getUserId(){
		Integer userId = (Integer) SpringWebUtils.getSession().getAttribute(USERID);
		if(userId == null || userId <= 0){
			throw new IllegalAccessError("null userId, maybe not login");
		}
		return userId;
	}
	
}