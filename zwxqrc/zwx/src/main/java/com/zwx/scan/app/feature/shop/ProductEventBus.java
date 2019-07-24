package com.zwx.scan.app.feature.shop;

import java.io.Serializable;

/**
 * author : lizhilong
 * time   : 2019/04/22
 * desc   :
 * version: 1.0
 **/
public class ProductEventBus implements Serializable {

    private String productId;
    private String operateFlag;
    private String stock;
    public ProductEventBus(String productId,String operateFlag,String stock) {
        this.productId = productId;
        this.operateFlag = operateFlag;
        this.stock = stock;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getOperateFlag() {
        return operateFlag;
    }

    public void setOperateFlag(String operateFlag) {
        this.operateFlag = operateFlag;
    }
}
