package com.welltech.controller;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.welltech.waterAffair.service.BasicManageService;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.welltech.waterAffair.common.util.UserUtils;
import com.welltech.waterAffair.domain.entity.MachineInfo;
import com.welltech.waterAffair.domain.entity.Ndata;
import com.welltech.waterAffair.domain.entity.NdataSs;
import com.welltech.waterAffair.domain.vo.NdataVo;
import com.welltech.waterAffair.domain.vo.NdatassVo;
import com.welltech.waterAffair.domain.vo.before.MachineInfoVo;
import com.welltech.waterAffair.service.MeterService;
import com.welltech.waterAffair.service.MobileService;

/**
 * 手机端数据接口
 * 
 * @author ryan
 *
 */
@RestController
public class MobileRestController {

	private static Logger logger = Logger
			.getLogger(MobileRestController.class);

	/*@Resource
	private WelltechSessionContext sessionContext;*/

	@Resource
	private MeterService meterService;

	@Resource
	private MobileService mobileService;

	@Resource
	private BasicManageService basicManageService;

	/**
	 *手机首页数据
	 */
	@RequestMapping("/queryMeterInfoForMobile")
	public List<NdataVo> queryMeterInfoForMobile(Integer id, HttpServletRequest request) {

		Map<String,List<NdataVo>> result=new HashMap<String,List<NdataVo>>();

		String area=request.getParameter("area");
		
		String pageNo = request.getParameter("pageNo");
		int currentPage = 0;
		if(StringUtils.isNotBlank(pageNo)){
			currentPage = Integer.parseInt(pageNo);
		}

		//获取需要查询的区域名称或者全部
		List<MachineInfo> meterListInCompany=((id==null||id==0)?mobileService.findUserMeterListInCompany(null,UserUtils.getUserId(),currentPage):mobileService.findUserMeterListInCompany(id,UserUtils.getUserId(),currentPage));
		List<NdataVo> meterDataList = new ArrayList<NdataVo>();
		for (MachineInfo machineInfo : meterListInCompany) {
			try {
				NdataVo tempData=meterService.findMeterLastestHourInfo(machineInfo.getNum());
				tempData.setSubUserName(machineInfo.getShortName());
				/*double increaseTotalflowMonth = basicManageService.queryIncreaseMonthTotalflow(machineInfo.getNum(),machineInfo.getiTime());
				float increaseTotalflow = basicManageService.queryIncreaseTotalflow(machineInfo.getNum(),machineInfo.getiTime());
				tempData.setIncreaseTotalflow(increaseTotalflow);
				tempData.setIncreaseTotalflowMonth(increaseTotalflowMonth);*/
				meterDataList.add(tempData);
			}catch (Exception e){
				e.printStackTrace();
			}
		}

		return meterDataList;
	}


	//为手机history页面曲线提供数据
	@RequestMapping("/queryMeterFlowByMinute2")
	public List<List<Double>> queryMeterHistoryMinuteFlow(Integer num, String timer,String type) throws ParseException {
		List<List<Double>> result = new ArrayList<List<Double>>();
		String date = timer;

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date now=dateFormat.parse(timer+" 00:00:00");
		for(NdataSs data:mobileService.findMeterMinuteDataByDate(num,dateFormat.parse(timer+" 00:00:00"),dateFormat.parse(timer+" 24:00:00"))){
			List<Double> oneMeterData= new ArrayList<Double>();
			oneMeterData.add((double)data.getTime().getTime());
			String tmp=null;
			try {
				NdatassVo vo =new NdatassVo();
				vo.setFlow(data.getFlow()+"");
				vo.setFtotalflow(data.getFtotalflow()+"");
				vo.setI_time(data.getiTime());
				vo.setNtotalflow(data.getTotalflown()+"");
				vo.setPress(data.getPress()+"");
				vo.setTime(data.getTime().getTime());
				vo.setTotalflow(data.getTotalflow()+"");
				if("signal_strength".equals(type)){//信号强度去掉，因为没有
					break;
				}
				tmp=BeanUtils.getProperty(vo, type);
			} catch (Exception e) {
				e.printStackTrace();
				tmp="0";
			}

			oneMeterData.add(Double.valueOf(this.formatNumber(tmp)));
			result.add(oneMeterData);
		}


		return result;
	}


	//为手机history页面表格提供数据
	@RequestMapping("/queryMeterFlowByMinute3")
	public List<List<Double>> queryMeterFlowByMinute3(Integer num, String timer,String type) throws ParseException {
		List<List<Double>> d1 = new ArrayList<List<Double>>();

		String date = timer;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		List<Ndata> result=mobileService.findMeterHourDataByDate(num,dateFormat.parse(date+" 00:00:00"),dateFormat.parse(date+" 24:00:00"));

		for(Ndata data:result){
			List<Double> oneMeterData= new ArrayList<Double>();
			oneMeterData.add((double)data.getiTime().getTime());
			String tmp=null;
			try {
				if("signal_strength".equals(type)){//信号强度去掉，因为没有
					break;
				}
				tmp=BeanUtils.getProperty(data, type);
			} catch (Exception e) {
				e.printStackTrace();
				tmp="0";
			}
			oneMeterData.add(Double.valueOf(this.formatNumber(tmp)));
            d1.add(oneMeterData);
		}
		return d1;
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

	//移动端地图页面
	@RequestMapping("/queryMeterList4MobileMap")
	public List<MachineInfoVo> queryMeterList4MobileMap(Model model) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<MachineInfoVo> result=new ArrayList<MachineInfoVo>();

		for(MachineInfo machineInfo:meterService.findUserMeterList(UserUtils.getUserId())){
			try {

				if( machineInfo.getLongitude()>10 && machineInfo.getLatitude()>10){

					NdataVo ndata=meterService.findMeterLastestHourInfo(machineInfo.getNum());
					MachineInfoVo machineInfoVo = new MachineInfoVo(machineInfo.getSubUserName(), machineInfo.getLongitude(), machineInfo.getLatitude(), ndata.getFlow(), machineInfo.getMintotal());
					//累计流量是显示日用水量当日凌晨到当前的
					Float f=meterService.getDayTotalFlowDiff(machineInfo);
					/*String detail = "站点：" + machineInfo.getSubUserName() +
							(ndata.getFlow() != null ? ("<br/>瞬时流量：" + formatNumber(""+ndata.getFlow()) ) : (""))+
							(ndata.getFlow() != null ? ("<br/>累计流量：" + formatNumber(""+f) ) : (""))+
							(ndata.getFlow() != null ? ("<br/>瞬时压力：" + formatNumber(""+ndata.getPress()) ) : ("")) +
							(ndata.getFlow() != null ? ("<br/>采集时间：" + dateFormat.format(ndata.getI_time()) ) : ("")) ;*/
					//显示抄表时间，瞬时流量，正向累计流量，反向累计流量，净累计流量，电池电量，信号强度，压力，工作电压，工作电流，上传次数。
					String detail = "站点：" + machineInfo.getShortName() +
							(ndata.getI_time() != null ? ("<br/>抄表时间：" + dateFormat.format(ndata.getI_time())) : ("")) +
							(ndata.getFlow() != null ? ("<br/>瞬时流量：" + formatNumber(""+ndata.getFlow()) + ("m³/h") ) : ("")) +
							(ndata.getTotalflow() != null ? ("<br/>正向累计流量：" + formatNumber(""+ndata.getTotalflow()) + ("m³") ) : ("")) +
							(ndata.getFtotalflow() != null ? ("<br/>反向累计流量：" + formatNumber(""+ndata.getFtotalflow()) + ("m³")) : ("")) +
							(ndata.getNtotalflow() != null ? ("<br/>净累计流量：" + formatNumber(""+ndata.getNtotalflow()) + ("m³") ) : (""))+
							(ndata.getSignal_strength() != null ? ("<br/>信号强度：" + formatNumber(""+ndata.getSignal_strength()) ) : ("")) +
							(ndata.getPress() != null ? ("<br/>压力：" + formatNumber(""+ndata.getPress()) + ("KPa") ) : ("")) +
							(ndata.getCurrentV() != null ? ("<br/>工作电压：" + formatNumber(""+ndata.getCurrentV()) + ("V")  ) : ("")) +
							(ndata.getCurrentI() != null ? ("<br/>工作电流：" + formatNumber(""+ndata.getCurrentI()) + ("mA") ) : (""))
						/*(ndata.get() != null ? ("<br/>电池电量：" + formatNumber(machineDetailInfo4MapVo.getBattery()) ) : ("")) +
						*/
						/*(ndata.get() != null ? ("<br/>上传次数：" + machineDetailInfo4MapVo.getUpdateTimes()) : (""))*/;
					machineInfoVo.setDetail(detail);
					result.add(machineInfoVo);
				}

			}catch (Exception e){

			}

		}



		return result;
	}
}
