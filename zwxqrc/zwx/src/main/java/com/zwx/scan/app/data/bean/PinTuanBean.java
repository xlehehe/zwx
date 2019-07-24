package com.zwx.scan.app.data.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * author : lizhilong
 * time   : 2019/02/13
 * desc   : 拼团管理
 * version: 1.0
 **/
public class PinTuanBean implements Serializable {

    private Campaign campaign;

    private List<Store> store;

    private CampaignGroupBuy campaignGroupBuy;



    private List<PinTuanBean.RewardBean> gamesecondinfo;

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

    public CampaignGroupBuy getCampaignGroupBuy() {
        return campaignGroupBuy;
    }

    public void setCampaignGroupBuy(CampaignGroupBuy campaignGroupBuy) {
        this.campaignGroupBuy = campaignGroupBuy;
    }

    public List<PinTuanBean.RewardBean> getGamesecondinfo() {
        return gamesecondinfo;
    }

    public void setGamesecondinfo(List<PinTuanBean.RewardBean> gamesecondinfo) {
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
