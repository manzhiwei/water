package com.welltech.controller.rest;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.welltech.waterAffair.common.util.UserUtils;
import com.welltech.waterAffair.domain.entity.MachineInfo;
import com.welltech.waterAffair.service.CompanyService;
import com.welltech.waterAffair.service.MeterService;
import com.welltech.waterAffair.service.ReportService;

/**
 * 
 * @author WangXin
 *
 */
@RestController
public class ReportRestController {

    @Resource
    private ReportService reportService;

	@Resource
	private CompanyService companyService;
	
	@Resource
	private MeterService meterService;
    
	@RequestMapping(value="/getDayReport", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getDayReport(HttpServletRequest request,@RequestParam String station, @RequestParam String date){
		Map<String, Object> map = new HashMap<String, Object>();
		Integer userId = UserUtils.getUserId();
		List<MachineInfo> machineInfo2s = meterService.findUserMeterList(userId);
		List<MachineInfo> condition = new ArrayList<MachineInfo>();
		for(MachineInfo info2 : machineInfo2s){
			if(info2.getShortName().equals(station)){
				condition.add(info2);
				break;
			}
		}

		if (date == null || date.length() == 0) {
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
			date = sdf1.format(new Date());
		}

		String[][] result =reportService.reportDay(userId,date,condition);
		map.put("result", result);
		return map;
	}
	
	@RequestMapping(value="/getMonthReport", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getMonthReprt(HttpServletRequest request,@RequestParam String station, @RequestParam String date){
		Map<String, Object> map = new HashMap<String, Object>();
		Integer userId = UserUtils.getUserId();
		List<MachineInfo> machineInfo2s = meterService.findUserMeterList(userId);
		
		List<MachineInfo> condition = new ArrayList<MachineInfo>();
		for(MachineInfo info2 : machineInfo2s){
			if(info2.getShortName().equals(station)){
				condition.add(info2);
				break;
			}
		}
		
		if (date == null || date.length() == 0) {
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM");
			date = sdf1.format(new Date());
		}
		
		String[][] result =reportService.reportMonth(userId, condition, date);
		map.put("result", result);
		return map;
	}
	
	@RequestMapping(value="/getSeasonReport", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getSeasonReprt(HttpServletRequest request,@RequestParam String station){
		Map<String, Object> map = new HashMap<String, Object>();
		Integer userId = UserUtils.getUserId();
		List<MachineInfo> machineInfo2s = meterService.findUserMeterList(userId);
		List<MachineInfo> condition = new ArrayList<MachineInfo>();
		for(MachineInfo info2 : machineInfo2s){
			if(info2.getShortName().equals(station)){
				condition.add(info2);
				break;
			}
		}
		
		String[][] result =reportService.reportSeason(userId, condition);
		map.put("result", result);
		return map;
	}
	
	@RequestMapping(value="/getYearReport", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getYearReprt(HttpServletRequest request,@RequestParam String station,@RequestParam String date){
		Map<String, Object> map = new HashMap<String, Object>();
		Integer userId = UserUtils.getUserId();
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy");
		if (date == null || date.length() == 0) {
			date = sdf1.format(new Date());
		}
		List<MachineInfo> machineInfo2s = meterService.findUserMeterList(userId);
		List<MachineInfo> condition = new ArrayList<MachineInfo>();
		for(MachineInfo info2 : machineInfo2s){
			if(info2.getShortName().equals(station)){
				condition.add(info2);
				break;
			}
		}
		
		String[][] result =reportService.reportYear(userId, condition,date);
		map.put("result", result);
		return map;
	}
}
