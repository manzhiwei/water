package com.welltech.waterAffair.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.welltech.waterAffair.domain.entity.Company;
import com.welltech.waterAffair.domain.entity.MachineInfo;
import com.welltech.waterAffair.domain.entity.Ndata;
import com.welltech.waterAffair.domain.entity.NdataSs;
import com.welltech.waterAffair.domain.vo.NdataVo;
import com.welltech.waterAffair.domain.vo.NdatassVo;
import com.welltech.waterAffair.domain.vo.before.MachineDetailInfo4MapVo;
import com.welltech.waterAffair.repository.CompanyMapper;
import com.welltech.waterAffair.repository.MachineInfoMapper;
import org.springframework.stereotype.Service;


/**
 * Created by zhoupei on 2016/12/4.
 * Motified by WangXin on 2017/04/19
 */
@Service
public class DashboardService {

    @Resource
    private CompanyMapper companyMapper;

    @Resource
    private MachineInfoMapper machineInfoMapper;

    @Resource
    private MeterService meterService;

    //查询用户所属公司
    public Company queryUserNameId(Integer userId) {
        return companyMapper.findCompanyByUserId(userId);

    }

    //查询公司下属分公司(区域)
    public List<Company> findSubCompanyList(Integer companyId){
        return companyMapper.findByParentCompanyId(companyId,null);
    }

    //查询公司下属分配给某个用户的仪表
    public List<MachineInfo> findCompanyUserMeterList(Integer companyIc,Integer userId){
        return machineInfoMapper.findByCompanyIdAndUserId(companyIc,userId);
    }

    //查询用户被分配的所有水表
    public List<MachineInfo> findUserMeterList(Integer userId){
        return null;
    }



    /**
     * 查询水表详细
     * @param userId
     */
    public MachineDetailInfo4MapVo queryMachineDetailInfo4MapVo(Integer num) {
        NdataVo res=meterService.findMeterLastestHourInfo(num);
        NdatassVo res1=meterService.findMeterLastestMinuteInfo(num);
        MachineDetailInfo4MapVo vo=new MachineDetailInfo4MapVo();

        try{
            vo.setUpdateTimes("0");
        }catch (Exception e){
            vo.setUpdateTimes("0");
        }

        try{
            vo.setVoltage(res.getCurrentV()!=null?(res.getCurrentV()+""):("无"));
        }catch (Exception e){
            vo.setVoltage("无");
        }

        try{
            vo.setPress(res1.getPress()+"");
        }catch (Exception e){
            vo.setPress("无");
        }
        try{
            vo.setSignal(res.getSignal_strength()+"");
        }catch (Exception e){
            vo.setSignal("无");
        }
        try{
            vo.setTotalFlow(res.getTotalflow()+"");
        }catch (Exception e){
            vo.setTotalFlow("无");
        }
        try{
            vo.setnTotalFlow(res.getTotalflow()+"");
        }catch (Exception e){
            vo.setnTotalFlow("无");
        }
        try{
            Date date = new Date(res1.getTime()*1000);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            //System.out.println(dateFormat.format(date));
            vo.setLastUpdateTime(dateFormat.format(date));
        }catch (Exception e){
            vo.setLastUpdateTime("无");
        }
        try{
            vo.setTotalFlow(res.getTotalflow()+"");
        }catch (Exception e){
            vo.setTotalFlow("无");
        }
        try{
            vo.setFlow(res1.getFlow()+"");
        }catch (Exception e){
            vo.setFlow("无");
        }
        try{
            vo.setCurrent(res.getCurrentI()!=null?(res.getCurrentI()+""):("无"));
        }catch (Exception e){
            vo.setCurrent("无");
        }
        try{
            vo.setBattery(res.getMe()+"");
        }catch (Exception e){
            vo.setBattery("无");
        }


        return vo;

    }


}
