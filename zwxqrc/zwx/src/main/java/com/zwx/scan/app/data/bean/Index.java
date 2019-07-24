package com.zwx.scan.app.data.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * author : lizhilong
 * time   : 2018/11/26
 * desc   :
 * version: 1.0
 **/
public class Index implements Serializable {

    private String  curMemberTotal;

    private String memberTotal;

    private String curCampaignTotal;

    private String campaignTotal;

    private String curStaffTotal;

    private String monthStaffTotal;

  /*  private String  startDate;

    private String endDate;*/
  private String date;
    private List<Map<String,Object>> memberTotalReport;

    private List<Map<String,Object>> campaignTotalReport;

    private List<Map<String,Object>> staffTotalReport;


    private List<TResource> resourceList;


    public String getCurMemberTotal() {
        return curMemberTotal;
    }

    public void setCurMemberTotal(String curMemberTotal) {
        this.curMemberTotal = curMemberTotal;
    }

    public String getMemberTotal() {
        return memberTotal;
    }

    public void setMemberTotal(String memberTotal) {
        this.memberTotal = memberTotal;
    }

    public String getCurCampaignTotal() {
        return curCampaignTotal;
    }

    public void setCurCampaignTotal(String curCampaignTotal) {
        this.curCampaignTotal = curCampaignTotal;
    }

    public String getCampaignTotal() {
        return campaignTotal;
    }

    public void setCampaignTotal(String campaignTotal) {
        this.campaignTotal = campaignTotal;
    }

    public String getCurStaffTotal() {
        return curStaffTotal;
    }

    public void setCurStaffTotal(String curStaffTotal) {
        this.curStaffTotal = curStaffTotal;
    }

    public String getMonthStaffTotal() {
        return monthStaffTotal;
    }

    public void setMonthStaffTotal(String monthStaffTotal) {
        this.monthStaffTotal = monthStaffTotal;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Map<String, Object>> getMemberTotalReport() {
        return memberTotalReport;
    }

    public void setMemberTotalReport(List<Map<String, Object>> memberTotalReport) {
        this.memberTotalReport = memberTotalReport;
    }

    public List<Map<String, Object>> getCampaignTotalReport() {
        return campaignTotalReport;
    }

    public void setCampaignTotalReport(List<Map<String, Object>> campaignTotalReport) {
        this.campaignTotalReport = campaignTotalReport;
    }

    public List<Map<String, Object>> getStaffTotalReport() {
        return staffTotalReport;
    }

    public void setStaffTotalReport(List<Map<String, Object>> staffTotalReport) {
        this.staffTotalReport = staffTotalReport;
    }

    public List<TResource> getResourceList() {
        return resourceList;
    }

    public void setResourceList(List<TResource> resourceList) {
        this.resourceList = resourceList;
    }
}
