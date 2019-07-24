package com.zwx.scan.app.data.bean;

import java.io.Serializable;

/**
 * author : lizhilong
 * time   : 2019/04/23
 * desc   :
 * version: 1.0
 **/
public class ProductStore implements Serializable {

    private Long productId;

    private Long compId;
    private String storeId;
    private String storeSelectType;
    private String storeName;

    private String shopTel;


    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getCompId() {
        return compId;
    }

    public void setCompId(Long compId) {
        this.compId = compId;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getStoreSelectType() {
        return storeSelectType;
    }

    public void setStoreSelectType(String storeSelectType) {
        this.storeSelectType = storeSelectType;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getShopTel() {
        return shopTel;
    }

    public void setShopTel(String shopTel) {
        this.shopTel = shopTel;
    }
}
