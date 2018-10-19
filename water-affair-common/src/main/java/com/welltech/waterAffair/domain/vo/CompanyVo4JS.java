package com.welltech.waterAffair.domain.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2016/12/26.
 */
public class CompanyVo4JS {
    /**公司名*/
    private String text;

    private String id;

    private int mid;

    private List<BranchCompanyVo4JS> children=new ArrayList<BranchCompanyVo4JS>();

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<BranchCompanyVo4JS> getChildren() {
        return children;
    }

    public void setChildren(List<BranchCompanyVo4JS> children) {
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
}
