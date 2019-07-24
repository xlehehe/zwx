package com.zwx.scan.app.feature.campaign;

import java.io.Serializable;

/**
 * author : lizhilong
 * time   : 2019/01/22
 * desc   :
 * version: 1.0
 **/
public class PrizeDefaultBean implements Serializable {

    private String id;
    private Boolean isChecked;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getChecked() {
        return isChecked;
    }

    public void setChecked(Boolean checked) {
        isChecked = checked;
    }
}
