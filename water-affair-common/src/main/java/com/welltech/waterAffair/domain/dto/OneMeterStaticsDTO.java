package com.welltech.waterAffair.domain.dto;

import com.welltech.waterAffair.domain.entity.MachineInfo;
import com.welltech.waterAffair.domain.entity.Ndata;
import com.welltech.waterAffair.domain.entity.NdataSs;
import com.welltech.waterAffair.domain.vo.ThreeDayVo;

import java.util.List;

/**
 * 单点数据统计DTO
 */
public class OneMeterStaticsDTO {

    //仪表基础信息
    MachineInfo machineInfo;

    //累计流量
    double totalFlow;

    //今日流量
    double todayFlow;

    //昨日同期流量
    double samePeroidYesterdayFlow;

    //昨日单天流量
    double yesterdayFlow;

    //实时小时数据
    Ndata ndata;

    //实时分钟数据
    NdataSs ndataSs;

    //瞬时流量
    List<ThreeDayVo> flowList;

    //压力
    List<ThreeDayVo> pressList;

    //正向累计
    List<ThreeDayVo> totalFlowList;

    //反向累计
    List<ThreeDayVo> ftotalFlowList;

    //工作电压
    List<ThreeDayVo> voltageList;

    //工作电流
    List<ThreeDayVo> currentList;

    //信号强度
    List<ThreeDayVo> signalList;




}
