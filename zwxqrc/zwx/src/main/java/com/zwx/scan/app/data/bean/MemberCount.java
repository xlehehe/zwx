package com.zwx.scan.app.data.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

/**
 * author : lizhilong
 * time   : 2018/11/29
 * desc   :
 * version: 1.0
 **/
public class MemberCount implements Serializable {

    private String date;

    ArrayList<Map<String,Object>> memberReportList;

    private String storeMemberTotal;

    private String memberReportTotal;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<Map<String, Object>> getMemberReportList() {
        return memberReportList;
    }

    public void setMemberReportList(ArrayList<Map<String, Object>> memberReportList) {
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
