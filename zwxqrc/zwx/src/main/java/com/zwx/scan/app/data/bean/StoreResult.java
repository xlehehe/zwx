package com.zwx.scan.app.data.bean;

import java.io.Serializable;
import java.util.List;

/**
 * author : lizhilong
 * time   : 2019/01/11
 * desc   :
 * version: 1.0
 **/
public class StoreResult implements Serializable {

    private Integer total;

    private Integer recordsFiltered;
    private List<Store> data;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getRecordsFiltered() {
        return recordsFiltered;
    }

    public void setRecordsFiltered(Integer recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
    }

    public List<Store> getData() {
        return data;
    }

    public void setData(List<Store> data) {
        this.data = data;
    }
}
