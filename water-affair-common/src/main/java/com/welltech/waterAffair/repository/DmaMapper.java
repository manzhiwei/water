package com.welltech.waterAffair.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.welltech.waterAffair.domain.entity.Dma;

@Mapper
public interface DmaMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Dma record);

    int insertSelective(Dma record);

    Dma selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Dma record);

    int updateByPrimaryKey(Dma record);

    /**
     * 根据用户id查询实体列表
     * @param userId
     * @return
     */
	List<Dma> findByUserId(Integer userId);

	/**
	 * 查询所有
	 * @return
	 */
	List<Dma> findAll();
}