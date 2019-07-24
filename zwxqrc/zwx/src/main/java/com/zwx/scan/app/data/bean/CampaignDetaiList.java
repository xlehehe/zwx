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
public class CampaignDetaiList implements Serializable {

    private List<Map<String,Object>> couponCampaignTotalList;

    //整体活动统计
    private CampaignTotal campaignTotal;

    public List<Map<String, Object>> getCouponCampaignTotalList() {
        return couponCampaignTotalList;
    }

    public void setCouponCampaignTotalList(List<Map<String, Object>> couponCampaignTotalList) {
        this.couponCampaignTotalList = couponCampaignTotalList;
    }

    public CampaignTotal getCampaignTotal() {
        return campaignTotal;
    }

    public void setCampaignTotal(CampaignTotal campaignTotal) {
        this.campaignTotal = campaignTotal;
    }
}
