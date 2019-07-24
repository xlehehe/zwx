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
public class CampaignChartBean implements Serializable {

    private List<Map<String,Object>> countList;

    private String storeName;
    private String storeId;
    private  boolean isChecked;
    private int color;

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public List<Map<String, Object>> getCountList() {
        return countList;
    }

    public void setCountList(List<Map<String, Object>> countList) {
        this.countList = countList;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }


    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
