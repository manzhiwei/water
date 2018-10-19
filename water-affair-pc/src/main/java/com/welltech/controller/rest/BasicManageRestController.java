package com.welltech.controller.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.welltech.waterAffair.domain.vo.NdataVo;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.welltech.waterAffair.common.util.UserUtils;
import com.welltech.waterAffair.domain.dto.MachineInfoDTO;
import com.welltech.waterAffair.domain.entity.MachineInfo;
import com.welltech.waterAffair.domain.vo.PageVo;
import com.welltech.waterAffair.domain.vo.before.ArchivesRecordVo;
import com.welltech.waterAffair.service.BasicManageService;

/**
 * 基础供水管理controller
 * 
 * @author zhoupei
 *
 */
@RestController
public class BasicManageRestController {
	@Resource
	private BasicManageService basicManageService;

	/**
	 * 更新仪表经纬度信息
	 *
	 * @return
	 */
	@RequestMapping("/updateMeterGPSInfo")
	public String updateMeterGPSInfo(HttpServletRequest request) {

		String lalo=request.getParameter("lalo");
		String la=lalo.split("_")[0];
		String lo=lalo.split("_")[1];
		String num=lalo.split("_")[2];
		//meterBaseInfoManager.updateMachineGPS(Integer.valueOf(num),Double.valueOf(la),Double.valueOf(lo));
		//请将la和lo数据更新至数据库

		return "true";
	}

	/**
	 * 查询仪表基础信息
	 * 
	 * @return
	 */
	@RequestMapping("/queryMeterInfo")
	public PageVo<MachineInfoDTO> queryMeterInfo(String subUserName,boolean flag,HttpServletRequest request) {
		Integer userId=UserUtils.getUserId();
		PageVo<MachineInfoDTO> result = basicManageService
				.queryMachineInfo(userId,subUserName,flag);
		return result;
	}
  	/**
	 * 查询水表基础信息
	 */
	@RequestMapping("/queryMachineInfo")
	public PageVo<MachineInfoDTO> queryMachineInfo( HttpServletRequest request){
		Integer userId = UserUtils.getUserId();
		PageVo<MachineInfoDTO> result = basicManageService
				.queryMachineInfo(userId,null,false);
		return result;
	}

	/**
	 * 查看仪表更换信息
	 * 
	 * @param id
	 * @param model
	 * @reFurn
	 */
	/*@RequestMapping(value = { "/queryMeterChangeRecordById" })
	public PageVo<MeterChangeRecord> queryMeterChangeRecordById(HttpServletRequest request,Model model) {
		Integer userId=p2PSessionContext.getCurrentAid(request);
		return meterChangeRecordService.queryMeterChangeRecordById(userId);
	}*/

	/**
	 * 查看仪表更换信息
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	/*@RequestMapping(value = { "/queryMeterChangeRecordByCondition" })
	public PageVo<MeterChangeRecord> queryMeterChangeRecordByCondition(
			String startTime, String endTime, String staions, HttpServletRequest request,Model model) {
		Integer userId=p2PSessionContext.getCurrentAid(request);
		Date start = null;
		Date end = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try {
			if (startTime != null&&startTime.length()>0) {
				start = sdf.parse(startTime);
			}
			if (endTime != null&&endTime.length()>0) {
				end = sdf.parse(endTime);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		List<String> s=null;
		if(staions!=null&&staions.length()>0){
			s = Arrays.asList(staions.split(",",-1));
		}
		return meterChangeRecordService.queryByCondition(userId, start, end,
				s);
	}*/

	/**
	 * 查看检修信息
	 * 
	 * @param num
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "/queryArchivesRecord" })
	public PageVo<ArchivesRecordVo> queryArchivesRecord(Integer num, Model model) {
		return basicManageService.queryArchivesRecord(num);
	}
	
	/**
	 * 取所有站点
	 * 
	 * @param num
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "/queryAllStation" })
	public List<String> queryAllStation() {
		return basicManageService.queryAllStation();
	}
	
	/**
	 * 更新水表为vip
	 * 
	 * @param num	水表号
	 * @param isVip	是否是vip
	 * @return
	 */
	@RequestMapping(value = { "/updateMachineVip" })
	public boolean updateMachineVip(@RequestBody Map<String,String> queryForm) {
		Integer num=Integer.valueOf(queryForm.get("num"));
		boolean isVip=new Boolean(queryForm.get("vip"));
		return basicManageService.updateMachineVip(num,isVip);
	}
	
	/**
	 * isExists
	 * 验证水表号是否存在
	 * @param num	水表号
	 * @param isVip	是否是vip
	 * @return
	 */
	@RequestMapping(value = { "/isExists" })
	public Map<String,Boolean> isExists(Integer num) {
		MachineInfo m =basicManageService.queryMachineInfo2(num);
		Map<String, Boolean> map = new HashMap<>();
		if(m!=null){
			map.put("valid", false);
		}else{
			map.put("valid", true);
		}
		return map;
	}
	
	/**
	 * 
	 * 查询水表所在公司
	 * @param num	水表号
	 * @return
	 */
	@RequestMapping(value = { "/querycompanyList" })
	public Map<String,String> querycompanyList(Integer num) {
    	Integer userId=UserUtils.getUserId();
		String m =basicManageService.queryMachineInfoForCompany(num,userId);
		Map<String, String> map = new HashMap<>();
		map.put("company", m);
		return map;
	}

	/**
	 * 根据用户的id,查找仪表的实时信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value = {"/queryTodayInfo"})
	public PageVo<NdataVo> queryTodayInfo(HttpServletRequest request){
		Integer userId=UserUtils.getUserId();
		//查询水表的监控信息
		List<NdataVo> ndataVos=basicManageService.queryNdataLastData(userId);
		for(NdataVo m:ndataVos){
			float increaseTotalflow = basicManageService.queryIncreaseTotalflow(m.getNum(),m.getI_time());
			m.setIncreaseTotalflow(increaseTotalflow);
			m.setIncreaseTotalflowMonth(basicManageService.queryIncreaseMonthTotalflow(m.getNum(),m.getI_time()));
		}
		PageVo<NdataVo> result = new PageVo<>(1, ndataVos.size(), 10, ndataVos);
		return result;
	}

	/**
	 * 根据仪表名称，查找仪表的实时信息
	 *
	 */
	@RequestMapping(value = {"/queryTodayInfoByShortName"})
	public PageVo<NdataVo> queryTodayInfoByShortName(HttpServletRequest request, String shortName,Boolean flag){
		Integer userId=UserUtils.getUserId();
		PageVo<NdataVo> result = basicManageService.getTodayInfoByShortName(userId,shortName,flag);

		return result;
	}
}
