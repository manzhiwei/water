package com.welltech.waterAffair.repository;

import org.apache.ibatis.annotations.Mapper;

import com.welltech.waterAffair.domain.entity.AlarmRowRecord;

@Mapper
public interface AlarmRowRecordMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AlarmRowRecord record);

    int insertSelective(AlarmRowRecord record);

    AlarmRowRecord selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AlarmRowRecord record);

    int updateByPrimaryKey(AlarmRowRecord record);
}