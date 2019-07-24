package com.zwx.scan.app.data.bean;

import java.io.Serializable;
import java.util.List;

/**
 * author : lizhilong
 * time   : 2019/04/23
 * desc   :
 * version: 1.0
 **/
public class ProductResultBean implements Serializable {

    private  ProductSetNew productSet;
    private  ProductExtend productExtend;
    private List<ProductMedia> productMediaList;
    private  List<ProductStore> productStoreList;
    private  List<ProductExtendDesc> roductExtendDescList;


    public List<ProductExtendDesc> getRoductExtendDescList() {
        return roductExtendDescList;
    }

    public void setRoductExtendDescList(List<ProductExtendDesc> roductExtendDescList) {
        this.roductExtendDescList = roductExtendDescList;
    }

    public ProductSetNew getProductSet() {
        return productSet;
    }

    public void setProductSet(ProductSetNew productSet) {
        this.productSet = productSet;
    }

    public ProductExtend getProductExtend() {
        return productExtend;
    }

    public void setProductExtend(ProductExtend productExtend) {
        this.productExtend = productExtend;
    }

    public List<ProductMedia> getProductMediaList() {
        return productMediaList;
    }

    public void setProductMediaList(List<ProductMedia> productMediaList) {
        this.productMediaList = productMediaList;
    }

    public List<ProductStore> getProductStoreList() {
        return productStoreList;
    }

    public void setProductStoreList(List<ProductStore> productStoreList) {
        this.productStoreList = productStoreList;
    }
}
