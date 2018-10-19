package com.welltech.controller.rest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.welltech.waterAffair.domain.entity.*;
import com.welltech.waterAffair.service.CompanyService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.welltech.waterAffair.common.util.UserUtils;
import com.welltech.waterAffair.domain.vo.PageByDataTableVo;
import com.welltech.waterAffair.domain.vo.PageVo;
import com.welltech.waterAffair.service.AlarmService;
import com.welltech.waterAffair.service.MeterService;

import net.sf.json.JSONObject;

@RestController
public class AlarmRestController {
	
	@Autowired
	private MeterService meterService;
	
	@Autowired
	private AlarmService alarmService;

	@Autowired
    private CompanyService companyService;
	
	@RequestMapping(value = { "/queryWarningByCondition" })
	public PageByDataTableVo<AlarmProcessRecord> queryByCondition(String draw,String start,String length,String startTime, String endTime,String staions,HttpServletRequest request){
		Integer userId = UserUtils.getUserId();
		String alarmType = request.getParameter("alarmType");
		User user = companyService.getUser(userId);

		int startIndex=0;
		if(start==null){
			startIndex=0;
		}else{
			startIndex=Integer.valueOf(start);
		}
		int pageSize=0;
		if(length==null){
			pageSize=10;
		}else{
			pageSize=Integer.valueOf(length);
		} 
		Date start1 = null;
		Date end1 = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		try {
			if (startTime != null&&startTime.length()>0) {
				start1 = sdf.parse(startTime);
			}else{
				//默认当天零点开始
				start1=sdf.parse(sdf1.format(new Date())+" 00:00");
			}
			if (endTime != null&&endTime.length()>0) {
				end1 = sdf.parse(endTime);
			}else{
				end1=new Date();//默认当前时刻
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		List<Integer> s=new ArrayList<Integer>();;

		List<MachineInfo> machines = meterService.findUserMeterList(userId);
		
		if(staions!=null&&staions.length()>0){
			List<String> tmp=Arrays.asList(staions.split(",",-1));
			Map<String,Integer> temp=new HashMap<String,Integer>();
			for(MachineInfo t:machines){
				temp.put(t.getShortName(), t.getNum());
			}
			for(String t:tmp){
				Integer num=temp.get(t);
				if(num!=null){
					s.add(num);
				}
			}
		}else{//如果为空则默认全部用户所属水表
			for(MachineInfo t:machines){
				s.add(t.getNum());
			}
		}
		PageVo<AlarmProcessRecord> res1 = alarmService.queryProcessRecordByCondition(start1, end1, s, alarmType, UserUtils.isAdmin(user), startIndex, pageSize);
		PageByDataTableVo<AlarmProcessRecord> result=new PageByDataTableVo<AlarmProcessRecord>(draw,res1.getCountSize(),pageSize,res1.getEntity());
		return result;
	}
	
	//告警查询
    @RequestMapping(value = { "/queryAlarmByAid" }, method = {RequestMethod.GET,RequestMethod.POST})
    public String queryAlarmByAid(HttpServletRequest request, Model model) {
        String aid=request.getParameter("aid");
        AlarmProcessRecord alarmMessage = alarmService.findRecordById(Integer.valueOf(aid));
        return JSONObject.fromObject(alarmMessage).toString();
    }

    //阅读告警信息
    @RequestMapping(value = { "/readAlarmByAid" }, method = {RequestMethod.GET,RequestMethod.POST})
    public String readAlarmByAid(HttpServletRequest request, Model model) {
        String aid=request.getParameter("aid");
        AlarmProcessRecord alarmMessage = alarmService.findRecordById(Integer.valueOf(aid));
        alarmMessage.setStatus(2);
        alarmService.saveRecord(alarmMessage);
        return JSONObject.fromObject(alarmMessage).toString();
    }

    //阅读告警信息
    @RequestMapping(value = { "/closeAlarmByAid" }, method = {RequestMethod.GET,RequestMethod.POST})
    public String closeAlarmByAid(HttpServletRequest request, Model model) {
        String aid=request.getParameter("aid");
        AlarmProcessRecord alarmMessage=alarmService.findRecordById(Integer.valueOf(aid));
        alarmMessage.setStatus(0);
        alarmService.saveRecord(alarmMessage);
        return JSONObject.fromObject(alarmMessage).toString();
    }
    
    //清除告警信息
    @RequestMapping(value = { "/deleteAlarmByAid" }, method = {RequestMethod.GET,RequestMethod.POST})
    public boolean deleteAlarmByAid(HttpServletRequest request, Model model) {
        String aid=request.getParameter("aid");
        alarmService.deleteRecord(Long.valueOf(aid));
        return true;
    }
    
  //告警多点更新设置
    @RequestMapping(value = { "/updateAlarmMultiSetting" }, method = {RequestMethod.GET,RequestMethod.POST})
    public String updateAlarmMultiSetting(HttpServletRequest request, Model model) {
        String current_hhvalue=request.getParameter("current_hhvalue");
        String current_hhlevel=request.getParameter("current_hhlevel");
        String current_hmvalue=request.getParameter("current_hmvalue");
        String current_hmlevel=request.getParameter("current_hmlevel");
        String current_hlvalue=request.getParameter("current_hlvalue");
        String current_hllevel=request.getParameter("current_hllevel");
        String current_lhvalue=request.getParameter("current_lhvalue");
        String current_lhlevel=request.getParameter("current_lhlevel");
        String current_lmvalue=request.getParameter("current_lmvalue");
        String current_lmlevel=request.getParameter("current_lmlevel");
        String current_llvalue=request.getParameter("current_llvalue");
        String current_lllevel=request.getParameter("current_lllevel");

        String type=request.getParameter("type");
        String alarmType=request.getParameter("alarmType");
        String meterListString=request.getParameter("meterList");
        if(meterListString!=null) {
            if (meterListString.length() > 0) {
                for(String mid:(meterListString.substring(1,meterListString.length())).split(",")){
                    try {
                        AlarmUserConfig alarmSetting= alarmService.findConfigByMidAndTypeAndAlarmType(Integer.valueOf(mid),type,alarmType);
                        if(alarmSetting==null){
                            alarmSetting=new AlarmUserConfig();
                        }
                        alarmSetting.setMid(Integer.valueOf(mid));
                        alarmSetting.setAtype(alarmType);
                        alarmSetting.setMtype(type);
                        try{
                            if(current_hhvalue.length()>0){
                                alarmSetting.setHighHighValue(Float.valueOf(current_hhvalue));
                                alarmSetting.setHighHighType(Integer.valueOf(current_hhlevel));
                            }

                            if(current_hmvalue.length()>0){
                                alarmSetting.setHighMiddleValue(Float.valueOf(current_hmvalue));
                                alarmSetting.setHighMiddleType(Integer.valueOf(current_hmlevel));
                            }

                            if(current_hlvalue.length()>0){
                                alarmSetting.setHighLowValue(Float.valueOf(current_hlvalue));
                                alarmSetting.setHighLowType(Integer.valueOf(current_hllevel));
                            }

                            if(current_lhvalue.length()>0){
                                alarmSetting.setLowHighValue(Float.valueOf(current_lhvalue));
                                alarmSetting.setLowHighType(Integer.valueOf(current_lhlevel));
                            }

                            if(current_lmvalue.length()>0){
                                alarmSetting.setLowMiddleValue(Float.valueOf(current_lmvalue));
                                alarmSetting.setLowMiddleType(Integer.valueOf(current_lmlevel));
                            }

                            if(current_llvalue.length()>0){
                                alarmSetting.setLowLowValue(Float.valueOf(current_llvalue));
                                alarmSetting.setLowLowType(Integer.valueOf(current_lllevel));
                            }


                        }catch (Exception e){
                            return "error";
                        }

                        alarmService.saveConfig(alarmSetting);
                    }catch (Exception e){
                        return "false";
                    }
                }
            }else{
                return "false";
            }
        }
        return "true";
    }
    
}
