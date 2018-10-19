package com.welltech.waterAffair.addon.echarts;

/**
 * Created by myMac on 17/1/13.
 */
public class Style {
    String color;
    String type;
    int width=2;

    public Style(String color,String type){
        this.color=color;
        this.type=type;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
