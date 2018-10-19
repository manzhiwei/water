package com.welltech.waterAffair.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.welltech.waterAffair.domain.entity.UserMeter;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMeterMapper {
    int insert(UserMeter record);

    int insertSelective(UserMeter record);

    /**
     * 根据用户id查询其水表关系
     * @param userId
     * @return
     */
	List<UserMeter> findByUserId(Integer userId);

	/**
	 * 查询所有关系
	 * @return
	 */
	List<UserMeter> findAll();

	/**
	 * 删除
	 * @param userMeter
	 */
	void delete(@Param("userMeter") UserMeter userMeter);

	/**
	 * 通过用户id删除
	 * @param userId
	 */
	void deleteByUserId(Integer userId);

	/**
	 * 查询关系
	 * @return
	 */
	List<UserMeter> findList(@Param("userMeter") UserMeter userMeter);

}