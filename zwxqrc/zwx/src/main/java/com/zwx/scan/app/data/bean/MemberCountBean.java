package com.zwx.scan.app.data.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * author : lizhilong
 * time   : 2018/12/11
 * desc   :
 * version: 1.0
 **/
public class MemberCountBean implements Serializable {

    private String date;

    List<CampaignChartBean> memberReportList;

    private String storeMemberTotal;

    private String memberReportTotal;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<CampaignChartBean> getMemberReportList() {
        return memberReportList;
    }

    public void setMemberReportList(List<CampaignChartBean> memberReportList) {
        this.memberReportList = memberReportList;
    }

    public String getStoreMemberTotal() {
        return storeMemberTotal;
    }

    public void setStoreMemberTotal(String storeMemberTotal) {
        this.storeMemberTotal = storeMemberTotal;
    }

    public String getMemberReportTotal() {
        return memberReportTotal;
    }

    public void setMemberReportTotal(String memberReportTotal) {
        this.memberReportTotal = memberReportTotal;
    }
}
