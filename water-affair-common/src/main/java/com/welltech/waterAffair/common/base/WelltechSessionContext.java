package com.welltech.waterAffair.common.base;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class WelltechSessionContext {

	public Object getAttribute(HttpServletRequest request, String name){
		HttpSession session = request.getSession();
		return session.getAttribute(name); 
	} 
	
	public void setAttribute(HttpServletRequest request, String name,Object value){
		HttpSession session = request.getSession();
		session.setAttribute(name, value);
	}
	
	public void removeAttribute(HttpServletRequest request, String name){
		HttpSession session = request.getSession();
		session.removeAttribute(name);
	}
	
	public String getSessionId(HttpServletRequest request){
		HttpSession session = request.getSession();
		return session.getId();
	}

	public Integer getCurrentAid(HttpServletRequest request) {
		Integer aid = (Integer) getAttribute(request, "aid");
		if(StringUtils.isNotBlank(""+aid)){
			return Integer.valueOf(aid);
		}
		return null;
	}


	public String getCurrentAidString(HttpServletRequest request) {
		Integer aid = (Integer) getAttribute(request, "aid");
		if(StringUtils.isNotBlank(""+aid)){
			return aid+"";
		}
		return null;
	}
	
}
