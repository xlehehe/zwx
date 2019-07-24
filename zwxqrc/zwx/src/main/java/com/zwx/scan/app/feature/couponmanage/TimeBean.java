package com.zwx.scan.app.feature.couponmanage;

import java.io.Serializable;

/**
 * author : lizhilong
 * time   : 2019/03/01
 * desc   :
 * version: 1.0
 **/
public class TimeBean implements Serializable {

    private String expireEndDate;
    private String expireStartDate;

    public String getExpireEndDate() {
        return expireEndDate;
    }

    public void setExpireEndDate(String expireEndDate) {
        this.expireEndDate = expireEndDate;
    }

    public String getExpireStartDate() {
        return expireStartDate;
    }

    public void setExpireStartDate(String expireStartDate) {
        this.expireStartDate = expireStartDate;
    }
}
