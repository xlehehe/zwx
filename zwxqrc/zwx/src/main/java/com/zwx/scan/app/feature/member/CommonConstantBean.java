package com.zwx.scan.app.feature.member;

import java.io.Serializable;

/**
 * author : lizhilong
 * time   : 2019/04/02
 * desc   :
 * version: 1.0
 **/
public class CommonConstantBean implements Serializable {

    private String key;
    private String value;

    private Boolean isChecked;

    public Boolean getChecked() {
        return isChecked;
    }

    public void setChecked(Boolean checked) {
        isChecked = checked;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
