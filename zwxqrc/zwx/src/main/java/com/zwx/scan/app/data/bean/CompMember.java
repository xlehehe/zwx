package com.zwx.scan.app.data.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

/**
 * author : lizhilong
 * time   : 2018/11/29
 * desc   :
 * version: 1.0
 **/
public class CompMember implements Serializable {


    private  String compMemtypeId;

    private String  memTypeName;

    private  String total;

    public String getCompMemtypeId() {
        return compMemtypeId;
    }

    public void setCompMemtypeId(String compMemtypeId) {
        this.compMemtypeId = compMemtypeId;
    }

    public String getMemTypeName() {
        return memTypeName;
    }

    public void setMemTypeName(String memTypeName) {
        this.memTypeName = memTypeName;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
