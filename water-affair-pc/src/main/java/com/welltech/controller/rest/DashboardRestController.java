package com.welltech.controller.rest;

import com.welltech.waterAffair.common.enums.CompanyLevelEnum;
import com.welltech.waterAffair.common.util.UserUtils;
import com.welltech.waterAffair.domain.entity.AlarmProcessRecord;
import com.welltech.waterAffair.domain.entity.Company;
import com.welltech.waterAffair.domain.entity.MachineInfo;
import com.welltech.waterAffair.domain.entity.Ndata;
import com.welltech.waterAffair.domain.vo.*;
import com.welltech.waterAffair.domain.vo.before.MachineDetailInfo4MapVo;
import com.welltech.waterAffair.domain.vo.before.MachineInfoVo;
import com.welltech.waterAffair.repository.CompanyMapper;
import com.welltech.waterAffair.service.*;
import net.sf.json.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author WangXin
 *
 */
@RestController
public class DashboardRestController {

	@Resource
	private MeterService meterService;

	@Resource
	private MobileService mobileService;

	@Resource
	private DashboardService dashboardService;

	@Resource
	private CompanyService companyService;

	@Resource
	private CompanyMapper companyMapper;

	@Resource
	private AlarmService alarmService;

 	//查询用户告警信息
	@RequestMapping(value = {"/queryAlarmMessage"}, method = {RequestMethod.GET, RequestMethod.POST})
	public String queryAlarmMessage(HttpServletRequest request) {

		Map<String,Object> maps = alarmService.listUntreatedAlarm(UserUtils.getUserId());
		List<AlarmProcessRecord> result= (List<AlarmProcessRecord>) maps.get("result");
		return "[{\"alarms\":" + JSONArray.fromObject(result).toString() + ",\"totalAmount\":" + maps.get("size") + ",\"state\": {\"opened\": true}}]";
		//return null;
	}

	//格式化数据，保留小数点最后2位
	public String formatNumber(String numberOriginal) {
		DecimalFormat df = new DecimalFormat("######0.00");
		try{
			return df.format(Double.valueOf(numberOriginal));
		}catch (Exception e){
			return "无";
		}
	}

	@RequestMapping("/queryMeterList4Map")
	public List<MachineInfoVo> queryMeterList(HttpServletRequest request) {
		String type = request.getParameter("type");
		List<MachineInfoVo> result = new ArrayList<MachineInfoVo>();

		List<MachineInfo> tempResult=meterService.findUserMeterList(UserUtils.getUserId());

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			for (MachineInfo machineInfo : tempResult) {

				try {
				if(machineInfo.getLatitude()>10&&machineInfo.getLongitude()>10&&(type.equals("1")||(type.equals("2")&&machineInfo.getIsVipAccount()>0)||(type.equals("3")&&alarmService.isUntreatedAlarm(machineInfo.getNum()).size()>0))){
					//查询每个水表的实时数据

					MachineInfoVo machineInfoVo = new MachineInfoVo(machineInfo.getShortName(), machineInfo.getLongitude(), machineInfo.getLatitude(), machineInfo.getMaxtotal(), machineInfo.getMintotal());
//					long startTime = System.currentTimeMillis();    //获取开始时间
					machineInfoVo.setStatus(alarmService.isUntreatedAlarm(machineInfo.getNum()).size()>0?1:0);
					/*long endTime = System.currentTimeMillis();    //获取结束时间
					System.out.println("程序运行时间：" + (endTime - startTime) + "ms");    //输出程序运行时间*/
					//machineInfoVo.setStatus(1);

					NdataVo ndata=meterService.findMeterLastestHourInfo(machineInfo.getNum()); // 10ms

					//显示抄表时间，瞬时流量，正向累计流量，反向累计流量，净累计流量，电池电量，信号强度，压力，工作电压，工作电流，上传次数。
					String detail = "站点：" + machineInfo.getShortName() +
							(ndata.getI_time() != null ? ("<br/>抄表时间：" + dateFormat.format(ndata.getI_time())) : ("")) +
							(ndata.getFlow() != null ? ("<br/>瞬时流量：" + formatNumber(""+ndata.getFlow()) + ("m³/h") ) : ("")) +
							(ndata.getTotalflow() != null ? ("<br/>正向累计流量：" + formatNumber(""+ndata.getTotalflow()) + ("m³") ) : ("")) +
							(ndata.getFtotalflow() != null ? ("<br/>反向累计流量：" + formatNumber(""+ndata.getFtotalflow()) + ("m³")) : ("")) +
							(ndata.getNtotalflow() != null ? ("<br/>净累计流量：" + formatNumber(""+ndata.getNtotalflow()) + ("m³") ) : (""))+
							(ndata.getSignal_strength() != null ? ("<br/>信号强度：" + formatNumber(""+ndata.getSignal_strength()) ) : ("")) +
							(ndata.getPress() != null ? ("<br/>压力：" + formatNumber(""+ndata.getPress()) + ("KPa") ) : (""))
							+(ndata.getCurrentV() != null ? ("<br/>工作电压：" + formatNumber(""+ndata.getCurrentV()) + ("V")  ) : ("")) +
							(ndata.getCurrentI() != null ? ("<br/>工作电流：" + formatNumber(""+ndata.getCurrentI()) + ("mA") ) : (""))
						/*(ndata.get() != null ? ("<br/>电池电量：" + formatNumber(machineDetailInfo4MapVo.getBattery()) ) : ("")) +
						*/
						/*(ndata.get() != null ? ("<br/>上传次数：" + machineDetailInfo4MapVo.getUpdateTimes()) : (""))*/;
					machineInfoVo.setDetail(detail);
					result.add(machineInfoVo);
				}

				}catch (Exception e){
					e.printStackTrace();
				}

			}
			return result;
	}



	//查询首页总司及水表目录
	@RequestMapping(value = {"/queryMeterList4Tree","/meterList4AlarmMultiSetting"}, method = RequestMethod.GET)
	public String queryMeterList4Dashboard(HttpServletRequest request) {
		String type = request.getParameter("type");

		Integer userId = UserUtils.getUserId();
		boolean isAdmin = companyService.isAdmin(userId);

		//用户下所有的一级公司
		List<CompanyVo4JS> companyList = new ArrayList<CompanyVo4JS>();

			//查询用户所在的总公司
			Company userHeadCompany=companyService.getHeadCompany(UserUtils.getUserId()); //Company();//companyMapper.findByParentCompanyId(null, CompanyLevelEnum.PARENT.getKey());
			Company userBranchCompany= companyService.getBranchCompany(UserUtils.getUserId());
			Company company=dashboardService.queryUserNameId(UserUtils.getUserId());

			CompanyVo4JS companyVo4JS = new CompanyVo4JS();
			companyVo4JS.setText(userHeadCompany.getCompanyName());
			companyVo4JS.setId("" +userHeadCompany.getCompanyId());

			if(userHeadCompany.getCompanyId()==company.getCompanyId()){
				//用户为总公司用户
				List<Company> branchCompanyList = dashboardService.findSubCompanyList(userHeadCompany.getCompanyId());

				for (Company branchCompany : branchCompanyList) {
					BranchCompanyVo4JS branchCompanyVo4JS = new BranchCompanyVo4JS();
					branchCompanyVo4JS.setText(branchCompany.getCompanyName());
					branchCompanyVo4JS.setId("" + branchCompany.getCompanyId());
					branchCompanyVo4JS.setIcon("fa fa-building");
					//用户下所有的三级公司
					List<Company> areaList = dashboardService.findSubCompanyList(branchCompany.getCompanyId());
					for (Company area : areaList) {
						BranchCompanyAreaVo4JS branchCompanyAreaVo4JS = new BranchCompanyAreaVo4JS();
						branchCompanyAreaVo4JS.setText(area.getCompanyName());
						branchCompanyAreaVo4JS.setId("" + area.getCompanyId());
						branchCompanyAreaVo4JS.setIcon("fa fa-building");

						for (MachineInfo meter : companyService.queryMeterByCompanyId(area.getCompanyId())) {
							MachineInfoVo4JS machineInfoVo4JS = new MachineInfoVo4JS(meter.getNum(), meter.getShortName(),
									meter.getLongitude(), meter.getLatitude(), meter.getMintotal(),
									meter.getMaxtotal());
							machineInfoVo4JS.setIcon("fa fa-tachometer");
							branchCompanyAreaVo4JS.getChildren().add(machineInfoVo4JS);
						}
						branchCompanyVo4JS.getChildren().add(branchCompanyAreaVo4JS);
					}

					//二级公司下属水表
					for (MachineInfo meter : companyService.queryMeterByCompanyId(branchCompany.getCompanyId())) {

						if((type.equals("1")||(type.equals("2")&&meter.getIsVipAccount()>0)||(type.equals("3")&&alarmService.isUntreatedAlarm(meter.getNum()).size()>0))){
							BranchCompanyAreaVo4JS branchCompanyAreaMeterVo4JS = new BranchCompanyAreaVo4JS();
							branchCompanyAreaMeterVo4JS.setText(meter.getShortName());
							//branchCompanyAreaMeterVo4JS.setId("" + meter.getNum());
							branchCompanyAreaMeterVo4JS.setLongitude(meter.getLongitude());
							branchCompanyAreaMeterVo4JS.setLatitude(meter.getLatitude());
							branchCompanyAreaMeterVo4JS.setMid(meter.getNum());
							branchCompanyAreaMeterVo4JS.setIcon("fa fa-tachometer");
							branchCompanyVo4JS.getChildren().add(branchCompanyAreaMeterVo4JS);
						}


					}
					companyVo4JS.getChildren().add(branchCompanyVo4JS);
				}

				List<MachineInfo> meterList=dashboardService.findCompanyUserMeterList(userHeadCompany.getCompanyId(),UserUtils.getUserId());
				//查询一级公司下属水表
				for (MachineInfo meter : meterList) {
					try{
						if((type.equals("1")||(type.equals("2")&&meter.getIsVipAccount()>0)||(type.equals("3")&&alarmService.isUntreatedAlarm(meter.getNum()).size()>0))){

								BranchCompanyVo4JS branchCompanyMeterVo4JS = new BranchCompanyVo4JS();
								branchCompanyMeterVo4JS.setText(meter.getShortName());
								branchCompanyMeterVo4JS.setId("" + meter.getNum());
								branchCompanyMeterVo4JS.setMid(meter.getNum());
								branchCompanyMeterVo4JS.setLongitude(meter.getLongitude());
								branchCompanyMeterVo4JS.setLatitude(meter.getLatitude());
								branchCompanyMeterVo4JS.setIcon("fa fa-tachometer");
								companyVo4JS.getChildren().add(branchCompanyMeterVo4JS);


						}

					}catch(Exception e){

					}
				}

			}else if(company.getCompanyId()==userBranchCompany.getCompanyId()){
				//用户为分公司用户
				BranchCompanyVo4JS branchCompanyVo4JS = new BranchCompanyVo4JS();
				branchCompanyVo4JS.setText(userBranchCompany.getCompanyName());
				branchCompanyVo4JS.setId("" + userBranchCompany.getCompanyId());
				branchCompanyVo4JS.setIcon("fa fa-building");
				//用户下所有的三级公司
				List<Company> areaList = dashboardService.findSubCompanyList(userBranchCompany.getCompanyId());
				for (Company area : areaList) {
					BranchCompanyAreaVo4JS branchCompanyAreaVo4JS = new BranchCompanyAreaVo4JS();
					branchCompanyAreaVo4JS.setText(area.getCompanyName());
					branchCompanyAreaVo4JS.setId("" + area.getCompanyId());
					branchCompanyAreaVo4JS.setIcon("fa fa-building");

					for (MachineInfo meter : companyService.queryMeterByCompanyId(area.getCompanyId())) {
						if((type.equals("1")||(type.equals("2")&&meter.getIsVipAccount()>0)||(type.equals("3")&&alarmService.isUntreatedAlarm(meter.getNum()).size()>0))){
							MachineInfoVo4JS machineInfoVo4JS = new MachineInfoVo4JS(meter.getNum(), meter.getShortName(),
									meter.getLongitude(), meter.getLatitude(), meter.getMintotal(),
									meter.getMaxtotal());
							machineInfoVo4JS.setIcon("fa fa-tachometer");
							branchCompanyAreaVo4JS.getChildren().add(machineInfoVo4JS);

						}

					}
					branchCompanyVo4JS.getChildren().add(branchCompanyAreaVo4JS);
				}

				//二级公司下属水表
				for (MachineInfo meter : dashboardService.findCompanyUserMeterList(userBranchCompany.getCompanyId(),UserUtils.getUserId())) {

					if((type.equals("1")||(type.equals("2")&&meter.getIsVipAccount()>0)||(type.equals("3")&&alarmService.isUntreatedAlarm(meter.getNum()).size()>0))){

						BranchCompanyAreaVo4JS branchCompanyAreaMeterVo4JS = new BranchCompanyAreaVo4JS();
						branchCompanyAreaMeterVo4JS.setText(meter.getShortName());
						//branchCompanyAreaMeterVo4JS.setId("" + meter.getNum());
						branchCompanyAreaMeterVo4JS.setLongitude(meter.getLongitude());
						branchCompanyAreaMeterVo4JS.setLatitude(meter.getLatitude());
						branchCompanyAreaMeterVo4JS.setMid(meter.getNum());
						branchCompanyAreaMeterVo4JS.setIcon("fa fa-tachometer");
						branchCompanyVo4JS.getChildren().add(branchCompanyAreaMeterVo4JS);

					}

				}
				companyVo4JS.getChildren().add(branchCompanyVo4JS);
			}else{
				//区域用户
				BranchCompanyVo4JS branchCompanyVo4JS = new BranchCompanyVo4JS();
				branchCompanyVo4JS.setText(userBranchCompany.getCompanyName());
				branchCompanyVo4JS.setId("" + userBranchCompany.getCompanyId());
				branchCompanyVo4JS.setIcon("fa fa-building");

				BranchCompanyAreaVo4JS branchCompanyAreaVo4JS = new BranchCompanyAreaVo4JS();
				branchCompanyAreaVo4JS.setText(company.getCompanyName());
				branchCompanyAreaVo4JS.setId("" + company.getCompanyId());
				branchCompanyAreaVo4JS.setIcon("fa fa-building");

				for (MachineInfo meter : dashboardService.findCompanyUserMeterList(company.getCompanyId(),UserUtils.getUserId())) {

					try{
						if((type.equals("1")||(type.equals("2")&&meter.getIsVipAccount()>0)||(type.equals("3")&&alarmService.isUntreatedAlarm(meter.getNum()).size()>0))){

							MachineInfoVo4JS machineInfoVo4JS = new MachineInfoVo4JS(meter.getNum(), meter.getShortName(),
									meter.getLongitude(), meter.getLatitude(), meter.getMintotal(),
									meter.getMaxtotal());
							machineInfoVo4JS.setIcon("fa fa-tachometer");
							branchCompanyAreaVo4JS.getChildren().add(machineInfoVo4JS);

						}
					}catch (Exception e){

					}


				}

				branchCompanyVo4JS.getChildren().add(branchCompanyAreaVo4JS);
				companyVo4JS.getChildren().add(branchCompanyVo4JS);
			}


			companyList.add(companyVo4JS);




		return "[{\"children\":" + JSONArray.fromObject(companyList).toString() + ",\"text\":\"所有公司\",\"state\": {\"opened\": true}}]";

	}


}
