package com.welltech.waterAffair.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.welltech.waterAffair.domain.entity.MachineInfo;

@Mapper
public interface MachineInfoMapper {
	
	/**
	 * 根据水表号查询水表信息
	 * @param num
	 * @return
	 */
	MachineInfo findOneByNum(Integer num);

	/**
	 * 查询所有水表信息
	 * @return
	 */
	List<MachineInfo> findAll();

	/**
	 * 根据公司id查询所有水表
	 * @param id
	 * @return
	 */
	List<MachineInfo> findByCompanyId(Integer companyId);

	/**
	 * 根据用户id查询水表
	 * @param userId
	 * @return
	 */
	List<MachineInfo> findByUserId(Integer userId);
	
	/**
	 * 根据用户id查询水表
	 * @param userId
	 * @return
	 */
	List<MachineInfo> findByUserIdAndSubUserName(@Param("userId")Integer userId,@Param("subUserName")String subUserName);
	/**
	 * 根据subUserName查询所属水表，给管理员使用
	 */
	List<MachineInfo> findBySubUserName(@Param("subUserName") String subUserName);
	/**
	 * 根据公司id和用户id查询所属水表
	 * @param companyId
	 * @param userId
	 * @return
	 */
	List<MachineInfo> findByCompanyIdAndUserId(@Param("companyId") Integer companyId, @Param("userId") Integer userId);

	List<MachineInfo> findPageByCriteria();

	/**
	 * 根据名称查询站点
	 * @param stations
	 * @return
	 */
	List<MachineInfo> findBySubname(@Param("stations") List<String> stations);

	/**
	 * 保存
	 * @param machineInfo
	 * @return
	 */
	int insertOne(MachineInfo machineInfo);

	/**
	 * 更新
	 * @param machineInfo
	 * @return
	 */
	int updateByNum(MachineInfo machineInfo);

	/**
	*
	* @param machineInfo
	*/
	int updateIpAndPortByNum(MachineInfo machineInfo);
	/**
	* @Author  Man Zhiwei
	* @Comment 查询最大的num值
	* @Param   []
	* @Date        2019-03-15 14:47
	*/
	int getMaxNum();


	int findOneByName(@Param("name") String name);

	int findOneByCCID(@Param("ccid") String ccid);
}