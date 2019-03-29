package com.welltech.waterAffair.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.welltech.waterAffair.domain.criteria.AlarmProcessRecordCriteria;
import com.welltech.waterAffair.domain.entity.AlarmProcessRecord;

@Mapper
public interface AlarmProcessRecordMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AlarmProcessRecord record);

    int insertSelective(AlarmProcessRecord record);

    AlarmProcessRecord selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AlarmProcessRecord record);

    int updateByPrimaryKey(AlarmProcessRecord record);

    /**
     * 分页查询告警
     * @param criteria
     * @return
     */
	List<AlarmProcessRecord> findPageByCriteria(@Param("criteria") AlarmProcessRecordCriteria criteria);

    List<AlarmProcessRecord> queryByCriteria(@Param("criteria") AlarmProcessRecordCriteria criteria);
	
	/**
	 * 条件查询告警
     * 只取一个用户所有水表报警信息记录的前三条
	 */
	List<AlarmProcessRecord> listByCriteria(@Param("criteria") AlarmProcessRecordCriteria criteria);
    /**
    * @Author  Man Zhiwei
    * @Comment 条件查询告警，固定条件状态
    * @Param   [criteria]
    * @Date        2018-12-13 16:41
    */
    List<AlarmProcessRecord> listByCriteria2(@Param("criteria") AlarmProcessRecordCriteria criteria);
    /**
     * 查询总的告警记录数
     * @param criteria
     * @return
     */
	Integer queryTotalRecord(@Param("criteria") AlarmProcessRecordCriteria criteria);
}