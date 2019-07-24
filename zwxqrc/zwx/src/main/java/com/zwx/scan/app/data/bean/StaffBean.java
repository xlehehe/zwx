package com.zwx.scan.app.data.bean;

import java.io.Serializable;
import java.util.List;

/**
 * author : lizhilong
 * time   : 2018/12/09
 * desc   :
 * version: 1.0
 **/
public class StaffBean implements Serializable {

    private String total;

    private String  recordsFiltered;

    private List<StaffWork> data;


    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getRecordsFiltered() {
        return recordsFiltered;
    }

    public void setRecordsFiltered(String recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
    }

    public List<StaffWork> getData() {
        return data;
    }

    public void setData(List<StaffWork> data) {
        this.data = data;
    }
}
