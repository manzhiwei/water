package com.welltech.waterAffair.domain.dto;

import com.welltech.waterAffair.common.util.ConstantsUtil;
import com.welltech.waterAffair.domain.entity.MachineInfo;

public class MachineInfoDTO extends MachineInfo {

	private String meterSizeStr;

	public String getMeterSizeStr() {
		return ConstantsUtil.sbkjDic.get(getMeterSize());
	}
}
