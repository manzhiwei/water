package com.welltech.waterAffair.domain.vo;

import java.util.List;

import com.welltech.waterAffair.domain.dto.ResourceImgDTO;
import com.welltech.waterAffair.domain.entity.MachineInfo;

/**
 * 水表详情
 * @author zhoupei
 *
 */
public class MeterDetailVo {
	/**水表详细*/
	private MachineInfo machineInfo2;
	/**资源文件*/
	private List<ResourceImgDTO> resourceImg;
	/**检修记录*/
	private List<Object> checkFixRecord;
	/**检定记录*/
	private List<Object> checkConfirmRecord;
	/**电池更换记录*/
	private List<Object> batteryChangeRecord;
	/**电池告警记录*/
	private List<Object> warningRecord;
	public MachineInfo getMachineInfo2() {
		return machineInfo2;
	}
	public void setMachineInfo2(MachineInfo machineInfo2) {
		this.machineInfo2 = machineInfo2;
	}
	public List<Object> getCheckFixRecord() {
		return checkFixRecord;
	}
	public void setCheckFixRecord(List<Object> checkFixRecord) {
		this.checkFixRecord = checkFixRecord;
	}
	public List<Object> getCheckConfirmRecord() {
		return checkConfirmRecord;
	}
	public void setCheckConfirmRecord(List<Object> checkConfirmRecord) {
		this.checkConfirmRecord = checkConfirmRecord;
	}
	public List<Object> getBatteryChangeRecord() {
		return batteryChangeRecord;
	}
	public void setBatteryChangeRecord(List<Object> batteryChangeRecord) {
		this.batteryChangeRecord = batteryChangeRecord;
	}
	public List<Object> getWarningRecord() {
		return warningRecord;
	}
	public void setWarningRecord(List<Object> warningRecord) {
		this.warningRecord = warningRecord;
	}
	public List<ResourceImgDTO> getResourceImg() {
		return resourceImg;
	}
	public void setResourceImg(List<ResourceImgDTO> resourceImg) {
		this.resourceImg = resourceImg;
	}
	
}
