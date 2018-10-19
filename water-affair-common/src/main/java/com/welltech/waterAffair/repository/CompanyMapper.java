package com.welltech.waterAffair.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.welltech.waterAffair.domain.entity.Company;

@Mapper
public interface CompanyMapper {
    int deleteByPrimaryKey(Integer companyId);

    int insert(Company record);

    int insertSelective(Company record);

    Company selectByPrimaryKey(Integer companyId);

    int updateByPrimaryKeySelective(Company record);

    int updateByPrimaryKey(Company record);
    
    //以下为自定义方法
    /**
     * 根据用户id查询公司
     * @param userId
     * @return
     */
    Company findCompanyByUserId(Integer userId);

    /**
     * 根据公司id和公司等级查询子公司
     * @param parentCompanyId
     * @return
     */
	List<Company> findByParentCompanyId(@Param("parentCompanyId") Integer parentCompanyId, 
			@Param("companyLevel") String companyLevel);

	/**
     * 根据公司id查询公司
     * @param parentCompanyId
     * @return
     */
	List<Company> findByCompanyIds(@Param("companyIds") List<Integer> companyIds,
			@Param("companyLevel") String companyLevel);
	
	/**
	 * 查询所有公司
	 * @return
	 */
	List<Company> findAll();

	/**
	 * 根据水表查询所属公司
	 * @param num
	 * @return
	 */
	Company findByMeterId(Integer num);
	
	/**
	 * 根据水表号查询公司列表
	 * @param num
	 * @return
	 */
	List<Company> listByMeterId(Integer num);
}