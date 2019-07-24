package com.zwx.scan.app.data.bean;

import java.io.Serializable;
import java.util.List;

/**
 * author : lizhilong
 * time   : 2019/01/17
 * desc   : 常规发圈
 * version: 1.0
 **/
public class CampaignCouponDetailbean implements Serializable {


    private CampaignTotal wholeInfo;

    private List<CampaignTotal> couponList;

    private List<CampaignTotal> storeList;

    private List<StoreCouponBean> storeCouponCountList;

    public CampaignTotal getWholeInfo() {
        return wholeInfo;
    }

    public void setWholeInfo(CampaignTotal wholeInfo) {
        this.wholeInfo = wholeInfo;
    }

    public List<CampaignTotal> getCouponList() {
        return couponList;
    }

    public void setCouponList(List<CampaignTotal> couponList) {
        this.couponList = couponList;
    }

    public List<CampaignTotal> getStoreList() {
        return storeList;
    }

    public void setStoreList(List<CampaignTotal> storeList) {
        this.storeList = storeList;
    }

    public List<StoreCouponBean> getStoreCouponCountList() {
        return storeCouponCountList;
    }

    public void setStoreCouponCountList(List<StoreCouponBean> storeCouponCountList) {
        this.storeCouponCountList = storeCouponCountList;
    }

    public class  StoreCouponBean{
        private CampaignTotal store;

        private List<CampaignTotal> coupon;

        public CampaignTotal getStore() {
            return store;
        }

        public void setStore(CampaignTotal store) {
            this.store = store;
        }

        public List<CampaignTotal> getCoupon() {
            return coupon;
        }

        public void setCoupon(List<CampaignTotal> coupon) {
            this.coupon = coupon;
        }
    }
}
