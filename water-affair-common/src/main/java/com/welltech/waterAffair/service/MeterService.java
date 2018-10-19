package com.welltech.waterAffair.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.welltech.waterAffair.repository.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.welltech.waterAffair.common.aop.pagination.bean.Page;
import com.welltech.waterAffair.common.util.MeterUtils;
import com.welltech.waterAffair.common.util.UserUtils;
import com.welltech.waterAffair.domain.criteria.MeterReplaceCriteria;
import com.welltech.waterAffair.domain.criteria.NdataCriteria;
import com.welltech.waterAffair.domain.dto.ReplaceMeterRecordDTO;
import com.welltech.waterAffair.domain.entity.Company;
import com.welltech.waterAffair.domain.entity.MachineInfo;
import com.welltech.waterAffair.domain.entity.Ndata;
import com.welltech.waterAffair.domain.entity.NdataSs;
import com.welltech.waterAffair.domain.entity.User;
import com.welltech.waterAffair.domain.vo.AreaCompanyVo;
import com.welltech.waterAffair.domain.vo.BranchCompanyVo;
import com.welltech.waterAffair.domain.vo.HeadCompanyVo;
import com.welltech.waterAffair.domain.vo.NdataVo;
import com.welltech.waterAffair.domain.vo.NdatassVo;
import com.welltech.waterAffair.domain.vo.PageVo;

/**
 * Created by myMac on 17/4/22.
 */
@Service
public class MeterService {

	@Autowired
	private MachineInfoMapper machineInfoMapper;
	
	@Autowired
	private NdataMapper ndataMapper;
	
	@Autowired
	private NdataSsMapper ndataSsMapper;
	
	@Autowired
	private GprsDataMapper gprsDataMapper;

	@Autowired
    private CompanyMapper companyMapper;
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private MeterOperationMapper meterOperationMapper;
	
	@Autowired
	private CompanyService companyService;

	@Autowired
	private GprsDataFor4200Mapper gprsDataFor4200Mapper;
	
	//查询某个用户拥有的所有水表
    public List<MachineInfo> findUserMeterList(Integer userId){
    	List<MachineInfo> result = null;
    	User user = userMapper.selectByPrimaryKey(userId);
    	if(UserUtils.isAdmin(user)){
    		result = machineInfoMapper.findAll();
    	} else{
    		result = machineInfoMapper.findByUserId(userId);
    		if(result == null){
    			result = new ArrayList<>();
    		}
    		List<HeadCompanyVo> vos = companyService.getCompanyVoByUserId(userId);
    		for(HeadCompanyVo vo : vos){
    			if(vo.getBranchList() != null){
    				for(BranchCompanyVo bvo: vo.getBranchList()){
    					if(bvo.getMeterList() != null){
    						for(MachineInfo info : bvo.getMeterList()){
    							addMachineInfo(result, info);
    						}
    					}
    					if(bvo.getBranchList() != null){
    						for(AreaCompanyVo avo : bvo.getBranchList()){
    							if(avo.getMeterList() != null){
    								for(MachineInfo info : avo.getMeterList()){
    									addMachineInfo(result, info);
    								}
    							}
    						}
    					}
    				}
    			}
    		}
    	}
    	if(result == null){
			result = new ArrayList<>();
		}
    	return result;
}
    
    //查询某个用户拥有的所有水表
    public List<MachineInfo> findUserMeterList(Integer userId,String subUserName,boolean flag){
    	List<MachineInfo> result = null;
    	User user = userMapper.selectByPrimaryKey(userId);
    	if(flag){//如果为true，表示按条件查询
			if(UserUtils.isAdmin(user)){
				result = machineInfoMapper.findBySubUserName(subUserName);
			}else{
				result = machineInfoMapper.findByUserIdAndSubUserName(userId,subUserName);//返回的空list按照业务需求必须按照公司查询
			}
			setCompanyVo(result,userId,subUserName);
    	}else{
        	if(UserUtils.isAdmin(user)){
        		result = machineInfoMapper.findAll();
        	} else{
        		result = machineInfoMapper.findByUserId(userId);
        		if(result == null){
        			result = new ArrayList<>();
        		}
        		setCompanyVo(result,userId,null);
        	}
    	}
    	if(result == null){
			result = new ArrayList<>();
		}
    	return result;
    }
    
    private void setCompanyVo(List<MachineInfo> result,Integer userId,String parameter){
    	List<HeadCompanyVo> vos = companyService.getCompanyVoByUserId(userId);
		for(HeadCompanyVo vo : vos){
			if(vo.getBranchList() != null){
				for(BranchCompanyVo bvo: vo.getBranchList()){
					if(bvo.getMeterList() != null){
						for(MachineInfo info : bvo.getMeterList()){
							if(parameter==null||info.getSubUserName().contains(parameter)){
								addMachineInfo(result, info);
							}
						}
					}
					if(bvo.getBranchList() != null){
						for(AreaCompanyVo avo : bvo.getBranchList()){
							if(avo.getMeterList() != null){
								for(MachineInfo info : avo.getMeterList()){
									if(parameter==null||info.getSubUserName().contains(parameter)){
										addMachineInfo(result, info);
									}
								}
							}
						}
					}
				}
			}
		}
    }
    
    private void addMachineInfo(List<MachineInfo> list, MachineInfo info){
    	if(info != null){
    		for(MachineInfo i : list){
    			if(i.getNum().compareTo(info.getNum()) == 0){
    				return;
    			}
    		}
    		list.add(info);
    	}
    }

    //查询某个水表某天的数据
    public List<Ndata> findMeterHourInfo(Integer meterId, Date date){
    	
        return null;
    }

    //查询某个水表最新小时数据
    public NdataVo findMeterLastestHourInfo(Integer meterId){
        Ndata ndata = null;
        NdataVo ndataVo = null;

    	MachineInfo info = machineInfoMapper.findOneByNum(meterId);

        if(MeterUtils.isGprs4300(info)){
        	if(info.getMeterTypeId() !=3)
    			ndata = gprsDataMapper.findOneNdataByNum(meterId);
        	else
        		ndata = gprsDataFor4200Mapper.findOneNdataByNum(meterId);
    	} else{
    		ndata = ndataMapper.findOneByNum(meterId);
    	}

    	if(ndata != null){
            ndataVo = new NdataVo(ndata.getCurrenti(),ndata.getCurrentv(),ndata.getDepdata(),ndata.getEsignal(),ndata.getFlow()
                        ,ndata.getFlowerror(),ndata.getFtotalflow(),ndata.getiTime(),ndata.getLastconnecting(),ndata.getMe()
                        ,ndata.getNtotalflow(),ndata.getPress(),ndata.getPresserror(),ndata.getSignalStrength(),ndata.getTemp()
                        ,ndata.getTotalflow());
            ndataVo.setSubUserName(info.getSubUserName());
            ndataVo.setNum(meterId);
        } else{
        	ndataVo = new NdataVo();
        	ndataVo.setSubUserName(info.getSubUserName());
        	ndataVo.setNum(meterId);
        }
    	return ndataVo;
    }

    //查询某个水表最新小时数据
    public NdatassVo findMeterLastestMinuteInfo(Integer meterId){
        NdataSs ndataSs = null;
        NdatassVo ndatassVo = null;

    	MachineInfo info = machineInfoMapper.findOneByNum(meterId);
    	if(MeterUtils.isGprs4300(info)){
    		if(info.getMeterTypeId() !=3)
            	ndataSs = gprsDataMapper.findOneNdataSsByNum(meterId);
    		else
    			ndataSs = gprsDataFor4200Mapper.findOneNdataSsByNum(meterId);
    	}
        ndataSs = ndataSsMapper.findOneByNum(meterId);

    	if(ndataSs != null){
            ndatassVo = new NdatassVo();
            ndatassVo.setFlow(ndataSs.getFlow().toString());
            ndatassVo.setFtotalflow(ndataSs.getFtotalflow().toString());
            ndatassVo.setI_time(ndataSs.getiTime());
            ndatassVo.setPress(ndataSs.getPress().toString());
            ndatassVo.setTime(ndataSs.getTime().getTime());
            ndatassVo.setTotalflow(ndataSs.getTotalflow().toString());
            ndatassVo.setNtotalflow(ndataSs.getTotalflown().toString());

        }

        return ndatassVo;
    }

    /**
     * 得到站点列表
     * @param userId
     * @return
     */
	public List<String> getStationList(Integer userId) {
		List<MachineInfo> infos = this.findUserMeterList(userId);
		List<String> result = new ArrayList<>();
		for(MachineInfo info : infos){
			result.add(info.getShortName());
		}
		return result;
	}

	/**
	 * 得到水表
	 * @param meterId
	 * @return
	 */
	public MachineInfo getMachineInfoById(Integer meterId) {
		return machineInfoMapper.findOneByNum(meterId);
	}

	//通过站点名称、开始时间、结束时间查询出数据
	public List<List<NdataSs>> findMetersDataRecordByDate(List<String> stations, Date startTime, Date endTime){
		//查询站点
		List<MachineInfo> infos = machineInfoMapper.findBySubname(stations);
		
		List<List<NdataSs>> result = new ArrayList<>();
		if(infos != null){
			for(MachineInfo info : infos){
				NdataCriteria criteria = new NdataCriteria();
				criteria.setStartDate(startTime);
				criteria.setEndDate(endTime);
				criteria.setMeterId(info.getNum());
				List<NdataSs> ndataSs = null;
				if(MeterUtils.isGprs4300(info)){
					if(info.getMeterTypeId() !=3)
						ndataSs = gprsDataMapper.findNdataSsByCriteria(criteria);
					else
						ndataSs = gprsDataFor4200Mapper.findNdataSsByCriteria(criteria);
				}else{
					ndataSs = ndataSsMapper.findByCriteria(criteria);
				}
				if(ndataSs == null){
					ndataSs = new ArrayList<>();
				}
				result.add(ndataSs);
			}
		}
		return result;
	}

	/**
	 * 仪表更换记录
	 * @param stations
	 * @param start1
	 * @param end1
	 * @param startIndex
	 * @param pageSize
	 * @return
	 */
	public PageVo<ReplaceMeterRecordDTO> findReplaceMeterRecordPage(String stations, Date startTime, Date endTime,
			int startIndex, int pageSize) {
		if(StringUtils.isBlank(stations)){
			return new PageVo<ReplaceMeterRecordDTO>(1, 0, 0, null);
		}
		MeterReplaceCriteria criteria = new MeterReplaceCriteria();
		criteria.setCheckType("6");
		criteria.setStartTime(startTime);
		criteria.setEndTime(endTime);
		criteria.setStations(stations);
		
		Page page = criteria.getPage();
		page.setDefalutPageRows(pageSize);
		page.setCurrentPage(startIndex/pageSize + 1);
		criteria.setPage(page);
		
		List<ReplaceMeterRecordDTO> recordDTOs = meterOperationMapper.findPageReplaceMeterRecordDTOByCriteria(criteria);
		if(recordDTOs == null){
			recordDTOs = new ArrayList<>();
		}
		
		page = criteria.getPage();
		return new PageVo<>(page.getCurrentPage(), page.getTotalRecord(), recordDTOs.size(), recordDTOs);
	}
	
	/**
	 * 得到水表所属公司
	 * @param num
	 * @param userId
	 * @return
	 */
	public String getMeterCompany(Integer num, Integer userId){
		List<Company> companys = companyMapper.listByMeterId(num);
		String result = "";
		if(companys != null && !companys.isEmpty()){
			
			result = companys.get(0).getCompanyName();			
			
			User user = userMapper.selectByPrimaryKey(userId);
			if(user != null && null != user.getCompanyId()){
				for(Company c : companys){
					// 先循环对比一次，避免每次都查询下属单位
					if(c.getCompanyId().equals(user.getCompanyId())){
						//直接属于
						return c.getCompanyName();
					}
				}
				
				//用户有关系的所有下属公司
				List<Company> ownCompanys = new ArrayList<Company>();
				List<Company> nextCompanys = companyMapper.findByParentCompanyId(user.getCompanyId(), null);
				if(nextCompanys != null){
					for(Company c : nextCompanys){
						ownCompanys.add(c);
						List<Company> nextCompanys2 = companyMapper.findByParentCompanyId(c.getCompanyId(), null);
						if(nextCompanys2 != null){
							for(Company c2 : nextCompanys2){
								ownCompanys.add(c2);
								List<Company> nextCompanys3 = companyMapper.findByParentCompanyId(c.getCompanyId(), null);
								if(nextCompanys3 != null){
									for(Company c3 : nextCompanys2){
										ownCompanys.add(c3);
									}
								}
							}
						}
					}
				}
				
				for(Company c : companys){
					if(c.getParentCompanyId() != null 
							&& c.getParentCompanyId().equals(user.getCompanyId())){
						// 上级单位
						result = c.getCompanyName();
					}
					for(Company c2 : ownCompanys){
						// 下属单位
						if(c2.getCompanyId().equals(c.getCompanyId())){
							result = c2.getCompanyName();
						}
					}
				}
			}
		}
		
		
		return result;
	}

	/**
	 * 当日水表流量差值
	 * @param machineInfo
	 * @return
	 */
	public Float getDayTotalFlowDiff(MachineInfo machineInfo){
		if(machineInfo != null){
			if(MeterUtils.isGprs4300(machineInfo)){
				if(machineInfo.getMeterTypeId() !=3)
					return gprsDataMapper.findDayTotalFlowDiff(machineInfo.getNum());
				else
					return  gprsDataFor4200Mapper.findDayTotalFlowDiff(machineInfo.getNum());
			} else{
				return ndataSsMapper.findDayTotalFlowDiff(machineInfo.getNum());
			}
		}
		return 0f;
	}

}
