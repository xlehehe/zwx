package com.zwx.scan.app.data.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * author : lizhilong
 * time   : 2018/12/10
 * desc   :
 * version: 1.0
 **/
public class CampaignCountSecond implements Serializable {


    private String date;

    private String  fowardCount;
    private String  getCount;
    private String  receiveCount;

    private List<CampaignChartBean> getCountList;

    private List<CampaignChartBean> fowardCountList;


    private List<CampaignChartBean> receiveCountList;


    private List<CampaignChartBean> couponCampaignTotalList;


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

    public List<CampaignChartBean> getGetCountList() {
        return getCountList;
    }

    public void setGetCountList(List<CampaignChartBean> getCountList) {
        this.getCountList = getCountList;
    }

    public List<CampaignChartBean> getFowardCountList() {
        return fowardCountList;
    }

    public void setFowardCountList(List<CampaignChartBean> fowardCountList) {
        this.fowardCountList = fowardCountList;
    }

    public List<CampaignChartBean> getReceiveCountList() {
        return receiveCountList;
    }

    public void setReceiveCountList(List<CampaignChartBean> receiveCountList) {
        this.receiveCountList = receiveCountList;
    }

    public List<CampaignChartBean> getCouponCampaignTotalList() {
        return couponCampaignTotalList;
    }

    public void setCouponCampaignTotalList(List<CampaignChartBean> couponCampaignTotalList) {
        this.couponCampaignTotalList = couponCampaignTotalList;
    }
}
