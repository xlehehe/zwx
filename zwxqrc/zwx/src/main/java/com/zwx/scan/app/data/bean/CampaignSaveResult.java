package com.zwx.scan.app.data.bean;

import java.io.Serializable;

/**
 * author : lizhilong
 * time   : 2018/12/28
 * desc   :
 * version: 1.0
 **/
public class CampaignSaveResult implements Serializable {

    private String campaignIdStr;

    public String getCampaignIdStr() {
        return campaignIdStr;
    }

    public void setCampaignIdStr(String campaignIdStr) {
        this.campaignIdStr = campaignIdStr;
    }
}
