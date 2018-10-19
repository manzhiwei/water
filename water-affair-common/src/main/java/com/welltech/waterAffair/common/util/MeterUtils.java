package com.welltech.waterAffair.common.util;

import com.welltech.waterAffair.domain.entity.MachineInfo;
import com.welltech.waterAffair.domain.entity.Ndata;
import com.welltech.waterAffair.domain.vo.NdataVo;

/**
 * 水表工具类
 * @author wangxin
 *
 */
public class MeterUtils {
	
	/**
	 * 是否为电磁流量计
	 * @param info
	 * @return
	 */
	public static boolean isGprs4300(MachineInfo info){
		if(info == null){
			throw new IllegalArgumentException("null object");
		}
		Integer type = info.getMeterTypeId();
		if(type != 1){
			return true;
		}
		return false;
	}

	/**
	 * 将实体类转换为VO对象
	 * @param ndata
	 * @param info
	 * @param meterId
	 * @return
	 */
	public static NdataVo getInstance(Ndata ndata, MachineInfo info){
		if(ndata == null){
			ndata = new Ndata();
		}
		NdataVo ndataVo = new NdataVo(ndata.getCurrenti(),ndata.getCurrentv(),ndata.getDepdata(),ndata.getEsignal(),ndata.getFlow()
				,ndata.getFlowerror(),ndata.getFtotalflow(),ndata.getiTime(),ndata.getLastconnecting(),ndata.getMe()
				,ndata.getNtotalflow(),ndata.getPress(),ndata.getPresserror(),ndata.getSignalStrength(),ndata.getTemp()
				,ndata.getTotalflow());
		ndataVo.setSubUserName(info.getSubUserName());
		ndataVo.setNum(info.getNum());
		ndataVo.setInstrNo(info.getInstrNo()); //add 18-10-20
		return ndataVo;
	}
}
