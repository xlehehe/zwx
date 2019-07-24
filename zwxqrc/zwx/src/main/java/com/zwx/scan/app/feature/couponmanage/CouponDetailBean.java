package com.zwx.scan.app.feature.couponmanage;

import com.zwx.scan.app.data.bean.Coupon;

import java.io.Serializable;

/**
 * author : lizhilong
 * time   : 2019/03/04
 * desc   :
 * version: 1.0
 **/
public class CouponDetailBean implements Serializable {

    private String code;
//    private Coupon coupon;
    private CouponDetailBeanTwo coupon;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
/*
    public Coupon getCoupon() {
        return coupon;
    }

    public void setCoupon(Coupon coupon) {
        this.coupon = coupon;
    }*/

    public CouponDetailBeanTwo getCoupon() {
        return coupon;
    }

    public void setCoupon(CouponDetailBeanTwo coupon) {
        this.coupon = coupon;
    }
}
