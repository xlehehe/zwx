package com.zwx.scan.app.feature.couponmanage;

import java.io.Serializable;

/**
 * author : lizhilong
 * time   : 2019/03/20
 * desc   :
 * version: 1.0
 **/
public class DayDateBean implements Serializable {

    private String id ;
    private String value;

    private Boolean checked;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }
}
