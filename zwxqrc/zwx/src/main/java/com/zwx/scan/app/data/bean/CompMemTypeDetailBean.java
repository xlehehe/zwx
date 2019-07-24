package com.zwx.scan.app.data.bean;

import java.io.Serializable;
import java.util.List;

/**
 * author : lizhilong
 * time   : 2019/04/15
 * desc   :
 * version: 1.0
 **/
public class CompMemTypeDetailBean implements Serializable {


    private Brand brand;


    private CompMemberType data;

//    public List<Brand> getBrandList() {
//        return brandList;
//    }
//
//    public void setBrandList(List<Brand> brandList) {
//        this.brandList = brandList;
//    }


    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public CompMemberType getData() {
        return data;
    }

    public void setData(CompMemberType data) {
        this.data = data;
    }
}
