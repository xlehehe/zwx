package com.zwx.scan.app.data.bean;

import java.io.Serializable;

/**
 * author : lizhilong
 * time   : 2019/04/23
 * desc   : 商城保存或上架返回Bean
 * version: 1.0
 **/
public class ShopResultBean implements Serializable {

    private String productId;
    private String k;


    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getK() {
        return k;
    }

    public void setK(String k) {
        this.k = k;
    }
}
