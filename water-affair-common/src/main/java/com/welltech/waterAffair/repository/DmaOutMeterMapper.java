package com.welltech.waterAffair.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.welltech.waterAffair.domain.entity.DmaOutMeter;

@Mapper
public interface DmaOutMeterMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DmaOutMeter record);

    int insertSelective(DmaOutMeter record);

    DmaOutMeter selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DmaOutMeter record);

    int updateByPrimaryKey(DmaOutMeter record);

    /**
     * 根据dmaId查询
     * @param dmaId
     * @return
     */
	List<DmaOutMeter> findByDmaId(Integer dmaId);

	/**
	 * 删除关系
	 * @param dmaId
	 * @param meterId
	 */
	void deleteByDmaIdAndMeterId(@Param("dmaId") Integer dmaId, @Param("meterId") Integer meterId);
}