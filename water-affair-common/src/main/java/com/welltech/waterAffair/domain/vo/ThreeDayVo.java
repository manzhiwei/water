package com.welltech.waterAffair.domain.vo;

/**
 * ryan
 * 水表三天的数据
 */
public class ThreeDayVo {

    int hour;
    double today;
    double yesterday;
    double dayBeforeYesterday;

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public double getToday() {
        return today;
    }

    public void setToday(double today) {
        this.today = today;
    }

    public double getYesterday() {
        return yesterday;
    }

    public void setYesterday(double yesterday) {
        this.yesterday = yesterday;
    }

    public double getDayBeforeYesterday() {
        return dayBeforeYesterday;
    }

    public void setDayBeforeYesterday(double dayBeforeYesterday) {
        this.dayBeforeYesterday = dayBeforeYesterday;
    }
}
