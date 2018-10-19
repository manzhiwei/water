package com.welltech.waterAffair.repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.welltech.waterAffair.domain.criteria.NdataCriteria;
import com.welltech.waterAffair.domain.criteria.WaterHourCriteria;
import com.welltech.waterAffair.domain.entity.Ndata;

@Mapper
public interface NdataMapper {
	
	Ndata findOneByNum(@Param("num") Integer num);

	/**
	 * 条件查询
	 * @param criteria
	 * @return
	 */
	List<Ndata> findByCriteria(@Param("criteria") NdataCriteria criteria);

	/**
	 * 条件查询
	 * @param criteria
	 * @return
	 */
	List<Ndata> findNdataByCriteriaLastConnecting(@Param("criteria") NdataCriteria criteria);

	/**
	 * 小时数据
	 * @param criteria
	 * @return
	 */
	Ndata findHourData(@Param("criteria") NdataCriteria criteria);

	/**
	 * 天数据
	 * @return
	 */
	Map<String,Object> findDayData(@Param("criteria") NdataCriteria criteria);

	/**
	 * 月累计流量
	 * @return
	 */
	Map<String,Object> findMonthTotalflowData(@Param("num") Integer num,@Param("year") String year,@Param("month") String month,@Param("lastmonth") String lastmonth);

	/**
	 * 分页查询
	 * @param criteria
	 * @return
	 */
	List<Ndata> findPageByWaterHourCriteria(@Param("criteria") WaterHourCriteria criteria);
	
	//同比:指当前时间的1年前
	String findYearByYear(@Param("num") Integer num, @Param("date") Date date);

	//环比:指当前时间的上一个月这个时候
	String findMonthByMonth(@Param("num") Integer num, @Param("date") Date date);

	/**
	 * 季度报表
	 * @param param
	 * @return
	 */
	Map<String, Object> findSeasonData(Map<String, Object> param);

	/**
	 * 年报表数据
	 * @param param
	 * @return
	 */
	Map<String, Object> findYearData(Map<String, Object> param);
	
	/**
	 * 累计流量
	 */
	Map<String, Object> findDiffTotalFlow(Map<String, Object> param);

	/**
	 * 查询近几天数据
	 * @param param
	 * @return
	 */
	List<Map<String, Object>> findDaysData(Map<String, Object> param);

	/**
	 * 统计流量
	 * @param param
	 * @return
	 */
	Float queryTotalflow(Map<String, Object> param);

	/**
	 * 查询最新数据对应昨天的数据
	 * @param num
	 * @return
	 */
	Ndata findOneYesterdayByNum(@Param("num") Integer num);

	/**
	 * 按偏移量查询一条记录
	 * @param meterId
	 * @param i
	 * @return
	 */
	Ndata findOneByNumAndOffset(@Param("num") Integer num, @Param("startIndex") int index);
	/**
	 *返回上一个时间节点
	 * @param num
	 * @param currentStart
	 * @return
	 */
	Ndata findConnectIntervalTime(@Param("num") Integer num,@Param("currentStart") Object currentStart);
}