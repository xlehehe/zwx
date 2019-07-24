package com.zwx.scan.app.data.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * author : lizhilong
 * time   : 2019/05/28
 * desc   :
 * version: 1.0
 **/
public class JiZanResultBean implements Serializable {

    private Campaign campaign;

    private List<Store> store;

    private CampaignCollect campaignCollect;



    private List<RewardBean> gamesecondinfo;

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

    public CampaignCollect getCampaignCollect() {
        return campaignCollect;
    }

    public void setCampaignCollect(CampaignCollect campaignCollect) {
        this.campaignCollect = campaignCollect;
    }

    public List<RewardBean> getGamesecondinfo() {
        return gamesecondinfo;
    }

    public void setGamesecondinfo(List<RewardBean> gamesecondinfo) {
        this.gamesecondinfo = gamesecondinfo;
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
