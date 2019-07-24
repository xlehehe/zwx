package com.zwx.scan.app.data.bean;

import java.io.Serializable;

/**
 * author : lizhilong
 * time   : 2019/05/24
 * desc   : Gridview 图片 名称 类型 实体类 适用于Grid
 * version: 1.0
 **/
public class CommonImageBean implements Serializable {

    private String name;
    private String type;
    private int drawable;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getDrawable() {
        return drawable;
    }

    public void setDrawable(int drawable) {
        this.drawable = drawable;
    }
}
