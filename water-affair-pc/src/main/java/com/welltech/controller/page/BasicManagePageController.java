package com.welltech.controller.page;


import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.welltech.waterAffair.domain.vo.NdataVo;
import org.apache.log4j.Logger;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.welltech.waterAffair.common.constant.Constants;
import com.welltech.waterAffair.common.util.ConstantsUtil;
import com.welltech.waterAffair.common.util.UserUtils;
import com.welltech.waterAffair.domain.dto.ArchivesRecord;
import com.welltech.waterAffair.domain.dto.ResourceImgDTO;
import com.welltech.waterAffair.domain.entity.MachineInfo;
import com.welltech.waterAffair.domain.entity.MeterType;
import com.welltech.waterAffair.domain.vo.MeterDetailVo;
import com.welltech.waterAffair.service.BasicManageService;

/**
 * Created by lenovo on 2017/2/21.
 */
@Controller
public class BasicManagePageController {

    private static Logger logger = Logger
            .getLogger(BasicManagePageController.class);

	@Resource
	private BasicManageService basicManageService;
	@Resource
    private ConstantsUtil constantsUtil;

    //仪表更换记录
    /*@RequestMapping(value = { "/meterChangeRecord" }, method = RequestMethod.GET)
    public String meterChangeRecord(HttpServletRequest request, Model model) {
        //Integer userId=p2PSessionContext.getCurrentAid(request);
        //model.addAttribute("stations",meterChangeRecordService.queryStationByUserId(userId));
        return "basic/meterChangeRecord";
    }*/

    //新增仪表
    @RequestMapping(value = { "/addNewMeter" }, method = RequestMethod.POST)
    public String addNewMeter() {
        return "basic/meterBasicList";
    }


    /**
    *	新增仪表
    * @param machineInfo	水表信息
    * @param installImg
    *            安装图
    * @param runImg
    *            走向图
    * @param request
    * @param response
    * @return
    */
   @RequestMapping(value = { "/saveMeterInfo" }, method = RequestMethod.POST)
   public String saveMeterInfo(@ModelAttribute MachineInfo machineInfo,String activeTimeStr,
                               @RequestParam MultipartFile installImg,
                               @RequestParam MultipartFile runImg, HttpServletRequest request,
                               HttpServletResponse response, Model model) {
       Integer userId=UserUtils.getUserId();

       MachineInfo res = basicManageService.queryMachineInfo2(machineInfo
               .getNum());
       if (res == null) {
    	   ResourceImgDTO install = null;
    	   ResourceImgDTO run = null;
           try {
        	   if(activeTimeStr!=null&&!"".equals(activeTimeStr)){//安装时间
         		  SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
         		  Date activeTime=sdf.parse(activeTimeStr);
         		  machineInfo.setActiveTime(activeTime);
         	  }
               if(installImg!=null&&installImg.getSize()>0){
                   install = constantsUtil.formateFile(request,installImg, Constants.RESOURCE_IMG_INSTALL,machineInfo.getNum());
               }
               if(runImg!=null&&runImg.getSize()>0){
                   run = constantsUtil.formateFile(request,runImg,Constants.RESOURCE_IMG_RUN,machineInfo.getNum());
               }
               List<ResourceImgDTO> list = new ArrayList<ResourceImgDTO>();
               list.add(install);
               list.add(run);

               basicManageService.saveMachineInfo(machineInfo, list, userId);
           } catch (Exception e) {
               logger.error("saveMeterInfo:",e);
           }
       }
       model.addAttribute("meterSizes", ConstantsUtil.sbkjDic);
       return "basic/meterBasicList";
   }
   
   /**
   *
   * @param machineInfo
   * @param installImg
   *            安装图
   * @param runImg
   *            走向图
   * @param request
   * @param response
   * @return
   */
  @RequestMapping(value = { "/updateMeterInfo" }, method = RequestMethod.POST)
  public String updateMeterInfo(@ModelAttribute MachineInfo machineInfo,String activeTimeStr,
                              @RequestParam MultipartFile installImg,
                              @RequestParam MultipartFile runImg, HttpServletRequest request,
                              HttpServletResponse response, Model model) {
      Integer userId=UserUtils.getUserId();

      MachineInfo res = basicManageService.queryMachineInfo2(machineInfo
              .getNum());
      if (res != null) {
          ResourceImgDTO install = null;
          ResourceImgDTO run = null;
          try {
        	  if(activeTimeStr!=null&&!"".equals(activeTimeStr)){//安装时间
        		  SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        		  Date activeTime=sdf.parse(activeTimeStr);
        		  machineInfo.setActiveTime(activeTime);
        	  }
              if(installImg!=null&&installImg.getSize()>0){
                  install = constantsUtil.formateFile(request,installImg, Constants.RESOURCE_IMG_INSTALL,machineInfo.getNum());
              }
              if(runImg!=null&&runImg.getSize()>0){
                  run = constantsUtil.formateFile(request,runImg,Constants.RESOURCE_IMG_RUN,machineInfo.getNum());
              }
              List<ResourceImgDTO> list = new ArrayList<ResourceImgDTO>();
              list.add(install);
              list.add(run);
              setFrom(res,machineInfo);
              basicManageService.upateMachineInfo(res, list, userId);
          } catch (Exception e) {
              logger.error("saveMeterInfo:",e);
          }
      }
      return meterListDetail(machineInfo.getNum(), model);
  }

  private void setFrom(MachineInfo dest,MachineInfo orig){
	   dest.setAddress(orig.getAddress());
	   if(orig.getMeterTypeId()!=null){
		   dest.setMetertype(orig.getMetertype());
		   dest.setMeterTypeId(orig.getMeterTypeId());
	   }
	   dest.setPositionNo(orig.getPositionNo());
	   dest.setSubUserName(orig.getSubUserName());
	   dest.setCcid(orig.getCcid());
	   dest.setMeterSize(orig.getMeterSize());

	   if(orig.getPowerTypeId()!=null){
		   dest.setPowerType(orig.getPowerType());
		   dest.setPowerTypeId(orig.getPowerTypeId());
	   }
	   dest.setLinkPeople(orig.getLinkPeople());
	   dest.setLinkWay(orig.getLinkWay());
	   dest.setLinkAddress(orig.getLinkAddress());
	   if(orig.getOutputSignalTypeId()!=null&&!"".equals(orig.getOutputSignalTypeId())){
		   dest.setOutputSignalTypeId(orig.getOutputSignalTypeId());
		   dest.setOutputSignalType(orig.getOutputSignalType());
	   }
	   dest.setMeterNum(orig.getMeterNum());
	   dest.setMeterManufacturer(orig.getMeterManufacturer());
	   dest.setWellNumber(orig.getWellNumber());
	   dest.setLongitude(orig.getLongitude());
	   dest.setLatitude(orig.getLatitude());
	   dest.setActiveTime(orig.getActiveTime());
	   dest.setShortName(orig.getShortName());
  }
  
  
    @RequestMapping(value = { "/download/{id}" }, method = RequestMethod.GET)
    public ResponseEntity<InputStreamResource> download(@PathVariable Integer id, HttpServletRequest request) throws IOException {
        ResourceImgDTO img=basicManageService.queryFileById(id);
        String filePath=ConstantsUtil.getRealPath(request)+img.getUri()+ File.separator+img.getFileName();
        FileSystemResource file = new FileSystemResource(filePath);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getFilename()));
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        return ResponseEntity
                .ok()
                .headers(headers)
                .contentLength(file.contentLength())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new InputStreamResource(file.getInputStream()));
    }

    /**
     * 查看仪表基础信息详情
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = { "/meterListDetail" }, method = RequestMethod.POST)
    public String meterListDetail(Integer num, Model model) {
        model.addAttribute("basic","active");
        model.addAttribute("meterBasicList","active");
        if (num != null) {
        	MeterDetailVo vo = basicManageService.queryMachineInfoDetail(num);
        	List<MeterType> meterTypeList=basicManageService.queryMeterType();
            model.addAttribute("data", vo);
            model.addAttribute("meterSizes", ConstantsUtil.sbkjDic);
            model.addAttribute("current_mid", num);
            model.addAttribute("meterTypeList", meterTypeList);
            model.addAttribute("test", "http://webapi.amap.com/maps?v=1.3&key=adaee8723d6ba8783b8b1d4d6f6d1397");
        }
        return "basic/meterListDetail";
    }
    //基础－仪表实时信息列表
    @RequestMapping(value = { "/todayMeterInfoList" }, method = RequestMethod.GET)
    public String meterDevicesMonitoring(HttpServletRequest request,Model model) {

        model.addAttribute("basic","active");
        model.addAttribute("todayMeterInfoList","active");
       // model.addAttribute("meterDevicesMonitoring", result);
        return "basic/todayMeterInfoList";
    }

    /**
     * 保存检查信息
     * @param record
     * @param checkImg
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = { "/saveArchivesRecord" }, method = RequestMethod.POST)
    public String saveArchivesRecord(ArchivesRecord record, @RequestParam MultipartFile checkImg, Model model, HttpServletRequest request) {
        Integer userId=UserUtils.getUserId();
        try {
        	basicManageService.saveOrUpdateArchivesRecord(record,checkImg, request, userId);
        } catch (Exception e) {
            logger.error("saveArchivesRecord:",e);
        }
        return meterListDetail(record.getNum(),model);
    }

    //修改仪表的IP和Port
    @RequestMapping(value = { "/changeIPAndPort" }, method = RequestMethod.GET)
    public String changeIPAndPort(HttpServletRequest request, Model model) {
        Integer userId= UserUtils.getUserId();

        model.addAttribute("tools","active");
        model.addAttribute("changeIPAndPort","active");

        return "tools/changeIPAndPort";
    }
}
