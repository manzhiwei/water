package com.welltech.controller.rest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.welltech.waterAffair.common.util.UserUtils;
import com.welltech.waterAffair.domain.dto.ReplaceMeterRecordDTO;
import com.welltech.waterAffair.domain.entity.MachineInfo;
import com.welltech.waterAffair.domain.entity.User;
import com.welltech.waterAffair.domain.entity.UserLog;
import com.welltech.waterAffair.domain.entity.UserMeter;
import com.welltech.waterAffair.domain.vo.AllMeterVo4JS;
import com.welltech.waterAffair.domain.vo.AreaCompanyVo;
import com.welltech.waterAffair.domain.vo.BranchCompanyAreaVo4JS;
import com.welltech.waterAffair.domain.vo.BranchCompanyVo;
import com.welltech.waterAffair.domain.vo.BranchCompanyVo4JS;
import com.welltech.waterAffair.domain.vo.CompanyMeterVo4JS;
import com.welltech.waterAffair.domain.vo.CompanyVo4JS;
import com.welltech.waterAffair.domain.vo.HeadCompanyVo;
import com.welltech.waterAffair.domain.vo.PageByDataTableVo;
import com.welltech.waterAffair.domain.vo.PageVo;
import com.welltech.waterAffair.domain.vo.ResponseVo;
import com.welltech.waterAffair.domain.vo.UserMeterVo;
import com.welltech.waterAffair.service.CompanyService;
import com.welltech.waterAffair.service.MeterService;

import net.sf.json.JSONArray;

/**
 * Created by WangXin on 2017/4/24.
 */
@RestController
public class SystemManageRestController {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private MeterService meterService;
    
    /**
     * 公司列表
     * @return
     */
    @RequestMapping(value = { "/clientManageController", "/userManageController" }, method = RequestMethod.GET)
    public String clientManageController(){
        Integer userId = UserUtils.getUserId();
        List<CompanyVo4JS> companyList=new ArrayList<CompanyVo4JS>();
        List<HeadCompanyVo> headCompanyVos = companyService.getCompanyVoByUserId(userId);
        for(HeadCompanyVo headCompanyVo : headCompanyVos){
            CompanyVo4JS companyVo4JS = new CompanyVo4JS();
            companyVo4JS.setId(headCompanyVo.getCompanyId() + "");
            companyVo4JS.setText(headCompanyVo.getCompanyName());
            List<BranchCompanyVo4JS> branchCompanyVo4JSs = new ArrayList<BranchCompanyVo4JS>();
            for(BranchCompanyVo branchCompanyVo : headCompanyVo.getBranchList()){
                BranchCompanyVo4JS branchCompanyVo4JS = new BranchCompanyVo4JS();
                branchCompanyVo4JS.setIcon("fa fa-building");
                branchCompanyVo4JS.setId(branchCompanyVo.getCompanyId() + "");
                branchCompanyVo4JS.setText(branchCompanyVo.getCompanyName());

                List<BranchCompanyAreaVo4JS> branchCompanyAreaVo4JSs = new ArrayList<BranchCompanyAreaVo4JS>();
                for(AreaCompanyVo areaCompanyVo : branchCompanyVo.getBranchList()){
                    BranchCompanyAreaVo4JS branchCompanyAreaVo4JS = new BranchCompanyAreaVo4JS();
                    branchCompanyAreaVo4JS.setId(areaCompanyVo.getCompanyId() + "");
                    branchCompanyAreaVo4JS.setText(areaCompanyVo.getCompanyName());
                    branchCompanyAreaVo4JSs.add(branchCompanyAreaVo4JS);

                }
                branchCompanyVo4JS.setChildren(branchCompanyAreaVo4JSs);
                branchCompanyVo4JSs.add(branchCompanyVo4JS);
            }



            companyVo4JS.setChildren(branchCompanyVo4JSs);
            companyList.add(companyVo4JS);
        }

        return "[{\"children\":"+ JSONArray.fromObject(companyList).toString()+",\"text\":\"所有公司\",\"id\":\"0\",\"state\": {\"opened\": true}},{\"text\":\"系统管理员\",\"id\":\"-1\",\"state\": {\"opened\": true}}]";
    }

    /**
     * 添加公司
     */
    @RequestMapping(value = {"addCompany4Tree"} , method = RequestMethod.POST)
    public String addCompany4Tree(@RequestParam(name = "id", defaultValue = "") String parentId, @RequestParam(name = "name") String name){
        String companyId = companyService.saveCompany(parentId, name);
        return "{\"id\":"+companyId+"}";
    }

    //重命名公司
    @RequestMapping(value = { "/renameCompany4Tree" }, method = RequestMethod.POST)
    public String renameCompany4Tree(@RequestParam(name = "id") String id, @RequestParam(name = "name") String name) {
        companyService.updateCompany(Integer.parseInt(id),name);
        return "{\"result\":true}";
    }

    //删除公司
    @RequestMapping(value = { "/removeCompany4Tree" }, method = RequestMethod.POST)
    public String removeCompany4Tree(@RequestParam(name = "id") String id) {
        companyService.deleteCompany(Integer.valueOf(id));
        return "{\"result\":true}";
    }

    //查询全部公司及分公司
    @RequestMapping(value = { "/queryMeterList4Company"}, method = RequestMethod.POST)
    public String queryMeterList4Company(String id) {
        List<MachineInfo> machineInfos = companyService.queryMeterByCompanyId(Integer.parseInt(id));
        List<CompanyMeterVo4JS> companyMeterVo4JSList=new ArrayList<CompanyMeterVo4JS>();
        for(MachineInfo meter : machineInfos){
            CompanyMeterVo4JS companyMeterVo4JS=new CompanyMeterVo4JS();
            companyMeterVo4JS.setText(meter.getSubUserName());
            companyMeterVo4JS.setId(""+meter.getNum());
            companyMeterVo4JSList.add(companyMeterVo4JS);
        }
        return JSONArray.fromObject(companyMeterVo4JSList).toString();
    }

    //查询全部公司及分公司
    @RequestMapping(value = { "/queryAllMeter4Company" }, method = RequestMethod.POST)
    public String queryAllMeter4Company(String id) {

        //后续修改为全部水表
        List<MachineInfo>  allMeterList = companyService.queryAllMachineInfo();
        List<MachineInfo>  companyMeterList = companyService.queryMeterByCompanyId(Integer.valueOf(id));

        Map<Integer,Integer> companyMeterMap=new HashMap<Integer,Integer>();
        for(MachineInfo meter:companyMeterList){
            companyMeterMap.put(meter.getNum(),meter.getNum());
        }

        List<AllMeterVo4JS> allMeterVo4JSList=new ArrayList<AllMeterVo4JS>();
        for(MachineInfo meter:allMeterList){

            AllMeterVo4JS allMeterVo4JS=new AllMeterVo4JS();
            allMeterVo4JS.setText(""+meter.getSubUserName());
            allMeterVo4JS.setId(""+meter.getNum());
            AllMeterVo4JS.TreeState ts=null;
            if(companyMeterMap.get(meter.getNum())!=null){
                ts=allMeterVo4JS.new TreeState(true,true);
            }else{
                ts=allMeterVo4JS.new TreeState(false,false);
            }
            allMeterVo4JS.setState(ts);
            allMeterVo4JSList.add(allMeterVo4JS);
        }
        return "[{\"children\":"+JSONArray.fromObject(allMeterVo4JSList).toString()+",\"text\":\"所有公司\",\"state\": {\"opened\": true,\"disabled\": true}}]";
    }

    @RequestMapping(value = "/addMeter4Company", method = {RequestMethod.POST })
    public String  addMeter4Company(String cid, String mid) {
        //读取需要添加仪表的公司
        try{
            companyService.saveMeterCompany(Integer.valueOf(cid),Integer.valueOf(mid));
        }catch (Exception e){

        }

        return "{\"id\":"+cid+"}";
    }

    @RequestMapping(value = "/addMultiMeter4Company", method = {RequestMethod.POST })
    public String  addMultiMeter4Company(String cid, String mid) {
        String[] midList=mid.split("-");

        for(String new_mid:midList){
            try{
                companyService.saveMeterCompany(Integer.valueOf(cid),Integer.valueOf(new_mid));
            }catch (Exception e){

            }
        }
        return "{\"id\":"+cid+"}";
    }
    
    

    //删除公司水表
    @RequestMapping(value = { "/removeCompanyMeter4Tree" }, method = RequestMethod.POST)
    public String removeCompanyMeter4Tree(String cid, String mid) {
        companyService.deleteMeterCompany(Integer.valueOf(cid),Integer.valueOf(mid));
        return "{\"result\":true}";
    }

    @RequestMapping(value = { "/queryCompanyUser" }, method = RequestMethod.POST)
    public String queryCompanyUser(String cid) {
    	Integer companyId = Integer.parseInt(cid);
    	List<User> result = companyService.getUsersByCompanyId(companyId);
    	if(companyId < 0){
    		result = companyService.getAdminUsers();
    	}
        return JSONArray.fromObject(result).toString();
    }

    @RequestMapping(value = {"/queryUserList4Company"}, method = RequestMethod.POST)
    public List<CompanyMeterVo4JS> queryUserList4Company(String id){
        List<User> users = companyService.getUsersByCompanyId(Integer.parseInt(id));
        List<CompanyMeterVo4JS> result = new ArrayList<CompanyMeterVo4JS>();
        for(User user : users){
            CompanyMeterVo4JS meterVo4JS = new CompanyMeterVo4JS();
            meterVo4JS.setId(user.getUserId() + "");
            meterVo4JS.setText(user.getUserName());
            result.add(meterVo4JS);
        }
        return result;
    }

    //查询某个员工的水表列表
    @RequestMapping(value = { "/queryMeterList4User" }, method = RequestMethod.POST)
    public List<UserMeterVo> queryUserList4User(String id) {
        List<UserMeterVo> companyMeterVo4JSList=new ArrayList<UserMeterVo>();
        List<UserMeter> userMeters = companyService.getUserMetersByUserId(Integer.parseInt(id));
        for(UserMeter userMeter:userMeters){
            UserMeterVo companyMeterVo4JS=new UserMeterVo();
            //通过仪表ID查询下名称
            MachineInfo machineInfo = companyService.getMachineInfo(userMeter.getMeterId());
            companyMeterVo4JS.setText(machineInfo.getSubUserName());
            companyMeterVo4JS.setMid(""+userMeter.getMeterId());
            companyMeterVo4JS.setUid(""+userMeter.getUserId());
            companyMeterVo4JS.setId(userMeter.getUserId() + "|" + userMeter.getMeterId());
            companyMeterVo4JSList.add(companyMeterVo4JS);
        }
        return companyMeterVo4JSList;
    }

    //查询某个用户所在公司的所有水表，并且剔除他所拥有的水表
    @RequestMapping(value = { "/queryMeterList4BranchCompany" }, method = RequestMethod.POST)
    public String queryMeterList4BranchCompany(String id) {
        //通过用户ID查到他所在公司的id
        User user = companyService.getUser(Integer.parseInt(id));
        //查询用户-仪表关系表 systemManagerService.queryAllMachineInfo();
        List<MachineInfo>  companyMeterList = companyService.queryMeterByCompanyId(user.getCompanyId());

        Map<Integer,Integer> companyMeterMap=new HashMap<Integer,Integer>();
        List<UserMeter> userMeters = companyService.getUserMetersByUserId(Integer.parseInt(id));
        for(UserMeter userMeter: userMeters){
            companyMeterMap.put(userMeter.getMeterId(),userMeter.getMeterId());
        }

        List<AllMeterVo4JS> allMeterVo4JSList=new ArrayList<AllMeterVo4JS>();
        for(MachineInfo meter:companyMeterList){
            AllMeterVo4JS allMeterVo4JS=new AllMeterVo4JS();
            allMeterVo4JS.setText(""+meter.getSubUserName());
            allMeterVo4JS.setId(""+meter.getNum());
            AllMeterVo4JS.TreeState ts=null;
            if(companyMeterMap.get(meter.getNum())!=null){
                ts=allMeterVo4JS.new TreeState(true,true);
            }else{
                ts=allMeterVo4JS.new TreeState(false,false);
            }
            allMeterVo4JS.setState(ts);
            allMeterVo4JSList.add(allMeterVo4JS);
        }
        return "[{\"children\":"+JSONArray.fromObject(allMeterVo4JSList).toString()+",\"text\":\"所有水表\",\"state\": {\"opened\": true,\"disabled\": true}}]";
    }

    /**
     * 删除用户水表关系
     * @param mid
     * @return
     */
    @RequestMapping(value = {"/removeUserMeter4Tree"},method = RequestMethod.POST)
    public String removeUserMeter4Tree(String mid) {
        String[] ids = mid.split("\\|");
        Integer userId = Integer.parseInt(ids[0]);
        Integer meterId = Integer.parseInt(ids[1]);
        try{
            companyService.deleteUserMeter(userId,meterId);
            return "{\"result\":true}";
        }catch (Exception e){
            e.printStackTrace();
            return "{\"result\":false}";
        }
    }

    /**
     * 添加用户水表关系
     * @param request
     * @return
     */
    @RequestMapping(value = "/addUserMeter4Company", method = {RequestMethod.POST })
    public String  addMeter4User(String cid, String mid) {
        //读取需要添加仪表的公司
        try{
            UserMeter um=new UserMeter();
            um.setUserId(Integer.valueOf(cid));
            um.setMeterId(Integer.valueOf(mid));
            companyService.addUserMeter(um);
        }catch (Exception e){
            e.printStackTrace();
        }

        return "{\"id\":"+cid+"}";
    }
    
    @RequestMapping(value = "/addMultiUserMeter4Company", method = {RequestMethod.POST })
    public String  addMultiUserMeter4Company(HttpServletRequest request) {
        String cid=request.getParameter("cid");
        String mid=request.getParameter("mid");

        String[] midList=mid.split("-");

        for(String new_mid:midList){
            try{
            	UserMeter um=new UserMeter();
                um.setUserId(Integer.valueOf(cid));
                um.setMeterId(Integer.valueOf(new_mid));
                companyService.addUserMeter(um);
            }catch (Exception e){
            }
        }
        return "{\"id\":"+cid+"}";
    }
    
  //编辑公司员工
    @RequestMapping(value = { "/editCompanyUser4JQGrid" }, method = RequestMethod.POST)
    public String editCompanyUser4JQGrid(HttpServletRequest request) {
        String id=request.getParameter("id");
        String userName=request.getParameter("userName");
        String password=request.getParameter("password");
        String modifypermission=request.getParameter("modifypermission");

        User newUser = new User();

        newUser.setUserId(Integer.parseInt(id));
        newUser.setUserName(userName);
        newUser.setPassword(password);
        try{
            newUser.setModifyPermission(Integer.valueOf(modifypermission));
        }catch (Exception e){

        }
        ResponseVo<User> result= companyService.updateUser(newUser);

        return result.getResponseMsg();
    }

    //添加公司员工
    @RequestMapping(value = { "/addCompanyUser4JQGrid" }, method = RequestMethod.POST)
    public String addCompanyUser4JQGrid(HttpServletRequest request) {
        String cid=request.getParameter("cid");
        String userName=request.getParameter("userName");
        String password=request.getParameter("password");
        String modifypermission=request.getParameter("modifypermission");

        User newUser=new User();
        newUser.setCompanyId(Integer.valueOf(cid));
        //需要通过分公司ID查询总公司id

        newUser.setUserName(userName);
        newUser.setPassword(password);
        try{
            newUser.setModifyPermission(Integer.valueOf(modifypermission));
        }catch (Exception e){

        }

        return companyService.addUser(newUser).getResponseMsg();
    }

    //删除公司员工
    @RequestMapping(value = { "/deleteCompanyUser4JQGrid" }, method = RequestMethod.POST)
    public String deleteCompanyUser4JQGrid(HttpServletRequest request) {
        String userId=request.getParameter("id");

        companyService.deleteUserByUserId(Integer.valueOf(userId));

        return "{\"id\":"+1+"}";
    }




    /**
	 * 查询日志
	 * @param parameter
	 * @return
	 */
	@RequestMapping(value = "/findByConditionRecord",method=RequestMethod.POST)
	public PageByDataTableVo<UserLog> findByConditionRecord(String draw,String start,String length,String startTime, String endTime,String ides,Integer type,HttpServletRequest request) {

		Integer userId = UserUtils.getUserId();
		int startIndex=0;
		if(start==null){
			startIndex=0;
		}else{
			startIndex=Integer.valueOf(start);
		}
		int pageSize=0;
		if(length==null){
			pageSize=10;
		}else{
			pageSize=Integer.valueOf(length);
		} 
		Date start1 = null;
		Date end1 = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try {
			if (startTime != null&&startTime.length()>0) {
				start1 = sdf.parse(startTime);
			}
			if (endTime != null&&endTime.length()>0) {
				end1 = sdf.parse(endTime);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String userIdes=null;
		if (ides != null&&ides.length()>0) {
			userIdes = ides;
		}else{//如果用户为空那么自动获取当前用户下的所有子用户
			Map<Integer,String> useres = companyService.querySubUser(userId);
			if(useres.size()>0){
				userIdes="";
				for(Integer s:useres.keySet()){
					userIdes+=","+s;
				}
				if(userIdes.startsWith(",")){
					userIdes=userIdes.substring(1);
				}
			}
		}
		PageVo<UserLog> res = companyService.findUserLogByConditionPage(userIdes, type, start1, end1, startIndex, pageSize);
		PageByDataTableVo<UserLog> result=new PageByDataTableVo<UserLog>(draw,res.getCountSize(),pageSize,res.getEntity());
		return result;
	}
	
	/**
	 * 仪表更换记录
	 * @param draw
	 * @param start
	 * @param length
	 * @param startTime
	 * @param endTime
	 * @param stations
	 * @param request
	 * @return
	 */
	@RequestMapping(value="findReplaceMeterRecord")
	public PageByDataTableVo<ReplaceMeterRecordDTO> findReplaceMeterRecord(String draw,String start,String length,String startTime, String endTime,String stations,HttpServletRequest request){
		Integer userId = UserUtils.getUserId();
		int startIndex=0;
		if(start==null){
			startIndex=0;
		}else{
			startIndex=Integer.valueOf(start);
		}
		int pageSize=0;
		if(length==null){
			pageSize=10;
		}else{
			pageSize=Integer.valueOf(length);
		} 
		Date start1 = null;
		Date end1 = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try {
			if (startTime != null&&startTime.length()>0) {
				start1 = sdf.parse(startTime);
			}	
			if (endTime != null&&endTime.length()>0) {
				end1 = sdf.parse(endTime);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		PageVo<ReplaceMeterRecordDTO> res = meterService.findReplaceMeterRecordPage(stations, start1, end1, startIndex, pageSize);
		PageByDataTableVo<ReplaceMeterRecordDTO> result=new PageByDataTableVo<ReplaceMeterRecordDTO>(draw,res.getCountSize(),pageSize,res.getEntity());
		return result;
	}
	
}
