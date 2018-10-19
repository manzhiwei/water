package com.welltech.waterAffair.domain.entity;

import java.io.Serializable;
import java.util.Date;

public class MachineInfo implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String machineid;

    private String gprsid;

    private Integer num;

    private Integer datanum;

    private Integer package_;

    private Integer time;

    private Integer signalStrength;

    private Integer upstate;

    private String mversion;

    private String gversion;

    private Integer estate;

    private String filename;

    private String ccid;

    private Date iTime;

    private Integer userNameId;

    private Double longitude;

    private Double latitude;

    private Float minflow;

    private Float maxflow;

    private Float mintotal;

    private Float maxtotal;

    private Float lowPower;

    private Integer lowSignal;

    private Integer alarmNotice;

    private String subUserName;

    private Integer debugMeter;

    private Integer debugMeterAddr;

    private Integer debugMeterLen;

    private Integer debugMeterRAddr;

    private Integer debugMeterRData;

    private Integer debugmeterRlen;

    private Integer debugMeterR;

    private Integer debugMeterData;

    private Integer diameter;

    private String metertype;

    private Integer connectIntervalTime;

    private Integer connectMaintainTime;

    private Integer sampleTime;

    private Integer timeState;

    private Integer highSampleTime;

    private Integer highSampleMaintain;

    private Integer highSampleMaintainDay;

    private Integer dmaTimeState;

    private Integer highSampleStartTime;

    private Integer highConnectIntervalTime;

    private Integer highDMAOpenStatus;

    private Integer meterParaWrite;

    private Integer meterParaRead;

    private Integer workMode;

    private Integer rWorkmode;

    private Integer meterSize;

    private Integer rMetersize;

    private Integer excitationFreq;

    private Integer rExcitationfreq;

    private Integer excitationI;

    private Integer rExcitationI;

    private Integer ePipe;

    private Integer rEpipe;

    private Integer ePipeFromMeter;

    private Integer ePipeThreshold;

    private Integer rEpipethreshold;

    private Float damping;

    private Float rDamping;

    private Integer unit;

    private Float primaryCz;

    private Float rPrimaryCz;

    private Integer meterSoftReset;

    private Integer rMetersoftreset;

    private Integer workModeFromMeter;

    private Integer meterSizeFromMeter;

    private Integer excitationFreqFromMeter;

    private Integer excitationIFromMeter;

    private Integer epipefrommeter;

    private Integer ePipeThresholdFromMeter;

    private Float dampingFromMeter;

    private Integer unitFromMeter;

    private Float primaryCzFromMeter;

    private Integer meterSoftResetFromMeter;

    private Float primaryCs;

    private Float rPrimarycs;

    private Float primaryCsFromMeter;

    private Integer meterParaWrite2;

    private Integer meterparawrite3;

    private Integer transportPermit;

    private Integer meterParaRead2;

    private Integer meterpararead3;

    private Integer softreset;

    private Integer watchdog;

    private Integer standby;

    private Integer poweron;

    private String meterName;

    private Integer meterTypeId;

    private String shortName;

    private Integer receiveHeartResponse;

    private String remark;

    private String address;

    private String instrNo;

    private String phoneNo;

    private String positionNo;

    private Date connectTime;

    private String convertNo;

    private Integer ifConnecting;

    private Float minPress;

    private Float maxPress;

    private Float lowFlowCut;

    private Float rLowflowcut;

    private Integer analogonoff;

    private Integer rAnalogonoff;

    private Integer pressOnOff;

    private Integer rPressonoff;

    private Float systemZero;

    private Float rSystemzero;

    private Float totalF;

    private Float rTotalf;

    private Float totalR;

    private Float rTotalr;

    private Integer analogTime;

    private Integer rAnalogtime;

    private Float pressHigh;

    private Float rPressHigh;

    private Date disConnectTime;

    private Integer onlyForward;

    private Float onlyforwarddata;

    private Integer isVipAccount;

    private String linkPeople;

    private String linkWay;

    private String linkAddress;

    private Integer powerTypeId;

    private String powerType;

    private String outputSignalTypeId;

    private String outputSignalType;

    private String meterNum;

    private String meterManufacturer;

    private String wellNumber;
    
	private String company;//这个字段不入库仅仅到时候存公司名返回到页面
	
	private Date activeTime;

	private Integer displayBoardRead;

	private Integer displayBoardWrite;

	private String rIp;

	private Integer rIpport;

	private String ip;

	private Integer ipPort;
	
	/**
	 * 超时设定：数据库无对应字段
	 */
	private int lastConnecting;
	
	/**
	 * 采样差值：数据库无对应字段
	 */
	private double diffHour;

    public String getMachineid() {
        return machineid;
    }

    public void setMachineid(String machineid) {
        this.machineid = machineid;
    }

    public String getGprsid() {
        return gprsid;
    }

    public void setGprsid(String gprsid) {
        this.gprsid = gprsid;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getDatanum() {
        return datanum;
    }

    public void setDatanum(Integer datanum) {
        this.datanum = datanum;
    }

    public Integer getPackage_() {
        return package_;
    }

    public void setPackage_(Integer package_) {
        this.package_ = package_;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public Integer getSignalStrength() {
        return signalStrength;
    }

    public void setSignalStrength(Integer signalStrength) {
        this.signalStrength = signalStrength;
    }

    public Integer getUpstate() {
        return upstate;
    }

    public void setUpstate(Integer upstate) {
        this.upstate = upstate;
    }

    public String getMversion() {
        return mversion;
    }

    public void setMversion(String mversion) {
        this.mversion = mversion;
    }

    public String getGversion() {
        return gversion;
    }

    public void setGversion(String gversion) {
        this.gversion = gversion;
    }

    public Integer getEstate() {
        return estate;
    }

    public void setEstate(Integer estate) {
        this.estate = estate;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getCcid() {
        return ccid;
    }

    public void setCcid(String ccid) {
        this.ccid = ccid;
    }

    public Date getiTime() {
        return iTime;
    }

    public void setiTime(Date iTime) {
        this.iTime = iTime;
    }

    public Integer getUserNameId() {
        return userNameId;
    }

    public void setUserNameId(Integer userNameId) {
        this.userNameId = userNameId;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Float getMinflow() {
        return minflow;
    }

    public void setMinflow(Float minflow) {
        this.minflow = minflow;
    }

    public Float getMaxflow() {
        return maxflow;
    }

    public void setMaxflow(Float maxflow) {
        this.maxflow = maxflow;
    }

    public Float getMintotal() {
        return mintotal;
    }

    public void setMintotal(Float mintotal) {
        this.mintotal = mintotal;
    }

    public Float getMaxtotal() {
        return maxtotal;
    }

    public void setMaxtotal(Float maxtotal) {
        this.maxtotal = maxtotal;
    }

    public Float getLowPower() {
        return lowPower;
    }

    public void setLowPower(Float lowPower) {
        this.lowPower = lowPower;
    }

    public Integer getLowSignal() {
        return lowSignal;
    }

    public void setLowSignal(Integer lowSignal) {
        this.lowSignal = lowSignal;
    }

    public Integer getAlarmNotice() {
        return alarmNotice;
    }

    public void setAlarmNotice(Integer alarmNotice) {
        this.alarmNotice = alarmNotice;
    }

    public String getSubUserName() {
        return subUserName;
    }

    public void setSubUserName(String subUserName) {
        this.subUserName = subUserName;
    }

    public Integer getDebugMeter() {
        return debugMeter;
    }

    public void setDebugMeter(Integer debugMeter) {
        this.debugMeter = debugMeter;
    }

    public Integer getDebugMeterAddr() {
        return debugMeterAddr;
    }

    public void setDebugMeterAddr(Integer debugMeterAddr) {
        this.debugMeterAddr = debugMeterAddr;
    }

    public Integer getDebugMeterLen() {
        return debugMeterLen;
    }

    public void setDebugMeterLen(Integer debugMeterLen) {
        this.debugMeterLen = debugMeterLen;
    }

    public Integer getDebugMeterRAddr() {
        return debugMeterRAddr;
    }

    public void setDebugMeterRAddr(Integer debugMeterRAddr) {
        this.debugMeterRAddr = debugMeterRAddr;
    }

    public Integer getDebugMeterRData() {
        return debugMeterRData;
    }

    public void setDebugMeterRData(Integer debugMeterRData) {
        this.debugMeterRData = debugMeterRData;
    }

    public Integer getDebugmeterRlen() {
        return debugmeterRlen;
    }

    public void setDebugmeterRlen(Integer debugmeterRlen) {
        this.debugmeterRlen = debugmeterRlen;
    }

    public Integer getDebugMeterR() {
        return debugMeterR;
    }

    public void setDebugMeterR(Integer debugMeterR) {
        this.debugMeterR = debugMeterR;
    }

    public Integer getDebugMeterData() {
        return debugMeterData;
    }

    public void setDebugMeterData(Integer debugMeterData) {
        this.debugMeterData = debugMeterData;
    }

    public Integer getDiameter() {
        return diameter;
    }

    public void setDiameter(Integer diameter) {
        this.diameter = diameter;
    }

    public String getMetertype() {
        return metertype;
    }

    public void setMetertype(String metertype) {
        this.metertype = metertype;
    }

    public Integer getConnectIntervalTime() {
        return connectIntervalTime;
    }

    public void setConnectIntervalTime(Integer connectIntervalTime) {
        this.connectIntervalTime = connectIntervalTime;
    }

    public Integer getConnectMaintainTime() {
        return connectMaintainTime;
    }

    public void setConnectMaintainTime(Integer connectMaintainTime) {
        this.connectMaintainTime = connectMaintainTime;
    }

    public Integer getSampleTime() {
        return sampleTime;
    }

    public void setSampleTime(Integer sampleTime) {
        this.sampleTime = sampleTime;
    }

    public Integer getTimeState() {
        return timeState;
    }

    public void setTimeState(Integer timeState) {
        this.timeState = timeState;
    }

    public Integer getHighSampleTime() {
        return highSampleTime;
    }

    public void setHighSampleTime(Integer highSampleTime) {
        this.highSampleTime = highSampleTime;
    }

    public Integer getHighSampleMaintain() {
        return highSampleMaintain;
    }

    public void setHighSampleMaintain(Integer highSampleMaintain) {
        this.highSampleMaintain = highSampleMaintain;
    }

    public Integer getHighSampleMaintainDay() {
        return highSampleMaintainDay;
    }

    public void setHighSampleMaintainDay(Integer highSampleMaintainDay) {
        this.highSampleMaintainDay = highSampleMaintainDay;
    }

    public Integer getDmaTimeState() {
        return dmaTimeState;
    }

    public void setDmaTimeState(Integer dmaTimeState) {
        this.dmaTimeState = dmaTimeState;
    }

    public Integer getHighSampleStartTime() {
        return highSampleStartTime;
    }

    public void setHighSampleStartTime(Integer highSampleStartTime) {
        this.highSampleStartTime = highSampleStartTime;
    }

    public Integer getHighConnectIntervalTime() {
        return highConnectIntervalTime;
    }

    public void setHighConnectIntervalTime(Integer highConnectIntervalTime) {
        this.highConnectIntervalTime = highConnectIntervalTime;
    }

    public Integer getHighDMAOpenStatus() {
        return highDMAOpenStatus;
    }

    public void setHighDMAOpenStatus(Integer highDMAOpenStatus) {
        this.highDMAOpenStatus = highDMAOpenStatus;
    }

    public Integer getMeterParaWrite() {
        return meterParaWrite;
    }

    public void setMeterParaWrite(Integer meterParaWrite) {
        this.meterParaWrite = meterParaWrite;
    }

    public Integer getMeterParaRead() {
        return meterParaRead;
    }

    public void setMeterParaRead(Integer meterParaRead) {
        this.meterParaRead = meterParaRead;
    }

    public Integer getWorkMode() {
        return workMode;
    }

    public void setWorkMode(Integer workMode) {
        this.workMode = workMode;
    }

    public Integer getrWorkmode() {
        return rWorkmode;
    }

    public void setrWorkmode(Integer rWorkmode) {
        this.rWorkmode = rWorkmode;
    }

    public Integer getMeterSize() {
        return meterSize;
    }

    public void setMeterSize(Integer meterSize) {
        this.meterSize = meterSize;
    }

    public Integer getrMetersize() {
        return rMetersize;
    }

    public void setrMetersize(Integer rMetersize) {
        this.rMetersize = rMetersize;
    }

    public Integer getExcitationFreq() {
        return excitationFreq;
    }

    public void setExcitationFreq(Integer excitationFreq) {
        this.excitationFreq = excitationFreq;
    }

    public Integer getrExcitationfreq() {
        return rExcitationfreq;
    }

    public void setrExcitationfreq(Integer rExcitationfreq) {
        this.rExcitationfreq = rExcitationfreq;
    }

    public Integer getExcitationI() {
        return excitationI;
    }

    public void setExcitationI(Integer excitationI) {
        this.excitationI = excitationI;
    }

    public Integer getrExcitationI() {
        return rExcitationI;
    }

    public void setrExcitationI(Integer rExcitationI) {
        this.rExcitationI = rExcitationI;
    }

    public Integer getePipe() {
        return ePipe;
    }

    public void setePipe(Integer ePipe) {
        this.ePipe = ePipe;
    }

    public Integer getrEpipe() {
        return rEpipe;
    }

    public void setrEpipe(Integer rEpipe) {
        this.rEpipe = rEpipe;
    }

    public Integer getePipeFromMeter() {
        return ePipeFromMeter;
    }

    public void setePipeFromMeter(Integer ePipeFromMeter) {
        this.ePipeFromMeter = ePipeFromMeter;
    }

    public Integer getrEpipethreshold() {
        return rEpipethreshold;
    }

    public void setrEpipethreshold(Integer rEpipethreshold) {
        this.rEpipethreshold = rEpipethreshold;
    }

    public Float getDamping() {
        return damping;
    }

    public void setDamping(Float damping) {
        this.damping = damping;
    }

    public Float getrDamping() {
        return rDamping;
    }

    public void setrDamping(Float rDamping) {
        this.rDamping = rDamping;
    }

    public Integer getUnit() {
        return unit;
    }

    public void setUnit(Integer unit) {
        this.unit = unit;
    }

    public Float getPrimaryCz() {
        return primaryCz;
    }

    public void setPrimaryCz(Float primaryCz) {
        this.primaryCz = primaryCz;
    }

    public Float getrPrimaryCz() {
        return rPrimaryCz;
    }

    public void setrPrimaryCz(Float rPrimaryCz) {
        this.rPrimaryCz = rPrimaryCz;
    }

    public Integer getMeterSoftReset() {
        return meterSoftReset;
    }

    public void setMeterSoftReset(Integer meterSoftReset) {
        this.meterSoftReset = meterSoftReset;
    }

    public Integer getrMetersoftreset() {
        return rMetersoftreset;
    }

    public void setrMetersoftreset(Integer rMetersoftreset) {
        this.rMetersoftreset = rMetersoftreset;
    }

    public Integer getWorkModeFromMeter() {
        return workModeFromMeter;
    }

    public void setWorkModeFromMeter(Integer workModeFromMeter) {
        this.workModeFromMeter = workModeFromMeter;
    }

    public Integer getMeterSizeFromMeter() {
        return meterSizeFromMeter;
    }

    public void setMeterSizeFromMeter(Integer meterSizeFromMeter) {
        this.meterSizeFromMeter = meterSizeFromMeter;
    }

    public Integer getExcitationFreqFromMeter() {
        return excitationFreqFromMeter;
    }

    public void setExcitationFreqFromMeter(Integer excitationFreqFromMeter) {
        this.excitationFreqFromMeter = excitationFreqFromMeter;
    }

    public Integer getExcitationIFromMeter() {
        return excitationIFromMeter;
    }

    public void setExcitationIFromMeter(Integer excitationIFromMeter) {
        this.excitationIFromMeter = excitationIFromMeter;
    }

    public Integer getEpipefrommeter() {
        return epipefrommeter;
    }

    public void setEpipefrommeter(Integer epipefrommeter) {
        this.epipefrommeter = epipefrommeter;
    }

    public Integer getePipeThresholdFromMeter() {
        return ePipeThresholdFromMeter;
    }

    public void setePipeThresholdFromMeter(Integer ePipeThresholdFromMeter) {
        this.ePipeThresholdFromMeter = ePipeThresholdFromMeter;
    }

    public Float getDampingFromMeter() {
        return dampingFromMeter;
    }

    public void setDampingFromMeter(Float dampingFromMeter) {
        this.dampingFromMeter = dampingFromMeter;
    }

    public Integer getUnitFromMeter() {
        return unitFromMeter;
    }

    public void setUnitFromMeter(Integer unitFromMeter) {
        this.unitFromMeter = unitFromMeter;
    }

    public Float getPrimaryCzFromMeter() {
        return primaryCzFromMeter;
    }

    public void setPrimaryCzFromMeter(Float primaryCzFromMeter) {
        this.primaryCzFromMeter = primaryCzFromMeter;
    }

    public Integer getMeterSoftResetFromMeter() {
        return meterSoftResetFromMeter;
    }

    public void setMeterSoftResetFromMeter(Integer meterSoftResetFromMeter) {
        this.meterSoftResetFromMeter = meterSoftResetFromMeter;
    }

    public Float getPrimaryCs() {
        return primaryCs;
    }

    public void setPrimaryCs(Float primaryCs) {
        this.primaryCs = primaryCs;
    }

    public Float getrPrimarycs() {
        return rPrimarycs;
    }

    public void setrPrimarycs(Float rPrimarycs) {
        this.rPrimarycs = rPrimarycs;
    }

    public Float getPrimaryCsFromMeter() {
        return primaryCsFromMeter;
    }

    public void setPrimaryCsFromMeter(Float primaryCsFromMeter) {
        this.primaryCsFromMeter = primaryCsFromMeter;
    }

    public Integer getMeterParaWrite2() {
        return meterParaWrite2;
    }

    public void setMeterParaWrite2(Integer meterParaWrite2) {
        this.meterParaWrite2 = meterParaWrite2;
    }

    public Integer getMeterparawrite3() {
        return meterparawrite3;
    }

    public void setMeterparawrite3(Integer meterparawrite3) {
        this.meterparawrite3 = meterparawrite3;
    }

    public Integer getTransportPermit() {
        return transportPermit;
    }

    public void setTransportPermit(Integer transportPermit) {
        this.transportPermit = transportPermit;
    }

    public Integer getMeterParaRead2() {
        return meterParaRead2;
    }

    public void setMeterParaRead2(Integer meterParaRead2) {
        this.meterParaRead2 = meterParaRead2;
    }

    public Integer getMeterpararead3() {
        return meterpararead3;
    }

    public void setMeterpararead3(Integer meterpararead3) {
        this.meterpararead3 = meterpararead3;
    }

    public Integer getSoftreset() {
        return softreset;
    }

    public void setSoftreset(Integer softreset) {
        this.softreset = softreset;
    }

    public Integer getWatchdog() {
        return watchdog;
    }

    public void setWatchdog(Integer watchdog) {
        this.watchdog = watchdog;
    }

    public Integer getStandby() {
        return standby;
    }

    public void setStandby(Integer standby) {
        this.standby = standby;
    }

    public Integer getPoweron() {
        return poweron;
    }

    public void setPoweron(Integer poweron) {
        this.poweron = poweron;
    }

    public String getMeterName() {
        return meterName;
    }

    public void setMeterName(String meterName) {
        this.meterName = meterName;
    }

    public Integer getMeterTypeId() {
        return meterTypeId;
    }

    public void setMeterTypeId(Integer meterTypeId) {
        this.meterTypeId = meterTypeId;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public Integer getReceiveHeartResponse() {
        return receiveHeartResponse;
    }

    public void setReceiveHeartResponse(Integer receiveHeartResponse) {
        this.receiveHeartResponse = receiveHeartResponse;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getInstrNo() {
        return instrNo;
    }

    public void setInstrNo(String instrNo) {
        this.instrNo = instrNo;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getPositionNo() {
        return positionNo;
    }

    public void setPositionNo(String positionNo) {
        this.positionNo = positionNo;
    }

    public Date getConnectTime() {
        return connectTime;
    }

    public void setConnectTime(Date connectTime) {
        this.connectTime = connectTime;
    }

    public String getConvertNo() {
        return convertNo;
    }

    public void setConvertNo(String convertNo) {
        this.convertNo = convertNo;
    }

    public Integer getIfConnecting() {
        return ifConnecting;
    }

    public void setIfConnecting(Integer ifConnecting) {
        this.ifConnecting = ifConnecting;
    }

    public Float getMinPress() {
        return minPress;
    }

    public void setMinPress(Float minPress) {
        this.minPress = minPress;
    }

    public Float getMaxPress() {
        return maxPress;
    }

    public void setMaxPress(Float maxPress) {
        this.maxPress = maxPress;
    }

    public Float getLowFlowCut() {
        return lowFlowCut;
    }

    public void setLowFlowCut(Float lowFlowCut) {
        this.lowFlowCut = lowFlowCut;
    }

    public Float getrLowflowcut() {
        return rLowflowcut;
    }

    public void setrLowflowcut(Float rLowflowcut) {
        this.rLowflowcut = rLowflowcut;
    }

    public Integer getAnalogonoff() {
        return analogonoff;
    }

    public void setAnalogonoff(Integer analogonoff) {
        this.analogonoff = analogonoff;
    }

    public Integer getrAnalogonoff() {
        return rAnalogonoff;
    }

    public void setrAnalogonoff(Integer rAnalogonoff) {
        this.rAnalogonoff = rAnalogonoff;
    }

    public Integer getPressOnOff() {
        return pressOnOff;
    }

    public void setPressOnOff(Integer pressOnOff) {
        this.pressOnOff = pressOnOff;
    }

    public Integer getrPressonoff() {
        return rPressonoff;
    }

    public void setrPressonoff(Integer rPressonoff) {
        this.rPressonoff = rPressonoff;
    }

    public Float getSystemZero() {
        return systemZero;
    }

    public void setSystemZero(Float systemZero) {
        this.systemZero = systemZero;
    }

    public Float getrSystemzero() {
        return rSystemzero;
    }

    public void setrSystemzero(Float rSystemzero) {
        this.rSystemzero = rSystemzero;
    }

    public Float getTotalF() {
        return totalF;
    }

    public void setTotalF(Float totalF) {
        this.totalF = totalF;
    }

    public Float getrTotalf() {
        return rTotalf;
    }

    public void setrTotalf(Float rTotalf) {
        this.rTotalf = rTotalf;
    }

    public Float getTotalR() {
        return totalR;
    }

    public void setTotalR(Float totalR) {
        this.totalR = totalR;
    }

    public Float getrTotalr() {
        return rTotalr;
    }

    public void setrTotalr(Float rTotalr) {
        this.rTotalr = rTotalr;
    }

    public Integer getAnalogTime() {
        return analogTime;
    }

    public void setAnalogTime(Integer analogTime) {
        this.analogTime = analogTime;
    }

    public Integer getrAnalogtime() {
        return rAnalogtime;
    }

    public void setrAnalogtime(Integer rAnalogtime) {
        this.rAnalogtime = rAnalogtime;
    }

    public Float getPressHigh() {
        return pressHigh;
    }

    public void setPressHigh(Float pressHigh) {
        this.pressHigh = pressHigh;
    }

    public Float getrPressHigh() {
        return rPressHigh;
    }

    public void setrPressHigh(Float rPressHigh) {
        this.rPressHigh = rPressHigh;
    }

    public Date getDisConnectTime() {
        return disConnectTime;
    }

    public void setDisConnectTime(Date disConnectTime) {
        this.disConnectTime = disConnectTime;
    }

    public Integer getOnlyForward() {
        return onlyForward;
    }

    public void setOnlyForward(Integer onlyForward) {
        this.onlyForward = onlyForward;
    }

    public Float getOnlyforwarddata() {
        return onlyforwarddata;
    }

    public void setOnlyforwarddata(Float onlyforwarddata) {
        this.onlyforwarddata = onlyforwarddata;
    }

    public Integer getIsVipAccount() {
        return isVipAccount;
    }

    public void setIsVipAccount(Integer isVipAccount) {
        this.isVipAccount = isVipAccount;
    }

    public String getLinkPeople() {
        return linkPeople;
    }

    public void setLinkPeople(String linkPeople) {
        this.linkPeople = linkPeople;
    }

    public String getLinkWay() {
        return linkWay;
    }

    public void setLinkWay(String linkWay) {
        this.linkWay = linkWay;
    }

    public String getLinkAddress() {
        return linkAddress;
    }

    public void setLinkAddress(String linkAddress) {
        this.linkAddress = linkAddress;
    }

    public Integer getPowerTypeId() {
        return powerTypeId;
    }

    public void setPowerTypeId(Integer powerTypeId) {
        this.powerTypeId = powerTypeId;
    }

    public String getPowerType() {
        return powerType;
    }

    public void setPowerType(String powerType) {
        this.powerType = powerType;
    }

    public String getOutputSignalTypeId() {
        return outputSignalTypeId;
    }

    public void setOutputSignalTypeId(String outputSignalTypeId) {
        this.outputSignalTypeId = outputSignalTypeId;
    }

    public String getOutputSignalType() {
        return outputSignalType;
    }

    public void setOutputSignalType(String outputSignalType) {
        this.outputSignalType = outputSignalType;
    }

    public String getMeterNum() {
        return meterNum;
    }

    public void setMeterNum(String meterNum) {
        this.meterNum = meterNum;
    }

    public String getMeterManufacturer() {
        return meterManufacturer;
    }

    public void setMeterManufacturer(String meterManufacturer) {
        this.meterManufacturer = meterManufacturer;
    }

    public String getWellNumber() {
        return wellNumber;
    }

    public void setWellNumber(String wellNumber) {
        this.wellNumber = wellNumber;
    }

    public Integer getePipeThreshold() {
        return ePipeThreshold;
    }

    public void setePipeThreshold(Integer ePipeThreshold) {
        this.ePipeThreshold = ePipeThreshold;
    }

    public Integer getDisplayBoardRead() {
        return displayBoardRead;
    }

    public void setDisplayBoardRead(Integer displayBoardRead) {
        this.displayBoardRead = displayBoardRead;
    }

    public Integer getDisplayBoardWrite() {
        return displayBoardWrite;
    }

    public void setDisplayBoardWrite(Integer displayBoardWrite) {
        this.displayBoardWrite = displayBoardWrite;
    }

    public String getrIp() {
        return rIp;
    }

    public void setrIp(String rIp) {
        this.rIp = rIp;
    }

    public Integer getrIpport() {
        return rIpport;
    }

    public void setrIpport(Integer rIpport) {
        this.rIpport = rIpport;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getIpPort() {
        return ipPort;
    }

    public void setIpPort(Integer ipPort) {
        this.ipPort = ipPort;
    }

    @Override
    public String toString() {
        return "MachineInfo{" +
                "machineid='" + machineid + '\'' +
                ", gprsid='" + gprsid + '\'' +
                ", num=" + num +
                ", datanum=" + datanum +
                ", package_=" + package_ +
                ", time=" + time +
                ", signalStrength=" + signalStrength +
                ", upstate=" + upstate +
                ", mversion='" + mversion + '\'' +
                ", gversion='" + gversion + '\'' +
                ", estate=" + estate +
                ", filename='" + filename + '\'' +
                ", ccid='" + ccid + '\'' +
                ", iTime=" + iTime +
                ", userNameId=" + userNameId +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", minflow=" + minflow +
                ", maxflow=" + maxflow +
                ", mintotal=" + mintotal +
                ", maxtotal=" + maxtotal +
                ", lowPower=" + lowPower +
                ", lowSignal=" + lowSignal +
                ", alarmNotice=" + alarmNotice +
                ", subUserName='" + subUserName + '\'' +
                ", debugMeter=" + debugMeter +
                ", debugMeterAddr=" + debugMeterAddr +
                ", debugMeterLen=" + debugMeterLen +
                ", debugMeterRAddr=" + debugMeterRAddr +
                ", debugMeterRData=" + debugMeterRData +
                ", debugmeterRlen=" + debugmeterRlen +
                ", debugMeterR=" + debugMeterR +
                ", debugMeterData=" + debugMeterData +
                ", diameter=" + diameter +
                ", metertype='" + metertype + '\'' +
                ", connectIntervalTime=" + connectIntervalTime +
                ", connectMaintainTime=" + connectMaintainTime +
                ", sampleTime=" + sampleTime +
                ", timeState=" + timeState +
                ", highSampleTime=" + highSampleTime +
                ", highSampleMaintain=" + highSampleMaintain +
                ", highSampleMaintainDay=" + highSampleMaintainDay +
                ", dmaTimeState=" + dmaTimeState +
                ", highSampleStartTime=" + highSampleStartTime +
                ", highConnectIntervalTime=" + highConnectIntervalTime +
                ", highDMAOpenStatus=" + highDMAOpenStatus +
                ", meterParaWrite=" + meterParaWrite +
                ", meterParaRead=" + meterParaRead +
                ", workMode=" + workMode +
                ", rWorkmode=" + rWorkmode +
                ", meterSize=" + meterSize +
                ", rMetersize=" + rMetersize +
                ", excitationFreq=" + excitationFreq +
                ", rExcitationfreq=" + rExcitationfreq +
                ", excitationI=" + excitationI +
                ", rExcitationI=" + rExcitationI +
                ", ePipe=" + ePipe +
                ", rEpipe=" + rEpipe +
                ", ePipeFromMeter=" + ePipeFromMeter +
                ", rEpipethreshold=" + rEpipethreshold +
                ", damping=" + damping +
                ", rDamping=" + rDamping +
                ", unit=" + unit +
                ", primaryCz=" + primaryCz +
                ", rPrimaryCz=" + rPrimaryCz +
                ", meterSoftReset=" + meterSoftReset +
                ", rMetersoftreset=" + rMetersoftreset +
                ", workModeFromMeter=" + workModeFromMeter +
                ", meterSizeFromMeter=" + meterSizeFromMeter +
                ", excitationFreqFromMeter=" + excitationFreqFromMeter +
                ", excitationIFromMeter=" + excitationIFromMeter +
                ", epipefrommeter=" + epipefrommeter +
                ", ePipeThresholdFromMeter=" + ePipeThresholdFromMeter +
                ", dampingFromMeter=" + dampingFromMeter +
                ", unitFromMeter=" + unitFromMeter +
                ", primaryCzFromMeter=" + primaryCzFromMeter +
                ", meterSoftResetFromMeter=" + meterSoftResetFromMeter +
                ", primaryCs=" + primaryCs +
                ", rPrimarycs=" + rPrimarycs +
                ", primaryCsFromMeter=" + primaryCsFromMeter +
                ", meterParaWrite2=" + meterParaWrite2 +
                ", meterparawrite3=" + meterparawrite3 +
                ", transportPermit=" + transportPermit +
                ", meterParaRead2=" + meterParaRead2 +
                ", meterpararead3=" + meterpararead3 +
                ", softreset=" + softreset +
                ", watchdog=" + watchdog +
                ", standby=" + standby +
                ", poweron=" + poweron +
                ", meterName='" + meterName + '\'' +
                ", meterTypeId=" + meterTypeId +
                ", shortName='" + shortName + '\'' +
                ", receiveHeartResponse=" + receiveHeartResponse +
                ", remark='" + remark + '\'' +
                ", address='" + address + '\'' +
                ", instrNo='" + instrNo + '\'' +
                ", phoneNo='" + phoneNo + '\'' +
                ", positionNo='" + positionNo + '\'' +
                ", connectTime=" + connectTime +
                ", convertNo='" + convertNo + '\'' +
                ", ifConnecting=" + ifConnecting +
                ", minPress=" + minPress +
                ", maxPress=" + maxPress +
                ", lowFlowCut=" + lowFlowCut +
                ", rLowflowcut=" + rLowflowcut +
                ", analogonoff=" + analogonoff +
                ", rAnalogonoff=" + rAnalogonoff +
                ", pressOnOff=" + pressOnOff +
                ", rPressonoff=" + rPressonoff +
                ", systemZero=" + systemZero +
                ", rSystemzero=" + rSystemzero +
                ", totalF=" + totalF +
                ", rTotalf=" + rTotalf +
                ", totalR=" + totalR +
                ", rTotalr=" + rTotalr +
                ", analogTime=" + analogTime +
                ", rAnalogtime=" + rAnalogtime +
                ", pressHigh=" + pressHigh +
                ", rPressHigh=" + rPressHigh +
                ", disConnectTime=" + disConnectTime +
                ", onlyForward=" + onlyForward +
                ", onlyforwarddata=" + onlyforwarddata +
                ", isVipAccount='" + isVipAccount + '\'' +
                ", linkPeople='" + linkPeople + '\'' +
                ", linkWay='" + linkWay + '\'' +
                ", linkAddress='" + linkAddress + '\'' +
                ", powerTypeId=" + powerTypeId +
                ", powerType='" + powerType + '\'' +
                ", outputSignalTypeId='" + outputSignalTypeId + '\'' +
                ", outputSignalType='" + outputSignalType + '\'' +
                ", meterNum='" + meterNum + '\'' +
                ", meterManufacturer='" + meterManufacturer + '\'' +
                ", wellNumber='" + wellNumber + '\'' +
                ", activeTime='" + activeTime + '\'' +
                '}';
    }

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public Date getActiveTime() {
		return activeTime;
	}

	public void setActiveTime(Date activeTime) {
		this.activeTime = activeTime;
	}

	public int getLastConnecting() {
		return lastConnecting;
	}

	public void setLastConnecting(int lastConnecting) {
		this.lastConnecting = lastConnecting;
	}

	public double getDiffHour() {
		return diffHour;
	}

	public void setDiffHour(double diffHour) {
		this.diffHour = diffHour;
	}
    
}