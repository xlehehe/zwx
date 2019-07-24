package com.zwx.scan.app.data.bean;

import java.util.List;

/**
 * author : lizhilong
 * time   : 2019/01/15
 * desc   :
 * version: 1.0
 **/
public class MessageBean {

    private Integer total;
    private Integer recordsFiltered;

    private List<MessageSet>  rows;
    private Integer  recordsTotal;


    public Integer getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(Integer recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

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

    public List<MessageSet> getRows() {
        return rows;
    }

    public void setRows(List<MessageSet> rows) {
        this.rows = rows;
    }
}
