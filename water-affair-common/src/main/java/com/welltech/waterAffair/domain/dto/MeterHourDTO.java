package com.welltech.waterAffair.domain.dto;

import com.welltech.waterAffair.domain.entity.Ndata;

/**
 * Created by myMac on 17/4/22.
 */
public class MeterHourDTO extends Ndata {

    //额外信息如下

    //公司名称
    String companyName;

    //流量昨日环比 （今日－昨日）／昨日
    double flowYesterdayChain;


}
