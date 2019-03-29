package com.welltech.waterAffair.service;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.welltech.waterAffair.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.welltech.waterAffair.common.constant.Constants;
import com.welltech.waterAffair.common.util.ConstantsUtil;
import com.welltech.waterAffair.common.util.MeterUtils;
import com.welltech.waterAffair.domain.criteria.NdataCriteria;
import com.welltech.waterAffair.domain.entity.MachineInfo;
import com.welltech.waterAffair.domain.entity.Ndata;
import com.welltech.waterAffair.domain.vo.NdataVo;

/**
 * Created by myMac on 17/4/22.
 */
@Service
public class ReportService {
	private static Logger logger=LoggerFactory.getLogger(ReportService.class);
	@Autowired
	private MachineInfoMapper machineInfoMapper;
	@Autowired
	private NdataMapper ndataMapper;
	@Autowired
	private NdataSsMapper ndataSsMapper;
	@Autowired
	private GprsDataMapper gprsDataMapper;

	@Autowired
	private GprsDataFor4200Mapper gprsDataFor4200Mapper;

    //日报 zhoupei实现
    public String[][] reportDay(Integer userId,String currentTime,List<MachineInfo> machines){
    	String[][] result=null;
		//初始化map，将当天分成24小时
		result=new String[29][machines.size()*5+1];//如果计算平均值那么需要y轴+5也就是24+5=29，平均值，最小值，最小值时间，最大值最大值时间
		for(int i=0;i<24;i++){
			result[i][0]=currentTime+" "+(i<10?"0"+i:i)+":00:00";//格式化时间
		}
		result[24][0]="平均值";
		result[25][0]="最小值";
		result[26][0]="最小值时间";
		result[27][0]="最大值";
		result[28][0]="最大值时间";
		for(int i=0;i<machines.size();i++){//因为数据太多只取10条，超过不取
			if(i>9){
				break;
			}
			Integer num=machines.get(i).getNum();
			List<NdataVo> ndate=queryReportDay(num, currentTime);
			formateNdataVoes(result, ndate,i);
		}
		return result;
    }

    /**
	 * 通过水表号和时间查询ndata数据
	 * 每条记录按小时统计应该24条，24小时
	 * Constants.isElectricity(meterTypeId)true电流ndata，false电磁gprs4300
	 * ndata和gprs4300水表数据都需要查询存入ndatavo没有的字段默认设置为0.0
	 * @param num			水表号
	 * @param currentTime	时间	2017-01-01	ndata是i_time,gprs4300是time
	 * @return
	 */
    private List<NdataVo> queryReportDay(Integer num, String currentTime) {
    	MachineInfo info = machineInfoMapper.findOneByNum(num);

    	Date date = null;

		NdataCriteria criteria = new NdataCriteria();
		criteria.setMeterId(num);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {
			date = df.parse(currentTime);
		} catch (ParseException e) {
			e.printStackTrace();
			date = new Date();
		}
		List<NdataVo> ndataVos = new ArrayList<NdataVo>(24);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		for(int i = 0; i <24 ; i++ ){
			calendar.set(Calendar.HOUR_OF_DAY,i);
			criteria.setCurrentTime(calendar.getTime());
			Ndata ndata = null;
			if(Constants.isElectricity(info.getMeterTypeId())){
				ndata = ndataMapper.findHourData(criteria);
			} else{
				if(info.getMeterTypeId() !=3)
					ndata = gprsDataMapper.findNdataHourData(criteria);
				else
					ndata = gprsDataFor4200Mapper.findNdataHourData(criteria);
			}
			if(ndata == null){
				ndataVos.add(new NdataVo(info.getSubUserName(),info.getNum(),criteria.getCurrentTime(),null,null,null,null,null));//如果没有数据那么将flow,press,totalflow默认为null
			}else{
				ndataVos.add(MeterUtils.getInstance(ndata,info));
			}
		}

		return ndataVos;
	}


	//月报 zhoupei实现
    /**
	 * 根据userId获得用户对应水表的月报信息
	 * @param UserId
	 * @param conditionMonth	选中的月份
	 */
	public String[][] reportMonth(Integer userId,List<MachineInfo> machines){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM");
		String conditionMonth=sdf.format(new Date());//默认当月
		return reportMonth(userId,machines,conditionMonth);
		
	}
	
	/**
	 * 根据userId获得用户对应水表的月报信息
	 * @param UserId
	 * @param conditionMonth	选中的月份
	 */
	public String[][] reportMonth(Integer userId,List<MachineInfo> machines,String conditionMonth){
		String[][] result=null;
		//用来存放月累加流量
		float temp = 0;
		//初始化map，将当月初始化
		int dayes=getCurrentMonthLastDay(conditionMonth);
		/*result=new String[dayes+5][machines.size()*9+1];//如果计算平均值那么需要y轴+5也就是24+5=29，平均值，最小值，最小值时间，最大值最大值时间*/
		result=new String[dayes+6][machines.size()*9+1];
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		Date conditionDate;
		try {
			conditionDate = sdf1.parse(conditionMonth+"-01");
		} catch (ParseException e) {
			logger.error("reportMonth:",e);
			conditionDate=new Date();
		}
		Calendar c=Calendar.getInstance();
		c.setTime(conditionDate);
		c.set(Calendar.DATE, 1);
		String currentTimeStart=sdf1.format(c.getTime());
		c.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天  
		String currentTimeEnd=sdf1.format(c.getTime());
		for(int i=0;i<dayes;i++){
			c.set(Calendar.DATE, i+1);
			result[i][0]=sdf1.format(c.getTime());
		}
		result[dayes][0]="平均值";
		result[dayes+1][0]="最小值";
		result[dayes+2][0]="最小值时间";
		result[dayes+3][0]="最大值";
		result[dayes+4][0]="最大值时间";
		result[dayes+5][0]="月累计量（m³）";
		for(int i=0;i<machines.size();i++){//因为数据太多只取10条，超过不取
			if(i>9){
				break;
			}
			Integer num=machines.get(i).getNum();
			List<Object[]> ndate=queryReportMonth(num, currentTimeStart,currentTimeEnd);
			formateArray(result, ndate,i,dayes);
		}

		for(int i = 0; i< dayes; i++ ){
			if(!"--".equals(result[i][9])){
				temp+=Float.parseFloat(result[i][9]);
			}

		}
		result[dayes+5][1] = temp +"";
		return result;
	}


	/**
	 * 通过水表号，开始时间，结束时间查询ndata或者gprs4300数据
	 * 每条记录是按日统计
	 * 顺序是：date_format(I_time,'%d') I_time,avg(press),max(press),min(press),avg(flow),max(flow),min(flow),avg(totalflow),max(totalflow),min(totalflow)
	 * 顺序说明：天数，平均压力，最大压力，最小压力，平均流量，最大流量，最小流量，起始读数，结束读数，偏移量 ，都要从ndata或者gprs4300表中取
	 * 同时按照i_time升序排列,没有的默认设置为0.0
	 * Constants.isElectricity(meterTypeId)true电流ndata，false电磁gprs4300
	 * @param num
	 * @param currentTimeStart	2017-01-01
	 * @param currentTimeEnd	2017-01-31
	 * @return	返回的是二位数组对象也不需要封装成对象了，我直接拿来用
	 */
    private List<Object[]> queryReportMonth(Integer num, String currentTimeStart, String currentTimeEnd) {
		List<Object[]> list = new ArrayList<>();

    	MachineInfo info = machineInfoMapper.findOneByNum(num);
		NdataCriteria criteria = new NdataCriteria();
		criteria.setMeterId(num);

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar startAt = Calendar.getInstance();
		Calendar endAt = Calendar.getInstance();
		try {
			startAt.setTime(df.parse(currentTimeStart));
			endAt.setTime(df.parse(currentTimeEnd));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		while (startAt.compareTo(endAt) <= 0){
			criteria.setCurrentDate(df.format(startAt.getTime()));
			Map<String,Object> map = null;
			if(Constants.isElectricity(info.getMeterTypeId())){
				map = ndataMapper.findDayData(criteria);
			} else {
				if(info.getMeterTypeId() !=3)
					map = gprsDataMapper.findNdataDayData(criteria);
				else
					map = gprsDataFor4200Mapper.findNdataDayData(criteria);
			}
			if(map == null){
				Object[] objects = {startAt.get(Calendar.DAY_OF_MONTH),null,null,null,null,null,null,null,null,null};
				list.add(objects);
			} else{
				Set<String> sets = map.keySet();
				Object[] objects = new Object[10];
				int index = 0;
				for(String set : sets){
					//因为月报表的天数是以1开始的，跟季报，年报以0开始的不一样，为了统一好让formateArray()方法进行格式化，将index=0的值统统减1
					if(index==0){
						if("0.0".equals(map.get(set).toString())){
							objects = new Object[]{startAt.get(Calendar.DAY_OF_MONTH),null,null,null,null,null,null,null,null,null};
							break;
						}
						try{
							int tmp=Integer.valueOf(map.get(set).toString());
							objects[0] =tmp-1 ;
						}catch(Exception e){
							objects = new Object[]{startAt.get(Calendar.DAY_OF_MONTH),null,null,null,null,null,null,null,null,null};
							break;
						}
					}else{
						objects[index] = map.getOrDefault(set, 0);
					}
//					if(objects[index] == null){
//						objects[index] = 0.0;
//					}
					index ++ ;
				}
				list.add(objects);
			}

			startAt.add(Calendar.DAY_OF_MONTH, 1);
		}

		return list;
	}

	//季报 zhoupei实现
    public String[][] reportSeason(Integer userId,List<MachineInfo> machines,String date){
        String[][] result=null;
        int dayes=4;//四季
        result=new String[4+5][machines.size()*9+1];//如果计算平均值那么需要y轴+5也就是4+5=9，平均值，最小值，最小值时间，最大值最大值时间
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy");
		if(date == null){
			Calendar c=Calendar.getInstance();
			 date=sdf1.format(c.getTime());
		}
        result[dayes][0]="平均值";
        result[dayes+1][0]="最小值";
        result[dayes+2][0]="最小值时间";
        result[dayes+3][0]="最大值";
        result[dayes+4][0]="最大值时间";
        result[0][0]="第一季度";
        result[1][0]="第二季度";
        result[2][0]="第三季度";
        result[3][0]="第四季度";
        for(int i=0;i<machines.size();i++){
			Integer num=machines.get(i).getNum();
            List<Object[]> ndate=queryReportSeason(num, date);
            formateArray(result, ndate,i,4);
        }
        return result;
    }

    /**
	 * 查询水表的季报
	 * 每条记录是一季，应该有4条记录
	 * 顺序是:{x},avg(press),max(press),min(press),avg(flow),max(flow),min(flow),avg(totalflow),max(totalflow),min(totalflow)
	 * {x}是数字0开始3截至，代表1到4季，之后的数据就是平均压力，最大压力，最小压力，平均流量，最大流量，最小流量，平均totalflow，最大totalflow，最小totalflow；ndata和gprs4300中取
	 * Constants.isElectricity(meterTypeId)true电流ndata，false电磁gprs4300
	 * 后三位取值换为流量起始读数，结束读数，累计流量
	 * @param num			水表号
	 * @param currentTime	年	2017
	 * @return	返回的是二位数组对象也不需要封装成对象了，我直接拿来用
	 */
    private List<Object[]> queryReportSeason(Integer num, String currentTime) {
    	MachineInfo info = machineInfoMapper.findOneByNum(num);
    	
    	List<Object[]> result = new ArrayList<>();
    	
    	Calendar cal = Calendar.getInstance();
    	cal.set(Integer.parseInt(currentTime), 0, 1, 0, 0, 0);
    	cal.set(Calendar.MILLISECOND, 0);
    	
    	for(int i = 0 ; i < 4; i++){
    		Map<String,Object> param = new HashMap<>();
    		param.put("startTime", cal.getTime());
    		cal.add(Calendar.MONTH,-3);
    		param.put("beforeStartTime",cal.getTime());
    		cal.add(Calendar.MONTH, 6);
    		param.put("endTime", cal.getTime());
    		param.put("num", num);
    		
    		Map<String,Object> map = null;
    		Map<String, Object> map2 = null;
    		
    		if(Constants.isElectricity(info.getMeterTypeId())){
    			map = ndataMapper.findSeasonData(param);
    			map2 = ndataMapper.findDiffTotalFlow(param);
    		} else{
    			if(info.getMeterTypeId() !=3){
					map = gprsDataMapper.findNdataSeasonData(param);
					map2 = gprsDataMapper.findDiffTotalFlow(param);
    			}else{
    				map = gprsDataFor4200Mapper.findNdataSeasonData(param);
    				map2 = gprsDataFor4200Mapper.findDiffTotalFlow(param);
				}
    		}
    		
    		double[] flow = {0,0,0};
    		int index = 0;
    		for(String set: map2.keySet()){
    			flow[index] = (double)map2.get(set);
    			index ++;
    		}
    		
    		if(map == null){
    			Object[] objects = {i,null,null,null,null,null,null,null,null,null};
    			result.add(objects);
			} else{
				Set<String> sets = map.keySet();
				Object[] objects = new Object[10];
				objects[0] = i;
				index = 1;
				for(String set : sets){
					objects[index] = map.get(set);
//					if(objects[index] == null){
//						objects[index] = 0.0;
//					}
					index ++ ;
				}
				objects[7]=flow[1];//起始读数
				objects[8]=flow[2];//结束读数
				objects[9]=flow[0];//差值
				if("0.0".equals(objects[0].toString())){
					objects = new Object[]{i,null,null,null,null,null,null,flow[1],flow[2],flow[0]};
				}
				result.add(objects);
			}
    		
    	}
		return result;
	}

	//年报 zhoupei实现
    public String[][] reportYear(Integer userId,List<MachineInfo> machines,String date){
        String[][] result=null;
		//用来存放年累加流量
		float temp = 0;
        Calendar c=Calendar.getInstance();
        int dayes=12;//年报,12个月
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy");
        String currentTime=sdf1.format(c.getTime());
        if(date.compareTo(currentTime)< 0){
        	currentTime = date;
		}
       /* currentTime = Integer.parseInt(currentTime)-1 + "";*/
        /*result=new String[dayes+5][machines.size()*9+1+1];//如果计算平均值那么需要y轴+5也就是4+5=9，平均值，最小值，最小值时间，最大值最大值时间*/
		//result=new String[dayes+6][machines.size()*9+1+1];//如果计算平均值那么需要y轴+5也就是4+5=9，平均值，最小值，最小值时间，最大值最大值时间
        result=new String[dayes+6][machines.size()*9+1]; ////modify by MZW 修改导出多余的数据
        for(int i=0;i<dayes;i++){
            result[i][0]=currentTime+"年"+(i+1)+"月";
        }

        result[dayes][0]="平均值";
        result[dayes+1][0]="最小值";
        result[dayes+2][0]="最小值时间";
        result[dayes+3][0]="最大值";
        result[dayes+4][0]="最大值时间";
		result[dayes+5][0]="年累计量(m³)";
        for(int i=0;i<machines.size();i++){

            Integer num=machines.get(i).getNum();
            List<Object[]> ndate=queryReportYear(num, currentTime);
            formateArray(result, ndate,i,dayes);
        }
		for(int i = 0; i< dayes; i++ ){
			if(!"--".equals(result[i][9])){
				temp+=Float.parseFloat(result[i][9]);
			}

		}
		result[dayes+5][1] = temp +"";
        return result;
    }

    /**
	 * 查询水表的年报
	 * 顺序：convert(date_format(I_time,'%m'),signed)-1 month,avg(press),max(press),min(press),avg(flow),max(flow),min(flow),avg(totalflow),max(totalflow),min(totalflow)
	 * 		累计流量差值，累计流量起始值，累计流量结束值
	 * 顺序说明：月份，平均压力，最大压力，最小压力，平均流量，最大流量，最小流量，平均净累计流量，最大净累计流量，最小净累计流量 ，都要从ndata或者gprs4300表中取
	 * Constants.isElectricity(meterTypeId)true电流ndata，false电磁gprs4300
	 * 应该有12条记录
	 * @param num			水表号
	 * @param currentTime	年2017
	 * @return	返回的是二位数组对象也不需要封装成对象了，我直接拿来用
	 */
    private List<Object[]> queryReportYear(Integer num, String currentTime) {
    	MachineInfo info = machineInfoMapper.findOneByNum(num);
    	
    	List<Object[]> result = new ArrayList<>();
    	
    	Calendar cal = Calendar.getInstance();
    	cal.set(Integer.parseInt(currentTime), 0, 1, 0, 0, 0);
    	cal.set(Calendar.MILLISECOND, 0);
    	
    	for(int i = 0 ; i < 12; i++){
    		Map<String,Object> param = new HashMap<>();
    		param.put("startTime", cal.getTime());
			cal.add(Calendar.MONTH,-1);
			param.put("beforeStartTime",cal.getTime());
    		cal.add(Calendar.MONTH, 2);
    		param.put("endTime", cal.getTime());
    		param.put("num", num);
    		
    		Map<String,Object> map = null;
    		Map<String, Object> map2 = null;
    		
    		if(Constants.isElectricity(info.getMeterTypeId())){
    			map = ndataMapper.findYearData(param);
    			map2 = ndataMapper.findDiffTotalFlow(param);
    		} else{
    			if(info.getMeterTypeId() !=3){
					map = gprsDataMapper.findNdataYearData(param);
					map2 = gprsDataMapper.findDiffTotalFlow(param);
				}else{
					map = gprsDataFor4200Mapper.findNdataYearData(param);
					map2 = gprsDataFor4200Mapper.findDiffTotalFlow(param);
				}

    		}
    		
    		double[] flow = {0,0,0};
    		int index = 0;
    		for(String set: map2.keySet()){
    			flow[index] = (double)map2.get(set);
    			index ++;
    		}
    		
    		if(map == null){
    			//Object[] objects = {i,null,null,null,null,null,null,null,null,null,null};
				Object[] objects = {i,null,null,null,null,null,null,null,null,null}; //modify by MZW 修改导出多余的数据
    			result.add(objects);
			} else{
				Set<String> sets = map.keySet();
				Object[] objects = new Object[10];
				index = 0;
				for(String set : sets){
					objects[index] = map.get(set);
//					if(index == 0){
//						objects[index] = i;
//					}
					index ++ ;
				}
				objects[7] = flow[1];//起始读数
				objects[8] = flow[2];//结束读数
				objects[9] = flow[0];//差值
				//objects[10] = flow[0];
				if("0.0".equals(objects[0].toString())){
					objects = new Object[]{i,null,null,null,null,null,null,flow[1],flow[2],flow[0]};
				}
				result.add(objects);
			}
    		
    	}
		return result;
	}

	/**
     * 查询所有水表
     * @return
     */
    public List<MachineInfo> findAll(){
    	return machineInfoMapper.findAll();
	}

    /**
     * 通过用户id查询用户所关联的水表
     * @param userId	用户id
     * @return
     */
    public List<MachineInfo> queryByUserId(Integer userId){
    	return machineInfoMapper.findByUserId(userId);
	}
    
    /**
	 * 这个方法的主要工作就是将查询出来的数据重新格式化，以时间为key，放入到map中
	 * @param
	 * @param ndate
	 */
	private void formateNdataVoes(String[][] result,List<NdataVo> ndate,int n){
		for(int i=0;i<29;i++){
			String press = "--";
			String flow  = "--";
			String totalflow = "--";
			String ntotalflow  = "--";
			String ftotalflow = "--";
			result[i][n*5+1]=press;//压力
			result[i][n*5+2]=flow;//瞬时流量
			result[i][n*5+3]=totalflow;//累计流量
			result[i][n*5+4]=ntotalflow;//累计流量
			result[i][n*5+5]=ftotalflow;//累计流量

		}
		float pressavg=0.0f;
		float flowavg=0.0f;
		float totalflowavg=0.0f;
		float ntotalflowavg=0.0f;
		float ftotalflowavg=0.0f;
		float pressmin=0.0f;
		float flowmin=0.0f;
		float totalflowmin=0.0f;
		float ntotalflowmin=0.0f;
		float ftotalflowmin=0.0f;
		Date pressminDate=null;
		Date flowminDate=null;
		Date totalflowminDate=null;
		Date ntotalflowminDate=null;
		Date ftotalflowminDate=null;
		float pressmax=0.0f;
		float flowmax=0.0f;
		float totalflowmax=0.0f;
		float ntotalflowmax=0.0f;
		float ftotalflowmax=0.0f;
		Date pressmaxDate=null;
		Date flowmaxDate=null;
		Date totalflowmaxDate=null;
		Date ntotalflowmaxDate=null;
		Date ftotalflowmaxDate=null;

		int pressCount=0;//压力总数
		int flowCount=0;//瞬时流量
		int totalflowCount=0;//累计流量
		int ntotalflowCount=0;//正向累计流量
		int ftotalflowCount=0;//反向累计流量

		for(int i=0;i<ndate.size();i++){
			NdataVo vo=ndate.get(i);
			Float press=ConstantsUtil.formateNumber(vo.getPress());
			Float flow=ConstantsUtil.formateNumber(vo.getFlow());
			Float totalflow=ConstantsUtil.formateNumber(vo.getTotalflow());
			Float ntotalflow=ConstantsUtil.formateNumber(vo.getNtotalflow());
			Float ftotalflow=ConstantsUtil.formateNumber(vo.getFtotalflow());

			//只有当数据不等于null有数据才进行平均值计算
			if(vo.getPress()!=null){
				pressCount=pressCount+1;//压力
				result[vo.getI_time().getHours()][n*5+1]=press.toString();
				pressavg=pressavg+press;//平均值
				if(press.compareTo(pressmin)<0||(press.compareTo(pressmin)>=0&&pressCount==1)){//最小值
					pressmin=press;
					pressminDate=vo.getI_time();
				}
				if(press.compareTo(pressmax)>0||(press.compareTo(pressmax)<=0&&pressCount==1)){
					pressmax=press;
					pressmaxDate=vo.getI_time();
				}
			}
			if(vo.getFlow()!=null){
				flowCount=flowCount+1;//瞬时流量
				result[vo.getI_time().getHours()][n*5+2]=flow.toString();
				flowavg=flowavg+flow;//平均值
				if(flow.compareTo(flowmin)<0||(flow.compareTo(flowmin)>=0&&flowCount==1)){//最小值
					flowmin=flow;
					flowminDate=vo.getI_time();
				}
				if(flow.compareTo(flowmax)>0||(flow.compareTo(flowmax)<=0&&flowCount==1)){
					flowmax=flow;
					flowmaxDate=vo.getI_time();
				}
			}
			if(vo.getTotalflow()!=null){
				totalflowCount=totalflowCount+1;//累计流量
				result[vo.getI_time().getHours()][n*5+3]=totalflow.toString();
				totalflowavg=totalflowavg+totalflow;//平均值
				if(totalflow.compareTo(totalflowmin)<0||(totalflow.compareTo(totalflowmin)>=0&&totalflowCount==1)){//最小值
					totalflowmin=totalflow;
					totalflowminDate=vo.getI_time();
				}
				if(totalflow.compareTo(totalflowmax)>0||(totalflow.compareTo(totalflowmax)<=0&&totalflowCount==1)){
					totalflowmax=totalflow;
					totalflowmaxDate=vo.getI_time();
				}
			}
			if(vo.getNtotalflow()!=null){
				ntotalflowCount=ntotalflowCount+1;//净累计流量
				result[vo.getI_time().getHours()][n*5+4]=ntotalflow.toString();
				ntotalflowavg=ntotalflowavg+ntotalflow;//平均值
				if(ntotalflow.compareTo(ntotalflowmin)<0||(ntotalflow.compareTo(ntotalflowmin)>=0&&ntotalflowCount==1)){//最小值
					ntotalflowmin=ntotalflow;
					ntotalflowminDate=vo.getI_time();
				}
				if(ntotalflow.compareTo(ntotalflowmax)>0||(ntotalflow.compareTo(ntotalflowmax)<=0&&ntotalflowCount==1)){
					ntotalflowmax=ntotalflow;
					ntotalflowmaxDate=vo.getI_time();
				}
			}
			if(vo.getFtotalflow()!=null){
				ftotalflowCount=ftotalflowCount+1;//反向累计流量
				result[vo.getI_time().getHours()][n*5+5]=ftotalflow.toString();
				ftotalflowavg=ftotalflowavg+ftotalflow;//平均值
				if(ftotalflow.compareTo(ftotalflowmin)<0||(ftotalflow.compareTo(ftotalflowmin)>=0&&ftotalflowCount==1)){//最小值
					ftotalflowmin=ftotalflow;
					ftotalflowminDate=vo.getI_time();
				}
				if(ftotalflow.compareTo(ftotalflowmax)>0||(ftotalflow.compareTo(ftotalflowmax)<=0&&ftotalflowCount==1)){
					ftotalflowmax=ftotalflow;
					ftotalflowmaxDate=vo.getI_time();
				}
			}
			
//
//			if(i==0){
//				flowavg=flow;
//				totalflowavg=totalflow;
//				pressmin=press;
//				flowmin=flow;
//				totalflowmin=totalflow;
//				pressminDate=vo.getI_time();
//				flowminDate=vo.getI_time();
//				totalflowminDate=vo.getI_time();
//				pressmax=press;
//				flowmax=flow;
//				totalflowmax=totalflow;
//				pressmaxDate=vo.getI_time();
//				flowmaxDate=vo.getI_time();
//				totalflowmaxDate=vo.getI_time();
//			}else{
//				pressavg+=press;
//				flowavg+=flow;
//				totalflowavg+=totalflow;
//				if(press.compareTo(pressmin)<0){
//					pressmin=press;
//					pressminDate=vo.getI_time();
//				}
//				if(flow.compareTo(flowmin)<0){
//					flowmin=flow;
//					flowminDate=vo.getI_time();
//				}
//				if(totalflow.compareTo(totalflowmin)<0){
//					totalflowmin=totalflow;
//					totalflowminDate=vo.getI_time();
//				}
//				if(press.compareTo(pressmax)>0){
//					pressmax=press;
//					pressmaxDate=vo.getI_time();
//				}
//				if(flow.compareTo(flowmax)>0){
//					flowmax=flow;
//					flowmaxDate=vo.getI_time();
//				}
//				if(totalflow.compareTo(totalflowmax)>0){
//					totalflowmax=totalflow;
//					totalflowmaxDate=vo.getI_time();
//				}
//			}
		}
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(pressCount!=0){
			result[24][n*5+1]=ConstantsUtil.formateNumber(pressavg/(pressCount==0?1:pressCount))+"";//平均压力
			result[25][n*5+1]=pressmin+"";//最小压力
			if(pressminDate!=null){
				result[26][n * 5 + 1] = sdf.format(pressminDate)+"";//最小压力时间
			}
			result[27][n*5+1]=pressmax+"";//最大压力
			if(pressmaxDate!=null){
				result[28][n*5+1]=sdf.format(pressmaxDate)+"";//最大压力时间
			}
		}
		if(flowCount!=0){
			result[24][n*5+2]=ConstantsUtil.formateNumber(flowavg/(flowCount==0?1:flowCount))+"";//平均流量
			result[25][n*5+2]=flowmin+"";//最小流量
			if(flowminDate!=null){
				result[26][n*5+2]=sdf.format(flowminDate)+"";//最小流量时间
			}
			result[27][n*5+2]=flowmax+"";//最大流量
			if(flowmaxDate!=null){
				result[28][n*5+2]=sdf.format(flowmaxDate)+"";//最大流量时间
			}
		}
		if(totalflowCount!=0){
			result[24][n*5+3]=ConstantsUtil.formateNumber(totalflowavg/(totalflowCount==0?1:totalflowCount))+"";//平均瞬时
			result[25][n*5+3]=totalflowmin+"";//最小瞬时
			if(totalflowminDate!=null){
				result[26][n*5+3]=sdf.format(totalflowminDate)+"";//最小瞬时时间
			}
			result[27][n*5+3]=totalflowmax+"";//最大瞬时
			if(totalflowmaxDate!=null){
				result[28][n*5+3]=sdf.format(totalflowmaxDate)+"";//最大瞬时时间
			}
		}
		if(ntotalflowCount!=0){
			result[24][n*5+4]=ConstantsUtil.formateNumber(ntotalflowavg/(ntotalflowCount==0?1:ntotalflowCount))+"";//平均净累计
			result[25][n*5+4]=ntotalflowmin+"";//最小净累计
			if(ntotalflowminDate!=null){
				result[26][n*5+4]=sdf.format(ntotalflowminDate)+"";//最小净累计时间
			}
			result[27][n*5+4]=ntotalflowmax+"";//最大净累计
			if(ntotalflowmaxDate!=null){
				result[28][n*5+4]=sdf.format(ntotalflowmaxDate)+"";//最大净累计时间
			}
		}
		if(ftotalflowCount!=0){
			result[24][n*5+5]=ConstantsUtil.formateNumber(ftotalflowavg/(ftotalflowCount==0?1:ftotalflowCount))+"";//平均反向累计
			result[25][n*5+5]=ftotalflowmin+"";//最小反向累计
			if(ftotalflowminDate!=null){
				result[26][n*5+5]=sdf.format(ftotalflowminDate)+"";//最小反向累计时间
			}
			result[27][n*5+5]=ftotalflowmax+"";//最大反向累计
			if(ftotalflowmaxDate!=null){
				result[28][n*5+5]=sdf.format(ftotalflowmaxDate)+"";//最大反向累计时间
			}
		}
	}
	
	/** 
	 * 取得当月天数 
	 * @throws ParseException 
	 * */  
	public static int getCurrentMonthLastDay(String conditionMonth)  
	{  
	    Calendar a = Calendar.getInstance();  
	    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM");
	    Date conditonDate;
		try {
			conditonDate = sdf.parse(conditionMonth);
		} catch (ParseException e) {
			logger.error("getCurrentMonthLastDay:",e);
			conditonDate=new Date();
		}
	    a.setTime(conditonDate);
	    a.set(Calendar.DATE, 1);//把日期设置为当月第一天  
	    a.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天  
	    int maxDate = a.get(Calendar.DATE);  
	    return maxDate;  
	}
	
	/**
	 * 
	 * 这个方法的主要工作就是将查询出来的数据重新格式化，以时间为key，放入到map中
	 * 此方法只针对数据库返回值是10列的数据，其他列数据需要重写
	 * @param result	返回到页面的数据
	 * @param ndate		数据库查询出的结果集
	 * @param n			步长
	 * @param height	需要初始化的高度，平均值，最小值不能初始化，因为里面有用到高度去计算这些东西
	 */
	private void formateArray(String[][] result,List<Object[]> ndate,int n,int height){
		for(int i=0;i<height+5;i++){//初始化各自水表的相关数据
			for(int j=0;j<10;j++){
				if(j==0){
					continue;
				}
//				if(i>=height){
					result[i][n*9+j]="--";
//				}else{
//					result[i][n*9+j]=0.0+"";
//				}
			}
		}
//		result[height]//里面放的都是临时数据用来找出平均值
//		result[height+1]//里面放的都是临时数据用来找出最小值
//		result[height+2]//里面放的都是临时数据用来找出最小值时间
//		result[height+3]//里面放的都是临时数据用来找出最大值
//		result[height+4]//里面放的都是临时数据用来找出最大值时间
		int[] i_1={0,0,0,0,0,0,0,0,0,0};
		for(int i=0;i<ndate.size();i++){
			Object[] vo=ndate.get(i);
			for(int j=0;j<vo.length;j++){
				
				if(j>0&&vo[j]!=null){
					//格式化两位小数
					vo[j]=ConstantsUtil.formateNumber(vo[j]);
					result[Integer.parseInt(vo[0]+"")][n*9+j]=(vo[j]==null?0.0+"":vo[j]+"");
					i_1[j]=i_1[j]+1;
					
					result[height][n*9+j]="--".equals(result[height][n*9+j])?vo[j]+"":(new BigDecimal(result[height][n*9+j]).add(new BigDecimal(vo[j]+"")).toString());
					if(result[height+1][n*9+j]==null||"--".equals(result[height+1][n*9+j])){
						result[height+1][n*9+j]=vo[j]+"";
						result[height+2][n*9+j]=result[Integer.parseInt(vo[0]+"")][0]+"";
					}else if(Float.parseFloat(result[height+1][n*9+j]+"")>Float.parseFloat(vo[j]+"")){//如果临时数组中的数字大于vo那么替换
						result[height+1][n*9+j]=vo[j]+"";
						result[height+2][n*9+j]=result[Integer.parseInt(vo[0]+"")][0]+"";
					}
					if(result[height+3][n*9+j]==null||"--".equals(result[height+3][n*9+j])){
						result[height+3][n*9+j]=vo[j]+"";
						result[height+4][n*9+j]=result[Integer.parseInt(vo[0]+"")][0]+"";
					}else if(Float.parseFloat(result[height+3][n*9+j]+"")<Float.parseFloat(vo[j]+"")){//如果临时数组中的数字小于vo那么替换
						result[height+3][n*9+j]=vo[j]+"";
						result[height+4][n*9+j]=result[Integer.parseInt(vo[0]+"")][0]+"";
					}
				}
			}
		}
		//平均值
		if(ndate!=null&&ndate.size()>0){
			for(int i=0;i<10;i++){
				if(i==0){
					continue;
				}
				if(!"--".equals(result[height][n*9+i])){
					result[height][n*9+i]=ConstantsUtil.formateNumber((Float.parseFloat(result[height][n*9+i]==null?0.0+"":result[height][n*9+i])/i_1[i]))+"";	
				}
			}
		}
	}
	
	/**
	 * 
	 * 这个方法的主要工作就是将查询出来的数据重新格式化，以时间为key，放入到map中
	 * 此方法只针对数据库返回值是11列的数据，其他列数据需要重写
	 * @param result	返回到页面的数据
	 * @param ndate		数据库查询出的结果集
	 * @param n			步长
	 * @param height	需要初始化的高度，平均值，最小值不能初始化，因为里面有用到高度去计算这些东西
	 */
	private void formateArray2(String[][] result,List<Object[]> ndate,int n,int height){
		for(int i=0;i<height+5;i++){//初始化各自水表的相关数据
			for(int j=0;j<10;j++){
				if(j==0){
					continue;
				}
//				if(i>=height){
					result[i][n*9+j]="--";
//				}else{
//					result[i][n*9+j]=0.0+"";
//				}
			}
		}
//		result[height]//里面放的都是临时数据用来找出平均值
//		result[height+1]//里面放的都是临时数据用来找出最小值
//		result[height+2]//里面放的都是临时数据用来找出最小值时间
//		result[height+3]//里面放的都是临时数据用来找出最大值
//		result[height+4]//里面放的都是临时数据用来找出最大值时间
		int[] i_1={0,0,0,0,0,0,0,0,0,0};
		for(int i=0;i<ndate.size();i++){
			Object[] vo=ndate.get(i);
			for(int j=0;j<vo.length;j++){
				
				if(j>0&&vo[j]!=null){
					//格式化两位小数
					vo[j]=ConstantsUtil.formateNumber(vo[j]);
					result[Integer.parseInt(vo[0]+"")][n*9+j]=(vo[j]==null?0.0+"":vo[j]+"");
					i_1[j]=i_1[j]+1;
					
					result[height][n*9+j]="--".equals(result[height][n*9+j])?vo[j]+"":(new BigDecimal(result[height][n*9+j]).add(new BigDecimal(vo[j]+"")).toString());
					if(result[height+1][n*9+j]==null||"--".equals(result[height+1][n*9+j])){
						result[height+1][n*9+j]=vo[j]+"";
						result[height+2][n*9+j]=result[Integer.parseInt(vo[0]+"")][0]+"";
					}else if(Float.parseFloat(result[height+1][n*9+j]+"")>Float.parseFloat(vo[j]+"")){//如果临时数组中的数字大于vo那么替换
						result[height+1][n*9+j]=vo[j]+"";
						result[height+2][n*9+j]=result[Integer.parseInt(vo[0]+"")][0]+"";
					}
					if(result[height+3][n*9+j]==null||"--".equals(result[height+3][n*9+j])){
						result[height+3][n*9+j]=vo[j]+"";
						result[height+4][n*9+j]=result[Integer.parseInt(vo[0]+"")][0]+"";
					}else if(Float.parseFloat(result[height+3][n*9+j]+"")<Float.parseFloat(vo[j]+"")){//如果临时数组中的数字小于vo那么替换
						result[height+3][n*9+j]=vo[j]+"";
						result[height+4][n*9+j]=result[Integer.parseInt(vo[0]+"")][0]+"";
					}
				}
			}
		}
		//平均值
		if(ndate!=null&&ndate.size()>0){
			for(int i=0;i<10;i++){
				if(i==0){
					continue;
				}
				if(!"--".equals(result[height][n*9+i])){
					result[height][n*9+i]=ConstantsUtil.formateNumber((Float.parseFloat(result[height][n*9+i]==null?0.0+"":result[height][n*9+i])/i_1[i]))+"";	
				}
			}
		}
	}
}
