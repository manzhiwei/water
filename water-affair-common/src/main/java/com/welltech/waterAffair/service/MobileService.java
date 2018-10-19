package com.welltech.waterAffair.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.welltech.waterAffair.repository.*;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.welltech.waterAffair.common.enums.CompanyLevelEnum;
import com.welltech.waterAffair.common.util.MeterUtils;
import com.welltech.waterAffair.domain.criteria.NdataCriteria;
import com.welltech.waterAffair.domain.entity.Company;
import com.welltech.waterAffair.domain.entity.MachineInfo;
import com.welltech.waterAffair.domain.entity.Ndata;
import com.welltech.waterAffair.domain.entity.NdataSs;
import com.welltech.waterAffair.domain.entity.User;

/**
 * 移动应用模块
 * @author zhoupei
 * @Motified Ryan
 * @Impl WangXin
 *
 */
@Service
public class MobileService {
	
	@Autowired
	private CompanyMapper companyMapper;
	
	@Autowired
	private MeterCompanyMapper meterCompanyMapper;
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private UserMeterMapper userMeterMapper;
	
	@Autowired
	private MachineInfoMapper machineInfoMapper;
	
	@Autowired
	private NdataMapper ndataMapper;
	
	@Autowired
	private NdataSsMapper ndataSsMapper;
	
	@Autowired
	private GprsDataMapper gprsDataMapper;

	@Autowired
	private MeterService meterService;

	@Autowired
	private GprsDataFor4200Mapper gprsDataFor4200Mapper;

	//1、用户下属区域列表
	public List<Company> findUserCompanyList(Integer userId){
		if(this.isAdmin(userId)){
			return companyMapper.findByParentCompanyId(null, CompanyLevelEnum.AREA.getKey());
		}
		//用户所属公司
		Company company = companyMapper.findCompanyByUserId(userId);
		
		if(CompanyLevelEnum.AREA.getKey().equals(company.getCompanyLevel())){
			//用户属于区域
			List<Company> companys = new ArrayList<Company>();
			companys.add(company);
			return companys;
		}
		
		List<Integer> companyIds = new ArrayList<Integer>();
		companyIds.add(company.getCompanyId());
		
		if(CompanyLevelEnum.PARENT.getKey().equals(company.getCompanyLevel())){
			//用户属于总公司，再查询分公司
			List<Company> companys = companyMapper.findByParentCompanyId(company.getCompanyId(), CompanyLevelEnum.BRANCH.getKey());
			if(companys != null && !companys.isEmpty()){
				for(Company c : companys){
					companyIds.add(c.getCompanyId());
				}
			}
		}
		
		return companyMapper.findByCompanyIds(companyIds, CompanyLevelEnum.AREA.getKey());
	}

	//2、通过区域id查询用户的水表，如果区域为空那么查询当前用户下所属公司所有水表
	// 举例：某个区域可能存在10个，属于用户admin仅5个
	/**
	 * 
	 * @param companyId
	 * @param userId
	 * @param currentPage 为0不分页
	 * @return
	 */
	public List<MachineInfo> findUserMeterListInCompany(Integer companyId,Integer userId, int currentPage){
		List<MachineInfo> infos = null;
		if(this.isAdmin(userId)){
			if(companyId == null){
				//区域为空 所有水表
				infos = machineInfoMapper.findAll();
			} else{
				infos = machineInfoMapper.findByCompanyId(companyId);
			}
		} else{
			if(companyId == null){
				infos =  meterService.findUserMeterList(userId);
			} else{
				infos =  machineInfoMapper.findByCompanyIdAndUserId(companyId, userId);
			}
		}
		
		if(currentPage == 0){
			return infos;
		}
		
		//手动分页
		List<MachineInfo> result = new ArrayList<>();
		int pageSize = 15;
		int index = (currentPage - 1) * pageSize;
		int end = index + pageSize;
		while(index < end && index < infos.size() ){
			result.add(infos.get(index));
			index ++;
		}
		
		return result;
	}

	//3、通过水表ID查询水表某个类型某天的历史数据
	//通过水表ID 和起止时间查询 时间精确到秒
	public List<Ndata> findMeterHourDataByDate(Integer meterId,Date startDate,Date endDate){
		List<Ndata> ndatas = null;
		MachineInfo info = machineInfoMapper.findOneByNum(meterId);
		
		NdataCriteria criteria = new NdataCriteria();
		criteria.setMeterId(meterId);
		criteria.setStartDate(startDate);
		criteria.setEndDate(endDate);

		if(MeterUtils.isGprs4300(info)){
			if(info.getMeterTypeId() !=3)
				ndatas = gprsDataMapper.findNdataByCriteria(criteria);
			else
				ndatas = gprsDataMapper.findNdataByCriteria(criteria);
		} else{
			ndatas = ndataMapper.findByCriteria(criteria);
		}

		return ndatas;
	}
	
	public List<Ndata> findNdataByCriteriaLastConnecting(Integer meterId,Date startDate,Date endDate){
		List<Ndata> ndatas = null;
		MachineInfo info = machineInfoMapper.findOneByNum(meterId);
		
		NdataCriteria criteria = new NdataCriteria();
		criteria.setMeterId(meterId);
		criteria.setStartDate(startDate);
		criteria.setEndDate(endDate);

		if(MeterUtils.isGprs4300(info)){
			if(info.getMeterTypeId() !=3)
				ndatas = gprsDataMapper.findNdataByCriteriaLastConnecting(criteria);
			else
				ndatas = gprsDataFor4200Mapper.findNdataByCriteriaLastConnecting(criteria);
		} else{
			ndatas = ndataMapper.findNdataByCriteriaLastConnecting(criteria);
		}

		return ndatas;
	}
	//4.1查询上一条和当前选中条数之间的历史数据
	public List<NdataSs> findMeterHistroryData(Integer meterId,String date,String time,String hour){
		List<NdataSs> ndataSs = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		MachineInfo info = machineInfoMapper.findOneByNum(meterId);
		if(MeterUtils.isGprs4300(info)){
			try {
				ndataSs = findMeterMinuteDataByDate(meterId,dateFormat.parse(date+" "+hour+":00:00"),dateFormat.parse(date+" "+hour+":59:59"));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}else{
			long time1 = Long.parseLong(time);
			Date date1 = new Date();
			date1.setTime(time1);
			String now = dateFormat.format(date1);
			Ndata ndata = ndataMapper.findConnectIntervalTime(meterId, now);
			if(ndata != null){
				/*String start2End = dateFormat.format(ndata.getiTime())+","+now;*/
				/*System.out.println(start2End);*/
				try {
					ndataSs = findMeterMinuteDataByDate(meterId,dateFormat.parse(dateFormat.format(ndata.getiTime())),dateFormat.parse(now));/*,(Integer.parseInt(pageNo)-1)*12+1*/
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}else{
				return null;
			}
		}

		return ndataSs;
	}
	/*//4、通过水表ID查询水表某个类型某个小时的历史数据
	public List<NdataSs> findMeterMinuteDataByDate1(Integer meterId, Date startDate, Date endDate,Integer pageNo){
		List<NdataSs> ndataSs = null;
		MachineInfo info = machineInfoMapper.findOneByNum(meterId);

		NdataCriteria criteria = new NdataCriteria();
		criteria.setMeterId(meterId);
		criteria.setStartDate(startDate);
		criteria.setEndDate(endDate);
		if(MeterUtils.isGprs4300(info)){
			ndataSs = gprsDataMapper.findNdataSsByCriteria(criteria);
		} else{
			ndataSs = ndataSsMapper.findByCriteria1(criteria,pageNo);
		}
		return ndataSs;
	}*/
	//4、通过水表ID查询水表某个类型某个小时的历史数据
	public List<NdataSs> findMeterMinuteDataByDate(Integer meterId, Date startDate, Date endDate){
		List<NdataSs> ndataSs = null;
		MachineInfo info = machineInfoMapper.findOneByNum(meterId);
		
		NdataCriteria criteria = new NdataCriteria();
		criteria.setMeterId(meterId);
		criteria.setStartDate(startDate);
		criteria.setEndDate(endDate);
		if(MeterUtils.isGprs4300(info)){
			if(info.getMeterTypeId() !=3)
				ndataSs = gprsDataMapper.findNdataSsByCriteria(criteria);
			else
				ndataSs = gprsDataFor4200Mapper.findNdataSsByCriteria(criteria);
		} else{
			ndataSs = ndataSsMapper.findByCriteria(criteria);
		}
		return ndataSs;
	}


	//5、通过水表ID查询仪表详情
	public MachineInfo findMeterInfo(Integer meterId){
		MachineInfo info = machineInfoMapper.findOneByNum(meterId);
		if(info != null){
			Ndata ndata = null;
			if(MeterUtils.isGprs4300(info)){
				if(info.getMeterTypeId() !=3)
					ndata = gprsDataMapper.findOneNdataByNum(meterId);
				else
					ndata = gprsDataFor4200Mapper.findOneNdataByNum(meterId);
			} else{
				ndata = ndataMapper.findOneByNum(meterId);
			}
			if(ndata != null){
				info.setLastConnecting(ndata.getLastconnecting());
				
				Ndata ndata2 = null;
				if(MeterUtils.isGprs4300(info)){
					if(info.getMeterTypeId() !=3)
						ndata2 = gprsDataMapper.findOneNdataByNumAndOffset(meterId,1);
					else
						ndata2 = gprsDataFor4200Mapper.findOneNdataByNumAndOffset(meterId,1);
				} else{
					ndata2 = ndataMapper.findOneByNumAndOffset(meterId,1);
				}
				
				if(ndata2 != null){
					Date time1 = ndata.getiTime();
					Date time2 = ndata2.getiTime();
					
					if (time1 != null && time2 != null) {
						long baseNum = 3600 * 1000;
						long absNum = Math.abs(time1.getTime() - time2.getTime());
						BigDecimal diffHour = new BigDecimal(absNum / baseNum);
						info.setDiffHour(diffHour.setScale(2, RoundingMode.HALF_UP).doubleValue());
					}
				}
			}
		}
		return info;
	}
	
	private boolean isAdmin(Integer userId){
		User user = userMapper.selectByPrimaryKey(userId);
		if(user != null && "admin".equals(user.getUserName())){
			return true;
		}
		return false;
	}

}
