package com.welltech.waterAffair.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.welltech.waterAffair.domain.entity.User;

@Mapper
public interface UserMapper {
    int deleteByPrimaryKey(Integer userId);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer userId);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    /**
     * 根据用户名查询实体
     * @param userName
     * @return
     */
	User findByUserName(String userName);

	/**
	 * 根据公司id查询实体
	 * @param id
	 * @return
	 */
	List<User> findByCompanyId(Integer companyId);

	/**
	 * 根据公司id查询实体
	 * @param id
	 * @return
	 */
	List<User> findByCompanyIds(@Param("companyIds") List<Integer> companyIds);

	/**
	 * 查询所有用户
	 * @return
	 */
	List<User> findAll();

	/**
	 * 根据公司id删除用户
	 */
	int deleteByCompanyId(Integer companyId);
}