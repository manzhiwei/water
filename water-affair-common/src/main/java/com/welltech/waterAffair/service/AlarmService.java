package com.welltech.waterAffair.service;

import java.util.*;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.welltech.waterAffair.common.aop.pagination.bean.Page;
import com.welltech.waterAffair.domain.criteria.AlarmProcessRecordCriteria;
import com.welltech.waterAffair.domain.entity.AlarmProcessRecord;
import com.welltech.waterAffair.domain.entity.AlarmUserConfig;
import com.welltech.waterAffair.domain.entity.MachineInfo;
import com.welltech.waterAffair.domain.vo.AlarmSettingVo;
import com.welltech.waterAffair.domain.vo.PageVo;
import com.welltech.waterAffair.repository.AlarmProcessRecordMapper;
import com.welltech.waterAffair.repository.AlarmUserConfigMapper;

/**
 * 告警配置
 * @author WangXin
 *
 */
@Service
public class AlarmService {

	@Autowired
	private AlarmUserConfigMapper alarmUserConfigMapper;
	
	@Autowired
	private AlarmProcessRecordMapper alarmProcessRecordMapper;
	
	@Autowired
	private MeterService meterService;
	
	/**
	 * 根据水表号 水表类型 报警类型得到水表报警配置
	 * @param meterId
	 * @param type
	 * @param alarmType
	 * @return
	 */
	public AlarmUserConfig findConfigByMidAndTypeAndAlarmType(Integer meterId, String type, String alarmType) {
		return alarmUserConfigMapper.findByMidAndTypeAndAlarmType(meterId, type, alarmType);
	}

	/**
	 * 封装报警配置视图层对象
	 * @param alarmSetting
	 * @return
	 */
	public AlarmSettingVo alarmSettingConfig(AlarmUserConfig alarmSetting) {
		AlarmSettingVo alarmSettingVo=new AlarmSettingVo();
        //超高  ?"":""+
        alarmSettingVo.setHhvalue(alarmSetting.getHighHighValue()==null?"":""+alarmSetting.getHighHighValue());
        int alarmSettingType=alarmSetting.getHighHighType() == null? 0 :alarmSetting.getHighHighType();
        switch (alarmSettingType){
            case 1:
                alarmSettingVo.setHhlevel1(""+alarmSettingType);
                break;
            case 2:
                alarmSettingVo.setHhlevel2(""+alarmSettingType);
                break;
            case 3:
                alarmSettingVo.setHhlevel3(""+alarmSettingType);
                break;
            default:break;
        }

        //高高
        alarmSettingVo.setHmvalue(alarmSetting.getHighMiddleValue()==null?"":""+alarmSetting.getHighMiddleValue());
        alarmSettingType=alarmSetting.getHighMiddleType() == null? 0 :alarmSetting.getHighMiddleType();
        switch (alarmSettingType){
            case 1:
                alarmSettingVo.setHmlevel1(""+alarmSettingType);
                break;
            case 2:
                alarmSettingVo.setHmlevel2(""+alarmSettingType);
                break;
            case 3:
                alarmSettingVo.setHmlevel3(""+alarmSettingType);
                break;
            default:break;
        }


        //高
        alarmSettingVo.setHlvalue(alarmSetting.getHighLowValue()==null?"":""+""+alarmSetting.getHighLowValue());
        alarmSettingType=alarmSetting.getHighLowType() == null? 0 :alarmSetting.getHighLowType();
        switch (alarmSettingType){
            case 1:
                alarmSettingVo.setHllevel1(""+alarmSettingType);
                break;
            case 2:
                alarmSettingVo.setHllevel2(""+alarmSettingType);
                break;
            case 3:
                alarmSettingVo.setHllevel3(""+alarmSettingType);
                break;
            default:break;
        }

        //超低
        alarmSettingVo.setLlvalue(alarmSetting.getLowLowValue()==null?"":""+alarmSetting.getLowLowValue());
        alarmSettingType=alarmSetting.getLowLowType() == null? 0 :alarmSetting.getLowLowType();
        switch (alarmSettingType){
            case 1:
                alarmSettingVo.setLllevel1(""+alarmSettingType);
                break;
            case 2:
                alarmSettingVo.setLllevel2(""+alarmSettingType);
                break;
            case 3:
                alarmSettingVo.setLllevel3(""+alarmSettingType);
                break;
            default:break;
        }


        //低低
        alarmSettingVo.setLmvalue(alarmSetting.getLowMiddleValue()==null?"":""+alarmSetting.getLowMiddleValue());
        alarmSettingType=alarmSetting.getLowMiddleType() == null? 0 :alarmSetting.getLowMiddleType();
        switch (alarmSettingType){
            case 1:
                alarmSettingVo.setLmlevel1(""+alarmSettingType);
                break;
            case 2:
                alarmSettingVo.setLmlevel2(""+alarmSettingType);
                break;
            case 3:
                alarmSettingVo.setLmlevel3(""+alarmSettingType);
                break;
            default:break;
        }

        //低
        alarmSettingVo.setLhvalue(alarmSetting.getLowHighValue()==null?"":""+alarmSetting.getLowHighValue());
        alarmSettingType=alarmSetting.getLowHighType() == null? 0 :alarmSetting.getLowHighType();
        switch (alarmSettingType){
            case 1:
                alarmSettingVo.setLhlevel1(""+alarmSettingType);
                break;
            case 2:
                alarmSettingVo.setLhlevel2(""+alarmSettingType);
                break;
            case 3:
                alarmSettingVo.setLhlevel3(""+alarmSettingType);
                break;
            default:break;
        }

        return alarmSettingVo;
	}

	//通过页面配置信息创建AlarmSetting
    public AlarmUserConfig alarmSettingCreate(AlarmUserConfig alarmSetting,String type,String paraType,HttpServletRequest request){
        alarmSetting.setMtype(paraType);
        alarmSetting.setAtype(type);
        String hhvalue=request.getParameter(type+"_hhvalue");
        if(hhvalue!=null){
            try{
                alarmSetting.setHighHighValue(Float.valueOf(hhvalue));
            }catch (Exception e){

            }

        }
        String hhlevel=request.getParameter(type+"_hhlevel");
        if(hhlevel!=null){
            try{
                alarmSetting.setHighHighType(Integer.valueOf(hhlevel));
            }catch (Exception e){

            }

        }

        String hmvalue=request.getParameter(type+"_hmvalue");
        if(hmvalue!=null){
            try{
                alarmSetting.setHighMiddleValue(Float.valueOf(hmvalue));
            }catch (Exception e){

            }

        }
        String hmlevel=request.getParameter(type+"_hmlevel");
        if(hmlevel!=null){
            try{
                alarmSetting.setHighMiddleType(Integer.valueOf(hmlevel));
            }catch (Exception e){

            }

        }

        String hlvalue=request.getParameter(type+"_hlvalue");
        if(hlvalue!=null){
            try{
                alarmSetting.setHighLowValue(Float.valueOf(hlvalue));
            }catch (Exception e){

            }

        }
        String hllevel=request.getParameter(type+"_hllevel");
        if(hllevel!=null){
            try{
                alarmSetting.setHighLowType(Integer.valueOf(hllevel));
            }catch (Exception e){

            }

        }

        String lhvalue=request.getParameter(type+"_lhvalue");
        if(lhvalue!=null){
            try{
                alarmSetting.setLowHighValue(Float.valueOf(lhvalue));
            }catch (Exception e){

            }

        }
        String lhlevel=request.getParameter(type+"_lhlevel");
        if(lhlevel!=null){
            try{
                alarmSetting.setLowHighType(Integer.valueOf(lhlevel));
            }catch (Exception e){

            }

        }

        String lmvalue=request.getParameter(type+"_lmvalue");
        if(lmvalue!=null){
            try{
                alarmSetting.setLowMiddleValue(Float.valueOf(lmvalue));
            }catch (Exception e){

            }

        }
        String lmlevel=request.getParameter(type+"_lmlevel");
        if(lmlevel!=null){
            try{
                alarmSetting.setLowMiddleType(Integer.valueOf(lmlevel));
            }catch (Exception e){

            }

        }

        String llvalue=request.getParameter(type+"_llvalue");
        if(llvalue!=null){
            try{
                alarmSetting.setLowLowValue(Float.valueOf(llvalue));
            }catch (Exception e){

            }

        }
        String lllevel=request.getParameter(type+"_lllevel");
        if(lllevel!=null){
            try{
                alarmSetting.setLowLowType(Integer.valueOf(lllevel));
            }catch (Exception e){

            }

        }

        return alarmSetting;
    }

    /**
     * 保存配置
     * @param alarmSettingCreate
     */
	public void saveConfig(AlarmUserConfig alarmSettingCreate) {
		if(alarmSettingCreate.getId() != null){
			alarmUserConfigMapper.updateByPrimaryKeySelective(alarmSettingCreate);
		} else{
			alarmUserConfigMapper.insertSelective(alarmSettingCreate);
		}
	}

	/**
	 * 分页条件查询报警记录
	 * @param startTime
	 * @param endTime
	 * @param nums
	 * @param alarmType
	 * @param startIndex
	 * @param pageSize
	 * @return
	 */
	public PageVo<AlarmProcessRecord> queryProcessRecordByCondition(Date startTime, Date endTime,
			List<Integer> nums, String alarmType, boolean isAdmin, int startIndex, int pageSize) {
		AlarmProcessRecordCriteria criteria = new AlarmProcessRecordCriteria();
		criteria.setStartTime(startTime);
		criteria.setEndTime(endTime);
		criteria.setNums(nums);
		criteria.setAlarmType(alarmType);

		if(!isAdmin){
			List<String> alarmContents = getAlarmContent();
			criteria.setAlarmContents(alarmContents);
		}
		
		Page page = criteria.getPage();
		page.setDefalutPageRows(pageSize);
		page.setCurrentPage(startIndex/pageSize + 1);
		criteria.setPage(page);
		
		List<AlarmProcessRecord> records = alarmProcessRecordMapper.findPageByCriteria(criteria);
		
		if(records == null){
			records = new ArrayList<>();
		}
		
		page = criteria.getPage();
		return new PageVo<>(page.getCurrentPage(), page.getTotalRecord(), records.size(), records);
	}

	/**
	 * 根据主键获取报警内容
	 * @param id
	 * @return
	 */
	public AlarmProcessRecord findRecordById(Integer id) {
		return alarmProcessRecordMapper.selectByPrimaryKey(id.longValue());
	}

	/**
	 * 保存报警修改
	 * @param alarmMessage
	 */
	public void saveRecord(AlarmProcessRecord alarmMessage) {
		alarmProcessRecordMapper.updateByPrimaryKeySelective(alarmMessage);
	}
	
	/**
	 * 根据水表号得到未处理的报警列表
	 * @param num
	 * @return
	 */
	public List<AlarmProcessRecord> isUntreatedAlarm(Integer num){
		List<Integer> nums = new ArrayList<>(1);
		nums.add(num);
		AlarmProcessRecordCriteria criteria = new AlarmProcessRecordCriteria();
		criteria.setNums(nums);
		criteria.setAlarmType("表端报警");
		criteria.setStatus("2");
        List<String> alarmContents = getAlarmContent();
		criteria.setAlarmContents(alarmContents);
		List<AlarmProcessRecord> result = alarmProcessRecordMapper.listByCriteria(criteria);
		if(result == null){
			result = new ArrayList<>();
		}
		return result;
    }

    private List<String> getAlarmContent() {
        List<String> alarmContents = new ArrayList<>();
        alarmContents.add("空管报警");
        alarmContents.add("励磁信号错误");
        alarmContents.add("电极信号错误");
        alarmContents.add("瞬时流量反向");
        alarmContents.add("正向累计流量溢出");
        alarmContents.add("反向累计流量溢出");
        alarmContents.add("工作电压过低");
        alarmContents.add("压力连接故障（断线报警）");
        alarmContents.add("压力下限报警");
        alarmContents.add("压力上限报警");
        return alarmContents;
    }

    /**
	 * 根据用户id获取所属水表的未处理报警列表
	 * @param userId
	 * @return Modify By MANZHIWEI
	 */
    public Map<String,Object> listUntreatedAlarm(Integer userId){
        List<MachineInfo> infos = meterService.findUserMeterList(userId);
        Map<String,Object> maps = new HashMap<>();
        List<AlarmProcessRecord> result = new ArrayList<>();
        Integer totalRecordAlarm = 0;
        List<Integer> nums = new ArrayList<>();
        if(infos != null && infos.size() > 0) {
            for (MachineInfo info : infos) {
                nums.add(info.getNum());
            }
            AlarmProcessRecordCriteria criteria = new AlarmProcessRecordCriteria();
            criteria.setNums(nums);
            criteria.setAlarmType("表端报警");
            criteria.setStatus("2");

            List<String> alarmContents = getAlarmContent();
            criteria.setAlarmContents(alarmContents);

            result = alarmProcessRecordMapper.listByCriteria(criteria);
            totalRecordAlarm = alarmProcessRecordMapper.queryTotalRecord(criteria);
        }

        maps.put("result",result);
        maps.put("size",totalRecordAlarm);

        return maps;
    }
/*public List<AlarmProcessRecord> listUntreatedAlarm(Integer userId){
		List<MachineInfo> infos = meterService.findUserMeterList(userId);
        Map<String,Object> maps = new HashMap<>();
		List<Integer> nums = new ArrayList<>();
		if(infos != null && infos.size() > 0){
			for(MachineInfo info : infos){
				nums.add(info.getNum());
			}
		} else{
			// 没有水表 直接返回空列表
			return new ArrayList<>();
		}
		AlarmProcessRecordCriteria criteria = new AlarmProcessRecordCriteria();
		criteria.setNums(nums);
		criteria.setAlarmType("表端报警");
		criteria.setStatus("2");
		List<String> alarmContents = new ArrayList<>();
		alarmContents.add("空管报警");
		alarmContents.add("压力报警-压力连接报警");
		alarmContents.add("压力报警-压力下限报警");
		alarmContents.add("压力报警-压力上限报警");
		criteria.setAlarmContents(alarmContents);
		List<AlarmProcessRecord> result = alarmProcessRecordMapper.listByCriteria(criteria);
		if(result == null){
			result = new ArrayList<>();
		}
		return result;
	}*/

	/**
	 * 保存报警删除
	 * @param alarmMessage
	 */
	public void deleteRecord(Long id) {
		if(id != null){
			alarmProcessRecordMapper.deleteByPrimaryKey(id);
		}
	}
	/*public  Integer queryTotalRecordAlarm(){

    }*/
}
