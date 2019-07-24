package com.zwx.scan.app.data.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * author : lizhilong
 * time   : 2018/12/29
 * desc   :
 * version: 1.0
 **/
public class CampaignEditBean implements Serializable {

    private Campaign campaign;

    private List<Store> store = new ArrayList<>();

    private CampaginGrant campaignGrant;

    private ArrayList<CampaignCoupon> listfoward = new ArrayList<>();

    private   ArrayList<CampaignCoupon> listreceive = new ArrayList<>();

    private ArrayList<RewardBean> gamesecondinfo;
    public CampaginGrant getCampaignGrant() {
        return campaignGrant;
    }

    public ArrayList<RewardBean> getGamesecondinfo() {
        return gamesecondinfo;
    }

    public void setGamesecondinfo(ArrayList<RewardBean> gamesecondinfo) {
        this.gamesecondinfo = gamesecondinfo;
    }

    public void setCampaignGrant(CampaginGrant campaignGrant) {
        this.campaignGrant = campaignGrant;
    }

    public Campaign getCampaign() {
        return campaign;
    }

    public void setCampaign(Campaign campaign) {
        this.campaign = campaign;
    }

    public List<Store> getStore() {
        return store;
    }

    public void setStore(List<Store> store) {
        this.store = store;
    }

    public ArrayList<CampaignCoupon> getListfoward() {
        return listfoward;
    }

    public void setListfoward(ArrayList<CampaignCoupon> listfoward) {
        this.listfoward = listfoward;
    }

    public ArrayList<CampaignCoupon> getListreceive() {
        return listreceive;
    }

    public void setListreceive(ArrayList<CampaignCoupon> listreceive) {
        this.listreceive = listreceive;
    }


    public class RewardBean implements Serializable{
        private String rewardName;
        private CampaignGamesetreward rewardinfo;

        private ArrayList<CampaignCoupon> couponinfo;

        public String getRewardName() {
            return rewardName;
        }

        public void setRewardName(String rewardName) {
            this.rewardName = rewardName;
        }

        public CampaignGamesetreward getRewardinfo() {
            return rewardinfo;
        }

        public void setRewardinfo(CampaignGamesetreward rewardinfo) {
            this.rewardinfo = rewardinfo;
        }

        public ArrayList<CampaignCoupon> getCouponinfo() {
            return couponinfo;
        }

        public void setCouponinfo(ArrayList<CampaignCoupon> couponinfo) {
            this.couponinfo = couponinfo;
        }
    }
}
