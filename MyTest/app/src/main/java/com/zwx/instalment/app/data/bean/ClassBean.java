package com.zwx.instalment.app.data.bean;

import java.io.Serializable;

/**
 * author : lizhilong
 * time   : 2019/06/27
 * desc   :
 * version: 1.0
 **/
public class ClassBean implements Serializable {
    private String key;
    private String value;
    private int drawable;

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

    public int getDrawable() {
        return drawable;
    }

    public void setDrawable(int drawable) {
        this.drawable = drawable;
    }
}
