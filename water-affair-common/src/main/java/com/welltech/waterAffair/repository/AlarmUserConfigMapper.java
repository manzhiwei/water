package com.welltech.waterAffair.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.welltech.waterAffair.domain.entity.AlarmUserConfig;

@Mapper
public interface AlarmUserConfigMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AlarmUserConfig record);

    int insertSelective(AlarmUserConfig record);

    AlarmUserConfig selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AlarmUserConfig record);

    int updateByPrimaryKey(AlarmUserConfig record);
    
    /**
     * 查找配置
     * @param meterId
     * @param type
     * @param alarmType
     * @return
     */
    AlarmUserConfig findByMidAndTypeAndAlarmType(@Param("mid") Integer meterId,
    		@Param("mtype") String type, @Param("atype") String alarmType);
}