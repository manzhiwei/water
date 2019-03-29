package com.welltech.waterAffair.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.welltech.waterAffair.repository.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.welltech.waterAffair.common.aop.pagination.bean.Page;
import com.welltech.waterAffair.common.base.Sort;
import com.welltech.waterAffair.common.util.ConstantsUtil;
import com.welltech.waterAffair.common.util.MeterUtils;
import com.welltech.waterAffair.common.util.ReflectionUtils;
import com.welltech.waterAffair.domain.criteria.NdataCriteria;
import com.welltech.waterAffair.domain.criteria.WaterHourCriteria;
import com.welltech.waterAffair.domain.dto.FlowAndPressTrendDTO;
import com.welltech.waterAffair.domain.dto.MeterHourDTO;
import com.welltech.waterAffair.domain.dto.MeterMinuteDTO;
import com.welltech.waterAffair.domain.dto.MeterRealtimeStaticsDTO;
import com.welltech.waterAffair.domain.dto.OneMeterStaticsDTO;
import com.welltech.waterAffair.domain.entity.MachineInfo;
import com.welltech.waterAffair.domain.entity.Ndata;
import com.welltech.waterAffair.domain.entity.NdataSs;
import com.welltech.waterAffair.domain.vo.NdataVo;
import com.welltech.waterAffair.domain.vo.NdatassVo;
import com.welltech.waterAffair.domain.vo.PageVo;
import com.welltech.waterAffair.domain.vo.StatisticsVo;
import com.welltech.waterAffair.domain.vo.WaterStatisticsHourVo;


/**
 * Created by zhoupei on 2016/12/4.
 * Motified by WangXin on 2017/04/19
 */
@Service
public class StaticsService {

	@Autowired
	private NdataMapper ndataMapper;
	@Autowired
	private NdataSsMapper ndataSsMapper;
	@Autowired
	private GprsDataMapper gprsDataMapper;
	@Autowired
	private MachineInfoMapper machineInfoMapper;
	@Autowired
	private CompanyMapper companyMapper;
	@Autowired
	private MeterService meterService;

	@Autowired
	private GprsDataFor4200Mapper gprsDataFor4200Mapper;
	
    //查询实时数据分析所有列表
	public Map<String,List<MeterRealtimeStaticsDTO>> findMeterRealtimeStaticsInfoLists(List<Integer> meterIds) {

		//当前最高列表：实时参数正序排列

		//当前最低列表：实时参数倒序排列

		//今日最高列表：各个水表当天最高的值，正序排列

		//今日最低列表：各个水表当天最低的值，倒序排列

		//今日振幅：各个水表当日（最大值－最小值）／最大值

		//去年同比振幅：各个水表去年的同一个时间（同一个小时内分钟最接近的）的值对比：（最大值－最小值）／最大值

		//日环比：各个水表同昨日的环比  （今日－昨日）／昨日 （同一个小时内分钟最接近的）

		//周环比：（今日－上周）／上周  （同一个小时内分钟最接近的）

		//月环比：（今日－上月）／上月 （同一个小时内分钟最接近的）


		return null;
	}


	//查询单点数据分析
	public OneMeterStaticsDTO findMeterThreeDayStaticsInfo(Integer meterId){
		return null;
	}

	//时用水统计 以小时统计
	public List<MeterHourDTO> findMeterHourStaticsInfo(Integer meterId, Date startDate, Date endDate){
		return null;
	}

	//时用水统计 查询某个小时内的统计数据 以分钟统计
	public List<MeterMinuteDTO> findMeterMinuteStaticsInfo(Integer meterId,Date startDate,Date endDate){
		return null;
	}

	//趋势图统计
	List<List<FlowAndPressTrendDTO>> findMetersFlowAndPressTrendInfo(List<Integer> meterIds,Date startDate,Date endDate){
		return null;
	}


	public Map<String, List<Object[]>> queryRealtimeStatistics(String column, List<Integer> station, String start,
			String end) {
		Map<String,List<Object[]>> result=new HashMap<String,List<Object[]>>();
		//当前最高
		for(int i:station){
			MachineInfo info = machineInfoMapper.findOneByNum(i);
			Object[] tmp=this.queryValue(column, info, start, end);
			formateData(tmp, result,info.getShortName());
			Object[] tmp1=this.queryValueCompareDay(column, info);
			formateData1(tmp1, result,"dayCompare",info.getShortName());
			Object[] tmp2=this.queryValueCompareMonth(column, info);
			formateData1(tmp2, result,"monthCompare",info.getShortName());
			Object[] tmp3=this.queryValueCompareWeek(column, info);
			formateData1(tmp3, result,"weekCompare",i+info.getShortName());
			Object[] tmp4=this.queryValueCompareYear(column, info);
			formateData1(tmp4, result,"yearCompare",info.getShortName());
		}
		dealValue(result);
		return result;
	}

	private Object[] queryValue(String column, MachineInfo info, String start, String end) {
		Map<String,Object> map = null;
		Map<String,Object> param = new HashMap<>();
		param.put("column", column);
		param.put("num", info.getNum());
		param.put("start", start);
		param.put("end", end);
		if(MeterUtils.isGprs4300(info)){
			if(info.getMeterTypeId() != 3)
				map = gprsDataMapper.queryValue(param);
			else
				map = gprsDataFor4200Mapper.queryValue(param);
		} else{
			map = ndataSsMapper.queryValue(param);
		}
		
		NdataSs ndataSs = null;
		if(MeterUtils.isGprs4300(info)){
			if(info.getMeterTypeId() !=3)
				ndataSs = gprsDataMapper.findOneNdataSsByNum(info.getNum());
			else
				ndataSs = gprsDataFor4200Mapper.findOneNdataSsByNum(info.getNum());
		} else{
			ndataSs = ndataSsMapper.findOneByNum(info.getNum());
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Object[] result = new Object[14];
		if(map != null){
			result = map.values().toArray(result);
		}
		result[0] = result[3] = result[6] = result[9] = result[12] = info.getShortName();
		result[1] = result[4] = ndataSs != null ? sdf.format(ndataSs.getiTime()) : sdf.format(new Date());
		result[2] = result[5] = ndataSs != null ? ReflectionUtils.getFieldValue(ndataSs, column) : 0;
		
		return result;
	}

	/**
	 * 日环比，就是拿当前时间点的数据，再查询昨天这个时间点的数据，但是可能昨天这个时间点不存在，那么就取最靠近这个时间点的数据
	 * 
	 * @param column
	 * @param num
	 * @return
	 */
	private Object[] queryValueCompareDay(String column, MachineInfo info) {
		Map<String,Object> map = null;
		Map<String,Object> param = new HashMap<>();
		param.put("column", column);
		param.put("num", info.getNum());
		if(MeterUtils.isGprs4300(info)){
			if(info.getMeterTypeId() !=3)
				map = gprsDataMapper.queryValueCompareDay(param);
			else
				map = gprsDataFor4200Mapper.queryValueCompareDay(param);
		} else{
			map = ndataSsMapper.queryValueCompareDay(param);
		}

		Object[] result = null;
		if(map != null){
			result = new Object[8];
			result = map.values().toArray(result);
			result[0] = info.getShortName();
		}
		
		return result;
	}

	/**
	 * 月环比，就是拿当前时间点的数据，再查询上月这个时间点的数据，但是可能上月这个时间点不存在，那么就取最靠近这个时间点的数据
	 * 
	 * @param column
	 * @param num
	 * @return
	 */
	private Object[] queryValueCompareMonth(String column, MachineInfo info) {
		Map<String,Object> map = null;
		Map<String,Object> param = new HashMap<>();
		param.put("column", column);
		param.put("num", info.getNum());
		if(MeterUtils.isGprs4300(info)){
			if(info.getMeterTypeId() !=3)
				map = gprsDataMapper.queryValueCompareMonth(param);
			else
				map = gprsDataFor4200Mapper.queryValueCompareDay(param);
		} else{
			map = ndataSsMapper.queryValueCompareMonth(param);
		}
		
		Object[] result = null;
		if(map != null){
			result = new Object[8];
			result = map.values().toArray(result);
			result[0] = info.getShortName();
		}
		
		return result;
	}


	/**
	 * 周环比，就是拿当前时间点的数据，再查询上周这个时间点的数据，但是可能上周这个时间点不存在，那么就取最靠近这个时间点的数据
	 * 
	 * @param column
	 * @param num
	 * @return
	 */
	private Object[] queryValueCompareWeek(String column, MachineInfo info) {
		Map<String,Object> map = null;
		Map<String,Object> param = new HashMap<>();
		param.put("column", column);
		param.put("num", info.getNum());
		if(MeterUtils.isGprs4300(info)){
			if(info.getMeterTypeId() !=3)
				map = gprsDataMapper.queryValueCompareWeek(param);
			else
				map = gprsDataFor4200Mapper.queryValueCompareWeek(param);
		} else{
			map = ndataSsMapper.queryValueCompareWeek(param);
		}
		
		Object[] result = null;
		if(map != null){
			result = new Object[8];
			result = map.values().toArray(result);
			result[0] = info.getShortName();
		}
		
		return result;
	}

	/**
	 * 同年环比，就是拿当前时间点的数据，再查询去年这个时间点的数据，但是可能去年这个时间点不存在，那么就取最靠近这个时间点的数据
	 * 
	 * @param column
	 * @param num
	 * @return
	 */
	private Object[] queryValueCompareYear(String column, MachineInfo info) {
		Map<String,Object> map = null;
		Map<String,Object> param = new HashMap<>();
		param.put("column", column);
		param.put("num", info.getNum());
		if(MeterUtils.isGprs4300(info)){
			if(info.getMeterTypeId() !=3)
				map = gprsDataMapper.queryValueCompareYear(param);
			else
				map = gprsDataFor4200Mapper.queryValueCompareYear(param);
		} else{
			map = ndataSsMapper.queryValueCompareYear(param);
		}
		
		Object[] result = null;
		if(map != null){
			result = new Object[8];
			result = map.values().toArray(result);
			result[0] = info.getShortName();
		}
		
		return result;
	}


	/**
	 * 当前最高
	 * @param parameter
	 * @return
	 */
	private void formateData(Object[] parameter,Map<String,List<Object[]>> result,String num){
		//因为现在的数据都还是有规律的，所以按照3列一拆还是可行
		String max="max";
		formateList(parameter, result, max, 0, 3,num,Sort.DOWM);//从大到小排列
		String min="min";
		formateList(parameter, result, min, 3, 3,num,Sort.UP);//从小到大排列
		String maxToday="maxToday";
		formateList(parameter, result, maxToday, 6, 3,num,Sort.DOWM);//从大到小排列
		String minToday="minToday";
		formateList(parameter, result, minToday, 9, 3,num,Sort.UP);//从小到大排列
		String swing="swing";
		formateList(parameter, result, swing, 12, 2,num,Sort.UP);//今日振幅
	}
	
	/**
	 * 将今日没数据的结果都设为 -- 并且放在最后不参与运算
	 * @param result
	 */
	private void dealValue(Map<String, List<Object[]>> result) {
		Set<String> empty = new HashSet<>();
		List<Object[]> max = result.get("max");
		dealArrays(max,empty);
		List<Object[]> min = result.get("min");
		dealArrays(min,empty);
		List<Object[]> maxToday = result.get("maxToday");
		dealArrays(maxToday,empty);
		List<Object[]> minToday = result.get("minToday");
		dealArrays(minToday,empty);
		
		//处理其他列数据
		List<Object[]> swing = result.get("swing");
		if(swing != null){
			for(Object[] o : swing){
				if(empty.contains(o[0])){
					o[1]="--";
				}
			}
		}
		List<Object[]> dayCompare = result.get("dayCompare");
		dealArrays2(dayCompare,empty);
		List<Object[]> monthCompare = result.get("monthCompare");
		dealArrays2(monthCompare,empty);
		List<Object[]> weekCompare = result.get("weekCompare");
		dealArrays2(weekCompare,empty);
		List<Object[]> yearCompare = result.get("yearCompare");
		dealArrays2(yearCompare,empty);
	}


	private void dealArrays2(List<Object[]> dayCompare, Set<String> empty) {
		if(dayCompare != null){
			for(Object[] o : dayCompare){
				if(empty.contains(o[0])){
					o[1]="--";
					o[2]="--";
					o[3]="--";
				}
			}
		}
	}


	/**
	 * 处理列表中今日没数据的结果
	 * @param max
	 */
	private void dealArrays(List<Object[]> value, Set<String> set) {
		if(value != null){
			LinkedList<Object[]> r = new LinkedList<>();
			for(int i=value.size()-1;i>=0;i--){
				Object[] os = value.get(i);
				if("0.0".equals(os[1]+"")){
					os[1] = "--";
					os[2] = "--";
					set.add(os[0]+"");
					r.addLast(os);
				} else{
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
					try {
						Date date = sdf.parse(os[1]+"");
						Date now = sdf2.parse(sdf2.format(new Date()));
						if(date.getTime() >= now.getTime()){
							r.addFirst(os);
						} else{
							os[1] = "--";
							os[2] = "--";
							set.add(os[0]+"");
							r.addLast(os);
						}
					} catch (ParseException e) {
						r.addFirst(os);
					}
				}
			}
			value = r;
		}
	}


	/**
	 * 当前比较
	 * @param parameter
	 * @return
	 */
	private void formateData1(Object[] parameter,Map<String,List<Object[]>> result,String key,String nummeter){
		Object[] tmp=new Object[4];
		List<Object[]> temp=result.get(key);
		if(temp==null){
			temp=new ArrayList<Object[]>();
		}
		if(parameter==null){
			tmp[0]=nummeter;
			tmp[1]="0.0";
			tmp[2]="0.0";
			tmp[3]="0.0";
		}else{
			String num=""+parameter[0];//num
			Date date1=(Date) parameter[1];//去年，上周，昨天，上月，靠近当前时间点
			float flow1=Float.parseFloat(ConstantsUtil.formateNumber(parameter[2])+"");//具体值
			flow1 = flow1 == -0.0f ? 0.0f : flow1;
			Date date2=(Date) parameter[3];//去年，上周，昨天，上月，远离当前时间点
			float flow2=Float.parseFloat(ConstantsUtil.formateNumber(parameter[4])+"");//具体值
			flow2 = flow2 == -0.0f ? 0.0f : flow2;
			Date date=(Date) parameter[5];//当前时间点
			float flow=Float.parseFloat(ConstantsUtil.formateNumber(parameter[6])+"");//当前值
			flow = flow == -0.0f ? 0.0f : flow;
			Date date3=(Date) parameter[7];//当前时间点的过去时间
			//比较date1和date2那个时间的值距离date的上一个年，周，月，天的日期接近
			long dValue1=0l;
			long dValue2=0l;
			if(date3 != null && date1 != null){
				if(date3.compareTo(date1)>0){
					dValue1=date3.getTime()-date1.getTime();
				}else{
					dValue1=date1.getTime()-date3.getTime();
				}
			}
			if(date3 != null && date2 != null){
				if(date3.compareTo(date2)>0){
					dValue2=date3.getTime()-date2.getTime();
				}else{
					dValue2=date2.getTime()-date3.getTime();
				}
			}
			float flow3=dValue1>dValue2?flow2:flow1;
			tmp[0]=num;
			tmp[1]=flow3;
			tmp[2]=flow;
			if(flow>flow3 && flow==0){
				tmp[3] = ConstantsUtil.formateNumber(flow3);
			} else if(flow<=flow3 && flow3 == 0){
				tmp[3] = ConstantsUtil.formateNumber(flow);
			} else{
				tmp[3]=ConstantsUtil.formateNumber(flow>flow3?((flow-flow3)/flow):((flow3-flow)/flow3));//振幅是最大值-最小值/最大值
			}
		}
		temp.add(tmp);
		result.put(key, temp);
	}
	
	private void formateList(Object[] parameter,Map<String,List<Object[]>> result,String key,int start,int step,String num,int sort){
		List<Object[]> t=result.get(key);
		if(t==null){
			t=new ArrayList<Object[]>();
		}
		Object[] tmp=new Object[step];
		for(int i=0;i<step;i++){//数据为空的进行初始化
			tmp[i]="0.0";
		}
		if(parameter!=null){
			for(int i=0;i<step;i++){
				tmp[i]=parameter[start+i]==null?"0.0":ConstantsUtil.formateNumber(parameter[start+i]);
			}
		}
		tmp[0]=num;
		t.add(tmp);
		Collections.sort(t, new Sort(sort,step-1));

		result.put(key, t);
	}

	/**
	 * 单点数据分析
	 * @param column
	 * @param num
	 * @return
	 */
	public StatisticsVo queryddsj(String column, String num) {
		MachineInfo info = machineInfoMapper.findOneByNum(Integer.parseInt(num));
		Ndata ndata = null;
		if(MeterUtils.isGprs4300(info)){
			if(info.getMeterTypeId() != 3)
				ndata = gprsDataMapper.findOneNdataByNum(info.getNum());
			else
				ndata = gprsDataFor4200Mapper.findOneNdataByNum(info.getNum());
		} else{
			ndata = ndataMapper.findOneByNum(info.getNum());
		}
		if(ndata == null){
			ndata = new Ndata();
		}
		
		StatisticsVo vo=new StatisticsVo();
		Calendar c=Calendar.getInstance();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Object[] f = {ndata.getFlow(),ndata.getiTime()};
		vo.setSsll(f==null?"":new BigDecimal((float)f[0]).setScale(2, RoundingMode.HALF_UP)+"");//瞬时流量
		vo.setFlow(vo.getSsll() + " m³/h");//瞬时流量
		
		vo.setPress((ndata.getPress() == null ? 0.00 : new BigDecimal(ndata.getPress()).setScale(2, RoundingMode.HALF_UP)) + " kPa");//压力
		vo.setTotalflow((ndata.getTotalflow() == null ? 0.00 : new BigDecimal(ndata.getTotalflow()).setScale(2, RoundingMode.HALF_UP)) + " m³");//正向流量
		vo.setFtotalflow((ndata.getFtotalflow() == null ? 0.00 : new BigDecimal(ndata.getFtotalflow()).setScale(2, RoundingMode.HALF_UP)) + " m³");//反向流量
		vo.setVoltage((ndata.getCurrentv() == null ? 0.00 : new BigDecimal(ndata.getCurrentv()).setScale(2, RoundingMode.HALF_UP)) + " V");//工作电压
		vo.setCurrent((ndata.getCurrenti() == null ? 0.00 : new BigDecimal(ndata.getCurrenti()).setScale(2, RoundingMode.HALF_UP)) + " mA");//工作电流
		vo.setSignal((ndata.getSignalStrength() == null ? 0: new BigDecimal(ndata.getSignalStrength()).setScale(0, RoundingMode.HALF_UP)) + "");//信号强度
		
		vo.setReceiveTime(f==null && ndata.getiTime()!= null?"":sdf1.format(ndata.getiTime())+"");//最后接收时间
		String currentStart=sdf.format(c.getTime())+" 00:00:00";
		String currentEnd=sdf.format(c.getTime())+" 23:59:59";
		Float f1 = this.queryTodaytotalflow(num, currentStart, currentEnd, true);
		Float f2 = this.queryTodaytotalflow(num, currentStart, currentEnd, false);
		float tmp1=f1==null?0:f1;
		float tmp2=f2==null?0:f2;
		vo.setJrll(new BigDecimal(tmp1-tmp2).setScale(2, RoundingMode.HALF_UP)+"");//今日流量
		
		if(MeterUtils.isGprs4300(info)){
			if(info.getMeterTypeId() !=3)
				ndata = gprsDataMapper.findOneYesterdayNdataByNum(info.getNum());
			else
				ndata = gprsDataFor4200Mapper.findOneYesterdayNdataByNum(info.getNum());
		} else{
			ndata = ndataMapper.findOneYesterdayByNum(info.getNum());
		}
		if(ndata == null){
			ndata = new Ndata();
		}
		vo.setZrtq(new BigDecimal(ndata.getFlow()==null?0:ndata.getFlow()).setScale(2, RoundingMode.HALF_UP) + "");//昨日同期瞬时流量
		
		c.add(Calendar.DATE, -1);
		currentStart=sdf.format(c.getTime())+" 00:00:00";
		currentEnd=sdf.format(c.getTime())+" 23:59:59";
		currentEnd=sdf1.format(c.getTime());
		f1 = this.queryTodaytotalflow(num, currentStart, currentEnd, true);
		f2 = this.queryTodaytotalflow(num, currentStart, currentEnd, false);
		tmp1=f1==null?0:f1;
		tmp2=f2==null?0:f2;
		vo.setZrll(new BigDecimal(tmp1-tmp2).setScale(2, RoundingMode.HALF_UP)+"");//昨日流量
		
		vo.setAddressInstall(info.getAddress());
		vo.setDeciverType(info.getMetertype());
		
		return vo;
	}

	/**
	 * 统计今日流量
	 * 
	 * @param num
	 *            水表号
	 * @param currentStart
	 *            当前时间开始
	 * @param currentEnd
	 *            当前时间结束
	 * @param isMax
	 *            true：时间段内最大值，false，取时间段内最小值
	 * @return
	 */
	private Float queryTodaytotalflow(String num, String currentStart, String currentEnd, boolean isMax) {
		MachineInfo info = machineInfoMapper.findOneByNum(Integer.valueOf(num));
		Map<String, Object> param = new HashMap<>();
		param.put("num", Integer.valueOf(num));
		param.put("currentStart", currentStart);
		param.put("currentEnd", currentEnd);
		if(isMax){
			param.put("sort", "desc");
		} else{
			param.put("sort", "asc");
		}
		
		Float result = null;
		if(MeterUtils.isGprs4300(info)){
			if(info.getMeterTypeId() !=3)
				result = gprsDataMapper.queryTotalflow(param);
			else
				result = gprsDataFor4200Mapper.queryTotalflow(param);
		} else{
			result = ndataMapper.queryTotalflow(param);
		}
		
		return result == null ? 0 : result;
	}


	/**
	 * 时用水统计
	 * @param startTime
	 * @param endTime
	 * @param staions
	 * @param startIndex
	 * @param pageSize
	 * @return
	 */
	public PageVo<WaterStatisticsHourVo> queryWaterByHour(Date startTime, Date endTime, String station,
			int startIndex, int pageSize, Integer userId) {
		
		List<MachineInfo> infos = machineInfoMapper.findBySubname(Arrays.asList(new String[]{station}));
		if(infos == null || infos.isEmpty()){
			return new PageVo<>(1, 0, 0, null);
		}
		MachineInfo info = infos.get(0);
		
		WaterHourCriteria criteria = new WaterHourCriteria();
		criteria.setStartDate(startTime);
		criteria.setEndDate(endTime);
		criteria.setMeterId(info.getNum());
		Page page = criteria.getPage();
		page.setDefalutPageRows(pageSize);
		page.setCurrentPage(startIndex/pageSize + 1);
		criteria.setPage(page);
		
		List<Ndata> ndatas = null;
		if(MeterUtils.isGprs4300(info)){
			if(info.getMeterTypeId() != 3)
				ndatas = gprsDataMapper.queryNdataByWaterHourCriteria(criteria);
			else
				ndatas = gprsDataFor4200Mapper.queryNdataByWaterHourCriteria(criteria);
		}else{
			ndatas = ndataMapper.queryByWaterHourCriteria(criteria);
		}
		
		String company = meterService.getMeterCompany(info.getNum(), userId);
		
		List<WaterStatisticsHourVo> waters = new ArrayList<>();
		if(ndatas != null){
			//DateFormat df = new SimpleDateFormat("yy-MM-dd HH"); 注释于辉度修改单点数据分析后
			DateFormat df = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
			for(Ndata ndata : ndatas){
				WaterStatisticsHourVo hourVo = new WaterStatisticsHourVo(info.getShortName(), ndata.getiTime(), ndata.getFlow() + "",
						ndata.getTotalflow()+ "", ndata.getFtotalflow()+ "", ndata.getNtotalflow()+ "", ndata.getPress()+ "", info.getNum() + "," + df.format(ndata.getiTime()));
				if(MeterUtils.isGprs4300(info)){
					hourVo.setYearByYear("0.0");
					hourVo.setMothByMonth("0.0");
				}else{
					String yearByYear = ndataMapper.findYearByYear(info.getNum(),ndata.getiTime());
					hourVo.setYearByYear(StringUtils.isEmpty(yearByYear)?"-":yearByYear);
					String monthByMonth = ndataMapper.findMonthByMonth(info.getNum(),ndata.getiTime());
					hourVo.setMothByMonth(StringUtils.isEmpty(monthByMonth)?"-":monthByMonth);
				}
				hourVo.setCompanyName(company);
				waters.add(hourVo);
			}
		}
		page = criteria.getPage();
		return new PageVo<>(page.getCurrentPage(), page.getTotalRecord(), waters.size(), waters);
	}

	public NdataVo queryNdataLastData(Integer num) {
		MachineInfo info = machineInfoMapper.findOneByNum(num);
		Ndata ndata = null;
		if(MeterUtils.isGprs4300(info)){
			if(info.getMeterTypeId() != 3)
				ndata = gprsDataMapper.findOneNdataByNum(num);
			else
				ndata = gprsDataFor4200Mapper.findOneNdataByNum(num);
		}else{
			ndata = ndataMapper.findOneByNum(num);
		}
		NdataVo ndataVo = MeterUtils.getInstance(ndata, info);
		return ndataVo;
	}

	/**
	 * 查找连接间隔时间内的数据
	 * @param num
	 * @param start2End
	 * @param
	 * @return
	 */
	public List<NdatassVo> findConnectIntervalTimeData(Integer num,String start2End){
		MachineInfo info = machineInfoMapper.findOneByNum(num);
		NdataCriteria criteria = new NdataCriteria();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date startDate = null;
		Date endDate   = null;
		try {
			if(!start2End.endsWith(",")) {
				startDate = df.parse(start2End.substring(0,start2End.indexOf(",")));
				endDate = df.parse(start2End.substring(start2End.indexOf(",") + 1, start2End.length()));
			}else{
				endDate = df.parse(start2End.substring(0,start2End.indexOf(",")));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}

		Calendar calendar = Calendar.getInstance();

		//设置起始查找时间
		if(startDate != null) {
			try {
				calendar.setTime(startDate);
				criteria.setStartDate(calendar.getTime());

			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			criteria.setStartDate(null);
		}
		//设置结束查找时间
		try {
			calendar.setTime(endDate);
			criteria.setEndDate(calendar.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}

		criteria.setMeterId(num);

		List<NdataSs> ndataSs = null;
		if(MeterUtils.isGprs4300(info)){
			if(info.getMeterTypeId() !=3)
				ndataSs = gprsDataMapper.findNdataSsByCriteria(criteria);
			else
				ndataSs = gprsDataFor4200Mapper.findNdataSsByCriteria(criteria);
		} else{
			ndataSs = ndataSsMapper.findByCriteria(criteria);
		}
		List<NdatassVo> result = new ArrayList<>();
		if(ndataSs != null){
			for(NdataSs ss : ndataSs){
				NdatassVo vo = new NdatassVo(ss.getFlow() + "", ss.getFtotalflow() + "",
						ss.getiTime(), ss.getPress() + "", ss.getTime().getTime()/1000, ss.getTotalflow() + "", ss.getTotalflown()+"");
				result.add(vo);
			}
		}

		return result;

	}
	/**
	 * 查找一小时内的数据
	 * @param num
	 * @param date4pc
	 * @param time4pc
	 * @return
	 */
	public List<NdatassVo> findHourData(Integer num, String date4pc, String time4pc) {
		MachineInfo info = machineInfoMapper.findOneByNum(num);
		NdataCriteria criteria = new NdataCriteria();
		String newTime4pc = time4pc.substring(0,time4pc.indexOf(":"));
		int hour = Integer.parseInt(newTime4pc);
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(df.parse(date4pc));
		} catch (ParseException e) {
			e.printStackTrace();
			calendar.setTime(new Date());
		}
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		criteria.setStartDate(calendar.getTime());

		calendar.add(Calendar.HOUR_OF_DAY, 1);
		criteria.setEndDate(calendar.getTime());
		criteria.setMeterId(num);
		List<NdataSs> ndataSs = null;
		if(MeterUtils.isGprs4300(info)){
			if(info.getMeterTypeId() != 3)
				ndataSs = gprsDataMapper.findNdataSsByCriteria(criteria);
			else
				ndataSs = gprsDataFor4200Mapper.findNdataSsByCriteria(criteria);
		} else{
			ndataSs = ndataSsMapper.findByCriteria(criteria);
		}
		
		List<NdatassVo> result = new ArrayList<>();
		if(ndataSs != null){
			for(NdataSs ss : ndataSs){
				NdatassVo vo = new NdatassVo(ss.getFlow() + "", ss.getFtotalflow() + "",
						ss.getiTime(), ss.getPress() + "", ss.getTime().getTime()/1000, ss.getTotalflow() + "", ss.getTotalflown()+"");
				result.add(vo);
			}
		}
		
		return result;
	}

	/**
	 * 统计今天、昨天、前天3天的数据
	 * @return
	 */
	public Object[][] queryData(String column,String num){
		MachineInfo info = machineInfoMapper.findOneByNum(Integer.parseInt(num));
		
		Calendar c=Calendar.getInstance();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");

		String currentTime=sdf.format(c.getTime());
		//查询前天的数据
		c.add(Calendar.DATE, - 2);
		String start=sdf.format(c.getTime())+" 00:00:00";
		String end=sdf.format(c.getTime())+" 23:59:59";
		List<Object[]> current = new ArrayList<>();
		
		Map<String,Object> param = new HashMap<>();
		param.put("num", num);
		param.put("start", start);
		param.put("end", end);
		
		List<Map<String,Object>> maps = null;
		if(MeterUtils.isGprs4300(info)){
			// 如果是4300水表只取flow,ftotal,rtotal,signal,press,temp
			if("currentI".equals(column)||"currentV".equals(column)){
				column="0.0 "+column;
			}else if("ftotalflow".equals(column)){
				column="rtotal";
			}else if("totalflow".equals(column)){
				column="ftotal";
			}else if("ntotalflow".equals(column)){
				column="rtotal";
			}else if("Signal_strength".equals(column)){
				column="signal_intensity";
			}
			param.put("column", column);
			if (info.getMeterTypeId() != 3)
				maps = gprsDataMapper.findDaysData(param);
			else
				maps = gprsDataFor4200Mapper.findDaysData(param);
		} else{
			param.put("column", column);
			maps = ndataMapper.findDaysData(param);
		}
		
		if(maps != null){
			for(Map<String, Object> map : maps){
				Object[] objects = new Object[2];
				objects = map.values().toArray(objects);
				current.add(objects);
			}
		}
		
		Object[][] result=new Object[current.size()][4];
		//初始化小时
		List<Object> hours = new ArrayList<>();
		c.add(Calendar.DATE, 2);
		for(int i=0;i<current.size();i++){
			Object[] object = current.get(i);
			for(int j=0;j<4;j++){
				result[i][j]="0.0";
			}
			result[i][0]=sdf.format(c.getTime()) + " " + object[0] + ":00:00";
			hours.add(object[0]);
		}
		c.add(Calendar.DATE, -2);
		int size = 0;
		if(current!=null&&current.size()>0){
			size = current.size();
			formate(result,current,2);
		}
		c.add(Calendar.DATE, 1);
		
		//查询昨日 今日数据
		for(int i=1;i<3;i++){
			current = new ArrayList<>();

			start = sdf.format(c.getTime())+" 00:00:00";
			end = sdf.format(c.getTime())+" 23:59:59";

			param.put("start", start);
			param.put("end", end);

			if(MeterUtils.isGprs4300(info)){
				if(info.getMeterTypeId() !=3)
					maps = gprsDataMapper.findDaysData(param);
				else
					maps = gprsDataFor4200Mapper.findDaysData(param);
			} else{
				maps = ndataMapper.findDaysData(param);
			}

			if(maps != null){
				int index = 0;
				for(Map<String, Object> map : maps){
					if(index ++ < size){
						Object[] objects = new Object[2];
						objects = map.values().toArray(objects);
						current.add(objects);
					}
				}
			}
			
			if(current!=null && current.size()>0){
				formate(result,current,2-i);
			}
			c.add(Calendar.DATE, 1);
		}
		//最后将result第一列的时间从00的格式换为2017-05-01 00:00:00的格式
//		for(int i=0;i<result.length;i++){
//			String tmp=result[i][0]+"";
//			result[i][0]=currentTime+" " + tmp + ":00:00";;
//		}

		if("ftotalflow".equals(column)
			|| "totalflow".equals(column)
			|| "rtotal".equals(column)
			|| "ftotal".equals(column)){

			/**
			 * 取3天前的最后一条数据
			 */
			c.add(Calendar.DATE, -4);
			start = sdf.format(c.getTime())+" 00:00:00";
			end = sdf.format(c.getTime())+" 23:59:59";
			param.put("start", start);
			param.put("end", end);

			if(MeterUtils.isGprs4300(info)){
				if(info.getMeterTypeId() !=3 )
					maps = gprsDataMapper.findDaysData(param);
				else
					maps = gprsDataFor4200Mapper.findDaysData(param);
			} else{
				maps = ndataMapper.findDaysData(param);
			}
			Object[] objects = new Object[2];
			if(maps != null && maps.size() > 0){
				Map<String, Object> map = maps.get(maps.size() - 1);
				map.values().toArray(objects);
			}

			result = calculateTotalFlow(result, objects[1]);
		}

		return result;
	}
	
	private void formate(Object[][] result,List<Object[]> data,int step){
		for(int i = 0; i < data.size(); i++){
			Object[] object = data.get(i);
			result[i][step+1] = ConstantsUtil.formateNumber(object[1]);
		}
		if(step == 0){
			//今天数据格外处理
			for(int i = data.size(); i < result.length; i ++ ){
				result[i][step + 1] = null;
			}
		}
	}

	/**
	 * 计算累计流量
	 * @param data
	 * @Param obj
	 */
	private Object[][] calculateTotalFlow(Object[][] data, Object obj){
		Object[][] result = new Object[data.length][4];
		for(int i = 0; i < data.length; i ++ ){
			for(int j = 0 ; j < data[i].length; j++){
				result[i][j] = data[i][j];
				if(i >= 1 && j >= 1 && data[i][j] != null){
					result[i][j] = new BigDecimal(data[i][j].toString()).subtract(new BigDecimal(data[i-1][j].toString())).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
				}
			}
		}
		if(result.length >= 1){
			result[0][1] = new BigDecimal(data[0][1].toString()).subtract(new BigDecimal(data[data.length-1][2].toString())).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
			result[0][2] = new BigDecimal(data[0][2].toString()).subtract(new BigDecimal(data[data.length-1][3].toString())).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
			result[0][3] = obj == null ? 0.0 : new BigDecimal(data[0][3].toString()).subtract(new BigDecimal(obj.toString())).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
		}
		return result;
	}

}
