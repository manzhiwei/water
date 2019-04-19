package com.welltech.waterAffair.repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.welltech.waterAffair.domain.criteria.NdataCriteria;
import com.welltech.waterAffair.domain.entity.NdataSs;

@Mapper
public interface NdataSsMapper {
	
	NdataSs findOneByNum(@Param("num") Integer num);

	/**
	 * 条件查询
	 * @param criteria
	 * @return
	 */
	List<NdataSs> findByCriteria(@Param("criteria") NdataCriteria criteria);

	/*List<NdataSs> findByCriteria1(@Param("criteria") NdataCriteria criteria,@Param("pageNo") Integer pageNo);*/
	/**
	 * 查询dma小流量数据
	 * @param map
	 * @return
	 */
	List<Map<String,Object>> queryDmaMicor(Map<String,Object> map);

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
	 * 某点平均数据
	 * @param param
	 * @return
	 */
	Float findDaysData(Map<String, Object> param);

	/**
	 * 当日最新流量差值
	 * @param num
	 * @return
	 */
	float findDayTotalFlowDiff(@Param("num") int num);

	/**
	* @Author  Man Zhiwei
	* @Comment 查询当前时间历史数据条数
	* @Param
	* @Date        2019-04-01 15:13
	*/
	int queryTotalNumber(@Param("criteria") NdataCriteria criteria);

	//添加
	float findHourTotalFlowDiff(@Param("num") int num,@Param("start") Date start,@Param("end") Date end);
}