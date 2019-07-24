package com.zwx.scan.app.data.bean;

import java.io.Serializable;
import java.util.List;

/**
 * author : lizhilong
 * time   : 2019/05/30
 * desc   :
 * version: 1.0
 **/
public class TOrderBean implements Serializable {

    private List<TOrder> data;

    public List<TOrder> getData() {
        return data;
    }

    public void setData(List<TOrder> data) {
        this.data = data;
    }
}
