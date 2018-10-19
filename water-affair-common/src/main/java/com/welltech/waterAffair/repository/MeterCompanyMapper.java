package com.welltech.waterAffair.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.welltech.waterAffair.domain.entity.MeterCompany;

@Mapper
public interface MeterCompanyMapper {
    int insert(MeterCompany record);

    int insertSelective(MeterCompany record);

    /**
     * 根据公司id删除
     * @param id
     * @return
     */
	void deleteByCompanyId(Integer companyId);

	/**
	 * 删除公司与水表的关系
	 * @param userNameId
	 * @param meterId
	 */
	void deleteByUserNameIdAndMeterId(@Param("companyId") Integer companyId, @Param("meterIds") List<Integer> meterIds);

	/**
	 * 计数
	 * @param companyId
	 * @param meterId
	 * @return
	 */
	int count(@Param("companyId") Integer companyId, @Param("meterId") Integer meterId);
}