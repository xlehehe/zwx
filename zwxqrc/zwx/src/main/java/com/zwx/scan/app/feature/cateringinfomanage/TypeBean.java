package com.zwx.scan.app.feature.cateringinfomanage;

import java.io.Serializable;

/**
 * author : lizhilong
 * time   : 2019/01/09
 * desc   :
 * version: 1.0
 **/
public class TypeBean implements Serializable {

    private String id;
    private String name;
    private boolean isChecked;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
