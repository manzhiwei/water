package com.welltech.waterAffair.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.welltech.waterAffair.domain.entity.DmaMeter;

@Mapper
public interface DmaMeterMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DmaMeter record);

    int insertSelective(DmaMeter record);

    DmaMeter selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DmaMeter record);

    int updateByPrimaryKey(DmaMeter record);

    /**
     * 根据dma区域id查找实体列表
     * @param dmaId
     * @return
     */
	List<DmaMeter> findByDmaId(Integer dmaId);

	/**
	 * 删除关系
	 * @param dmaId
	 * @param meterId
	 */
	void deleteByDmaIdAndMeterId(@Param("dmaId") Integer dmaId, @Param("meterId") Integer meterId);
}