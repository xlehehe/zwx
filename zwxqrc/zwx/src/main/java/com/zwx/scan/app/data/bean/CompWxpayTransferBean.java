package com.zwx.scan.app.data.bean;

import java.io.Serializable;
import java.util.List;

/**
 * author : lizhilong
 * time   : 2019/05/24
 * desc   :
 * version: 1.0
 **/
public class CompWxpayTransferBean implements Serializable {

    private List<CompWxpayTransfer> list;


    public List<CompWxpayTransfer> getList() {
        return list;
    }

    public void setList(List<CompWxpayTransfer> list) {
        this.list = list;
    }
}
