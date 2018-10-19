package com.welltech.waterAffair.domain.dto;

import java.util.Date;

/**
 * Created by myMac on 17/4/21.
 */
public class MeterRealtimeStaticsDTO {

    String meterName;

    double flowValue;

    Date flowTime;

    double pressValue;

    Date pressTime;

    public String getMeterName() {
        return meterName;
    }

    public void setMeterName(String meterName) {
        this.meterName = meterName;
    }

    public double getFlowValue() {
        return flowValue;
    }

    public void setFlowValue(double flowValue) {
        this.flowValue = flowValue;
    }

    public Date getFlowTime() {
        return flowTime;
    }

    public void setFlowTime(Date flowTime) {
        this.flowTime = flowTime;
    }

    public double getPressValue() {
        return pressValue;
    }

    public void setPressValue(double pressValue) {
        this.pressValue = pressValue;
    }

    public Date getPressTime() {
        return pressTime;
    }

    public void setPressTime(Date pressTime) {
        this.pressTime = pressTime;
    }
}
