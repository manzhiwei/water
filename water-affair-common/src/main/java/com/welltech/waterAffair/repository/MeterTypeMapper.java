package com.welltech.waterAffair.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.welltech.waterAffair.domain.entity.MeterType;

@Mapper
public interface MeterTypeMapper {
	/**
	 * 查询所有水表信息
	 * @return
	 */
	List<MeterType> findAll();
}