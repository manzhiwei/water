package com.welltech.waterAffair.addon.echarts;

import java.util.List;

/**
 * Created by myMac on 17/1/14.
 */
public class Option {
    List<String> legend;
    List<Series> series;
    double maxFlow=0,minFlow=0,maxPress=0,minPress=0,deltaFlow=0,deltaPress=0;
    public List<Series> getSeries() {
        return series;
    }

    public void setSeries(List<Series> series) {
        this.series = series;
    }

    public List<String> getLegend() {
        return legend;
    }

    public void setLegend(List<String> legend) {
        this.legend = legend;
    }

    public double getMaxFlow() {
        return maxFlow;
    }

    public void setMaxFlow(double maxFlow) {
        this.maxFlow = maxFlow;
    }

    public double getMinFlow() {
        return minFlow;
    }

    public void setMinFlow(double minFlow) {
        this.minFlow = minFlow;
    }

    public double getMaxPress() {
        return maxPress;
    }

    public void setMaxPress(double maxPress) {
        this.maxPress = maxPress;
    }

    public double getMinPress() {
        return minPress;
    }

    public void setMinPress(double minPress) {
        this.minPress = minPress;
    }

    public double getDeltaFlow() {
        return deltaFlow;
    }

    public void setDeltaFlow(double deltaFlow) {
        this.deltaFlow = deltaFlow;
    }

    public double getDeltaPress() {
        return deltaPress;
    }

    public void setDeltaPress(double deltaPress) {
        this.deltaPress = deltaPress;
    }
}
