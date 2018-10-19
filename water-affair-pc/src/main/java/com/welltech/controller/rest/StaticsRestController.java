package com.welltech.controller.rest;


import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.welltech.waterAffair.service.BasicManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.welltech.waterAffair.addon.echarts.ItemStyle;
import com.welltech.waterAffair.addon.echarts.LineStyle;
import com.welltech.waterAffair.addon.echarts.OneMeterData;
import com.welltech.waterAffair.addon.echarts.Option;
import com.welltech.waterAffair.addon.echarts.Series;
import com.welltech.waterAffair.addon.echarts.Style;
import com.welltech.waterAffair.common.util.UserUtils;
import com.welltech.waterAffair.domain.entity.MachineInfo;
import com.welltech.waterAffair.domain.entity.NdataSs;
import com.welltech.waterAffair.domain.vo.DatatablesViewPage;
import com.welltech.waterAffair.domain.vo.NdatassVo;
import com.welltech.waterAffair.domain.vo.PageByDataTableVo;
import com.welltech.waterAffair.domain.vo.PageVo;
import com.welltech.waterAffair.domain.vo.WaterStatisticsHourVo;
import com.welltech.waterAffair.domain.vo.before.Data3Vo;
import com.welltech.waterAffair.domain.vo.before.QueryForm;
import com.welltech.waterAffair.service.MeterService;
import com.welltech.waterAffair.service.StaticsService;

/**
 * 
 * @author WangXin
 *
 */
@RestController
public class StaticsRestController {

	@Autowired
	private StaticsService staticsService;
	@Autowired
	private MeterService meterService;

	@Autowired
	private BasicManageService basicManageService;

	@RequestMapping("/queryWaterByHour")
	public PageByDataTableVo<WaterStatisticsHourVo> queryWaterByHour(String draw, String start, String length,
			String startTime, String endTime, String staions, HttpServletRequest request) {
		Integer userId = UserUtils.getUserId();
		int startIndex = 0;
		if (start == null) {
			startIndex = 0;
		} else {
			startIndex = Integer.valueOf(start);
		}
		int pageSize = 0;
		if (length == null) {
			pageSize = 10;
		} else {
			pageSize = Integer.valueOf(length);
		}
		Date start1 = null;
		Date end1 = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try {
			if (startTime != null && startTime.length() > 0) {
				start1 = sdf.parse(startTime);
			}
			if (endTime != null && endTime.length() > 0) {
				end1 = sdf.parse(endTime);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (staions == null) {// 站点为空返回空数据
			PageByDataTableVo<WaterStatisticsHourVo> result = new PageByDataTableVo<WaterStatisticsHourVo>(draw, 0,
					pageSize, new ArrayList<WaterStatisticsHourVo>());
			return result;
		}
		PageVo<WaterStatisticsHourVo> res = staticsService.queryWaterByHour(start1, end1,staions, startIndex, pageSize, userId);
		PageByDataTableVo<WaterStatisticsHourVo> result = new PageByDataTableVo<WaterStatisticsHourVo>(draw,
				res.getCountSize(), pageSize, res.getEntity());
		return result;
	}
	
	//趋势图所需数据
	@RequestMapping("/queryFlowAndPress" )
	public Option queryFlowAndPress2(@RequestBody QueryForm queryForm, HttpServletRequest request) throws ParseException {


		Option option =new Option();
		double maxFlow=0,minFlow=0,maxPress=0,minPress=0;
		ArrayList<String> legend = new ArrayList<String>();
		ArrayList<String> colorList = new ArrayList<String>();
		colorList.add("rgb(0, 0, 255)");
		colorList.add("rgb(255, 0, 0)");
		colorList.add("rgb(0, 255, 0)");
		colorList.add("rgb(255, 0, 255)");
		colorList.add("rgb(40, 210, 255)");
		//返回一个数据组
		List<Series> result=new ArrayList<>();

		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String startTime = queryForm.getStartTime();
		String endTime = queryForm.getEndTime();
		List<String> stations = queryForm.getStations();

		Date startTime4Query = null;
		Date endTime4Query = null;

		try {
			if (startTime != null && startTime.length() > 0) {
				startTime4Query = sdf.parse(startTime);
			}
			if (endTime != null && endTime.length() > 0) {
				endTime4Query = sdf.parse(endTime);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}


		List<List<NdataSs>> datas=meterService.findMetersDataRecordByDate(stations,startTime4Query,endTime4Query);

		for(int i=0;i<datas.size();i++){

			List<NdataSs> meterData=datas.get(i);

			//每个水表创建两条线
			List<List<Double>> press = new ArrayList<List<Double>>();
			List<List<Double>> flow = new ArrayList<List<Double>>();
			String pressLegendName="",flowLegendName="";

			for(NdataSs point:meterData){
				List<Double> pressPoint= new ArrayList<Double>();
				List<Double> flowPoint= new ArrayList<Double>();

				pressPoint.add((double)point.getTime().getTime());
				pressPoint.add(formateNumber(Double.valueOf(point.getPress())));
				press.add(pressPoint);

				flowPoint.add((double)point.getTime().getTime());
				flowPoint.add(formateNumber(Double.valueOf(point.getFlow())));
				flow.add(flowPoint);

				pressLegendName=stations.get(i)+"(压)";
				flowLegendName=stations.get(i)+"(流)";
			}

			try{
				double currentMaxPress=getDoubleMax(press);
				double currentMinPress=getDoubleMin(press);
				double currentMaxFlow=getDoubleMax(flow);
				double currentMinFlow=getDoubleMin(flow);

				if(maxPress<currentMaxPress){
					maxPress=currentMaxPress;
				}
				if(currentMinPress<minPress){
					minPress=currentMinPress;
				}

				if(maxFlow<currentMaxFlow){
					maxFlow=currentMaxFlow;
				}
				if(currentMinFlow<minFlow){
					minFlow=currentMinFlow;
				}
			}catch (Exception e){

			}

			legend.add(pressLegendName);legend.add(flowLegendName);
			Series pressSeries=getSeries(1,pressLegendName
					,colorList.get(i)
					,"dotted"
					,"circle"
					,maxPress,maxFlow,press);
			Series flowSeries=getSeries(0,flowLegendName
					,colorList.get(i)
					,"solid"
					,"diamond"
					,maxPress,maxFlow,flow);

			result.add(pressSeries);
			result.add(flowSeries);
		}


		option.setDeltaPress(maxPress-minPress);
		option.setDeltaFlow(maxFlow-minFlow);
		option.setMaxFlow(maxFlow);
		option.setMinFlow(minFlow);
		option.setMaxPress(maxPress);
		option.setMinPress(minPress);
		option.setLegend(legend);
		option.setSeries(result);
		return option;
	}



	private  Series getSeries(int index4y,String name,String color,String type,String symbol,double max4x,double max4y,List<List<Double>> data){
		Series series=new Series();
		series.setMax4x(max4x);
		series.setMax4y(max4y);
		LineStyle lineStyle=new LineStyle(new Style(color,type));
		ItemStyle itemStyle=new ItemStyle(new Style(color,type));
		series.setName(name);
		series.setyAxisIndex(index4y);
		series.setSymbol(symbol);
		series.setLineStyle(lineStyle);
		series.setItemStyle(itemStyle);
		series.setData(data);
		return series;
	}

	//计算List最大值
	private double getDoubleMax(List<List<Double>> dlist){
		Double max = (Double)dlist.get(0).get(1);
		for (int i = 0; i < dlist.size(); i++) {
			if (max < (Double)dlist.get(i).get(1)) max = (Double)dlist.get(i).get(1);
		}
		return max;
	}

	//计算List最小值
	private double getDoubleMin(List<List<Double>> dlist){
		Double min = (Double)dlist.get(0).get(1);
		for (int i = 0; i < dlist.size(); i++) {
			if (min > (Double)dlist.get(i).get(1)) min = (Double)dlist.get(i).get(1);
		}
		return min;
	}

	@RequestMapping(value = { "/queryMeterNameById" },method = {RequestMethod.GET,RequestMethod.POST})
	public String queryMeterNameById(HttpServletRequest request) {
		String mid=request.getParameter("mid");
		MachineInfo machineInfo = meterService.getMachineInfoById(Integer.parseInt(mid));
		String meterName = machineInfo.getShortName();
		return meterName;
	}
	
	/*@RequestMapping(value = { "/queryHourData4Pc" },method = {RequestMethod.GET,RequestMethod.POST})
	public DatatablesViewPage<NdatassVo> queryHourData4Pc(Integer id,String date,String time,HttpServletRequest request) {
		List<NdatassVo> res = new ArrayList<NdatassVo>();
		String detail=request.getParameter("detail");
		String id4pc=detail.substring(0,detail.indexOf(","));
		String date4pc="20"+detail.substring(detail.indexOf(",")+1,detail.indexOf(" "));
		String time4pc=detail.substring(detail.indexOf(" ")+1,detail.length());;
		res = staticsService.findHourData(Integer.valueOf(id4pc), date4pc,time4pc);

		DatatablesViewPage<NdatassVo> view = new DatatablesViewPage<NdatassVo>();
		view.setiTotalDisplayRecords(res.size());
		view.setiTotalRecords(res.size());
		view.setAaData(res);
		return view;
	}*/
	@RequestMapping(value = { "/queryHourData4Pc" },method = {RequestMethod.GET,RequestMethod.POST})
	public DatatablesViewPage<NdatassVo> queryHourData4Pc(Integer id,String date,String time,HttpServletRequest request) {
		List<NdatassVo> res = new ArrayList<NdatassVo>();
		String detail=request.getParameter("detail");
		String id4pc=detail.substring(0,detail.indexOf(","));
		String date4pc="20"+detail.substring(detail.indexOf(",")+1,detail.indexOf(" "));
		String time4pc=detail.substring(detail.indexOf(" ")+1,detail.length());
		//返回查询历史记录的时间节点
		String start2End = basicManageService.getHistoryConnectIntervalTime(Integer.parseInt(id4pc),date4pc,time4pc);
		if(start2End != null){
			res = staticsService.findConnectIntervalTimeData(Integer.valueOf(id4pc), start2End);
		}else{
			//TODO
			//说明当前电磁流量计
			res = staticsService.findHourData(Integer.parseInt(id4pc),date4pc,time4pc);
		}


		DatatablesViewPage<NdatassVo> view = new DatatablesViewPage<NdatassVo>();
		view.setiTotalDisplayRecords(res.size());
		view.setiTotalRecords(res.size());
		view.setAaData(res);
		return view;
	}

	public static Double formateNumber(Double f){
		if(f.isNaN()){
			return 0.00d;
		}
		DecimalFormat df = new DecimalFormat("######0.00");
		return Double.valueOf(df.format(f));
	}
	
	/**
	 * 
	 * 查询单点数据信息
	 * flow,press,ftotalflow,ntotalflow,currentI,currentV,Signal_strength
	 * @return
	 */
	@RequestMapping("/queryoneSignMsg")
	public Map<String,List<Data3Vo>> queryMeterInfoForMobile(String draw, String start, String length,String column,String num, Model model, HttpServletRequest request) {
		num=request.getParameter("mid");
		column=request.getParameter("type");
		Map<String,List<Data3Vo>> result=new HashMap<String,List<Data3Vo>>();
		Object[][] t= staticsService.queryData(column, num);
		//格式化
		List<Data3Vo> tmp=formate(t,column);
		result.put("entity", tmp);
		return result;
	}
	
	private List<Data3Vo> formate(Object[][] para, String type){
		String unit = "";
		if("currentI".equals(type)){
			unit = "mA";
		} else if("currentV".equals(type)){
			unit = "V";
		} else if("ftotalflow".equals(type) || "totalflow".equals(type)){
			unit = "m³";
		} else if("flow".equals(type)){
			unit = "m³/h";
		} else if("press".equals(type)){
			unit = "kPa";
		} else if("Signal_strength".equals(type)){
			
		}
		List<Data3Vo> t=new ArrayList<Data3Vo>();
		if(t!=null){
			for(Object[] o:para){
				Data3Vo vo=new Data3Vo();
				vo.setHour(o[0]+"");
				vo.setToday(o[1] != null ? o[1]+" "+unit : "--");
				vo.setYestoday(o[2]+" "+unit);
				vo.setTheDayBeforeYesterday(o[3]+" "+unit);
				t.add(vo);
			}
		}
		return t;
	}
	
	/**
	 * 流量、压力趋势图
	 *
	 * @param num
	 * @param model
	 * @return
	 */
	@RequestMapping("/queryOneMeterChart" )
	public OneMeterData queryOneMeterChart(HttpServletRequest request) throws ParseException {
		Integer userId = UserUtils.getUserId();
		OneMeterData omd= new OneMeterData();
		//double[] yesterday=new double[]{1.1, 11.7, 15.2, 13, 12, 13,11, 11, 15, 13, 12, 13,11, 11, 15, 13, 12, 13,11, 11, 15, 13, 12, 13};
		//double[] dbyesterday=new double[]{1, -2, 2, 5, 3, 2,1, -2, 2, 5, 3, 2,1, -2, 2, 5, 3, 2,1, -2, 2, 5, 3, 2};
		//double[] today=new double[]{5, 3, 5, 7, 5.2, 4,5, 3, 5, 7, 5, 4,5, 3, 5, 7, 5, 4,5, 3, 5, 7, 5, 4 };
		String num=request.getParameter("mid");
		String column=request.getParameter("type");
		//if(!column.equals("press")){
			Object[][] t= staticsService.queryData(column, num);
			double[] yesterday=new double[t.length];
			double[] dbyesterday=new double[t.length];
			Double[] today=new Double[t.length];
			String[] xAxis=new String[t.length];
			
			int index = 0;
			for(Object[] o:t){
				String date = o[0] + "";
				xAxis[index]=date.substring(11, 16);
				dbyesterday[index]=Double.valueOf(o[3]+"");
				yesterday[index]=Double.valueOf(o[2]+"");
				today[index]= o[1] != null ? Double.valueOf(o[1]+"") : null;
				index++;
			}
		//}

		String unit = "";
		if("currentI".equals(column)){
			unit = "mA";
		} else if("currentV".equals(column)){
			unit = "V";
		} else if("ftotalflow".equals(column) || "totalflow".equals(column)){
			unit = "m³";
		} else if("flow".equals(column)){
			unit = "m³/h";
		} else if("press".equals(column)){
			unit = "kPa";
		} else if("Signal_strength".equals(column)){
			
		}
			
		omd.setYesterday(yesterday);
		omd.setDbyesterday(dbyesterday);
		omd.setToday(today);
		omd.setXAxis(xAxis);
		omd.setUnit(unit);
		return  omd;
	}
}
