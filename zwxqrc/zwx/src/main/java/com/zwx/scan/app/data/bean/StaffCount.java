package com.zwx.scan.app.data.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * author : lizhilong
 * time   : 2018/11/29
 * desc   :
 * version: 1.0
 **/
public class StaffCount implements Serializable {


    private String date;
    private List<Map<String,Object>> staffReport;
    private String total;


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Map<String, Object>> getStaffReport() {
        return staffReport;
    }

    public void setStaffReport(List<Map<String, Object>> staffReport) {
        this.staffReport = staffReport;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
