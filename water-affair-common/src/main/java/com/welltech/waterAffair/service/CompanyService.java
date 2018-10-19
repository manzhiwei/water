package com.welltech.waterAffair.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.welltech.waterAffair.common.aop.pagination.bean.Page;
import com.welltech.waterAffair.common.constant.Constants;
import com.welltech.waterAffair.common.enums.CompanyLevelEnum;
import com.welltech.waterAffair.common.util.UserUtils;
import com.welltech.waterAffair.domain.criteria.UserLogCriteria;
import com.welltech.waterAffair.domain.entity.Company;
import com.welltech.waterAffair.domain.entity.MachineInfo;
import com.welltech.waterAffair.domain.entity.MeterCompany;
import com.welltech.waterAffair.domain.entity.User;
import com.welltech.waterAffair.domain.entity.UserLog;
import com.welltech.waterAffair.domain.entity.UserMeter;
import com.welltech.waterAffair.domain.vo.AreaCompanyVo;
import com.welltech.waterAffair.domain.vo.BranchCompanyVo;
import com.welltech.waterAffair.domain.vo.HeadCompanyVo;
import com.welltech.waterAffair.domain.vo.PageVo;
import com.welltech.waterAffair.domain.vo.ResponseVo;
import com.welltech.waterAffair.repository.CompanyMapper;
import com.welltech.waterAffair.repository.GprsDataMapper;
import com.welltech.waterAffair.repository.MachineInfoMapper;
import com.welltech.waterAffair.repository.MeterCompanyMapper;
import com.welltech.waterAffair.repository.NdataMapper;
import com.welltech.waterAffair.repository.NdataSsMapper;
import com.welltech.waterAffair.repository.UserLogMapper;
import com.welltech.waterAffair.repository.UserMapper;
import com.welltech.waterAffair.repository.UserMeterMapper;

/**
 * Created by WangXin on 2017/4/24.
 */
@Service
public class CompanyService {

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
    private UserLogMapper userLogMapper;

    /**
     * 根据用户id查询所在公司与水表
     * @param userId
     * @return
     */
    public List<HeadCompanyVo> getCompanyVoByUserId(Integer userId){
        List<HeadCompanyVo> result = new ArrayList<HeadCompanyVo>();
        User user = userMapper.selectByPrimaryKey(userId);

        if(UserUtils.isAdmin(user)){
            List<Company> companies1 = companyMapper.findByParentCompanyId(null, CompanyLevelEnum.PARENT.getKey());
            if(companies1 != null && !companies1.isEmpty()){
                for(Company c1 : companies1){
                    HeadCompanyVo headCompanyVo = new HeadCompanyVo();
                    headCompanyVo.setUserId(userId);
                    headCompanyVo.setCompanyId(c1.getCompanyId());
                    headCompanyVo.setCompanyName(c1.getCompanyName());

                    List<Company> companies2 = companyMapper.findByParentCompanyId(c1.getCompanyId(), CompanyLevelEnum.BRANCH.getKey());
                    List<BranchCompanyVo> branchCompanyVos = new ArrayList<BranchCompanyVo>();

                    if(companies2 != null && companies2.size()>0){
                        for(Company c2 : companies2){
                            BranchCompanyVo branchCompanyVo = new BranchCompanyVo();
                            branchCompanyVo.setUserId(userId);
                            branchCompanyVo.setCompanyId(c2.getCompanyId());
                            branchCompanyVo.setCompanyName(c2.getCompanyName());

                            List<Company> companies3 = companyMapper.findByParentCompanyId(c2.getCompanyId(), CompanyLevelEnum.AREA.getKey());
                            List<AreaCompanyVo> areaCompanyVos = new ArrayList<AreaCompanyVo>();

                            if(companies3 != null && companies3.size()>0){
                                for (Company c3 : companies3){
                                    AreaCompanyVo areaCompanyVo = new AreaCompanyVo();
                                    areaCompanyVo.setUserId(userId);
                                    areaCompanyVo.setCompanyId(c3.getCompanyId());
                                    areaCompanyVo.setCompanyName(c3.getCompanyName());

                                    List<MachineInfo> machineInfos = machineInfoMapper.findByCompanyId(c3.getCompanyId());
                                    areaCompanyVo.setMeterList(machineInfos);

                                    areaCompanyVos.add(areaCompanyVo);
                                }
                            }

                            branchCompanyVo.setBranchList(areaCompanyVos);

                            List<MachineInfo> machineInfos = machineInfoMapper.findByCompanyId(c2.getCompanyId());
                            branchCompanyVo.setMeterList(machineInfos);
                            branchCompanyVos.add(branchCompanyVo);
                        }
                    }

                    headCompanyVo.setBranchList(branchCompanyVos);
                    List<MachineInfo> machineInfos = machineInfoMapper.findByCompanyId(c1.getCompanyId());
                    headCompanyVo.setMeterList(machineInfos);
                    result.add(headCompanyVo);
                }
            }

        }else{
            Company company = companyMapper.selectByPrimaryKey(user.getCompanyId());
            HeadCompanyVo headCompanyVo = new HeadCompanyVo();
            headCompanyVo.setUserId(userId);

            if(CompanyLevelEnum.PARENT.getKey().equals(company.getCompanyLevel())){
                //该用户属于总公司
                headCompanyVo.setCompanyName(company.getCompanyName());
                headCompanyVo.setCompanyId(company.getCompanyId());

                List<BranchCompanyVo> branchCompanyVos = new ArrayList<BranchCompanyVo>();
                List<Company> companies2 = companyMapper.findByParentCompanyId(company.getCompanyId(),CompanyLevelEnum.BRANCH.getKey());
                if(companies2 != null && companies2.size()>0){
                    for(Company c2 : companies2){
                        BranchCompanyVo branchCompanyVo = new BranchCompanyVo();
                        branchCompanyVo.setUserId(userId);
                        branchCompanyVo.setCompanyId(c2.getCompanyId());
                        branchCompanyVo.setCompanyName(c2.getCompanyName());

                        List<AreaCompanyVo> areaCompanyVos = new ArrayList<AreaCompanyVo>();
                        List<Company> companies3 = companyMapper.findByParentCompanyId(c2.getCompanyId(),CompanyLevelEnum.AREA.getKey());

                        if(companies3 != null && !companies3.isEmpty()){
                            for (Company c3 : companies3){
                                AreaCompanyVo areaCompanyVo = new AreaCompanyVo();

                                areaCompanyVo.setUserId(userId);
                                areaCompanyVo.setCompanyName(c3.getCompanyName());
                                areaCompanyVo.setCompanyId(c3.getCompanyId());

                                List<MachineInfo> machineInfos = machineInfoMapper.findByCompanyId(c3.getCompanyId());
                                areaCompanyVo.setMeterList(machineInfos);

                                areaCompanyVos.add(areaCompanyVo);
                            }
                        }

                        branchCompanyVo.setBranchList(areaCompanyVos);
                        List<MachineInfo> machineInfos = machineInfoMapper.findByCompanyId(c2.getCompanyId());
                        branchCompanyVo.setMeterList(machineInfos);
                        branchCompanyVos.add(branchCompanyVo);
                    }
                }

                headCompanyVo.setBranchList(branchCompanyVos);
                List<MachineInfo> machineInfos = machineInfoMapper.findByCompanyIdAndUserId(company.getCompanyId(),userId);
                headCompanyVo.setMeterList(machineInfos);

            } else if (CompanyLevelEnum.BRANCH.getKey().equals(company.getCompanyLevel())){
                //该用户属于分公司
                Company company1 = companyMapper.selectByPrimaryKey(company.getParentCompanyId());

                headCompanyVo.setCompanyName(company1.getCompanyName());
                headCompanyVo.setCompanyId(company1.getCompanyId());

                List<BranchCompanyVo> branchCompanyVos = new ArrayList<BranchCompanyVo>();

                BranchCompanyVo branchCompanyVo = new BranchCompanyVo();
                branchCompanyVo.setUserId(userId);
                branchCompanyVo.setCompanyName(company.getCompanyName());
                branchCompanyVo.setCompanyId(company.getCompanyId());

                List<AreaCompanyVo> areaCompanyVos = new ArrayList<AreaCompanyVo>();
                List<Company> companies3 = companyMapper.findByParentCompanyId(company.getCompanyId(), CompanyLevelEnum.AREA.getKey());

                if(companies3 != null){
                    for(Company c3 : companies3){
                        AreaCompanyVo areaCompanyVo = new AreaCompanyVo();
                        areaCompanyVo.setUserId(userId);
                        areaCompanyVo.setCompanyId(c3.getCompanyId());
                        areaCompanyVo.setCompanyName(c3.getCompanyName());
                        List<MachineInfo> machineInfos = machineInfoMapper.findByCompanyId(c3.getCompanyId());
                        areaCompanyVo.setMeterList(machineInfos);
                    }
                }

                branchCompanyVo.setBranchList(areaCompanyVos);
                List<MachineInfo> machineInfos2 = machineInfoMapper.findByCompanyIdAndUserId(company.getCompanyId(),userId);
                branchCompanyVo.setMeterList(machineInfos2);
                branchCompanyVos.add(branchCompanyVo);

                headCompanyVo.setBranchList(branchCompanyVos);

                List<MachineInfo> machineInfos = machineInfoMapper.findByCompanyIdAndUserId(company1.getCompanyId(),userId);
                headCompanyVo.setMeterList(machineInfos);

            } else{
                //该用户属于区域
                Company company2 = companyMapper.selectByPrimaryKey(company.getParentCompanyId());
                Company company1 = companyMapper.selectByPrimaryKey(company2.getParentCompanyId());

                headCompanyVo.setCompanyName(company1.getCompanyName());
                headCompanyVo.setCompanyId(company1.getCompanyId());

                List<BranchCompanyVo> branchCompanyVos = new ArrayList<BranchCompanyVo>();
                BranchCompanyVo branchCompanyVo = new BranchCompanyVo();
                branchCompanyVo.setUserId(userId);
                branchCompanyVo.setCompanyName(company2.getCompanyName());
                branchCompanyVo.setCompanyId(company2.getCompanyId());

                List<AreaCompanyVo> areaCompanyVos = new ArrayList<AreaCompanyVo>();
                AreaCompanyVo areaCompanyVo = new AreaCompanyVo();
                areaCompanyVo.setUserId(userId);
                areaCompanyVo.setCompanyName(company.getCompanyName());
                areaCompanyVo.setCompanyId(company.getCompanyId());
                List<MachineInfo> machineInfos3 = machineInfoMapper.findByCompanyIdAndUserId(company.getCompanyId(), userId);
                areaCompanyVo.setMeterList(machineInfos3);
                branchCompanyVo.setBranchList(areaCompanyVos);

                List<MachineInfo> machineInfos2 = machineInfoMapper.findByCompanyIdAndUserId(company2.getCompanyId(),userId);
                branchCompanyVo.setMeterList(machineInfos2);
                branchCompanyVos.add(branchCompanyVo);
                headCompanyVo.setBranchList(branchCompanyVos);

                List<MachineInfo> machineInfos = machineInfoMapper.findByCompanyIdAndUserId(company1.getCompanyId(),userId);
                headCompanyVo.setMeterList(machineInfos);

            }
            result.add(headCompanyVo);
        }

        return result;
    }

    /**
     * 保存公司
     * @param parentId
     * @param name
     * @return
     */
    public String saveCompany(String parentId, String name){
        //Company company = companyMapper.findOneByCompanyName(name);
        Company company = new Company();
        Integer companyParentId = null;
        String companyLevel = CompanyLevelEnum.PARENT.getKey();
        if(StringUtils.isNotBlank(parentId) && !"0".equals(parentId)){
            companyParentId = Integer.parseInt(parentId);
            Company parentCompany = companyMapper.selectByPrimaryKey(companyParentId);
            companyLevel = Integer.parseInt(parentCompany.getCompanyLevel()) + 1 + "";
        }

        company.setParentCompanyId(companyParentId);
        company.setCompanyLevel(companyLevel);
        company.setCompanyName(name.trim());
        companyMapper.insertSelective(company);

        return company.getCompanyId() + "";
    }

    /**
     * 更新公司
     * @param companyId
     * @param companyName
     */
    public void updateCompany(Integer companyId, String companyName){
    	if(companyId != null && companyId > 0){
    		Company company = companyMapper.selectByPrimaryKey(companyId);
    		company.setCompanyName(companyName.trim());
    		companyMapper.updateByPrimaryKey(company);
    	}
    }

    /**
     * 删除公司
     * @param companyId
     */
    public void deleteCompany(Integer companyId){
        companyMapper.deleteByPrimaryKey(companyId);
        meterCompanyMapper.deleteByCompanyId(companyId);
        List<User> users = userMapper.findByCompanyId(companyId);
        if(users != null){
        	for(User user : users){
        		userMeterMapper.deleteByUserId(user.getUserId());
        	}
        	userMapper.deleteByCompanyId(companyId);
        }
    }

    /**
     * 根据公司id查询水表
     * @param companyId
     * @return
     */
    public List<MachineInfo> queryMeterByCompanyId(Integer companyId){
        List<MachineInfo> machineInfos = machineInfoMapper.findByCompanyId(companyId);
        if(machineInfos == null){
            machineInfos = new ArrayList<MachineInfo>();
        }
        return machineInfos;
    }

    /**
     * 查询所有水表
     * @return
     */
    public List<MachineInfo> queryAllMachineInfo(){
        List<MachineInfo> machineInfos = machineInfoMapper.findAll();
        if(machineInfos == null){
            machineInfos = new ArrayList<MachineInfo>();
        }
        return machineInfos;
    }

    /**
     * 保存水表公司的关系
     * @param companyId
     * @param meterId
     */
    public void saveMeterCompany(Integer companyId, Integer meterId){
        int count = meterCompanyMapper.count(companyId,meterId);
        if(count < 1){
            MeterCompany meterCompany = new MeterCompany();
            meterCompany.setCompanyId(companyId);
            meterCompany.setMeterId(meterId);
            meterCompanyMapper.insert(meterCompany);
        }
    }

    /**
     * 删除公司水表关系
     * @param companyId
     * @param meterId
     */
    public void deleteMeterCompany(Integer companyId, Integer meterId){
        List<Integer> meterIds = new ArrayList<Integer>();
        meterIds.add(meterId);
        meterCompanyMapper.deleteByUserNameIdAndMeterId(companyId, meterIds);
    }

    /**
     * 根据公司id查询用户列表
     * @param companyId
     * @return
     */
    public List<User> getUsersByCompanyId(Integer companyId){
        List<User> users = userMapper.findByCompanyId(companyId);
        return users;
    }

    /**
     * 根据用户id查询用户水表关系
     * @param userId
     * @return
     */
    public List<UserMeter> getUserMetersByUserId(Integer userId){
        List<UserMeter> userMeters = userMeterMapper.findByUserId(userId);
        if(userMeters == null){
            userMeters = new ArrayList<UserMeter>();
        }
        return userMeters;
    }

    /**
     * 得到水表
     * @param num
     * @return
     */
    public MachineInfo getMachineInfo(Integer num){
        return  machineInfoMapper.findOneByNum(num);
    }

    /**
     * 得到用户
     * @param userId
     * @return
     */
    public User getUser(Integer userId){
        return userMapper.selectByPrimaryKey(userId);
    }

    /**
     * 删除用户水表关系
     * @param userId
     * @param meterId
     */
    public void deleteUserMeter(Integer userId, Integer meterId) {
        UserMeter userMeter = new UserMeter();
        userMeter.setUserId(userId);
        userMeter.setMeterId(meterId);
        userMeterMapper.delete(userMeter);
    }

    /**
     * 添加用户水表关系
     * @param um
     */
    public void addUserMeter(UserMeter um) {
        List<UserMeter> ums = userMeterMapper.findList(um);
        if(ums == null || ums.isEmpty()){
            userMeterMapper.insert(um);
        }
    }

    /**
     * 是否为admin用户
     * @param userId
     * @return
     */
    public boolean isAdmin(Integer userId){
        User user = userMapper.selectByPrimaryKey(userId);
        if(user != null){
            return UserUtils.isAdmin(user);
        }
        return false;
    }

    /**
     * 通过用户id查询总公司
     * @param userId
     * @return
     */
    public Company getHeadCompany(Integer userId){
        Company company = companyMapper.findCompanyByUserId(userId);
        if(CompanyLevelEnum.PARENT.getKey().equals(company.getCompanyLevel())){
            return company;
        }
        company = companyMapper.selectByPrimaryKey(company.getParentCompanyId());
        if(CompanyLevelEnum.PARENT.getKey().equals(company.getCompanyLevel())){
            return company;
        }
        company = companyMapper.selectByPrimaryKey(company.getParentCompanyId());
        if(CompanyLevelEnum.PARENT.getKey().equals(company.getCompanyLevel())){
            return company;
        }
        return null;
    }

    /**
     * 通过用户id查询分公司
     * @param userId
     * @return
     */
    public Company getBranchCompany(Integer userId){
        Company company = companyMapper.findCompanyByUserId(userId);
        if(CompanyLevelEnum.PARENT.getKey().equals(company.getCompanyLevel())){
            return null;
        }
        if(CompanyLevelEnum.BRANCH.getKey().equals(company.getCompanyLevel())){
            return company;
        }
        company = companyMapper.selectByPrimaryKey(company.getParentCompanyId());
        if(CompanyLevelEnum.BRANCH.getKey().equals(company.getCompanyLevel())){
            return company;
        }
        return null;
    }

    /**
     * 查询所属员工
     * @param userId
     * @return
     */
	public Map<Integer, String> querySubUser(Integer userId) {
		User user = userMapper.selectByPrimaryKey(userId);
		List<User> users = null;
		if(UserUtils.isAdmin(user)){
			users = userMapper.findAll();
		}else{
			List<Integer> companyIds = new ArrayList<>();
			//companyIds.add(user.getCompanyId());
			List<Company> companies = companyMapper.findByParentCompanyId(user.getCompanyId(), null);
			if(companies != null){
				for(Company c1:companies){
					companyIds.add(c1.getCompanyId());
					List<Company> companies2 = companyMapper.findByParentCompanyId(c1.getCompanyId(), null);
					if(companies2 != null){
						for(Company c2:companies2){
							companyIds.add(c2.getCompanyId());
						}
					}
				}
			}
			if(companyIds.size() > 0){
				users = userMapper.findByCompanyIds(companyIds);
			}
		}
		
		Map<Integer, String> result = new HashMap<>();
		if(users != null){
			for(User u : users){
				result.put(u.getUserId(), u.getUserName());
			}
		}
		return result;
	}

	/**
	 * 分页查询日志
	 * @param userIdes
	 * @param type
	 * @param start1
	 * @param end1
	 * @param startIndex
	 * @param pageSize
	 * @return
	 */
	public PageVo<UserLog> findUserLogByConditionPage(String userIds, Integer type, Date startTime, Date endTime,
			int startIndex, int pageSize) {
		UserLogCriteria criteria = new UserLogCriteria();
		criteria.setStartTime(startTime);
		criteria.setEndTime(endTime);
		criteria.setEventValue(type + "");
		criteria.setUserIds(userIds);
		Page page = criteria.getPage();
		page.setDefalutPageRows(pageSize);
		page.setCurrentPage(startIndex/pageSize + 1);
		criteria.setPage(page);
		
		List<UserLog> userLogs = userLogMapper.findPageByCriteria(criteria);
		if(userLogs == null){
			userLogs = new ArrayList<>();
		}
		page = criteria.getPage();
		return new PageVo<>(page.getCurrentPage(), page.getTotalRecord(), userLogs.size(), userLogs);
	}

	/**
	 * 更新用户
	 * @param parameter
	 * @return
	 */
	public ResponseVo<User> updateUser(User parameter) {
		ResponseVo<User> res = new ResponseVo<>();
		if(parameter.getUserId()==null){
			res.setResponseMsg("主键Id不能为空");
			res.setResponseCode(Constants.RETURN_CODE_001);
			return res;
		}
		if(parameter.getUserName()==null||parameter.getUserName().trim().length()==0){
			res.setResponseMsg("用户名不能为空");
			res.setResponseCode(Constants.RETURN_CODE_001);
			return res;
		}
		if(parameter.getPassword()==null||parameter.getPassword().trim().length()==0){
			res.setResponseMsg("密码不能为空");
			res.setResponseCode(Constants.RETURN_CODE_001);
			return res;
		}
		User tmp = userMapper.selectByPrimaryKey(parameter.getUserId());
		if(tmp==null){
			res.setResponseMsg("用户不存在");
			res.setResponseCode(Constants.RETURN_CODE_001);
			return res;
		}
		if(UserUtils.isAdmin(tmp) && !"admin".equals(parameter.getUserName()) ){
			res.setResponseMsg("admin不能修改用户名");
			res.setResponseCode(Constants.RETURN_CODE_001);
			return res;
		}
		tmp.setUserName(parameter.getUserName());
		tmp.setPassword(parameter.getPassword());

		res.setResponseMsg("用户修改成功！");
		res.setResponseCode(Constants.RETURN_CODE_000);
		userMapper.updateByPrimaryKeySelective(tmp);
		return res;
	}

	/**
	 * 添加用户
	 * @param parameter
	 * @return
	 */
	public ResponseVo<User> addUser(User parameter) {
		ResponseVo<User> res=new ResponseVo<User>();
		User result=null;
		if(parameter.getUserId()!=null){
			res.setResponseMsg("不得传入主键id");
			res.setResponseCode(Constants.RETURN_CODE_001);
			return res;
		}
		if(parameter.getUserName()==null||parameter.getUserName().trim().length()==0){
			res.setResponseMsg("用户名不能为空");
			res.setResponseCode(Constants.RETURN_CODE_001);
			return res;
		}
		if(parameter.getPassword()==null||parameter.getPassword().trim().length()==0){
			res.setResponseMsg("密码不能为空");
			res.setResponseCode(Constants.RETURN_CODE_001);
			return res;
		}
		User temp=userMapper.findByUserName(parameter.getUserName());
		if(temp!=null){
			res.setResponseMsg("用户名已存在");
			res.setResponseCode(Constants.RETURN_CODE_001);
			return res;
		}
		
		userMapper.insertSelective(parameter);
		res.setData(userMapper.findByUserName(parameter.getUserName()));
		res.setResponseMsg("用户新增成功！");
		res.setResponseCode(Constants.RETURN_CODE_000);

		return res;
	}

	/**
	 * 删除用户
	 * @param valueOf
	 */
	public void deleteUserByUserId(Integer userId) {
		userMapper.deleteByPrimaryKey(userId);
		userMeterMapper.deleteByUserId(userId);
	}

	public List<User> getAdminUsers() {
		User user = userMapper.findByUserName("admin");
		List<User> users = new ArrayList<>();
		users.add(user);
		return users;
	}

	public Company getCompany(Integer userId) {
		return companyMapper.findCompanyByUserId(userId);
	}
}
