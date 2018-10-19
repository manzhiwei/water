package com.welltech.waterAffair.addon.echarts;

import java.util.List;

/**
 * Created by lenovo on 2017/1/13.
 */
public class Series {

    public double max4x;
    public double max4y;
    public String name;
    public String type="line";
    public int yAxisIndex=0;
    public int symbolSize=1;
    public String symbol;
    public String smooth="true";
    public LineStyle lineStyle;
    public ItemStyle itemStyle;
    public List<List<Double>> data;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<List<Double>> getData() {
        return data;
    }

    public void setData(List<List<Double>> data) {
        this.data = data;
    }

    public LineStyle getLineStyle() {
        return lineStyle;
    }

    public void setLineStyle(LineStyle lineStyle) {
        this.lineStyle = lineStyle;
    }

    public ItemStyle getItemStyle() {
        return itemStyle;
    }

    public void setItemStyle(ItemStyle itemStyle) {
        this.itemStyle = itemStyle;
    }

    public String getSmooth() {
        return smooth;
    }

    public void setSmooth(String smooth) {
        this.smooth = smooth;
    }

    public int getyAxisIndex() {
        return yAxisIndex;
    }

    public void setyAxisIndex(int yAxisIndex) {
        this.yAxisIndex = yAxisIndex;
    }

    public int getSymbolSize() {
        return symbolSize;
    }

    public void setSymbolSize(int symbolSize) {
        this.symbolSize = symbolSize;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public double getMax4x() {
        return max4x;
    }

    public void setMax4x(double max4x) {
        this.max4x = max4x;
    }

    public double getMax4y() {
        return max4y;
    }

    public void setMax4y(double max4y) {
        this.max4y = max4y;
    }
}
