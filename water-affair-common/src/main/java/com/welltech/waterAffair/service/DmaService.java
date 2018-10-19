package com.welltech.waterAffair.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.welltech.waterAffair.common.util.ConstantsUtil;
import com.welltech.waterAffair.common.util.MeterUtils;
import com.welltech.waterAffair.domain.entity.Dma;
import com.welltech.waterAffair.domain.entity.DmaMeter;
import com.welltech.waterAffair.domain.entity.DmaOutMeter;
import com.welltech.waterAffair.domain.entity.MachineInfo;
import com.welltech.waterAffair.repository.DmaMapper;
import com.welltech.waterAffair.repository.DmaMeterMapper;
import com.welltech.waterAffair.repository.DmaOutMeterMapper;
import com.welltech.waterAffair.repository.GprsDataMapper;
import com.welltech.waterAffair.repository.NdataSsMapper;

@Service
public class DmaService {

	@Autowired
	private DmaMapper dmaMapper;
	
	@Autowired
	private DmaMeterMapper dmaMeterMapper;
	
	@Autowired
	private DmaOutMeterMapper dmaOutMeterMapper;
	
	@Autowired
	private NdataSsMapper ndataSsMapper;
	
	@Autowired
	private GprsDataMapper gprsDataMapper;

	/**
	 * 根据用户id查询dma区域
	 * @param userId
	 * @return
	 */
	public List<Dma> getDMAByUid(Integer userId) {
		List<Dma> dmas = dmaMapper.findByUserId(userId);
		if(dmas == null){
			dmas = new ArrayList<Dma>();
		}
		return dmas;
	}

	/**
	 * 得到所有dma区域
	 * @return
	 */
	public List<Dma> getAll() {
		List<Dma> dmas = dmaMapper.findAll();
		if(dmas == null){
			dmas = new ArrayList<Dma>();
		}
		return dmas;
	}

	/**
	 * 保存dma区域
	 * @param dma
	 */
	public void save(Dma dma) {
		dmaMapper.insertSelective(dma);
	}

	/**
	 * 根据id查找入水表
	 * @param dmaId
	 * @return
	 */
	public List<DmaMeter> findDmaMetersByDmaId(Integer dmaId) {
		List<DmaMeter> dmaMeters = dmaMeterMapper.findByDmaId(dmaId);
		if(dmaMeters == null){
			dmaMeters = new ArrayList<>();
		}
		return dmaMeters;
	}

	/**
	 * 根据id查找出水表
	 * @param dmaId
	 * @return
	 */
	public List<DmaOutMeter> findDmaOutMetersByDmaId(Integer dmaId) {
		List<DmaOutMeter> dmaOutMeters = dmaOutMeterMapper.findByDmaId(dmaId);
		if(dmaOutMeters == null){
			dmaOutMeters = new ArrayList<>();
		}
		return dmaOutMeters;
	}

	/**
	 * 重命名dma
	 * @param id
	 * @param newName
	 */
	public void renameDma(Integer id, String newName) {
		Dma dma = dmaMapper.selectByPrimaryKey(id);
		if(dma != null){
			dma.setName(newName);
			dmaMapper.updateByPrimaryKey(dma);
		}
	}

	/**
	 * 删除dma
	 * @param id
	 */
	public void deleteDma(Integer id) {
		dmaMapper.deleteByPrimaryKey(id);
	}

	/**
	 * 删除入水表
	 * @param dmaId
	 * @param meterId
	 */
	public void deleteDmaMeter(Integer dmaId, Integer meterId) {
		dmaMeterMapper.deleteByDmaIdAndMeterId(dmaId,meterId);
	}

	/**
	 * 保存入水表
	 * @param dmaMeter
	 */
	public void saveDmaMeter(DmaMeter dmaMeter) {
		dmaMeterMapper.insertSelective(dmaMeter);
	}

	/**
	 * 删除出水表
	 * @param dmaId
	 * @param meterId
	 */
	public void deleteDmaOutMeter(Integer dmaId, Integer meterId) {
		dmaOutMeterMapper.deleteByDmaIdAndMeterId(dmaId,meterId);
	}
	
	/**
	 * 保存出水表
	 * @param dmaOutMeter
	 */
	public void saveDmaOutMeter(DmaOutMeter dmaOutMeter) {
		dmaOutMeterMapper.insertSelective(dmaOutMeter);
	}

	/**
	 * dma小流量
	 * @param userId
	 * @param condition
	 * @param startTime
	 * @param endTime
	 * @param startHour
	 * @param endHour
	 * @return
	 */
	public String[][] querydmaMicroflow(Integer userId,List<MachineInfo> machines,String currentTimeStart,String currentTimeEnd,int darkStar,int darkEnd) {
		String[][] result=null;
		//初始化map，将当月初始化
		Map<String,Integer> dayes = getCurrentMonthLastDay(currentTimeStart,currentTimeEnd);
		result=new String[dayes.size()][machines.size()*7+1];
		for(int i=0;i<machines.size();i++){//因为数据太多只取10条，超过不取
			Integer num = machines.get(i).getNum();
			List<Map<String, Object>> resultMap = null;
			Map<String, Object> param = new HashMap<>();
			param.put("num", num);
			param.put("currentTimeStart", currentTimeStart);
			param.put("currentTimeEnd", currentTimeEnd);
			param.put("darkStar", darkStar);
			param.put("darkEnd", darkEnd);
			if(MeterUtils.isGprs4300(machines.get(i))){
				if(machines.get(i).getMeterTypeId() !=3)
					resultMap = gprsDataMapper.queryDmaMicor(param);
				else
					resultMap = null;//TODO 可能出错
			}else{
				resultMap = ndataSsMapper.queryDmaMicor(param);
			}
			
			List<Object[]> ndate = new ArrayList<>();
			if(resultMap != null){
				for(Map<String, Object> m : resultMap){
					Object[] objects = new Object[8];
					objects = m.values().toArray(objects);
					ndate.add(objects);
				}
			}
			
			formateArray(result, ndate,i,dayes);
		}
		return result;
	}
	
	/**
	 * 
	 * 这个方法的主要工作就是将查询出来的数据重新格式化，以时间为key，放入到map中
	 * 此方法只针对数据库返回值是10列的数据，其他列数据需要重写
	 * @param result	返回到页面的数据
	 * @param ndate		数据库查询出的结果集
	 * @param n			步长
	 * @param height	需要初始化的高度
	 */
	private void formateArray(String[][] result,List<Object[]> ndate,int n,Map<String,Integer> height){
		for(int i=0;i<height.size();i++){//初始化各自水表的相关数据
			for(int j=0;j<8;j++){
				if(j==0){
					continue;
				}
				result[i][n*7+j]="";
			}
		}
		for(int i=0;i<ndate.size();i++){
			Object[] vo=ndate.get(i);
			int index=height.get(vo[0]);
			for(int j=0;j<vo.length;j++){
				if(j>0){
					if(j == 2 || j == 4){
						try{
							result[index][n*7+j] = (vo[j]+"").substring(11,19);
						} catch(IndexOutOfBoundsException e){
							result[index][n*7+j]=ConstantsUtil.formateNumber(vo[j])+"";
						}
					} if(j ==7){
						result[index][n*7+j]=ConstantsUtil.formateNumber((double)vo[j]*100)+"%";
					} else{
						result[index][n*7+j]=ConstantsUtil.formateNumber(vo[j])+"";
					}
				}else{
					if(result[index][0]==null){
						result[index][0]=vo[j]+"";
					}
				}
			}
		}
	}
	
	/** 
	 * 取得当月天数 
	 * @throws ParseException 
	 * */  
	public static Map<String,Integer> getCurrentMonthLastDay(String startDate,String endDate)  
	{  
	    Calendar a = Calendar.getInstance();  
	    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
	    Date conditonSDate = null;
	    Date conditonEDate = null;
		try {
			conditonSDate = sdf.parse(startDate);
			conditonEDate = sdf.parse(endDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	    a.setTime(conditonSDate);
	    Map<String,Integer> res=new HashMap<String,Integer>();
	    int i=0;
	    while(a.getTime().compareTo(conditonEDate)<0||a.getTime().compareTo(conditonEDate)==0){
	    	String tmp=sdf.format(a.getTime());
	    	res.put(tmp, i);
	    	a.add(Calendar.DATE, 1);
	    	i++;
	    }
	    return res;  
	}
}
