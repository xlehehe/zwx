package com.zwx.scan.app.data.bean;

import java.io.Serializable;
import java.util.List;

/**
 * author : lizhilong
 * time   : 2019/04/18
 * desc   :
 * version: 1.0
 **/
public class OrderObjectBean implements Serializable {

    private List<TOrderObject> list;

    private Integer total;


    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<TOrderObject> getList() {
        return list;
    }

    public void setList(List<TOrderObject> list) {
        this.list = list;
    }
}
