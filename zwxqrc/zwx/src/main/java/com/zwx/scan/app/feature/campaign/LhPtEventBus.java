package com.zwx.scan.app.feature.campaign;

import com.luck.picture.lib.entity.LocalMedia;
import com.zwx.scan.app.feature.user.LoginModule;

import java.util.List;

/**
 * author : lizhilong
 * time   : 2019/01/23
 * desc   :
 * version: 1.0
 **/
public class LhPtEventBus {

//    private List<LocalMedia> selectList;

    private String name;

    public LhPtEventBus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
