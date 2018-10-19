package com.welltech.controller.rest;

import java.util.ArrayList;
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
import com.welltech.waterAffair.domain.entity.Dma;
import com.welltech.waterAffair.domain.entity.DmaMeter;
import com.welltech.waterAffair.domain.entity.DmaOutMeter;
import com.welltech.waterAffair.domain.entity.MachineInfo;
import com.welltech.waterAffair.domain.entity.User;
import com.welltech.waterAffair.domain.vo.DMAVo4JS;
import com.welltech.waterAffair.domain.vo.MeterInfoVo4DMA;
import com.welltech.waterAffair.service.CompanyService;
import com.welltech.waterAffair.service.DmaService;
import com.welltech.waterAffair.service.MeterService;

import net.sf.json.JSONArray;

/**
 * 
 * @author WangXin
 *
 */
@RestController
public class DmaRestController {

	@Autowired
	private CompanyService companyService;
	@Autowired
	private DmaService dmaService;
	@Autowired
	private MeterService meterService;
	
    @RequestMapping(value = { "/getDmaList" }, method = RequestMethod.GET)
    public String getDmaList() {
        Integer userId = UserUtils.getUserId();
        List<DMAVo4JS> dmaList=new ArrayList<DMAVo4JS>();
        User user = companyService.getUser(userId);
        
        List<Dma> dmaListRaw = null;
        if(UserUtils.isAdmin(user)){
            dmaListRaw = dmaService.getAll();
        } else{
        	dmaListRaw = dmaService.getDMAByUid(userId);
        }

        for(Dma dma:dmaListRaw){
            DMAVo4JS dmaVo4JS=new DMAVo4JS();
            dmaVo4JS.setId(""+dma.getId());
            dmaVo4JS.setText(dma.getName());
            dmaList.add(dmaVo4JS);
        }
        return "[{\"children\":"+JSONArray.fromObject(dmaList).toString()+",\"text\":\"DMA列表\",\"state\": {\"opened\": true}}]";

    }
    
    //刷新入水表区域的数据刷新
    @RequestMapping(value = { "/changeDmaArea" }, method = RequestMethod.POST)
    public String changeDmaArea(@RequestParam String id) {
        Map<Integer,Integer> meterMap=new HashMap<Integer,Integer>();
        Integer dmaId = Integer.valueOf(id);
        for(DmaMeter dmaMeter:dmaService.findDmaMetersByDmaId(dmaId)){
            meterMap.put(dmaMeter.getMeterId(),dmaMeter.getId());
        }
        //出水表
        Map<Integer,Integer> outMeterMap=new HashMap<Integer,Integer>();
        for(DmaOutMeter dmaOutMeter:dmaService.findDmaOutMetersByDmaId(Integer.valueOf(id))){
        	outMeterMap.put(dmaOutMeter.getMeterId(),dmaOutMeter.getId());
        }
        
        Integer userId = UserUtils.getUserId();
        //查询某个用户下面的所有水表
        List<MeterInfoVo4DMA> machineInfoVo4JSList=new ArrayList<MeterInfoVo4DMA>();
        for(MachineInfo meter: meterService.findUserMeterList(userId)){
            if(meter.getShortName().length()>0){
                MeterInfoVo4DMA machineInfoVo4JS=new MeterInfoVo4DMA(meterMap.containsKey(meter.getNum())?meterMap.get(meter.getNum()):-1,meter.getShortName(),meter.getNum(),meterMap.containsKey(meter.getNum())?true:false, outMeterMap.containsKey(meter.getNum()));
                machineInfoVo4JSList.add(machineInfoVo4JS);
            }
        }

        return JSONArray.fromObject(machineInfoVo4JSList).toString();

    }
    
    //重命名DMA
    @RequestMapping(value = { "/renameDMA4Tree" }, method = RequestMethod.POST)
    public String renameDMA4Tree(HttpServletRequest request) {
        String id=request.getParameter("id");
        String newName=request.getParameter("name");
        dmaService.renameDma(Integer.parseInt(id),newName);

        return "{\"result\":true}";
    }

    //删除DMA
    @RequestMapping(value = { "/removeDMA4Tree" }, method = RequestMethod.POST)
    public String removeDMA4Tree(HttpServletRequest request) {
        String id=request.getParameter("id");
        dmaService.deleteDma(Integer.valueOf(id));
        return "{\"result\":true}";
    }
    
    @RequestMapping(value = { "/removeInMeter4DMA" }, method = RequestMethod.POST)
    public String removeInMeter4DMA(HttpServletRequest request) {
        String did=request.getParameter("did");
        String mids=request.getParameter("mids");
        for(String id:mids.split(",",-1)){
            //获取水表id,修改wt_dma_meter关系
             if(id.length()>0){
            	 dmaService.deleteDmaMeter(Integer.valueOf(did),Integer.valueOf(id));
             }
        }
        return "true";
    }

    @RequestMapping(value = { "/addInMeter4DMA" }, method = RequestMethod.POST)
    public String addInMeter4DMA(HttpServletRequest request) {
        String did=request.getParameter("did");
        String mids=request.getParameter("mids");
        for(String id:mids.split(",",-1)){
            //获取水表id,修改wt_dma_meter关系
            if(id.length()>0){
                DmaMeter dmaMeter=new DmaMeter();
                dmaMeter.setDmaId(Integer.valueOf(did));
                dmaMeter.setMeterId(Integer.valueOf(id));
                dmaService.saveDmaMeter(dmaMeter);
            }
        }
        return "true";
    }
    
    @RequestMapping(value = { "/removeOutMeter4DMA" }, method = RequestMethod.POST)
    public String removeOutMeter4DMA(HttpServletRequest request) {
        String did=request.getParameter("did");
        String mids=request.getParameter("mids");
        for(String id:mids.split(",",-1)){
            //获取水表id,修改wt_dma_meter关系
             if(id.length()>0){
            	 dmaService.deleteDmaOutMeter(Integer.valueOf(did),Integer.valueOf(id));
             }
        }

        return "true";
    }

    @RequestMapping(value = { "/addOutMeter4DMA" }, method = RequestMethod.POST)
    public String addOutMeter4DMA(HttpServletRequest request) {
        String did=request.getParameter("did");
        String mids=request.getParameter("mids");
        for(String id:mids.split(",",-1)){
            //获取水表id,修改wt_dma_meter关系
            if(id.length()>0){
                DmaOutMeter dmaOutMeter=new DmaOutMeter();
                dmaOutMeter.setDmaId(Integer.valueOf(did));
                dmaOutMeter.setMeterId(Integer.valueOf(id));
                dmaService.saveDmaOutMeter(dmaOutMeter);
            }
        }
        return "true";
    }
	
}
