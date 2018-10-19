package com.welltech.controller.rest;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.welltech.waterAffair.common.util.UserUtils;
import com.welltech.waterAffair.domain.entity.MachineInfo;
import com.welltech.waterAffair.domain.vo.before.MeterList4DmaMicroFlowVo;
import com.welltech.waterAffair.service.CompanyService;
import com.welltech.waterAffair.service.MeterService;
import com.welltech.waterAffair.service.ReportService;

@RestController  
public class ExcelController {  

    @Resource
    private ReportService reportService;
    @Resource
    private CompanyService companyService;
	@Resource
	private MeterService meterService;
    // 导出excel方法  测试接口
    @RequestMapping("exportExcel")  
    public void exportExcel(HttpServletRequest request, HttpServletResponse response)  
    {  
        HttpSession session = request.getSession();  
        session.setAttribute("state", null);  
        // 生成提示信息，  
        response.setContentType("application/vnd.ms-excel");  
        String codedFileName = null;  
        OutputStream fOut = null;  
        try  
        {  
            // 进行转码，使其支持中文文件名  
            codedFileName = java.net.URLEncoder.encode("中文", "UTF-8");  
            response.setHeader("content-disposition", "attachment;filename=" + codedFileName + ".xls");  
            // response.addHeader("Content-Disposition", "attachment;   filename=" + codedFileName + ".xls");  
            // 产生工作簿对象  
            HSSFWorkbook workbook = new HSSFWorkbook();  
            //产生工作表对象  
            HSSFSheet sheet = workbook.createSheet();  
            for (int i = 0; i <= 30000; i++)  
            {  
                HSSFRow row = sheet.createRow((int)i);//创建一行  
                HSSFCell cell = row.createCell((int)0);//创建一列  
                cell.setCellType(HSSFCell.CELL_TYPE_STRING);  
                cell.setCellValue("测试成功" + i);  
            }  
            fOut = response.getOutputStream();  
            workbook.write(fOut);  
        }  
        catch (UnsupportedEncodingException e1)  
        {}  
        catch (Exception e)  
        {}  
        finally  
        {  
            try  
            {  
                fOut.flush();  
                fOut.close();  
            }  
            catch (IOException e)  
            {}  
            session.setAttribute("state", "open");  
        }  
        System.out.println("文件生成...");  
    }  
    
 // 导出excel方法  
    @RequestMapping("exportDayReportExcel")
    @ResponseBody
    public void exportDayReportExcel(HttpServletRequest request, HttpServletResponse response,Model model)  
    {  
        // 生成提示信息，  
        response.setContentType("application/vnd.ms-excel");  
        String codedFileName = null;  
        OutputStream fOut = null;  
        try  
        {    
            //
        	Integer userId=UserUtils.getUserId();
        	String stations = request.getParameter("staions");  //查询水表列表
            String[] stationList= null;
            if(stations!=null){
            	stationList=stations.split(",");
            }
            String date = request.getParameter("date");
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
            if (date == null || date.length() == 0) {
                date = sdf1.format(new Date());
            }
            //人所对应的所有水表
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

            String[][] result = reportService.reportDay(userId,date,condition);
        	//

            model.addAttribute("result", result);
        	codedFileName="dayReport_"+date;
            response.setHeader("content-disposition", "attachment;filename=" + codedFileName + ".xls");
            // 产生工作簿对象  
            HSSFWorkbook workbook = new HSSFWorkbook();  
            //产生工作表对象  
            HSSFSheet sheet = workbook.createSheet(); 
            //生成head
            
            HSSFRow head = sheet.createRow((int)0);//创建一行head
        	HSSFCell cell0 = head.createCell(0);//创建一列  
            cell0.setCellType(HSSFCell.CELL_TYPE_STRING);  
            cell0.setCellValue("序号");
            sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 0));
        	HSSFCell cellhead = head.createCell(1);//创建一列  
        	cellhead.setCellType(HSSFCell.CELL_TYPE_STRING);  
        	cellhead.setCellValue("时间");
            sheet.addMergedRegion(new CellRangeAddress(0, 1, 1, 1));
            HSSFRow head1 = sheet.createRow((int)1);//创建一行head1
            for(int i=0;i<condition.size();i++){
                HSSFCell cell = head.createCell(i*3+2);//创建一列  
                cell.setCellType(HSSFCell.CELL_TYPE_STRING);  
                cell.setCellValue(condition.get(i).getShortName());
                sheet.addMergedRegion(new CellRangeAddress(0, 0, i*3+2, (i+1)*3+1));
                HSSFCell cell1 = head1.createCell(i*3+2);//创建一列  
                cell1.setCellType(HSSFCell.CELL_TYPE_STRING);  
                cell1.setCellValue("压力（kPa）");
                HSSFCell cell2 = head1.createCell(i*3+3);//创建一列  
                cell2.setCellType(HSSFCell.CELL_TYPE_STRING);  
                cell2.setCellValue("瞬时流量（m³/h）");
                HSSFCell cell3 = head1.createCell(i*3+4);//创建一列  
                cell3.setCellType(HSSFCell.CELL_TYPE_STRING);  
                cell3.setCellValue("累计流量（m³）");
                
            }
            //
            for(int i=0;i<result.length;i++){
                HSSFRow row = sheet.createRow((int)i+2);//创建一行body
                HSSFCell cellbody0 = row.createCell(0);//创建一列 
                cellbody0.setCellType(HSSFCell.CELL_TYPE_STRING);
                cellbody0.setCellValue(i+1);
                for(int j=0;j<result[i].length;j++){
                    HSSFCell cell = row.createCell(j+1);//创建一列
                    if(j==0 || "--".equals(result[i][j]) || i == 26 || i == 28){
                        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                        cell.setCellValue(result[i][j]);
                    }else{
                        cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                        cell.setCellValue(Float.parseFloat(result[i][j]));
                    }

                }
            }
            fOut = response.getOutputStream();  
            workbook.write(fOut);  
        }catch (Exception e)  
        {e.printStackTrace();}  
        finally  
        {  
            try  
            {  
                fOut.flush();  
                fOut.close();  
            }  
            catch (IOException e)  
            {}  
        }  
        System.out.println("文件生成...");  
    }  
    
 // 导出excel方法  
    @RequestMapping("exportMonthReportExcel")
    @ResponseBody
    public void exportMonthReportExcel(HttpServletRequest request, HttpServletResponse response,Model model)  
    {  
        // 生成提示信息，  
        response.setContentType("application/vnd.ms-excel");  
        String codedFileName = null;  
        OutputStream fOut = null;  
        try  
        {    
            //
        	Integer userId=UserUtils.getUserId();
        	String stations = request.getParameter("staions");  //查询水表列表
            String[] stationList= null;
            if(stations!=null){
            	stationList=stations.split(",");
            }
            String date = request.getParameter("date");
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM");
            if (date == null || date.length() == 0) {
                date = sdf1.format(new Date());
            }
            //得到当前月份的天数
            int dayes=reportService.getCurrentMonthLastDay(date);
            //人所对应的所有水表
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

            String[][] result = reportService.reportMonth(userId,condition,date);
        	//

            model.addAttribute("result", result);
        	codedFileName="monthReport_"+date;
            response.setHeader("content-disposition", "attachment;filename=" + codedFileName + ".xls");
            // 产生工作簿对象  
            HSSFWorkbook workbook = new HSSFWorkbook();  
            //产生工作表对象  
            HSSFSheet sheet = workbook.createSheet(); 
            //生成head
            
            HSSFRow head = sheet.createRow((int)0);//创建一行head
        	HSSFCell cell0 = head.createCell(0);//创建一列  
            cell0.setCellType(HSSFCell.CELL_TYPE_STRING);  
            cell0.setCellValue("序号");
            sheet.addMergedRegion(new CellRangeAddress(0, 2, 0, 0));
        	HSSFCell cellhead = head.createCell(1);//创建一列  
        	cellhead.setCellType(HSSFCell.CELL_TYPE_STRING);  
        	cellhead.setCellValue("时间");
            sheet.addMergedRegion(new CellRangeAddress(0, 2, 1, 1));
            HSSFRow head1 = sheet.createRow((int)1);//创建一行head1
            HSSFRow head2 = sheet.createRow((int)2);//创建一行head2
            for(int i=0;i<condition.size();i++){
                HSSFCell cell = head.createCell(i*9+2);//创建一列  
                cell.setCellType(HSSFCell.CELL_TYPE_STRING);  
                cell.setCellValue(condition.get(i).getShortName());
                sheet.addMergedRegion(new CellRangeAddress(0, 0, i*9+2, (i+1)*9+1));
                
                HSSFCell cell1 = head1.createCell(i*9+2);//创建一列  
                int step=i*9;
                cell1.setCellType(HSSFCell.CELL_TYPE_STRING);  
                cell1.setCellValue("压力（KPa）");
                sheet.addMergedRegion(new CellRangeAddress(1, 1, step+2, step+4));
                HSSFCell cell2 = head1.createCell(i*9+5);//创建一列  
                cell2.setCellType(HSSFCell.CELL_TYPE_STRING);  
                cell2.setCellValue("瞬时流量（m³/h）");
                sheet.addMergedRegion(new CellRangeAddress(1, 1, step+5, step+7));
                HSSFCell cell3 = head1.createCell(i*9+8);//创建一列  
                cell3.setCellType(HSSFCell.CELL_TYPE_STRING);  
                cell3.setCellValue("实际累计（m³）");
                sheet.addMergedRegion(new CellRangeAddress(1, 1, step+8, step+10));
                
                HSSFCell cell31 = head2.createCell(i*9+2);//创建一列  
                cell31.setCellType(HSSFCell.CELL_TYPE_STRING);  
                cell31.setCellValue("平均值");
                HSSFCell cell32 = head2.createCell(i*9+3);//创建一列  
                cell32.setCellType(HSSFCell.CELL_TYPE_STRING);  
                cell32.setCellValue("最大值");
                HSSFCell cell33 = head2.createCell(i*9+4);//创建一列  
                cell33.setCellType(HSSFCell.CELL_TYPE_STRING);  
                cell33.setCellValue("最小值");
                HSSFCell cell34 = head2.createCell(i*9+5);//创建一列  
                cell34.setCellType(HSSFCell.CELL_TYPE_STRING);  
                cell34.setCellValue("平均值");
                HSSFCell cell35 = head2.createCell(i*9+6);//创建一列  
                cell35.setCellType(HSSFCell.CELL_TYPE_STRING);  
                cell35.setCellValue("最大值");
                HSSFCell cell36 = head2.createCell(i*9+7);//创建一列  
                cell36.setCellType(HSSFCell.CELL_TYPE_STRING);  
                cell36.setCellValue("最小值");
                HSSFCell cell37 = head2.createCell(i*9+8);//创建一列  
                cell37.setCellType(HSSFCell.CELL_TYPE_STRING);  
                cell37.setCellValue("起始读数");
                HSSFCell cell38 = head2.createCell(i*9+9);//创建一列  
                cell38.setCellType(HSSFCell.CELL_TYPE_STRING);  
                cell38.setCellValue("结束读数");
                HSSFCell cell39 = head2.createCell(i*9+10);//创建一列  
                cell39.setCellType(HSSFCell.CELL_TYPE_STRING);  
                cell39.setCellValue("累计用量");
            }
            //
            //
            for(int i=0;i<result.length;i++){
                HSSFRow row = sheet.createRow((int)i+3);//创建一行body
                HSSFCell cellbody0 = row.createCell(0);//创建一列
                cellbody0.setCellType(HSSFCell.CELL_TYPE_STRING);
                cellbody0.setCellValue(i+1);
                for(int j=0;j<result[i].length;j++){
                    HSSFCell cell = row.createCell(j+1);//创建一列
                    if(j==0 || "--".equals(result[i][j]) || i ==dayes+2 || i == dayes+4 || i == dayes + 5){
                        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                        cell.setCellValue(result[i][j]);
                    }else{
                        cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                        cell.setCellValue(Float.parseFloat(result[i][j]));
                    }

                }
            }
            fOut = response.getOutputStream();  
            workbook.write(fOut);  
        }catch (Exception e)  
        {e.printStackTrace();}  
        finally  
        {  
            try  
            {  
                fOut.flush();  
                fOut.close();  
            }  
            catch (IOException e)  
            {}  
        }  
        System.out.println("文件生成...");  
    }  
    
 // 导出excel方法  
    @RequestMapping("exportSeasonReportExcel")
    @ResponseBody
    public void exportSeasonReportExcel(HttpServletRequest request, HttpServletResponse response,Model model)  
    {  
        // 生成提示信息，  
        response.setContentType("application/vnd.ms-excel");  
        String codedFileName = null;  
        OutputStream fOut = null;  
        try  
        {    
            //
        	Integer userId=UserUtils.getUserId();
        	String stations = request.getParameter("staions");  //查询水表列表
            String[] stationList= null;
            if(stations!=null){
            	stationList=stations.split(",");
            }
            //人所对应的所有水表
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

            String[][] result = reportService.reportSeason(userId,condition);
        	//

            model.addAttribute("result", result);
        	codedFileName="seasonReport";
            response.setHeader("content-disposition", "attachment;filename=" + codedFileName + ".xls");
            // 产生工作簿对象  
            HSSFWorkbook workbook = new HSSFWorkbook();  
            //产生工作表对象  
            HSSFSheet sheet = workbook.createSheet(); 
            //生成head
            
            HSSFRow head = sheet.createRow((int)0);//创建一行head
        	HSSFCell cell0 = head.createCell(0);//创建一列  
            cell0.setCellType(HSSFCell.CELL_TYPE_STRING);  
            cell0.setCellValue("序号");
            sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 0));
        	HSSFCell cellhead = head.createCell(1);//创建一列  
        	cellhead.setCellType(HSSFCell.CELL_TYPE_STRING);  
        	cellhead.setCellValue("时间");
            sheet.addMergedRegion(new CellRangeAddress(0, 1, 1, 1));
            
            HSSFRow head1 = sheet.createRow((int)1);//创建一行head1
            HSSFRow head2 = sheet.createRow((int)2);//创建一行head2
            for(int i=0;i<condition.size();i++){
                HSSFCell cell = head.createCell(i*9+2);//创建一列  
                cell.setCellType(HSSFCell.CELL_TYPE_STRING);  
                cell.setCellValue(condition.get(i).getShortName());
                sheet.addMergedRegion(new CellRangeAddress(0, 0, i*9+2, (i+1)*9+1));
                
                HSSFCell cell1 = head1.createCell(i*9+2);//创建一列  
                int step=i*9;
                cell1.setCellType(HSSFCell.CELL_TYPE_STRING);  
                cell1.setCellValue("压力（KPa）");
                sheet.addMergedRegion(new CellRangeAddress(1, 1, step+2, step+4));
                HSSFCell cell2 = head1.createCell(i*9+5);//创建一列  
                cell2.setCellType(HSSFCell.CELL_TYPE_STRING);  
                cell2.setCellValue("瞬时流量（m³/h）");
                sheet.addMergedRegion(new CellRangeAddress(1, 1, step+5, step+7));
                HSSFCell cell3 = head1.createCell(i*9+8);//创建一列  
                cell3.setCellType(HSSFCell.CELL_TYPE_STRING);  
                cell3.setCellValue("累计流量（m³）");
                sheet.addMergedRegion(new CellRangeAddress(1, 1, step+8, step+10));
                
                HSSFCell cell31 = head2.createCell(i*9+2);//创建一列  
                cell31.setCellType(HSSFCell.CELL_TYPE_STRING);  
                cell31.setCellValue("平均值");
                HSSFCell cell32 = head2.createCell(i*9+3);//创建一列  
                cell32.setCellType(HSSFCell.CELL_TYPE_STRING);  
                cell32.setCellValue("最大值");
                HSSFCell cell33 = head2.createCell(i*9+4);//创建一列  
                cell33.setCellType(HSSFCell.CELL_TYPE_STRING);  
                cell33.setCellValue("最小值");
                HSSFCell cell34 = head2.createCell(i*9+5);//创建一列  
                cell34.setCellType(HSSFCell.CELL_TYPE_STRING);  
                cell34.setCellValue("平均值");
                HSSFCell cell35 = head2.createCell(i*9+6);//创建一列  
                cell35.setCellType(HSSFCell.CELL_TYPE_STRING);  
                cell35.setCellValue("最大值");
                HSSFCell cell36 = head2.createCell(i*9+7);//创建一列  
                cell36.setCellType(HSSFCell.CELL_TYPE_STRING);  
                cell36.setCellValue("最小值");
                HSSFCell cell37 = head2.createCell(i*9+8);//创建一列  
                cell37.setCellType(HSSFCell.CELL_TYPE_STRING);  
                cell37.setCellValue("起始读数");
                HSSFCell cell38 = head2.createCell(i*9+9);//创建一列  
                cell38.setCellType(HSSFCell.CELL_TYPE_STRING);  
                cell38.setCellValue("结束读数");
                HSSFCell cell39 = head2.createCell(i*9+10);//创建一列  
                cell39.setCellType(HSSFCell.CELL_TYPE_STRING);  
                cell39.setCellValue("累计用量");
            }
            //
            for(int i=0;i<result.length;i++){
                HSSFRow row = sheet.createRow((int)i+2);//创建一行body
                HSSFCell cellbody0 = row.createCell(0);//创建一列
                cellbody0.setCellType(HSSFCell.CELL_TYPE_STRING);
                cellbody0.setCellValue(i+1);
                for(int j=0;j<result[i].length;j++){
                    HSSFCell cell = row.createCell(j+1);//创建一列
                    if(j==0 || "--".equals(result[i][j]) || i == 6 || i == 8){
                        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                        cell.setCellValue(result[i][j]);
                    }else{
                        cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                        cell.setCellValue(Float.parseFloat(result[i][j]));
                    }
                }
            }
            fOut = response.getOutputStream();  
            workbook.write(fOut);  
        }catch (Exception e)  
        {e.printStackTrace();}  
        finally  
        {  
            try  
            {  
                fOut.flush();  
                fOut.close();  
            }  
            catch (IOException e)  
            {}  
        }  
        System.out.println("文件生成...");  
    }  
    
 // 导出excel方法  
    @RequestMapping("exportYearReportExcel")
    @ResponseBody
    public void exportYearReportExcel(HttpServletRequest request, HttpServletResponse response,Model model)  
    {  
        // 生成提示信息，  
        response.setContentType("application/vnd.ms-excel");  
        String codedFileName = null;  
        OutputStream fOut = null;  
        try  
        {    
            //
        	Integer userId=UserUtils.getUserId();
        	String stations = request.getParameter("staions");  //查询水表列表
            String[] stationList= null;
            if(stations!=null){
            	stationList=stations.split(",");
            }

            String date = request.getParameter("date");  //查询日期
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy");
            if (date == null || date.length() == 0) {
                date = sdf1.format(new Date());
            }
            //人所对应的所有水表
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

            String[][] result = reportService.reportYear(userId,condition,date);
        	//

            model.addAttribute("result", result);
        	codedFileName="yearReport";
            response.setHeader("content-disposition", "attachment;filename=" + codedFileName + ".xls");
            // 产生工作簿对象  
            HSSFWorkbook workbook = new HSSFWorkbook();  
            //产生工作表对象  
            HSSFSheet sheet = workbook.createSheet(); 
            //生成head
            
            HSSFRow head = sheet.createRow((int)0);//创建一行head
        	HSSFCell cell0 = head.createCell(0);//创建一列  
            cell0.setCellType(HSSFCell.CELL_TYPE_STRING);  
            cell0.setCellValue("序号");
            sheet.addMergedRegion(new CellRangeAddress(0, 2, 0, 0));
        	HSSFCell cellhead = head.createCell(1);//创建一列  
        	cellhead.setCellType(HSSFCell.CELL_TYPE_STRING);  
        	cellhead.setCellValue("时间");
            sheet.addMergedRegion(new CellRangeAddress(0, 2, 1, 1));

            HSSFRow head1 = sheet.createRow((int)1);//创建一行head1
            HSSFRow head2 = sheet.createRow((int)2);//创建一行head2
            for(int i=0;i<condition.size();i++){
                HSSFCell cell = head.createCell(i*9+2);//创建一列  
                cell.setCellType(HSSFCell.CELL_TYPE_STRING);  
                cell.setCellValue(condition.get(i).getShortName());
                sheet.addMergedRegion(new CellRangeAddress(0, 0, i*9+2, (i+1)*9+1));
                
                HSSFCell cell1 = head1.createCell(i*9+2);//创建一列  
                int step=i*9;
                cell1.setCellType(HSSFCell.CELL_TYPE_STRING);  
                cell1.setCellValue("压力（KPa）");
                sheet.addMergedRegion(new CellRangeAddress(1, 1, step+2, step+4));
                HSSFCell cell2 = head1.createCell(i*9+5);//创建一列  
                cell2.setCellType(HSSFCell.CELL_TYPE_STRING);  
                cell2.setCellValue("瞬时流量（m³/h）");
                sheet.addMergedRegion(new CellRangeAddress(1, 1, step+5, step+7));
                HSSFCell cell3 = head1.createCell(i*9+8);//创建一列  
                cell3.setCellType(HSSFCell.CELL_TYPE_STRING);  
                cell3.setCellValue("累计流量（m³）");
                sheet.addMergedRegion(new CellRangeAddress(1, 1, step+8, step+10));
                
                HSSFCell cell31 = head2.createCell(i*9+2);//创建一列  
                cell31.setCellType(HSSFCell.CELL_TYPE_STRING);  
                cell31.setCellValue("平均值");
                HSSFCell cell32 = head2.createCell(i*9+3);//创建一列  
                cell32.setCellType(HSSFCell.CELL_TYPE_STRING);  
                cell32.setCellValue("最大值");
                HSSFCell cell33 = head2.createCell(i*9+4);//创建一列  
                cell33.setCellType(HSSFCell.CELL_TYPE_STRING);  
                cell33.setCellValue("最小值");
                HSSFCell cell34 = head2.createCell(i*9+5);//创建一列  
                cell34.setCellType(HSSFCell.CELL_TYPE_STRING);  
                cell34.setCellValue("平均值");
                HSSFCell cell35 = head2.createCell(i*9+6);//创建一列  
                cell35.setCellType(HSSFCell.CELL_TYPE_STRING);  
                cell35.setCellValue("最大值");
                HSSFCell cell36 = head2.createCell(i*9+7);//创建一列  
                cell36.setCellType(HSSFCell.CELL_TYPE_STRING);  
                cell36.setCellValue("最小值");
                HSSFCell cell37 = head2.createCell(i*9+8);//创建一列  
                cell37.setCellType(HSSFCell.CELL_TYPE_STRING);  
                cell37.setCellValue("起始读数");
                HSSFCell cell38 = head2.createCell(i*9+9);//创建一列  
                cell38.setCellType(HSSFCell.CELL_TYPE_STRING);  
                cell38.setCellValue("结束读数");
                HSSFCell cell39 = head2.createCell(i*9+10);//创建一列  
                cell39.setCellType(HSSFCell.CELL_TYPE_STRING);  
                cell39.setCellValue("累计用量");
            }
            //
            for(int i=0;i<result.length;i++){
                HSSFRow row = sheet.createRow((int)i+3);//创建一行body
                HSSFCell cellbody0 = row.createCell(0);//创建一列 
                cellbody0.setCellType(HSSFCell.CELL_TYPE_STRING);
                cellbody0.setCellValue(i+1);
                for(int j=0;j<result[i].length;j++){
                    HSSFCell cell = row.createCell(j+1);//创建一列
                    if(j==0 || "--".equals(result[i][j]) || i == 14 || i == 16 || i==17){
                        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                        cell.setCellValue(result[i][j]);
                    }else{
                        cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                        cell.setCellValue(Float.parseFloat(result[i][j]));
                    }
                    /*
                     //
            for(int i=0;i<result.length;i++){
                HSSFRow row = sheet.createRow((int)i+2);//创建一行body
                HSSFCell cellbody0 = row.createCell(0);//创建一列
                cellbody0.setCellType(HSSFCell.CELL_TYPE_STRING);
                cellbody0.setCellValue(i+1);
                for(int j=0;j<result[i].length;j++){
                    HSSFCell cell = row.createCell(j+1);//创建一列
                    if(j==0 || "--".equals(result[i][j]) || i == 6 || i == 8){
                        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                        cell.setCellValue(result[i][j]);
                    }else{
                        cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                        cell.setCellValue(Float.parseFloat(result[i][j]));
                    }
                }
            }
                     */
                }
            }
            fOut = response.getOutputStream();  
            workbook.write(fOut);  
        }catch (Exception e)  
        {e.printStackTrace();}  
        finally  
        {  
            try  
            {  
                fOut.flush();  
                fOut.close();  
            }  
            catch (IOException e)  
            {}  
        }  
        System.out.println("文件生成...");  
    }  
      
}  