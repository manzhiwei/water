package com.welltech.controller.page;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.welltech.waterAffair.common.util.UserUtils;
import com.welltech.waterAffair.domain.entity.AlarmUserConfig;
import com.welltech.waterAffair.service.AlarmService;


@Controller
public class AlarmPageController {
	
	@Autowired
	private AlarmService alarmService;
	
	//告警单点更新设置
    @RequestMapping(value = { "/updateAlarmSingleSetting" }, method = {RequestMethod.GET,RequestMethod.POST})
    public String updateAlarmSingleSetting(HttpServletRequest request, Model model) {
        String mname=request.getParameter("mname");
        String tname=request.getParameter("tname");
        //更新某个水表的告警配置
        String mid=mname.substring(mname.indexOf("=")+1,mname.length());
        String type=tname.substring(tname.lastIndexOf("=")+1,tname.length());

        AlarmUserConfig alarmSetting4Limit=alarmService.findConfigByMidAndTypeAndAlarmType(Integer.valueOf(mid),type,"limit");
        AlarmUserConfig alarmSetting4Change=alarmService.findConfigByMidAndTypeAndAlarmType(Integer.valueOf(mid),type,"change");

        alarmSetting4Limit=alarmSetting4Limit==null?(new AlarmUserConfig()):alarmSetting4Limit;
        alarmSetting4Limit.setMid(Integer.valueOf(mid));

        alarmSetting4Change=alarmSetting4Change==null?(new AlarmUserConfig()):alarmSetting4Change;
        alarmSetting4Change.setMid(Integer.valueOf(mid));

        alarmService.saveConfig(alarmService.alarmSettingCreate(alarmSetting4Limit,"limit",type,request));
        alarmService.saveConfig(alarmService.alarmSettingCreate(alarmSetting4Change,"change",type,request));

        return "redirect:/alarmSingleSetting?mid="+(mid)+"&type="+type;
    }
}
