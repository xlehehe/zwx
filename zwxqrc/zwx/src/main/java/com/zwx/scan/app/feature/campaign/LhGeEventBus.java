package com.zwx.scan.app.feature.campaign;

import com.luck.picture.lib.entity.LocalMedia;

import java.util.List;

/**
 * author : lizhilong
 * time   : 2019/02/15
 * desc   :
 * version: 1.0
 **/
public class LhGeEventBus {

    private String imageId;
    private String name;
    public LhGeEventBus(String imageId,String name) {
        this.imageId = imageId;
        this.name = name;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
