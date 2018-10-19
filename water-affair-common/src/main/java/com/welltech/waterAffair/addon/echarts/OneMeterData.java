package com.welltech.waterAffair.addon.echarts;

/**
 * Created by lenovo on 2017/3/6.
 */
public class OneMeterData {

    double[] yesterday;
    double[] dbyesterday;
    Double[] today;
    String[] xAxis;
    String unit;

    public double[] getYesterday() {
        return yesterday;
    }

    public void setYesterday(double[] yesterday) {
        this.yesterday = yesterday;
    }

    public double[] getDbyesterday() {
        return dbyesterday;
    }

    public void setDbyesterday(double[] dbyesterday) {
        this.dbyesterday = dbyesterday;
    }

    public Double[] getToday() {
        return today;
    }

    public void setToday(Double[] today) {
        this.today = today;
    }

	public String[] getXAxis() {
		return xAxis;
	}

	public void setXAxis(String[] xAxis) {
		this.xAxis = xAxis;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	
}
