package com.zwx.scan.app.data.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * author : lizhilong
 * time   : 2018/12/11
 * desc   :
 * version: 1.0
 **/
public class StaffCountBean implements Serializable {

    private List<CampaignChartBean> staffReport;
    private String total;
    private List<CampaignChartBean> pStaffReport;  //拉新任务统计
    private List<CampaignChartBean> psStaffReport; //拉新成功统计
    private String ptotal;  //拉新任务数
    private String pstotal; //拉新成功数
    public List<CampaignChartBean> getStaffReport() {
        return staffReport;
    }

    public void setStaffReport(List<CampaignChartBean> staffReport) {
        this.staffReport = staffReport;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<CampaignChartBean> getpStaffReport() {
        return pStaffReport;
    }

    public void setpStaffReport(List<CampaignChartBean> pStaffReport) {
        this.pStaffReport = pStaffReport;
    }

    public List<CampaignChartBean> getPsStaffReport() {
        return psStaffReport;
    }

    public void setPsStaffReport(List<CampaignChartBean> psStaffReport) {
        this.psStaffReport = psStaffReport;
    }

    public String getPtotal() {
        return ptotal;
    }

    public void setPtotal(String ptotal) {
        this.ptotal = ptotal;
    }

    public String getPstotal() {
        return pstotal;
    }

    public void setPstotal(String pstotal) {
        this.pstotal = pstotal;
    }
}
