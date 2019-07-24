package com.zwx.scan.app.data.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * author : lizhilong
 * time   : 2018/11/28
 * desc   :
 * version: 1.0
 **/
public class VerificationRecord implements Serializable{

    String verificationTotal;
    String penVerificationTotal;

    String date;
    List<Map<String,Object>> verificationTotalReport;
    List<Map<String,Object>> penVerificationTotalReport;

    public String getVerificationTotal() {
        return verificationTotal;
    }

    public void setVerificationTotal(String verificationTotal) {
        this.verificationTotal = verificationTotal;
    }

    public String getPenVerificationTotal() {
        return penVerificationTotal;
    }

    public void setPenVerificationTotal(String penVerificationTotal) {
        this.penVerificationTotal = penVerificationTotal;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Map<String, Object>> getVerificationTotalReport() {
        return verificationTotalReport;
    }

    public void setVerificationTotalReport(List<Map<String, Object>> verificationTotalReport) {
        this.verificationTotalReport = verificationTotalReport;
    }

    public List<Map<String, Object>> getPenVerificationTotalReport() {
        return penVerificationTotalReport;
    }

    public void setPenVerificationTotalReport(List<Map<String, Object>> penVerificationTotalReport) {
        this.penVerificationTotalReport = penVerificationTotalReport;
    }
}
