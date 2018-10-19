package com.welltech.waterAffair.domain.vo.before;

/**
 * Created by lenovo on 2017/3/2.
 */
public class MachineDetailInfo4MapVo {
    String lastUpdateTime; //最后抄表时间
    String flow;//瞬时流量
    String totalFlow;//正向累计流量
    String fTotalFlow;//反向累计流量
    String nTotalFlow;//净累计流量
    String battery;//电池电量
    String signal;//信号强度
    String press;//压力
    String voltage;//工作电压
    String current;//工作电流
    String updateTimes;//上传次数
    int status;

    public String getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getFlow() {
        return flow;
    }

    public void setFlow(String flow) {
        this.flow = flow;
    }

    public String getTotalFlow() {
        return totalFlow;
    }

    public void setTotalFlow(String totalFlow) {
        this.totalFlow = totalFlow;
    }

    public String getfTotalFlow() {
        return fTotalFlow;
    }

    public void setfTotalFlow(String fTotalFlow) {
        this.fTotalFlow = fTotalFlow;
    }

    public String getnTotalFlow() {
        return nTotalFlow;
    }

    public void setnTotalFlow(String nTotalFlow) {
        this.nTotalFlow = nTotalFlow;
    }

    public String getBattery() {
        return battery;
    }

    public void setBattery(String battery) {
        this.battery = battery;
    }

    public String getSignal() {
        return signal;
    }

    public void setSignal(String signal) {
        this.signal = signal;
    }

    public String getPress() {
        return press;
    }

    public void setPress(String press) {
        this.press = press;
    }

    public String getVoltage() {
        return voltage;
    }

    public void setVoltage(String voltage) {
        this.voltage = voltage;
    }

    public String getCurrent() {
        return current;
    }

    public void setCurrent(String current) {
        this.current = current;
    }

    public String getUpdateTimes() {
        return updateTimes;
    }

    public void setUpdateTimes(String updateTimes) {
        this.updateTimes = updateTimes;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
