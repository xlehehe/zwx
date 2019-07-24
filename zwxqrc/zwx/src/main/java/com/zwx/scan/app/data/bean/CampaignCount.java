package com.zwx.scan.app.data.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * author : lizhilong
 * time   : 2018/11/29
 * desc   :
 * version: 1.0
 **/
public class CampaignCount implements Serializable {

    private String date;

    private String  fowardCount;
    private String  getCount;
    private String  receiveCount;

    private List<Map<String,Object>> getCountList;

    private List<Map<String,Object>> fowardCountList;


    private List<Map<String,Object>> receiveCountList;


    private List<Map<String,Object>> couponCampaignTotalList;

    //整体活动统计
    private List<CampaignTotal> campaignTotal;
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFowardCount() {
        return fowardCount;
    }

    public void setFowardCount(String fowardCount) {
        this.fowardCount = fowardCount;
    }

    public String getGetCount() {
        return getCount;
    }

    public void setGetCount(String getCount) {
        this.getCount = getCount;
    }

    public String getReceiveCount() {
        return receiveCount;
    }

    public void setReceiveCount(String receiveCount) {
        this.receiveCount = receiveCount;
    }

    public List<Map<String, Object>> getGetCountList() {
        return getCountList;
    }

    public void setGetCountList(List<Map<String, Object>> getCountList) {
        this.getCountList = getCountList;
    }

    public List<Map<String, Object>> getFowardCountList() {
        return fowardCountList;
    }

    public void setFowardCountList(List<Map<String, Object>> fowardCountList) {
        this.fowardCountList = fowardCountList;
    }

    public List<Map<String, Object>> getReceiveCountList() {
        return receiveCountList;
    }

    public void setReceiveCountList(List<Map<String, Object>> receiveCountList) {
        this.receiveCountList = receiveCountList;
    }

    public List<Map<String, Object>> getCouponCampaignTotalList() {
        return couponCampaignTotalList;
    }

    public void setCouponCampaignTotalList(List<Map<String, Object>> couponCampaignTotalList) {
        this.couponCampaignTotalList = couponCampaignTotalList;
    }

    public List<CampaignTotal> getCampaignTotal() {
        return campaignTotal;
    }

    public void setCampaignTotal(List<CampaignTotal> campaignTotal) {
        this.campaignTotal = campaignTotal;
    }
}
