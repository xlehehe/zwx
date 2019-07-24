package com.zwx.scan.app.data.bean;

import java.io.Serializable;
import java.util.List;

/**
 * author : lizhilong
 * time   : 2019/04/28
 * desc   : 商品核销记录详情
 * version: 1.0
 **/
public class TOrderConsumeLogBean implements Serializable {

    protected List<TOrderConsumeLog> data;

    public List<TOrderConsumeLog> getData() {
        return data;
    }

    public void setData(List<TOrderConsumeLog> data) {
        this.data = data;
    }
}
