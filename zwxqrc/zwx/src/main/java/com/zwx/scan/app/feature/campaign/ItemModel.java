package com.zwx.scan.app.feature.campaign;

import android.animation.TimeInterpolator;

/**
 * author : lizhilong
 * time   : 2018/09/12
 * desc   :
 * version: 1.0
 **/
public class ItemModel {

    public  String rule;
    public  int colorId1;
    public  int colorId2;
    public final TimeInterpolator interpolator;

    private String money;
    private String name;
    private String desc;
    private String couponName;
    private String useCouponName;

    private String price;

    private int drawableId;

    private String isNew;

    private Boolean isChecked;

    private String isUse;
    public ItemModel(TimeInterpolator interpolator) {
//        this.rule = rule;
//        this.colorId1 = colorId1;
//        this.colorId2 = colorId2;
        this.interpolator = interpolator;
    }

    public String getRule() {
        return rule;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public int getColorId1() {
        return colorId1;
    }

    public void setColorId1(int colorId1) {
        this.colorId1 = colorId1;
    }

    public int getColorId2() {
        return colorId2;
    }

    public void setColorId2(int colorId2) {
        this.colorId2 = colorId2;
    }

    public int getDrawableId() {
        return drawableId;
    }

    public void setDrawableId(int drawableId) {
        this.drawableId = drawableId;
    }

    public String getIsNew() {
        return isNew;
    }


    public void setIsNew(String isNew) {
        this.isNew = isNew;
    }

    public Boolean getChecked() {
        return isChecked;
    }

    public void setChecked(Boolean checked) {
        isChecked = checked;
    }


    public String getIsUse() {
        return isUse;
    }

    public void setIsUse(String isUse) {
        this.isUse = isUse;
    }

    public String getUseCouponName() {
        return useCouponName;
    }

    public void setUseCouponName(String useCouponName) {
        this.useCouponName = useCouponName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
