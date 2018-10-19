package com.welltech.controller.rest;

import com.welltech.controller.page.BasicManagePageController;
import com.welltech.waterAffair.common.util.UserUtils;
import com.welltech.waterAffair.domain.dto.MachineInfoDTO;
import com.welltech.waterAffair.domain.entity.MachineInfo;
import com.welltech.waterAffair.domain.vo.NdataVo;
import com.welltech.waterAffair.domain.vo.PageVo;
import com.welltech.waterAffair.service.BasicManageService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
@RestController
public class SystemToolsController {
    private static Logger logger = Logger
            .getLogger(SystemToolsController.class);
    @Autowired
    BasicManageService basicManageService;


    /**
     * 根据水表名称查询水表的IP和Port
     */
    @RequestMapping(value={"/queryIPAndPortByMeterName"}, method = RequestMethod.POST)
    public PageVo<MachineInfo> queryIPAndPort(HttpServletRequest request){
        Integer userId=UserUtils.getUserId();
        String shortName = null;
        PageVo<MachineInfo> result = null;
        shortName = request.getParameter("shortName");
        if(shortName != null)
            result = basicManageService.queryMachineIPAndPort(shortName);
        if(result != null)
            return result;
        else
            return null;
    }

    /**
     * 读显示板
     * @param request
     * @return
     */
    @RequestMapping(value = {"/readDisplayBoard"},method = RequestMethod.POST)
    public  PageVo<MachineInfo> readDisplayBoard(HttpServletRequest request){
        Integer num = 0;
        MachineInfo result;
        List<MachineInfo> resultList = null;
        PageVo<MachineInfo> resultSet;
        num = Integer.parseInt(request.getParameter("num"));
        if(num != null){
            result = basicManageService.queryMachineInfo2(num);
            if(result != null) {
                resultList.add(result);
            }
        }
        if(resultList != null){
            resultSet = new PageVo<>(1,1,10,resultList);
            return  resultSet;
        }
        return null;
    }

    /**
     * ADD by MANZHIWEI
     * 更新表端的IP和端口
     * 判断是否是管理员
     * 判断版本，Mversion >= 1.45
     * 设置权限为4，dispalyBoardWrite = 4
     * 如果当前权限已经是4了，下发命令还没有执行，若为0，设置当前权限为4
     * 设置好参数以后，命令不会立刻就下发，而是等待下次连接后执行下发
     * 再下次，将会连接到新的IP和Port
     * @param request
     * @return
     */
    @RequestMapping(value = {"/updateIpAndPort"},method = RequestMethod.POST)//TODO 读得命令没有做，也是需要授予权限 4，然后才能读回ip和port
    public String updateIpAndPort(HttpServletRequest request){
        Integer userId = UserUtils.getUserId();
        String result = null;
        if(userId != 1)
            return "权限不足";
        String num  = request.getParameter("num");
        String ip   = request.getParameter("ip");
        String port = request.getParameter("port");
        if(num != null && ip != null && port != null )
            result = basicManageService.updateMachineIPAndPort(num,ip,port);
           else
               return "参数不足";

        return  result;
    }
}
