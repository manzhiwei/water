/*
 * Copyright (c) 2016 NeuLion, Inc. All Rights Reserved.
 */
package com.welltech.controller;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.welltech.waterAffair.common.base.Properties;
import com.welltech.waterAffair.common.util.UserUtils;
import com.welltech.waterAffair.domain.entity.MachineInfo;
import com.welltech.waterAffair.domain.entity.Ndata;
import com.welltech.waterAffair.domain.entity.NdataSs;
import com.welltech.waterAffair.service.MeterService;
import com.welltech.waterAffair.service.MobileService;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.mobile.device.Device;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.welltech.waterAffair.common.base.Properties;
import com.welltech.waterAffair.common.util.UserUtils;
import com.welltech.waterAffair.domain.entity.MachineInfo;
import com.welltech.waterAffair.domain.entity.NdataSs;
import com.welltech.waterAffair.service.MobileService;

@Controller
public class MobilePageController {

	private static Logger logger = Logger
			.getLogger(MobilePageController.class);

	@Resource
	private Properties properties;

	@Resource
	private MeterService meterService;


	@Resource
	MobileService mobileService;

	//移动端登录请求
	@RequestMapping(value = { "/login" }, method = RequestMethod.GET)
	public String login() {
		return "login";
	}


	//移动端首页
	@RequestMapping(value = { "/" }, method = RequestMethod.GET)
	public String dashboard(HttpServletRequest request, HttpServletResponse response, Device device, Model model) {

		//添加用户下属区域
		model.addAttribute("usernameIds", mobileService.findUserCompanyList(UserUtils.getUserId()));
		//model.addAttribute("usernameIds", mobileService.findUserCompanyList(1));
		//显示页面中可以查询的参数列表
		String showCol=properties.showCol;
		String[] temp=showCol.split(",",-1);
		Map<String,String> coles=new LinkedHashMap<String,String>();
		for(String t:temp){
			String[] tmp=t.split(":");
			coles.put(tmp[0], tmp[1]);
		}
		model.addAttribute("coles", coles);

		return "mobile/m-index";
	}

	/**
	 * 查看手机历史数据
	 * @param id	水表号
	 * @param type	需要查看的类型——瞬时流量:flow,净累计:ntotalflow,正向累计总量:totalflow,反向累计总量:ftotalflow,瞬时压力:press,信号强度:signal_strength
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "/history" }, method = RequestMethod.GET)
	public String history(Integer id,HttpServletRequest request,String type,Model model) {

		//读取请求时页面的日期
		String date=request.getParameter("date");
        String area=request.getParameter("area");

		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		String showCol=properties.showCol;
		String[] temp=showCol.split(",",-1);
		Map<String,String> coles=new LinkedHashMap<String,String>();
		for(String t:temp){
			String[] tmp=t.split(":");
			if(tmp[1].equals(type)){
				coles.put(tmp[0], tmp[1]);
				model.addAttribute("type", type);
				model.addAttribute("typeName", tmp[0]+"(m³/h)");
			}
		}
		MachineInfo meter=mobileService.findMeterInfo(id);

		model.addAttribute("num",id);//传过来的水表号
		model.addAttribute("meterName", meter.getShortName());
		model.addAttribute("coles", coles);
		date=(date==null?sdf.format(new Date()):date);
		model.addAttribute("currentDate", date);
		model.addAttribute("backUrl", "/?area="+area);
		return "mobile/m-history";
	}
	
	/**
	 * 查看手机一个小时内的历史数据页面
	 * @param id	水表号
	 * @param type	需要查看的类型——瞬时流量:flow,净累计:ntotalflow,正向累计总量:totalflow,反向累计总量:ftotalflow,瞬时压力:press,信号强度:signal_strength
	 * @param time	时间是哪一个小时:2017020110
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "/hishour" }, method = RequestMethod.GET)
	public String history(Integer id,String type,String date,Integer time,Integer interval, Model model) throws ParseException {

		List<List<Object>> d1 = new ArrayList<List<Object>>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


		for(NdataSs data:mobileService.findMeterMinuteDataByDate(id,dateFormat.parse(date+" "+time+":00:00"),dateFormat.parse(date+" "+(time + interval - 1)+":59:59"))){
			List<Object> oneMeterData= new ArrayList<Object>();
			oneMeterData.add(data.getTime());//因为是UNIX时间戳，所以*1000
			String tmp=null;
			try {
				if("signal_strength".equals(type)){//信号强度去掉，因为没有
					break;
				}
				tmp= BeanUtils.getProperty(data, type);
			} catch (Exception e) {
				e.printStackTrace();
				tmp="0";
			}
			oneMeterData.add(tmp);
			d1.add(oneMeterData);
		}
		model.addAttribute("num",id);//传过来的水表号
		String showCol=properties.showCol;
		String[] temp=showCol.split(",",-1);
		String typeName="";
		Map<String,String> coles=new LinkedHashMap<String,String>();
		for(String t:temp){
			String[] tmp=t.split(":");
			if(tmp[1].equals(type)){
				coles.put(tmp[0], tmp[1]);
				model.addAttribute("type", type);
				model.addAttribute("typeName", tmp[0]);
				typeName=tmp[0];
				break;
			}
		}
		MachineInfo m=mobileService.findMeterInfo(id);
		model.addAttribute("meterName", m.getSubUserName());
		model.addAttribute("date", date);
		model.addAttribute("time", time);
		model.addAttribute("result", d1);
		model.addAttribute("type", typeName);
		model.addAttribute("backUrl", "/history?id="+id+"&type="+type+"&date="+date);
		return "mobile/m-hishour";
	}

	//移动端仪表详情页面
	@RequestMapping(value = { "/detail" }, method = RequestMethod.GET)
	public String detail(Integer id,String type,String date,Model model) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		MachineInfo machineInfo=mobileService.findMeterInfo(id);
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date start;
		Ndata n=new Ndata();
		try {
			start = dateFormat1.parse(date+" 00:00:00");
			Date end=dateFormat1.parse(date+" 23:59:59");
			List<Ndata> ndata=mobileService.findNdataByCriteriaLastConnecting(id, start, end);
			if(ndata!=null&&ndata.size()>0){
				n=ndata.get(ndata.size()-1);
			}else{
				n.setLastconnecting(0);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("ndata", n);
		model.addAttribute("machine", machineInfo);
		if(machineInfo.getActiveTime()!=null){
			model.addAttribute("installDate", dateFormat.format(machineInfo.getActiveTime()));
		}else{
			model.addAttribute("installDate","无" );
		}
		model.addAttribute("backUrl", "/history?id="+id+"&type="+type+"&date="+date);
		return "mobile/m-history_details";
	}

	@RequestMapping(value = { "/map" }, method = RequestMethod.GET)
	public String map(Integer id,Model model) {
		return "mobile/m-map";
	}

	//移动端退出页面
	@RequestMapping(value = { "/logout" }, method = RequestMethod.GET)
	public String logout() {
		return "login";
	}


	//移动端错误页面
	@RequestMapping(value = { "/error" }, method = RequestMethod.GET)
	public String error(HttpServletRequest request,Model model) {
		model.addAttribute("error", "error");
		return "login";
	}
}

