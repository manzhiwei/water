package com.welltech.waterAffair.domain.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2016/12/26.
 */
public class BranchCompanyVo4JS {
    private String text;
    private String id;
    private  int mid;
    private Double longitude;
    private Double latitude;
    private String icon;
    private List<BranchCompanyAreaVo4JS> children=new ArrayList<BranchCompanyAreaVo4JS>();

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<BranchCompanyAreaVo4JS> getChildren() {
        return children;
    }

    public void setChildren(List<BranchCompanyAreaVo4JS> children) {
        this.children = children;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
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
}
