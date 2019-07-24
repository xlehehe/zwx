package com.zwx.scan.app.data.bean;

import java.io.Serializable;

/**
 * author : lizhilong
 * time   : 2019/04/18
 * desc   : 商城基本设置->产品分类
 * version: 1.0
 **/
public class ProductCategory implements Serializable {
    //品牌公司id
    private Long compId;
    //分类id
    private Long catId;

    //分类名称
    private String catName;

    //分类描述
    private String catDesc;

    //商品数量
    private String num;

    //销售类型
    private String salesType;

    public Long getCompId() {
        return compId;
    }

    public void setCompId(Long compId) {
        this.compId = compId;
    }

    public Long getCatId() {
        return catId;
    }

    public void setCatId(Long catId) {
        this.catId = catId;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public String getCatDesc() {
        return catDesc;
    }

    public void setCatDesc(String catDesc) {
        this.catDesc = catDesc;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getSalesType() {
        return salesType;
    }

    public void setSalesType(String salesType) {
        this.salesType = salesType;
    }
}
