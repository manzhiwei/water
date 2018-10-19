package com.welltech.waterAffair.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.welltech.waterAffair.domain.entity.ResourceImg;

@Mapper
public interface ResourceImgMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ResourceImg record);

    int insertSelective(ResourceImg record);

    ResourceImg selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ResourceImg record);

    int updateByPrimaryKey(ResourceImg record);
    
    /**
     * 根据操作id查询实体
     * @param id
     * @return
     */
    ResourceImg findOneByOperationId(Integer id);

    /**
     * 根据水表编号和文件类型查询
     * @param num
     * @param fileType
     * @return
     */
	ResourceImg findByNumAndFileType(@Param("num") Integer num, @Param("fileType") String fileType);

	/**
	 * 根据水表编号查询
	 * @param num
	 * @return
	 */
	List<ResourceImg> findByNum(Integer num);
}