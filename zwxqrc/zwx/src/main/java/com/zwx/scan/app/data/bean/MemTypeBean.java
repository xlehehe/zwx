package com.zwx.scan.app.data.bean;

import java.io.Serializable;

/**
 * author : lizhilong
 * time   : 2018/12/12
 * desc   :
 * version: 1.0
 **/
public class MemTypeBean implements Serializable {
    private String memTypeId;
    private String memTypeName;

    public String getMemTypeId() {
        return memTypeId;
    }

    public void setMemTypeId(String memTypeId) {
        this.memTypeId = memTypeId;
    }

    public String getMemTypeName() {
        return memTypeName;
    }

    public void setMemTypeName(String memTypeName) {
        this.memTypeName = memTypeName;
    }
}
