package com.zwx.scan.app.feature.cateringinfomanage;

import java.io.Serializable;

/**
 * author : lizhilong
 * time   : 2019/01/09
 * desc   :
 * version: 1.0
 **/
public class CateringinfBean implements Serializable {

    private String storeName;
    private String status;

    private String name;
    private String phone;

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
