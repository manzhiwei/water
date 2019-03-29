package com.welltech.waterAffair.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.welltech.waterAffair.domain.criteria.NdataCriteria;
import com.welltech.waterAffair.domain.criteria.WaterHourCriteria;
import com.welltech.waterAffair.domain.entity.Ndata;
import com.welltech.waterAffair.domain.entity.NdataSs;

@Mapper
public interface GprsDataMapper {

	/**
	 * 查询电磁水表一条最新数据并封装为Ndata实体
	 */
	Ndata findOneNdataByNum(Integer num);
	
	/**
	 * 查询电磁水表一条最新数据并封装为NdataSs实体
	 */
	NdataSs findOneNdataSsByNum(Integer num);

	/**
	 * 条件查询电磁水表数据
	 * @param criteria
	 * @return
	 */
	List<Ndata> findNdataByCriteria(@Param("criteria") NdataCriteria criteria);

	/**
	 * 条件查询电磁水表数据
	 * @param criteria
	 * @return
	 */
	List<Ndata> findNdataByCriteriaLastConnecting(@Param("criteria") NdataCriteria criteria);

	/**
	 * 条件查询电磁水表数据
	 * @param criteria
	 * @return
	 */
	List<NdataSs> findNdataSsByCriteria(@Param("criteria") NdataCriteria criteria);

	/**
	 * 查询某天小时数据
	 * @param criteria
	 * @return
	 */
	Ndata findNdataHourData(@Param("criteria") NdataCriteria criteria);


	/**
	 * 查询日数据
	 * @param criteria
	 * @return
	 */
	Map<String,Object> findNdataDayData(@Param("criteria") NdataCriteria criteria);

	/**
	 * 查询dma小流量
	 * @param param
	 * @return
	 */
	List<Map<String, Object>> queryDmaMicor(Map<String, Object> param);

	/**
	 * 分页查询
	 * @param criteria
	 * @return
	 */
	List<Ndata> findPageNdataByWaterHourCriteria(@Param("criteria") WaterHourCriteria criteria);

	List<Ndata> queryNdataByWaterHourCriteria(@Param("criteria") WaterHourCriteria criteria);

	/**
	 * 季度报表数据
	 * @param param
	 * @return
	 */
	Map<String, Object> findNdataSeasonData(Map<String, Object> param);

	/**
	 * 年报表数据
	 * @param param
	 * @return
	 */
	Map<String, Object> findNdataYearData(Map<String, Object> param);

	/**
	 * 累计流量
	 */
	Map<String, Object> findDiffTotalFlow(Map<String, Object> param);
	
	/**
	 * 近几天数据
	 * @param param
	 * @return
	 */
	List<Map<String, Object>> findDaysData(Map<String, Object> param);

	/**
	 * 实时数据
	 * @param param
	 * @return
	 */
	Map<String, Object> queryValue(Map<String, Object> param);

	/**
	 * 日环比
	 * @param param
	 * @return
	 */
	Map<String, Object> queryValueCompareDay(Map<String, Object> param);

	/**
	 * 月环比
	 * @param param
	 * @return
	 */
	Map<String, Object> queryValueCompareMonth(Map<String, Object> param);

	/**
	 * 周环比
	 * @param param
	 * @return
	 */
	Map<String, Object> queryValueCompareWeek(Map<String, Object> param);

	/**
	 * 年环比
	 * @param param
	 * @return
	 */
	Map<String, Object> queryValueCompareYear(Map<String, Object> param);

	/**
	 * 流量统计
	 * @param param
	 * @return
	 */
	Float queryTotalflow(Map<String, Object> param);

	/**
	 * 某一点平均值
	 * @param param
	 * @return
	 */
	Float findSsDaysData(Map<String, Object> param);

	/**
	 * 当日最新流量差值
	 * @param num
	 * @return
	 */
	float findDayTotalFlowDiff(int num);
	
	/**
	 * 昨日同期数据
	 * @param num
	 * @return
	 */
	Ndata findOneYesterdayNdataByNum(Integer num);

	/**
	 * 按偏移量查询一条记录
	 * @return
	 */
	Ndata findOneNdataByNumAndOffset(@Param(value="num") Integer num, @Param(value="startIndex") int index);

	/**
	 * 查询本月使用的净累计流量
	 * @param num
	 * @param year
	 * @param month
	 * @param lastmonth
	 * @return
	 */
	Map<String,Object> find4300MonthTotalflowData(@Param(value = "num") Integer num,@Param(value = "year") String year,@Param(value = "month") String month,@Param(value = "lastmonth") String lastmonth);
}