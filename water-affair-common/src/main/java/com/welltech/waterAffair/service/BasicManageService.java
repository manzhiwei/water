package com.welltech.waterAffair.service;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import com.welltech.waterAffair.domain.criteria.NdataCriteria;
import com.welltech.waterAffair.repository.*;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.welltech.waterAffair.common.constant.Constants;
import com.welltech.waterAffair.common.util.ConstantsUtil;
import com.welltech.waterAffair.common.util.MeterUtils;
import com.welltech.waterAffair.domain.dto.ArchivesRecord;
import com.welltech.waterAffair.domain.dto.MachineInfoDTO;
import com.welltech.waterAffair.domain.dto.MeterMonitorDTO;
import com.welltech.waterAffair.domain.dto.ResourceImgDTO;
import com.welltech.waterAffair.domain.dto.UserMeterAuthorityDTO;
import com.welltech.waterAffair.domain.entity.Company;
import com.welltech.waterAffair.domain.entity.MachineInfo;
import com.welltech.waterAffair.domain.entity.MeterOperation;
import com.welltech.waterAffair.domain.entity.MeterType;
import com.welltech.waterAffair.domain.entity.Ndata;
import com.welltech.waterAffair.domain.entity.ResourceImg;
import com.welltech.waterAffair.domain.vo.MeterDetailVo;
import com.welltech.waterAffair.domain.vo.NdataVo;
import com.welltech.waterAffair.domain.vo.PageVo;
import com.welltech.waterAffair.domain.vo.before.ArchivesRecordVo;


/**
 * Created by zhoupei on 2016/12/4.
 * Motified by WangXin on 2017/04/19
 */
@Service
public class BasicManageService {

	private Logger logger=LoggerFactory.getLogger(BasicManageService.class);

	@Autowired
	private MachineInfoMapper machineInfoMapper;
	@Autowired
	private DashboardService dashboardService;
	@Autowired
	private ConstantsUtil constantsUtil;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private NdataMapper ndataMapper;
	@Autowired
	private GprsDataMapper gprsDataMapper;
	@Autowired
	private CompanyMapper companyMapper;
	@Autowired
	private MeterOperationMapper meterOperationMapper;
	@Autowired
	private CompanyService companyService;
	@Autowired
	private ResourceImgMapper resourceImgMapper;
	@Autowired
	private MeterService meterService;
	@Autowired
	private MeterTypeMapper meterTypeMapper;
	//新增，添加第三类水表类型 wt4200
	@Autowired
	private GprsDataFor4200Mapper gprsDataFor4200Mapper;

    //查询用户所有水表列表
	public List<Company> findUserMeterList(Integer userId) {
		return null;
	}

	//查询用户所有水表的监控信息（）
	public List<MeterMonitorDTO> findUserMeterMonitorInfoList(Integer userId){
		return null;
	}

	//查询水表详情信息
	public MachineInfo findMeterInfo(Integer userId){
		return null;
	}

	//查询档案记录
	//public List<＊＊＊Info> findMeter＊＊＊＊List

	/**
	 * 根据用户ID查询当前用户所属公司的仪表信息
	 *
	 * @param userId	用户id
	 * @return	返回一个machineinfo的所有数据，默认第一页，
	 * 每页10条，分页操作前端页面做假分页
	 */
	public PageVo<MachineInfoDTO> queryMachineInfo(Integer userId,String subUserName,boolean flag) {
		List<MachineInfo> result=null;
		if(flag){
			result = meterService.findUserMeterList(userId, subUserName, flag);
		}else{
			result = meterService.findUserMeterList(userId);
		}
		List<MachineInfoDTO> resultDto =new ArrayList<MachineInfoDTO>();

		for(MachineInfo m : result){
			MachineInfoDTO t=new MachineInfoDTO();
			try {
				BeanUtils.copyProperties(t, m);
			} catch (IllegalAccessException e) {
				t=new MachineInfoDTO();
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				t=new MachineInfoDTO();
				e.printStackTrace();
			}
			String tmp=queryMachineInfoForCompany(m.getNum(),userId);
			t.setCompany(tmp);
			resultDto.add(t);
		}

		PageVo<MachineInfoDTO> page = new PageVo<MachineInfoDTO>(1, resultDto.size(),
				10, resultDto);
		return page;
	}

	/**
	 * 查询通过水表号当前仪表的详情
	 *
	 * @param num
	 */

	public MachineInfo queryMachineInfo2(Integer num) {
		return machineInfoMapper.findOneByNum(num);
	}


	/**
	 *
	 * 新增仪表信息
	 *
	 * @param machineInfo	水表基础信息
	 * @param imgs			上传图片数据最多两条，安装图，线路图
	 * @param userId		用户ID
	 */
	public void saveMachineInfo(MachineInfo machineInfo,List<ResourceImgDTO> imgs,Integer userId){
		MachineInfo dbData=saveMachineinfo(machineInfo);
		//添加公司与水表的关系
		List<UserMeterAuthorityDTO> u=queryByMeterId(dbData.getNum());
		if(u==null||u.size()==0){//判断用户与水表是否有这个关系了，如果没有才insert
			Company usernameId=dashboardService.queryUserNameId(userId);
			if(usernameId!=null){
				Integer companyId=usernameId.getCompanyId();//拿到当前用户的公司
				saveUsermeterauthority(companyId, dbData.getNum());
			}
		}
		if(imgs!=null){
			for(ResourceImgDTO i:imgs){
				if(i!=null){
					ResourceImgDTO temp=findByNumAndFileType(dbData.getNum(),i.getFileType());
					if(temp.getId()!=null){
						temp.setFileName(i.getFileName());
						temp.setUri(i.getUri());
						temp.setUpdateTime(new Date());
						temp.setUpdateUser(userId);
						saveResourceImg(temp);
					}else{
						i.setNum(dbData.getNum());
						i.setCreateTime(new Date());
						i.setCreateUser(userId);
						i.setUpdateTime(new Date());
						i.setUpdateUser(userId);
						saveResourceImg(i);
					}
				}
			}
		}
	}

	/**
	 *
	 * 更新仪表信息
	 *
	 * @param machineInfo	水表基础信息
	 * @param imgs			上传图片数据最多两条，安装图，线路图
	 * @param userId		用户ID
	 */
	public void upateMachineInfo(MachineInfo machineInfo,List<ResourceImgDTO> imgs,Integer userId){
		MachineInfo dbData=updateMachineinfo(machineInfo);
		//添加公司与水表的关系
		List<UserMeterAuthorityDTO> u=queryByMeterId(dbData.getNum());
		if(u==null||u.size()==0){//判断用户与水表是否有这个关系了，如果没有才insert
			Company usernameId=dashboardService.queryUserNameId(userId);
			if(usernameId!=null){
				Integer companyId=usernameId.getCompanyId();//拿到当前用户的公司
				saveUsermeterauthority(companyId, dbData.getNum());
			}
		}
		if(imgs!=null){
			for(ResourceImgDTO i:imgs){
				if(i!=null){
					ResourceImgDTO temp=findByNumAndFileType(dbData.getNum(),i.getFileType());
					if(temp.getId()!=null){
						temp.setFileName(i.getFileName());
						temp.setUri(i.getUri());
						temp.setUpdateTime(new Date());
						temp.setUpdateUser(userId);
						saveResourceImg(temp);
					}else{
						i.setNum(dbData.getNum());
						i.setCreateTime(new Date());
						i.setCreateUser(userId);
						i.setUpdateTime(new Date());
						i.setUpdateUser(userId);
						saveResourceImg(i);
					}
				}
			}
		}
	}

	/**
	 * 保存上传的附件
	 * @param temp
	 */
	private void saveResourceImg(ResourceImgDTO temp) {
		ResourceImg resourceImg = new ResourceImg();
		try {
			BeanUtils.copyProperties(resourceImg, temp);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(resourceImg.getId()!=null){
			resourceImgMapper.updateByPrimaryKey(resourceImg);
		}else{
			resourceImgMapper.insertSelective(resourceImg);
		}
	}

	/**
	 * 通过水表号和文件类型找出对应的上传附件
	 * @param num
	 * @param fileType
	 * @return
	 */
	private ResourceImgDTO findByNumAndFileType(Integer num, String fileType) {
		ResourceImg resourceImg = resourceImgMapper.findByNumAndFileType(num,fileType);
		ResourceImgDTO temp = new ResourceImgDTO();
		try {
			BeanUtils.copyProperties( temp,resourceImg);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return temp;
	}

	/**
	 * 保存公司水表关联关系
	 * @param companyId
	 * @param num
	 */
	private void saveUsermeterauthority(Integer companyId, Integer num) {
		companyService.saveMeterCompany(companyId, num);
	}

	/**
	 * 通过水表号检索对应的公司
	 * @param num
	 * @return
	 */
	private List<UserMeterAuthorityDTO> queryByMeterId(Integer num) {
		Company company = companyMapper.findByMeterId(num);

		List<UserMeterAuthorityDTO> authorityDTOs = new ArrayList<>();
		if(company != null){
			UserMeterAuthorityDTO dto = new UserMeterAuthorityDTO();
			dto.setMeterId(num);
			dto.setUserNameId(company.getCompanyId());
			authorityDTOs.add(dto);
		}
		return authorityDTOs;
	}

	/**
	 * 新增仪表信息
	 * @param machineInfo
	 * @return
	 */
	private MachineInfo saveMachineinfo(MachineInfo machineInfo) {
		machineInfoMapper.insertOne(machineInfo);
		return machineInfoMapper.findOneByNum(machineInfo.getNum());
	}

	/**
	 * 更新仪表信息
	 * @param machineInfo
	 * @return
	 */
	private MachineInfo updateMachineinfo(MachineInfo machineInfo) {
		machineInfoMapper.updateByNum(machineInfo);
		return machineInfoMapper.findOneByNum(machineInfo.getNum());
	}

	/**
	 * 查询文件
	 * 通过主键ID查询表resource_img的数据
	 * @param id
	 */
	public ResourceImgDTO queryFileById(Integer id){
		ResourceImgDTO temp=findResourceImgById(id);
		return temp;
	}

	/**
	 * 通过主键id查询resourceimg
	 * @param id
	 * @return
	 */
	private ResourceImgDTO findResourceImgById(Integer id) {
		ResourceImg img = resourceImgMapper.selectByPrimaryKey(id);
		ResourceImgDTO dto = new ResourceImgDTO();
		try {
			BeanUtils.copyProperties(dto, img);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dto;
	}

	/**
	 * 查询当前仪表,检修记录、检定记录、电池更换、告警记录
	 * 分别通过水表号查询表machineinfo，reosurce_img
	 *
	 * @param num	水表号
	 */
	public MeterDetailVo queryMachineInfoDetail(Integer num) {
		MeterDetailVo vo = new MeterDetailVo();
		// 仪表
		MachineInfo machine = machineInfoMapper.findOneByNum(num);
		MachineInfoDTO dto=new MachineInfoDTO();
		try {
			BeanUtils.copyProperties(dto, machine);
		} catch (Exception e) {
			e.printStackTrace();
			dto=new MachineInfoDTO();
		}
		vo.setMachineInfo2(dto);
		List<ResourceImgDTO> imgs=queryFileByNum(num);
		vo.setResourceImg(imgs);
		return vo;
	}

	/**
	 * 查询仪表类型
	 */
	public List<MeterType> queryMeterType() {
		// 仪表类型
		List<MeterType> meterType = meterTypeMapper.findAll();
		return meterType;
	}

	/**
	 * 查询resource_img文件
	 *
	 * @param machineInfo
	 */
	public List<ResourceImgDTO> queryFileByNum(Integer num){
		List<ResourceImgDTO> temp=findResourceImgByNum(num);
		return temp;
	}

	/**
	 * 通过水表号查询所有的resourceimg
	 * @param num
	 * @return
	 */
	private List<ResourceImgDTO> findResourceImgByNum(Integer num) {
		List<ResourceImg> imgs = resourceImgMapper.findByNum(num);
		List<ResourceImgDTO> result = new ArrayList<>();
		if(imgs != null){
			for(ResourceImg img : imgs){
				ResourceImgDTO dto = new ResourceImgDTO();
				try {
					BeanUtils.copyProperties(dto, img);
				} catch (Exception e) {
					e.printStackTrace();
				}
				result.add(dto);
			}
		}
		return result;
	}

	/**
	 * 添加修改检修记录
	 * @param record
	 * @throws Exception
	 */
	public void saveOrUpdateArchivesRecord(ArchivesRecord record,MultipartFile checkImg,HttpServletRequest request,Integer userId) throws Exception{
		record.setCheckType(Constants.changeValue(record.getCheckTypeValue().intValue()));
		saveArchivesRecord(record);
		ResourceImgDTO install = null;
		if(checkImg!=null&&checkImg.getSize()>0){
			install = constantsUtil.formateFile(request,checkImg,record.getCheckTypeValue().toString(),record.getNum(),record.getId());
		}
		List<ResourceImgDTO> imgs = new ArrayList<ResourceImgDTO>();
		imgs.add(install);
		if(imgs!=null){
			for(ResourceImgDTO i:imgs){
				if(i!=null){
					ResourceImgDTO temp=findResourceImgByArchivesRecordId(record.getId());
					if(temp.getId()!=null){
						temp.setFileName(i.getFileName());
						temp.setUri(i.getUri());
						temp.setUpdateTime(new Date());
						temp.setUpdateUser(userId);
						saveResourceImg(temp);
					}else{
						i.setNum(record.getNum());
						i.setCreateTime(new Date());
						i.setCreateUser(userId);
						i.setUpdateTime(new Date());
						i.setUpdateUser(userId);
						i.setArchivesRecordId(record.getId());
						saveResourceImg(i);
					}
				}
			}
		}
	}

	/**
	 * 通过archives_recourd_id检索表resourceimg
	 * @param id
	 * @return
	 */
	private ResourceImgDTO findResourceImgByArchivesRecordId(Integer id) {
		ResourceImg img = resourceImgMapper.findOneByOperationId(id);
		ResourceImgDTO dto = new ResourceImgDTO();
		try {
			BeanUtils.copyProperties(dto, img);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dto;
	}

	/**
	 * 保存检查检定记录
	 * @param record
	 */
	private void saveArchivesRecord(ArchivesRecord record) {
		MeterOperation meterOperation = new MeterOperation();
		meterOperation.setMeterId(record.getNum());
		meterOperation.setCheckPeople(record.getCheckPeople());
		meterOperation.setCheckTime(record.getCheckTime());
		meterOperation.setCheckType(record.getCheckTypeValue() + "");
		meterOperation.setCheckDescription(record.getCheckType());
		if(record.getId()==null){
			meterOperationMapper.insertSelective(meterOperation);
			record.setId(meterOperation.getId());
		}else{
			meterOperation.setId(record.getId());
			meterOperationMapper.updateByPrimaryKey(meterOperation);
		}
	}

	/**
	 * 根据水表号查询检查检修检定记录
	 * @param num
	 * @return
	 */
	public PageVo<ArchivesRecordVo> queryArchivesRecord(Integer num){
		List<ArchivesRecordVo> result=findArchivesRecordByNum(num);
		PageVo<ArchivesRecordVo> page=new PageVo<ArchivesRecordVo>(1,result.size(),10,result);
		return page;
	}

	/**
	 * 通过水表号检索检查检定记录
	 * ArchivesRecordVo对象的构造函数最后两个参数是表resource_img的主键id和file_name，我需要这两个参数才能知道下载具体哪个文件
	 * @param num
	 * @return
	 */
	private List<ArchivesRecordVo> findArchivesRecordByNum(Integer num) {
		List<MeterOperation> meterOperations = meterOperationMapper.findByMeterId(num);

		List<ArchivesRecordVo> result = new ArrayList<>();
		if(meterOperations != null){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			for(MeterOperation operation : meterOperations){
				ResourceImg img = resourceImgMapper.findOneByOperationId(operation.getId());
				ArchivesRecordVo recordVo=null;
				if(img!=null){
					recordVo = new ArchivesRecordVo(operation.getId(), num, operation.getCheckPeople(),
							sdf.format(operation.getCheckTime()), operation.getCheckDescription(), Integer.parseInt(operation.getCheckType()), img.getFileName(), img.getId());

				}else{
					recordVo = new ArchivesRecordVo(operation.getId(), num, operation.getCheckPeople(),
							sdf.format(operation.getCheckTime()), operation.getCheckDescription(), Integer.parseInt(operation.getCheckType()), "", 0);
				}
				result.add(recordVo);
			}
		}
		return result;
	}

	/**
     * 查询所有站点
     * @param userId
     * @return	返回machineinfo的shortName
     */
    public List<String> queryAllStation(){
    	List<String> result= queryMachineInfoAllStation();
    	return result;
    }
    /**
     * 检索machineinfo的所有shortname
     * @return
     */
    private List<String> queryMachineInfoAllStation() {
    	List<MachineInfo> infos = machineInfoMapper.findAll();
		List<String> result = new ArrayList<>();
		for(MachineInfo info : infos){
			result.add(info.getShortName());
		}
		return result;
	}

	/**
     * 将水表更新为vip
     * @param num	水表号
     * @param isVip	是否是vip，true是，false不是
     * @return	执行成功返回true否则false
     */
    public boolean updateMachineVip(Integer num,Boolean isVip){
    	MachineInfo machineInfo2=machineInfoMapper.findOneByNum(num);
		if(machineInfo2!=null){
			Integer vip=isVip?1:0;
			machineInfo2.setIsVipAccount(vip);
			updateMachineinfo(machineInfo2);
		}
		return true;
	}

    /**
	 * 根据水表num查询此水表所属哪个公司
	 *
	 * @param userId
	 * @return
	 */
	public String queryMachineInfoForCompany(Integer num,Integer userId) {
		String company="";
		company=meterService.getMeterCompany(num, userId);
//		Company c=queryCompanyByMeterId(num);
//		if(c==null){
//			return company;
//		}
//		company=c.getCompanyName();
//		Integer parentId=c.getParentCompanyId();
//		if(parentId!=null){
//			for(int i=0;i<3;i++){//最多3层
//				Company us=queryCompanyById(parentId);//查看当前公司有母公司没有
//				if(us!=null){
//					company+="/"+us.getCompanyName();
//					parentId=us.getParentCompanyId();
//				}else{
//					break;
//				}
//			}
//		}
		return company;
	}

	/**
	 * 根据主键id查询公司
	 * @param parentId
	 * @return
	 */
	private Company queryCompanyById(Integer parentId) {
		return companyMapper.selectByPrimaryKey(parentId);
	}

	/**
	 * 根据水表号查询属于那个公司
	 * @param num
	 * @return
	 */
	private Company queryCompanyByMeterId(Integer num) {
		return companyMapper.findByMeterId(num);
	}

	/**
	 *
	 * @param userId
	 * @return
	 */
	public List<NdataVo> queryNdataLastData(Integer userId) {
		List<NdataVo> result=new ArrayList<NdataVo>();
		//人所对应的所有水表
		List<MachineInfo> machines = meterService.findUserMeterList(userId);
		for(MachineInfo t:machines){
			/*if(76==t.getNum()){   Delete by ManZhiWei
				System.out.println();
			}*/
			try{
				List<NdataVo> tmp=queryNdataLastDataByNum(t.getNum());
				NdataVo temp=null;
				if(tmp==null||tmp.size()==0){
					temp=new NdataVo();
				}else{
					temp=tmp.get(0);
				}
				temp.setNum(t.getNum());
				result.add(temp);
			}catch(Exception e){
				logger.error("t.getNum():"+t.getNum(),e);
			}
		}
		return result;
	}

	/**
	 * 查询水表号最新的一条记录封装到ndatavo中
	 * 涉及表ndata和GPRS4300
	 * Constants.isElectricity(meterTypeId)true电流ndata，false电磁gprs4300
	 * @param num
	 * @return
	 */
	private List<NdataVo> queryNdataLastDataByNum(Integer num) {
		MachineInfo info = machineInfoMapper.findOneByNum(num);
		Ndata ndata = null;
		if(Constants.isElectricity(info.getMeterTypeId())){
				ndata = ndataMapper.findOneByNum(num);
		} else{
			if(info.getMeterTypeId() !=3)
				ndata = gprsDataMapper.findOneNdataByNum(num);
			else
				ndata = gprsDataFor4200Mapper.findOneNdataByNum(num);
		}
		List<NdataVo> ndataVos = new ArrayList<>();
		if(ndata != null){
			NdataVo ndataVo = MeterUtils.getInstance(ndata, info);
			ndataVos.add(ndataVo);
		}
		return ndataVos;
	}

	/**
	 * ADD BY  MANZHIWEI  2017-12-20
	 * 返回时间间隔节点
	 * @param num
	 * @param nowDate
	 * @param nowTime
	 * @return
	 */
	public String getHistoryConnectIntervalTime(Integer num,String nowDate,String nowTime){
		MachineInfo info = machineInfoMapper.findOneByNum(num);
		if(MeterUtils.isGprs4300(info)){
			return null;
		}else {
			String now = nowDate + " " + nowTime;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Ndata ndata = ndataMapper.findConnectIntervalTime(num, now);
			if (ndata != null) {
				Date date1 = ndata.getiTime();
				return sdf.format(date1) + "," + now;
			} else {
				//TODO 这个地方待处理。Modify by manzhiwei 2017-12-23
				return now + ",";
			}
		}
	}

	/**
	 * ADD　BY  MANZHIWEI　　2018-03-20
	 * 查找今日使用累计流量
	 * @param num
	 * @param currentITime
	 * @return
	 */
	public float queryIncreaseTotalflow(Integer num,Date currentITime){
		float increaseTotalflow = 0.0f;
		double testTotalflow =0.0;
		MachineInfo info = machineInfoMapper.findOneByNum(num);
		Map<String,Object> map= null;
		NdataCriteria ndataCriteria = new NdataCriteria();
		ndataCriteria.setMeterId(num);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		ndataCriteria.setCurrentDate(sdf.format(currentITime));
		if(MeterUtils.isGprs4300(info)){
				if(info.getMeterTypeId() != 3)
					map = gprsDataMapper.findNdataDayData(ndataCriteria);
					else
					map = gprsDataFor4200Mapper.findNdataDayData(ndataCriteria);
		}else {
				map = ndataMapper.findDayData(ndataCriteria);
		}
		if(map == null){
			//TODO
		} else{
			testTotalflow = (Double)map.get("increaseTotalflow");
			increaseTotalflow = (float)testTotalflow;

		}
		return increaseTotalflow;
	}

	/**
	 * ADD by MANZHIWEI
	 * @param shortName
	 * @return
	 */
	public PageVo<MachineInfo> queryMachineIPAndPort(String shortName){
		if(shortName.equals("")|| shortName == null){
			shortName = "测试用表";
		}
		List<MachineInfo> info = machineInfoMapper.findBySubUserName(shortName);
		if(info != null){
			PageVo<MachineInfo> result = new PageVo<MachineInfo>(1,info.size(),10,info);
				return  result;
		}else{
			return null;
		}
	}

	/**
	 * 通过页面上面的搜索框来查找
	 * @param userId
	 * @param shortName
	 * @param flag
	 * @return
	 */
	public PageVo<NdataVo> getTodayInfoByShortName(Integer userId, String shortName,Boolean flag) {

		List<MachineInfo> infos = meterService.findUserMeterList(userId, shortName, flag);
		List<NdataVo> ndataVos =  new ArrayList<>();
		Ndata ndata = null;
		PageVo<NdataVo> result = null;
		if(infos != null){
			for (MachineInfo info: infos) {
				if(Constants.isElectricity(info.getMeterTypeId())){
					ndata = ndataMapper.findOneByNum(info.getNum());
				} else{
					if(info.getMeterTypeId() !=3)
						ndata = gprsDataMapper.findOneNdataByNum(info.getNum());
					else
						ndata = gprsDataFor4200Mapper.findOneNdataByNum(info.getNum());
				}
				if(ndata != null){
					NdataVo ndataVo = MeterUtils.getInstance(ndata, info);
					ndataVos.add(ndataVo);
				}
			}
		}

		if(ndataVos !=null) {

			for(NdataVo m: ndataVos){
				m.setIncreaseTotalflow(queryIncreaseTotalflow(m.getNum(),m.getI_time()));
				m.setIncreaseTotalflowMonth(queryIncreaseMonthTotalflow(m.getNum(),m.getI_time()));
			}
			result = new PageVo<>(1, ndataVos.size(), 10, ndataVos);

			return result;
		}
		return null;
	}

	/**
	 * ADD　BY  MANZHIWEI  2018-03-15
	 * 查找月累计流量数据
	 * @param num
	 * @param currentITime
	 * @return
	 */
	public Double queryIncreaseMonthTotalflow(Integer num, Date currentITime) {
		Map<String,Object> map = null;
		Double increaseMonthTotalflow =0.0;
		MachineInfo info = machineInfoMapper.findOneByNum(num);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		Date now = new Date();
		String year = sdf.format(currentITime).substring(0,4);
		String month = sdf.format(currentITime).substring(5,7);
		String lastmonth = "" + (Integer.parseInt(month)-1);
		if(MeterUtils.isGprs4300(info)){
			if(info.getMeterTypeId() !=3)
				map = gprsDataMapper.find4300MonthTotalflowData(num,year,month,lastmonth);//TODO 如果当月是12月，TODO 如果最后一次不是当前月到达
			else
				map = gprsDataFor4200Mapper.find4200MonthTotalflowData(num,year,month,lastmonth);//TEST
		}else {
			map = ndataMapper.findMonthTotalflowData(num,year,month,lastmonth);//TODO 如果当月是12月，TODO 如果最后一次不是当前月到达
		}
		if(map.get("increaseMonthTotalflow") != null){
			increaseMonthTotalflow = (Double) map.get("increaseMonthTotalflow");
		}else{
			if(map.get("currentMonthTotalflow") != null){
				increaseMonthTotalflow =(Double) map.get("currentMonthTotalflow")- (Double) map.get("lastMonthTotalflow");
			}else {
				increaseMonthTotalflow = 0.0;
			}
		}
		return increaseMonthTotalflow;
	}

	/**
	 * ADD BY MANZHIWEI  2018-03-05
	 * @param num
	 * @param ip
	 * @param port
	 * @return
	 */
	public String updateMachineIPAndPort(String num, String ip, String port) {
		MachineInfo info = queryMachineInfo2(Integer.parseInt(num));
		int result;
		if(info == null)
			return "当前仪表不存在";
		if (info.getGversion().compareTo("1.45") < 0)
			return "表端版本太低";
		else{
			if(info.getDisplayBoardWrite() == 4)
				return "当前写入成功，未执行命令";
			else if (info.getDisplayBoardWrite() == 0){
				info.setDisplayBoardWrite(4);
				info.setIp(ip);
				info.setIpPort(Integer.parseInt(port));
				result = machineInfoMapper.updateIpAndPortByNum(info);
			}else{
				return "权限指令错误,displayBoardWirter值异常";
			}
		}
		if(result != 1)
			return "sql语句执行错误";
		else
			return "写入成功";
	}
}


