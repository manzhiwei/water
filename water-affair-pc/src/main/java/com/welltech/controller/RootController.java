package com.welltech.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.welltech.waterAffair.common.base.WelltechSessionContext;
import com.welltech.waterAffair.common.util.SpringWebUtils;
import com.welltech.waterAffair.service.AlarmService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.welltech.waterAffair.common.util.ConstantsUtil;
import com.welltech.waterAffair.common.util.UserUtils;
import com.welltech.waterAffair.domain.entity.Company;
import com.welltech.waterAffair.domain.entity.MeterType;
import com.welltech.waterAffair.domain.entity.User;
import com.welltech.waterAffair.service.BasicManageService;
import com.welltech.waterAffair.service.CompanyService;

@Controller
public class RootController {

	private static Logger logger = Logger
			.getLogger(RootController.class);

    @Autowired
    private CompanyService companyService;
	@Resource
	private BasicManageService basicManageService;

	@Autowired
	private AlarmService alarmService;

	@RequestMapping(value = { "/login" }, method = RequestMethod.GET)
	public String login() {
		return "login";
	}

	//登录成功后首页
	@RequestMapping(value = { "/" }, method = RequestMethod.GET)
	public String dashboard(HttpServletRequest request, HttpServletResponse response,Model model) {
//		model.addAttribute("dashboard","active");
//		model.addAttribute("type","raw");

		Integer userId = UserUtils.getUserId();
		User user = companyService.getUser(userId);
		request.getSession().setAttribute("userName", user.getUserName());
		Company company = companyService.getCompany(userId);
		request.getSession().setAttribute("company", company == null ? "" : company.getCompanyName());

		if(user.getUserName().equals("admin")){
	    	List<MeterType> meterTypeList=basicManageService.queryMeterType();
			model.addAttribute("basic","active");
			model.addAttribute("meterBasicList","active");
	        model.addAttribute("meterTypeList", meterTypeList);
	        model.addAttribute("meterSizes", ConstantsUtil.sbkjDic);
			return "basic/meterBasicList";
		}else{

//			model.addAttribute("basic","active");
//			model.addAttribute("todayMeterInfoList","active");
			// model.addAttribute("meterDevicesMonitoring", result);
			SpringWebUtils.getSession().setAttribute("totalAmount", alarmService.getTotalAlarmRecord(userId));
			return "basic/todayMeterInfoList";

//			return "basic/todayMeterInfoList";
//			return "dashboard/index";
		}
	}

	@RequestMapping(value = { "/logout" }, method = RequestMethod.GET)
	public String logout() {
		return "login";
	}

	@RequestMapping(value = { "/error" }, method = RequestMethod.GET)
	public String error(HttpServletRequest request,Model model) {
		model.addAttribute("error", "error");
		return "login";
	}

}

