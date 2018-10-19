package com.welltech.waterAffair.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.welltech.waterAffair.domain.criteria.UserLogCriteria;
import com.welltech.waterAffair.domain.entity.UserLog;

@Mapper
public interface UserLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserLog record);

    int insertSelective(UserLog record);

    UserLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserLog record);

    int updateByPrimaryKey(UserLog record);
    
    /**
     * 分页条件查询
     * @param criteria
     * @return
     */
    List<UserLog> findPageByCriteria(@Param("criteria") UserLogCriteria criteria);
}