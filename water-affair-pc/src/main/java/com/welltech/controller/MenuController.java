package com.welltech.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.welltech.waterAffair.repository.UserMapper;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.welltech.waterAffair.common.util.ConstantsUtil;
import com.welltech.waterAffair.common.util.UserUtils;
import com.welltech.waterAffair.domain.entity.AlarmUserConfig;
import com.welltech.waterAffair.domain.entity.Dma;
import com.welltech.waterAffair.domain.entity.DmaMeter;
import com.welltech.waterAffair.domain.entity.DmaOutMeter;
import com.welltech.waterAffair.domain.entity.MachineInfo;
import com.welltech.waterAffair.domain.entity.MeterType;
import com.welltech.waterAffair.domain.entity.User;
import com.welltech.waterAffair.domain.vo.DmaMicroFlowVo;
import com.welltech.waterAffair.domain.vo.MachineInfoVo4AlarmSetting;
import com.welltech.waterAffair.domain.vo.NdataVo;
import com.welltech.waterAffair.domain.vo.StatisticsVo;
import com.welltech.waterAffair.domain.vo.Tab4HourStaticsVo;
import com.welltech.waterAffair.domain.vo.before.MeterList4DmaMicroFlowVo;
import com.welltech.waterAffair.service.AlarmService;
import com.welltech.waterAffair.service.BasicManageService;
import com.welltech.waterAffair.service.CompanyService;
import com.welltech.waterAffair.service.DmaService;
import com.welltech.waterAffair.service.MeterService;
import com.welltech.waterAffair.service.ReportService;
import com.welltech.waterAffair.service.StaticsService;

@Controller
public class MenuController {

    private static Logger logger = Logger
            .getLogger(MenuController.class);
    @Resource
    private ReportService reportService;
	@Resource
	private BasicManageService basicManageService;
	@Resource
	private MeterService meterService;
    @Resource
    private CompanyService companyService;
    @Resource
    private StaticsService staticsService;
    @Resource
    private DmaService dmaService;
    @Resource
    private AlarmService alarmService;

    @Resource
    private UserMapper userMapper;
    
    //基础－仪表基础信息列表
    @RequestMapping(value = { "/meterBasicList" }, method = RequestMethod.GET)
    public String meterList(HttpServletRequest request,Model model) {
    	List<MeterType> meterTypeList=basicManageService.queryMeterType();
        model.addAttribute("basic","active");
        model.addAttribute("meterTypeList", meterTypeList);
        model.addAttribute("meterSizes", ConstantsUtil.sbkjDic);
        model.addAttribute("meterBasicList","active");
        return "basic/meterBasicList";
    }

    /**
     * ADD BY MANZHIWEI
     * @param request
     * @param model
     * @return
     */
    //系统信息
    @RequestMapping(value = { "/machineInfoList" }, method = RequestMethod.GET)
    public String machineInfoList(HttpServletRequest request,Model model){
        Integer userId=UserUtils.getUserId();
        model.addAttribute("basic","active");
        model.addAttribute("machineInfoList","active");
        return "basic/machineInfoList";
    }
    //设备信息
    @RequestMapping(value = { "/meterInfoList" }, method = RequestMethod.GET)
    public String meterDevicesMonitoring(HttpServletRequest request,Model model) {
    	Integer userId=UserUtils.getUserId();
		//查询水表的监控信息
		List<NdataVo> result=basicManageService.queryNdataLastData(userId);
		/*for(NdataVo m:result){
			String tmp=basicManageService.queryMachineInfoForCompany(m.getNum(),userId);
			m.setCompanyName(tmp);
    }*/
        model.addAttribute("basic","active");
        model.addAttribute("meterInfoList","active");
		model.addAttribute("meterDevicesMonitoring", result);
        return "basic/meterInfoList";
    }


    //统计－实时数据分析
    @RequestMapping(value = { "/realtimeStatistics" }, method = {RequestMethod.GET,RequestMethod.POST})
    public String realtimeStatistics(HttpServletRequest request,Model model) {
        model.addAttribute("statics","active");
        model.addAttribute("realtimeStatistics","active");
        Integer userId=UserUtils.getUserId();
        String name=request.getParameter("typeName");
        String[] stationList= request.getParameterValues("staions");
        String column="flow";//默认flow
        if(name!=null&&name.equals("压力")){
            model.addAttribute("press_select","selected");
            column="press";
        }else{
            model.addAttribute("flow_select","selected"); 
            column="flow";
        }

        //List<DmaMicroFlowVo> dmaList=new ArrayList<DmaMicroFlowVo>();
        //处理选中仪表清单
        List<MeterList4DmaMicroFlowVo> allMeterList=new ArrayList<MeterList4DmaMicroFlowVo>();
        Map<String,String> selectedMeterList=new HashMap<String,String>();
        try{
            for(String station:stationList){
                selectedMeterList.put(station,station);
            }
        }catch (Exception e){

        }
        List<Integer> station=new ArrayList<Integer>();
        //清空选中仪表的ID列表
        List<MachineInfo> machines = meterService.findUserMeterList(userId);
        for(MachineInfo meter:machines){
            MeterList4DmaMicroFlowVo meterList4DmaMicroFlowVo = new MeterList4DmaMicroFlowVo();
            meterList4DmaMicroFlowVo.setName(meter.getShortName());
            if (selectedMeterList.get(meter.getShortName()) != null) {
                meterList4DmaMicroFlowVo.setSelect("selected");
                station.add(meter.getNum());
            }
            allMeterList.add(meterList4DmaMicroFlowVo);
        }

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Calendar c=Calendar.getInstance();
        String start=sdf.format(c.getTime());
        c.add(Calendar.DATE, 1);
        String end=sdf.format(c.getTime());

        Map<String,List<Object[]>> result = staticsService.queryRealtimeStatistics(column, station, start, end);

        model.addAttribute("result",result);
        model.addAttribute("stationList",allMeterList);

        model.addAttribute("stations",meterService.getStationList(userId));
        return "statics/realtimeStatistics";
    }

    //统计－单点数据分析
    @RequestMapping(value = { "/oneMeterStatistic" }, method = {RequestMethod.GET,RequestMethod.POST})
    public String oneMeterStatistic(HttpServletRequest request,Model model) {
        model.addAttribute("statics","active");
        model.addAttribute("oneMeterStatistic","active");
        Integer userId = UserUtils.getUserId();
        //String dmaMame=request.getParameter("dma-name"); //dma Id
        String did=request.getParameter("did"); //dma Id
        //处理选中仪表清单

        String num="";

        List<DmaMicroFlowVo> dmaList=new ArrayList<DmaMicroFlowVo>();
        List<MachineInfo> machineInfo2List = meterService.findUserMeterList(userId);
        
        //默认页面请求选中第一个区域
        if(did==null){
            if(machineInfo2List.size()>0){
                did=""+machineInfo2List.get(0).getNum();
                num=machineInfo2List.get(0).getNum()+"";
            }
        }
        for(MachineInfo machineInfo2:machineInfo2List){
            DmaMicroFlowVo dmaMicroFlowVo=new DmaMicroFlowVo();
            dmaMicroFlowVo.setName(machineInfo2.getShortName());
            dmaMicroFlowVo.setValue("/oneMeterStatistic?did="+machineInfo2.getNum());
            if(machineInfo2.getNum().toString().equals(did)){
                dmaMicroFlowVo.setSelect("selected");
                num=did;
            }
            dmaList.add(dmaMicroFlowVo);
        }

        String column="flow";//默认flow
        StatisticsVo  s = staticsService.queryddsj(column, num);
        model.addAttribute("ddsj",s);
        model.addAttribute("dmaList",dmaList);
        model.addAttribute("current_mid",did);
        return "statics/oneMeterStatistic";
    }

    //统计－时用水统计
    @RequestMapping(value = { "/dataStatisticsByHour" }, method = {RequestMethod.GET,RequestMethod.POST})
    public String dataStatisticsByHour(HttpServletRequest request, Model model) {
        model.addAttribute("statics","active");
        model.addAttribute("dataStatisticsByHour","active");
        Integer userId = UserUtils.getUserId();
        List<Tab4HourStaticsVo> meterIdList=new ArrayList<Tab4HourStaticsVo>();
        for(MachineInfo meter:meterService.findUserMeterList(userId)){
            Tab4HourStaticsVo tab4HourStaticsVo=new Tab4HourStaticsVo();
            tab4HourStaticsVo.setMid(""+meter.getNum());
            tab4HourStaticsVo.setName(meter.getShortName());
            tab4HourStaticsVo.setTabId("tab-"+meter.getNum());
            tab4HourStaticsVo.setTableId("meterChangeList"+meter.getNum());
            meterIdList.add(tab4HourStaticsVo);
        }
        model.addAttribute("tabs",meterIdList);

        //默认选3条
        List<String> stationList = meterService.getStationList(userId);
        List<MeterList4DmaMicroFlowVo> stations = new ArrayList<MeterList4DmaMicroFlowVo>();
        if(stationList != null && stationList.size()>0){
            for(int i =0 ; i < stationList.size(); i++){
                MeterList4DmaMicroFlowVo flowVo = new MeterList4DmaMicroFlowVo();
                flowVo.setName(stationList.get(i));
                /*if(i <= 2){
                    flowVo.setSelect("selected");
                }*/
                stations.add(flowVo);
            }
        }
        model.addAttribute("stations", stations);

        return "statics/dataStatisticsByHour";
    }

    //从仪表实时信息跳转到时用水信息
    @RequestMapping(value = "/dataStatisticsHourByIdAndName")
    public String dataStatisticsByHour2(HttpServletRequest request, Model model){
        model.addAttribute("statics","active");
        model.addAttribute("dataStatisticsByHour","active");

        //1.判断当前用户的登录信息
        Integer userId = UserUtils.getUserId();
        //2.若用户登录，则进行查找，否则，返回登录首页
        //3.根据name 进行查找，此处暂时不进行直接查找
        //3.是根据name 查找，还是根据ID 查找  根据id 查找 有索引，快 根据name 无 索引 慢
        // Integer id   = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");

        //默认选3条
        List<String> stationList = meterService.getStationList(userId);
        List<MeterList4DmaMicroFlowVo> stations = new ArrayList<MeterList4DmaMicroFlowVo>();
        if(stationList != null && stationList.size()>0){
            for(int i =0 ; i < stationList.size(); i++){
                MeterList4DmaMicroFlowVo flowVo = new MeterList4DmaMicroFlowVo();
                flowVo.setName(stationList.get(i));
                if(!name.equals("")&&!stationList.get(i).equals("")&&name.equals(stationList.get(i))){
                    flowVo.setSelect("selected");
                }
                /*if(i <= 2){
                    flowVo.setSelect("selected");
                }*/
                stations.add(flowVo);
            }
        }
        model.addAttribute("stations", stations);

        return "statics/dataStatisticsByHour";
    }

    //统计－趋势图
    @RequestMapping(value = { "/trendChart" }, method = RequestMethod.GET)
    public String trendChart(HttpServletRequest request,Model model) throws ParseException {
        model.addAttribute("statics","active");
        model.addAttribute("trendChart","active");
        //检测默认是否带时间过来
        /*
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            long timeStart=sdf.parse("2017/01/12 12:30:45").getTime();
        */
        Integer userId = UserUtils.getUserId();
        //默认选3条
        List<String> stationList = meterService.getStationList(userId);
        List<MeterList4DmaMicroFlowVo> stations = new ArrayList<MeterList4DmaMicroFlowVo>();
        if(stationList != null && stationList.size()>0){
            for(int i =0 ; i < stationList.size(); i++){
                MeterList4DmaMicroFlowVo flowVo = new MeterList4DmaMicroFlowVo();
                flowVo.setName(stationList.get(i));
//                if(i <= 2){
//                    flowVo.setSelect("selected");
//                }
                stations.add(flowVo);
            }
        }
        model.addAttribute("stations", stations);
        return "statics/trendChart";
    }

    //DMA－基础管理
    @RequestMapping(value = { "/dmaManage" }, method = RequestMethod.GET)
    public String dmaManage(HttpServletRequest request,Model model) {
        model.addAttribute("dma","active");
        model.addAttribute("dmaManage","active");
        return "dma/dmaManage";
    }

    //DMA-区域监控
    @RequestMapping(value = { "/dmaMapMonitor" }, method = RequestMethod.GET)
    public String dmaMapMonitor(HttpServletRequest request, Model model) {
        model.addAttribute("dma","active");
        model.addAttribute("dmaMapMonitor","active");
        //查询用户所有分区
        Integer userId = UserUtils.getUserId();
        
        List<Dma> userDmaList = dmaService.getDMAByUid(userId);
        if(companyService.isAdmin(userId)){
        	userDmaList = dmaService.getAll();
        }
        
        List<Map<String,Object>> monitorDatas = new ArrayList<Map<String,Object>>();
        for(Dma dma : userDmaList){
            Float inData = 0f;
            try{
                for(DmaMeter dmaMeter:dmaService.findDmaMetersByDmaId(Integer.valueOf(dma.getId()))){
                    MachineInfo meter = meterService.getMachineInfoById(dmaMeter.getMeterId());
                    NdataVo ndataVo = staticsService.queryNdataLastData(meter.getNum());
                    inData += ndataVo.getFlow()== null ? 0 : ndataVo.getFlow();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            Float outData = 0f;
            try{
                for(DmaOutMeter dmaMeter:dmaService.findDmaOutMetersByDmaId(Integer.valueOf(dma.getId()))){
                    MachineInfo meter = meterService.getMachineInfoById(dmaMeter.getMeterId());
                    NdataVo ndataVo = staticsService.queryNdataLastData(meter.getNum());
                    outData += ndataVo.getFlow()== null ? 0 : ndataVo.getFlow();
                }
            }catch (Exception e){
                e.printStackTrace();
            }

            Float rate = 0f;
            if(outData != 0f){
                rate = inData/outData;
            }

            Map<String,Object> monitorData = new HashMap<String,Object>();
            monitorData.put("inData",inData);
            monitorData.put("outData",outData);
            monitorData.put("rate",rate);
            monitorDatas.add(monitorData);
        }

        model.addAttribute("monitorDatas",monitorDatas);

        return "dma/dmaMapMonitor";
    }

    //DMA－小流量监控
    @RequestMapping(value = { "/dmaMicroFlow" }, method = {RequestMethod.POST,RequestMethod.GET})
    public String dmaMicroFlow(HttpServletRequest request, Model model) {
        model.addAttribute("dma","active");
        model.addAttribute("dmaMicroFlow","active");
        Integer userId = UserUtils.getUserId();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        String did=request.getParameter("did"); //dma Id
        String meterIdList=request.getParameter("meterIdList"); //dma Id
        String dmaMame=request.getParameter("dma-name"); //dma Id
        String startTime=request.getParameter("startTime"); //查询起始时间
        String endTime=request.getParameter("endTime");     //查询结束时间
        String[] stationList= request.getParameterValues("staions");  //查询水表列表
        String nightRange= request.getParameter("night_range");
        int startHour=0;
        int endHour=3;
        if(nightRange==null){
            model.addAttribute("nightRange", "夜间时段(00:00-03:00)");
        }else{
            model.addAttribute("nightRange", request.getParameter("night_range"));
            //解析出夜间时间段
            startHour=Integer.valueOf(nightRange.substring(5,7));
            endHour=Integer.valueOf(nightRange.substring(11,13));

            logger.debug(startHour+"--------"+endHour);
        }

        //dma菜单下拉请求
        if(dmaMame!=null&&did==null){
            //通过url解析出did
            did=dmaMame.substring(dmaMame.indexOf("=")+1,dmaMame.length());
        }

        //返回页面DMA列表
        List<DmaMicroFlowVo> dmaList=new ArrayList<DmaMicroFlowVo>();
        User user = companyService.getUser(userId);
        List<Dma> userDmaList = dmaService.getDMAByUid(userId);
        if(UserUtils.isAdmin(user)){
            userDmaList=dmaService.getAll();
        }
        //默认页面请求选中第一个区域
        if(did==null){
            if(userDmaList.size()>0){
                did=""+userDmaList.get(0).getId();
            }
        }

        for(Dma dma:userDmaList){
            DmaMicroFlowVo dmaMicroFlowVo=new DmaMicroFlowVo();
            dmaMicroFlowVo.setName(dma.getName());
            dmaMicroFlowVo.setValue("/dmaMicroFlow?did="+dma.getId());
            if(dma.getId().toString().equals(did)){
                dmaMicroFlowVo.setSelect("selected");
            }
            dmaList.add(dmaMicroFlowVo);
        }
        model.addAttribute("dmaList",dmaList);

        //起止时间设置
        String startTime4return=formatter.format(new Date())+" 00:00";
        try{
            if(!startTime.equals("")){
                startTime4return=startTime;
            }
        }catch (Exception e){

        }
        String endTime4return=formatter.format(new Date())+" 24:00";
        try{
            if(!endTime.equals("")){
                endTime4return=endTime;
            }
        }catch (Exception e){

        }

        model.addAttribute("startTime",startTime4return);
        model.addAttribute("endTime",endTime4return);

        //处理选中仪表清单
        List<MeterList4DmaMicroFlowVo> allMeterList=new ArrayList<MeterList4DmaMicroFlowVo>();
        Map<String,String> selectedMeterList=new HashMap<String,String>();
        List<MachineInfo> condition=new ArrayList<MachineInfo>();
        try{
            for(String station:stationList){
                selectedMeterList.put(station,station);
            }
        }catch (Exception e){

        }

        List<MachineInfo> machines = new ArrayList<MachineInfo>();
        //清空选中仪表的ID列表
        meterIdList="";
        try{
            for(DmaMeter dmaMeter:dmaService.findDmaMetersByDmaId(Integer.valueOf(did))){
                MachineInfo meter = meterService.getMachineInfoById(dmaMeter.getMeterId());
                machines.add(meter);
                MeterList4DmaMicroFlowVo meterList4DmaMicroFlowVo = new MeterList4DmaMicroFlowVo();
                meterList4DmaMicroFlowVo.setName(meter.getShortName());
                if (selectedMeterList.get(meter.getShortName()) != null) {
                    meterList4DmaMicroFlowVo.setSelect("selected");
                    //记录每个仪表的Id
                    meterIdList=meterIdList+","+dmaMeter.getMeterId();
                    condition.add(meter);
                }
                allMeterList.add(meterList4DmaMicroFlowVo);
            }
            for(DmaOutMeter dmaMeter:dmaService.findDmaOutMetersByDmaId(Integer.valueOf(did))){
                MachineInfo meter = meterService.getMachineInfoById(dmaMeter.getMeterId());
                machines.add(meter);
                MeterList4DmaMicroFlowVo meterList4DmaMicroFlowVo = new MeterList4DmaMicroFlowVo();
                meterList4DmaMicroFlowVo.setName(meter.getShortName());
                if (selectedMeterList.get(meter.getShortName()) != null) {
                    meterList4DmaMicroFlowVo.setSelect("selected");
                    //记录每个仪表的Id
                    meterIdList=meterIdList+","+dmaMeter.getMeterId();
                    condition.add(meter);
                }
                allMeterList.add(meterList4DmaMicroFlowVo);
            }
            model.addAttribute("stationList",allMeterList);
        }catch (Exception e){

        }



        model.addAttribute("meterIdList",meterIdList);
        //待完成，需要将查询结果写入modal给页面表格
        //已准备好如下数据
        /*
            1.被选中的仪表ID    meterIdList 通过逗号分隔
            2.起止时间 startTime4return endTime4return  格式为  yyyy-MM-DD HH:MM
            将查询结果写入modal，每个仪表需要查询上述时间段内的字段：最小流量、最小流量发生时间、最大流量、最大流量发生时间、平均流量、夜间最小流量、夜间最小流量占比
         */


        //需要加上查询月份


        //machineInfo2Repo.queryByUserId(userId);

        if(condition==null||condition.size()==0){
            condition=machines;
        }
        //时间格式化
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");

        Calendar c=Calendar.getInstance();

        if(startTime==null||"".equals(startTime)){
            c.setTime(new Date());
        }else{
            try {
                c.setTime(sdf.parse(startTime));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        startTime=sdf.format(c.getTime());
        if(endTime==null||"".equals(endTime)){
            c.setTime(new Date());
        }else{
            try {
                c.setTime(sdf.parse(endTime));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        endTime=sdf.format(c.getTime());
        //

        String[][] result = dmaService.querydmaMicroflow(userId, condition, startTime, endTime, startHour, endHour);
        model.addAttribute("title", condition);
        model.addAttribute("result", result);
        model.addAttribute("startTime", startTime);
        model.addAttribute("endTime", endTime);


        return "dma/dmaMicroFlow";
    }

    //告警-查询查询
    @RequestMapping(value = { "/alarmList" }, method = RequestMethod.GET)
    public String alarmManage(HttpServletRequest request, Model model) {
        model.addAttribute("alarm","active");
        model.addAttribute("alarmList","active");
        Integer userId = UserUtils.getUserId();
        model.addAttribute("stations",meterService.getStationList(userId));
        return "alarm/alarmList";
    }

    //告警-单点设置
    @RequestMapping(value = { "/alarmSingleSetting" }, method = {RequestMethod.GET,RequestMethod.POST})
    public String alarmSingleSetting(HttpServletRequest request, Model model) {
        model.addAttribute("alarm","active");
        model.addAttribute("alarmSingleSetting","active");
        Integer userId = UserUtils.getUserId();
        String mid=request.getParameter("mid"); //meter Id
        String type=request.getParameter("type"); //meter Id
        String alarmType=request.getParameter("alarmType"); //meter Id
        String mname=request.getParameter("mname"); //meter Name
        List<MachineInfo> machineInfo2List = meterService.findUserMeterList(userId);
        //默认页面请求选中第一个区域
        if(mid==null){
            if(machineInfo2List.size()>0){
                mid=""+machineInfo2List.get(0).getNum();
            }
        }
        //dma菜单下拉请求
        if(mname!=null&&mid==null){
            //通过url解析出did
            mid=mname.substring(mname.indexOf("=")+1,mname.length());
        }

        //类型选择
        Map<String,String> types=new HashMap<String,String>();
        types.put("流量","flow");
        types.put("压力","press");
        types.put("电量","battery");
        types.put("电压","voltage");
        types.put("电流","current");
        types.put("信号","signal");
        types.put("温度","temp");

        List<MachineInfoVo4AlarmSetting> typeList=new ArrayList<MachineInfoVo4AlarmSetting>();
        for(Map.Entry<String, String> entry : types.entrySet()){
            MachineInfoVo4AlarmSetting meter=new MachineInfoVo4AlarmSetting();
            meter.setName(entry.getKey());
            meter.setValue("/alarmSingleSetting?mid="+mid+"&type="+entry.getValue());
            try{
                if(type.equals(entry.getValue())){
                    meter.setSelect("selected");
                }
            }catch (Exception e){

            }
            typeList.add(meter);
        }
        //如果type为空，则默认选择第一个
        if(type==null){
            type="temp";
            typeList.get(0).setSelect("selected");
        }

        List<MachineInfoVo4AlarmSetting> meterList=new ArrayList<MachineInfoVo4AlarmSetting>();
        for(MachineInfo machineInfo2:machineInfo2List){
            MachineInfoVo4AlarmSetting meter=new MachineInfoVo4AlarmSetting();
            meter.setMid(machineInfo2.getNum());
            meter.setName(machineInfo2.getShortName());
            meter.setValue("/alarmSingleSetting?mid="+machineInfo2.getNum());
            if(machineInfo2.getNum().toString().equals(mid)){
                meter.setSelect("selected");
            }
            meterList.add(meter);
        }
        AlarmUserConfig alarmSetting4Limit= alarmService.findConfigByMidAndTypeAndAlarmType(Integer.valueOf(mid),type,"limit");
        if(alarmSetting4Limit==null){
            alarmSetting4Limit=new AlarmUserConfig();
        }
        AlarmUserConfig alarmSetting4Change= alarmService.findConfigByMidAndTypeAndAlarmType(Integer.valueOf(mid),type,"change");
        if(alarmSetting4Change==null){
            alarmSetting4Change=new AlarmUserConfig();
        }
        model.addAttribute("limit",alarmService.alarmSettingConfig(alarmSetting4Limit));
        model.addAttribute("change",alarmService.alarmSettingConfig(alarmSetting4Change));
        model.addAttribute("meterList",meterList);
        model.addAttribute("typeList",typeList);
        return "alarm/alarmSingleSetting";
    }

    //告警-多点设置
    @RequestMapping(value = { "/alarmMultiSetting" }, method = {RequestMethod.GET,RequestMethod.POST})
    public String alarmMultiSetting(HttpServletRequest request, Model model) {
        model.addAttribute("alarm","active");
        model.addAttribute("alarmMultiSetting","active");
        String meterListString=request.getParameter("meterList");
        String tname=request.getParameter("tname");
        if(meterListString!=null){
            if(meterListString.length()>0){
                model.addAttribute("status","success");
                //循环解析需要设置的节点
                for(String mid:(meterListString.substring(1,meterListString.length())).split(",")){
                    try {
                        AlarmUserConfig  alarmSetting4Limit= alarmService.findConfigByMidAndTypeAndAlarmType(Integer.valueOf(mid),tname,"limit");
                        if(alarmSetting4Limit==null){
                            alarmSetting4Limit=new AlarmUserConfig();
                        }
                        alarmSetting4Limit.setMid(Integer.valueOf(mid));

                        AlarmUserConfig  alarmSetting4Change= alarmService.findConfigByMidAndTypeAndAlarmType(Integer.valueOf(mid),tname,"change");
                        if(alarmSetting4Change==null){
                            alarmSetting4Change=new AlarmUserConfig();
                        }
                        alarmSetting4Change.setMid(Integer.valueOf(mid));

                        alarmService.saveConfig(alarmService.alarmSettingCreate(alarmSetting4Limit,"limit",tname,request));
                        alarmService.saveConfig(alarmService.alarmSettingCreate(alarmSetting4Change,"change",tname,request));

                        model.addAttribute("current",alarmService.alarmSettingConfig(alarmSetting4Limit));
                        return "alarm/alarmMultiSetting";
                    }catch (Exception e){
                        model.addAttribute("status","fail");
                    }
                }
            }
        }
        model.addAttribute("current",alarmService.alarmSettingConfig(new AlarmUserConfig()));
        return "alarm/alarmMultiSetting";
    }


    //日报表
    @RequestMapping(value = { "/dayReport" }, method = {RequestMethod.GET,RequestMethod.POST})
    public String dayReport(HttpServletRequest request,Model model) {
        model.addAttribute("report","active");
        model.addAttribute("dayReport","active");
        Integer userId=UserUtils.getUserId();
        String[] stationList= request.getParameterValues("staions");  //查询水表列表
        String date = request.getParameter("date");
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        if (date == null || date.length() == 0) {
            date = sdf1.format(new Date());
        }
        //人所对应的所有水表
        //boolean res=companyService.isAdmin(UserUtils.getUserId());
        List<MachineInfo> machines = meterService.findUserMeterList(userId);
        
        List<MachineInfo> condition=new ArrayList<MachineInfo>();

        //处理选中仪表清单
        List<MeterList4DmaMicroFlowVo> allMeterList=new ArrayList<MeterList4DmaMicroFlowVo>();
        Map<String,String> selectedMeterList=new HashMap<String,String>();
        try{
            for(String station:stationList){
                selectedMeterList.put(station,station);
            }
        }catch (Exception e){
                e.printStackTrace();//TODO 异常处理计划 在登录状态下，点击日报表
        }
        for(MachineInfo meter:machines){
            MeterList4DmaMicroFlowVo meterList4DmaMicroFlowVo = new MeterList4DmaMicroFlowVo();
            meterList4DmaMicroFlowVo.setName(meter.getShortName());
            if (selectedMeterList.get(meter.getShortName()) != null) {
                meterList4DmaMicroFlowVo.setSelect("selected");
                condition.add(meter);
            }
            allMeterList.add(meterList4DmaMicroFlowVo);
        }

        if(condition==null||condition.size()==0){//初始化时condition最多选择0条显示
        }else{
        	String[][] result = reportService.reportDay(userId,date,condition);
            model.addAttribute("result", result);
        }
        model.addAttribute("title", condition);
        model.addAttribute("date",date);

        model.addAttribute("stationList",allMeterList);


        return "report/dayReport";
    }

    //月报表
    @RequestMapping(value = { "/monthReport" }, method = {RequestMethod.GET,RequestMethod.POST})
    public String monthReport(HttpServletRequest request,Model model) {
        model.addAttribute("report","active");
        model.addAttribute("monthReport","active");
        Integer userId=UserUtils.getUserId();
        String[] stationList= request.getParameterValues("staions");  //查询水表列表
        String date = request.getParameter("date");
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM");
        if (date == null || date.length() == 0) {
            date = sdf1.format(new Date());
        }
        //人所对应的所有水表
        //需要加上查询月份
        List<MachineInfo> machines = meterService.findUserMeterList(userId);
        
        List<MachineInfo> condition=new ArrayList<MachineInfo>();

        //处理选中仪表清单
        List<MeterList4DmaMicroFlowVo> allMeterList=new ArrayList<MeterList4DmaMicroFlowVo>();
        Map<String,String> selectedMeterList=new HashMap<String,String>();
        try{
            for(String station:stationList){
                selectedMeterList.put(station,station);
            }
        }catch (Exception e){

        }
        for(MachineInfo meter:machines){
            MeterList4DmaMicroFlowVo meterList4DmaMicroFlowVo = new MeterList4DmaMicroFlowVo();
            meterList4DmaMicroFlowVo.setName(meter.getShortName());
            if (selectedMeterList.get(meter.getShortName()) != null) {
                meterList4DmaMicroFlowVo.setSelect("selected");
                condition.add(meter);
            }
            allMeterList.add(meterList4DmaMicroFlowVo);
        }

        if(condition==null||condition.size()==0){//初始化时condition最多选择0条显示
        }else{
        	String[][] result =reportService.reportMonth(userId,condition,date);
            model.addAttribute("result", result);
        }
        model.addAttribute("title", condition);
        model.addAttribute("date",date);
        model.addAttribute("stationList",allMeterList);
        return "report/monthReport";
    }

    //季报表
    @RequestMapping(value = { "/seasonReport" }, method = {RequestMethod.GET,RequestMethod.POST})
    public String seasonReport(HttpServletRequest request,Model model) {
        model.addAttribute("report","active");
        model.addAttribute("seasonReport","active");
        Integer userId=UserUtils.getUserId();
        String[] stationList= request.getParameterValues("staions");  //查询水表列表
        String date = request.getParameter("date");  //查询日期
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy");
        if (date == null || date.length() == 0) {
            date = sdf1.format(new Date());
        }
        //人所对应的所有水表
        List<MachineInfo> machines = meterService.findUserMeterList(userId);
        
//		List<MachineInfo2> machines = machineInfo2Repo.queryByUserId(userId);
        List<MachineInfo> condition=new ArrayList<MachineInfo>();


        //处理选中仪表清单
        List<MeterList4DmaMicroFlowVo> allMeterList=new ArrayList<MeterList4DmaMicroFlowVo>();
        Map<String,String> selectedMeterList=new HashMap<String,String>();
        try{
            for(String station:stationList){
                selectedMeterList.put(station,station);
            }
        }catch (Exception e){

        }
        for(MachineInfo meter:machines){
            MeterList4DmaMicroFlowVo meterList4DmaMicroFlowVo = new MeterList4DmaMicroFlowVo();
            meterList4DmaMicroFlowVo.setName(meter.getShortName());
            if (selectedMeterList.get(meter.getShortName()) != null) {
                meterList4DmaMicroFlowVo.setSelect("selected");
                condition.add(meter);
            }
            allMeterList.add(meterList4DmaMicroFlowVo);
        }

        if(condition==null||condition.size()==0){//初始化时condition最多选择0条显示
        }else{
        	String[][] result =reportService.reportSeason(userId,condition,date);
            model.addAttribute("result", result);
        }
        model.addAttribute("title", condition);
        model.addAttribute("stationList",allMeterList);
        model.addAttribute("date",date);
        return "report/seasonReport";
    }

    //年报表
    @RequestMapping(value = { "/yearReport" }, method ={RequestMethod.GET,RequestMethod.POST})
    public String yearReport(HttpServletRequest request,Model model) {
        model.addAttribute("report","active");
        model.addAttribute("yearReport","active");
        Integer userId=UserUtils.getUserId();
        String[] stationList= request.getParameterValues("staions");  //查询水表列表
        String date = request.getParameter("date");  //查询日期
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy");
        if (date == null || date.length() == 0) {
            date = sdf1.format(new Date());
        }
        //人所对应的所有水表
        List<MachineInfo> machines = meterService.findUserMeterList(userId);
        
//		List<MachineInfo2> machines = machineInfo2Repo.queryByUserId(userId);
        List<MachineInfo> condition=new ArrayList<MachineInfo>();


        //处理选中仪表清单
        List<MeterList4DmaMicroFlowVo> allMeterList=new ArrayList<MeterList4DmaMicroFlowVo>();
        Map<String,String> selectedMeterList=new HashMap<String,String>();
        try{
            for(String station:stationList){
                selectedMeterList.put(station,station);
            }
        }catch (Exception e){

        }
        for(MachineInfo meter:machines){
            MeterList4DmaMicroFlowVo meterList4DmaMicroFlowVo = new MeterList4DmaMicroFlowVo();
            meterList4DmaMicroFlowVo.setName(meter.getShortName());
            if (selectedMeterList.get(meter.getShortName()) != null) {
                meterList4DmaMicroFlowVo.setSelect("selected");
                condition.add(meter);
            }
            allMeterList.add(meterList4DmaMicroFlowVo);
        }

        if(condition==null||condition.size()==0){//初始化时condition最多选择10条显示
        }else{
            String[][] result =reportService.reportYear(userId,condition,date);
            model.addAttribute("result", result);
        }
        model.addAttribute("title", condition);
        model.addAttribute("date",date);
        model.addAttribute("stationList",allMeterList);
        return "report/yearReport";
    }


    //系统－仪表公司管理
    @RequestMapping(value = { "/meterCompanyManage" }, method = RequestMethod.GET)
    public String meterCompanyManage(Model model) {
        model.addAttribute("system","active");
        model.addAttribute("meterCompanyManage","active");
        return "system/meterCompanyManage";
    }

    //系统－仪表员工管理
    @RequestMapping(value = { "/meterUserManage" }, method = RequestMethod.GET)
    public String userMeterManage(HttpServletRequest request,Model model) {
        model.addAttribute("system","active");
        model.addAttribute("meterUserManage","active");
        //测试下插入水表与用户关系的功能
        return "system/meterUserManage";
    }

    //系统－公司用户管理
    @RequestMapping(value = { "/companyUserManage" }, method = RequestMethod.GET)
    public String userManage(Model model) {
        model.addAttribute("system","active");
        model.addAttribute("companyUserManage","active");
        return "system/companyUserManage";
    }

    //系统－日志管理
    @RequestMapping(value = { "/logManage" }, method = RequestMethod.GET)
    public String logManage(HttpServletRequest request,Model model) {
        model.addAttribute("system","active");
        model.addAttribute("logManage","active");
        Integer userId = UserUtils.getUserId();
        Map<Integer,String> useres = companyService.querySubUser(userId);
        if(userId == 1){
            //admin
            useres.remove(userId);
        }
        model.addAttribute("subUser", useres);

        //默认时间
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        String startAt = DateFormatUtils.format(calendar, "yyyy-MM-dd HH:mm:ss");
        model.addAttribute("startAt", startAt);
        calendar.add(Calendar.DATE, 1);
        String endAt = DateFormatUtils.format(calendar, "yyyy-MM-dd HH:mm:ss");
        model.addAttribute("endAt", endAt);

        model.addAttribute("currentUserId", userId);
        model.addAttribute("currentUserName", companyService.getUser(userId).getUserName());

        return "system/logManage";
    }

    //系统－仪表更换记录
    @RequestMapping(value = { "/meterChangeRecord" }, method = RequestMethod.GET)
    public String meterChangeRecord(HttpServletRequest request, Model model) {
        model.addAttribute("system","active");
        model.addAttribute("meterChangeRecord","active");
        
        //默认时间
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        String startAt = DateFormatUtils.format(calendar, "yyyy-MM-dd HH:mm:ss");
        model.addAttribute("startAt", startAt);
        calendar.add(Calendar.DATE, 1);
        String endAt = DateFormatUtils.format(calendar, "yyyy-MM-dd HH:mm:ss");
        model.addAttribute("endAt", endAt);
        
      //默认选3条
  		Integer userId = UserUtils.getUserId();
  		List<MachineInfo> machines = meterService.findUserMeterList(userId);
  		
        List<Map<String,Object>> stations = new ArrayList<Map<String,Object>>();
        if(machines != null && machines.size()>0){
        	for(int i =0 ; i < machines.size(); i++){
        		Map<String,Object> station = new HashMap<String,Object>();
        		station.put("name", machines.get(i).getShortName());
        		station.put("num", machines.get(i).getNum());
            	if(i <= 2){
            		station.put("select","selected");
            	} else{
            		station.put("select",null);
            	}
            	stations.add(station);
        	}
        }
        model.addAttribute("stations", stations);
        
        return "system/meterChangeRecord";
    }


    //修改密码
    @RequestMapping(value = { "/changePassword" }, method = RequestMethod.POST)
    public String changePassword(HttpServletRequest request,Model model) {
        String oldPassword=request.getParameter("old-password");
        String newPassword=request.getParameter("new-password");
        String confirmPassword=request.getParameter("confirm-password");

        Integer userId=UserUtils.getUserId();
        User user=userMapper.selectByPrimaryKey(userId);

        if(user.getPassword().equals(oldPassword)){

            if(newPassword.equals(confirmPassword)){
                user.setPassword(newPassword);
                userMapper.updateByPrimaryKey(user);
                model.addAttribute("message","success");
                model.addAttribute("content","密码更新成功！");
            }else{
                model.addAttribute("message","fail");
                model.addAttribute("content","两次新密码输入不一致！");
            }

        }else{
            model.addAttribute("message","fail");
            model.addAttribute("content","密码错误！");
        }


        model.addAttribute("basic","active");
        model.addAttribute("meterBasicList","active");

        return "basic/meterBasicList";
    }

}
