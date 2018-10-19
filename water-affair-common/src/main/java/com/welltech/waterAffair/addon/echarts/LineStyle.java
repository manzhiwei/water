package com.welltech.waterAffair.addon.echarts;

/**
 * Created by myMac on 17/1/13.
 */
public class LineStyle {
    Style normal;

    public LineStyle(Style normal){
        this.normal=normal;
    }

    public Style getNormal() {
        return normal;
    }

    public void setNormal(Style normal) {
        this.normal = normal;
    }
}
