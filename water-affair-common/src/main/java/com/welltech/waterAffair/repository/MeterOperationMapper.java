package com.welltech.waterAffair.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.welltech.waterAffair.domain.criteria.MeterReplaceCriteria;
import com.welltech.waterAffair.domain.dto.ReplaceMeterRecordDTO;
import com.welltech.waterAffair.domain.entity.MeterOperation;

@Mapper
public interface MeterOperationMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MeterOperation record);

    int insertSelective(MeterOperation record);

    MeterOperation selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MeterOperation record);

    int updateByPrimaryKey(MeterOperation record);
    
    /**
     * 分页查询
     */
    List<ReplaceMeterRecordDTO> findPageReplaceMeterRecordDTOByCriteria(@Param("criteria") MeterReplaceCriteria criteria);

    /**
     * 查询记录
     * @param num
     * @return
     */
	List<MeterOperation> findByMeterId(Integer num);

}