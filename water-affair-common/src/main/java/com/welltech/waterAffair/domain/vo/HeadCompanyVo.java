package com.welltech.waterAffair.domain.vo;

import com.welltech.waterAffair.domain.entity.Company;
import com.welltech.waterAffair.domain.entity.MachineInfo;

import java.util.List;

/**
 * 用户公司信息(总公司－分公司－区域)
 *
 *
 */
public class HeadCompanyVo {

    //用户id
    private Integer userId;

    /**
     * 公司id
     */
    private Integer companyId;

    /**
     * 公司名称
     */
    private String companyName;

    //用户分公司列表
    private List<BranchCompanyVo> branchList;

    //直属
    private List<MachineInfo> meterList;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public List<BranchCompanyVo> getBranchList() {
        return branchList;
    }

    public void setBranchList(List<BranchCompanyVo> branchList) {
        this.branchList = branchList;
    }

    public List<MachineInfo> getMeterList() {
        return meterList;
    }

    public void setMeterList(List<MachineInfo> meterList) {
        this.meterList = meterList;
    }
}
