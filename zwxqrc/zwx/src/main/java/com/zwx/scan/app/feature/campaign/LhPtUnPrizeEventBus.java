package com.zwx.scan.app.feature.campaign;

/**
 * author : lizhilong
 * time   : 2019/06/05
 * desc   :
 * version: 1.0
 **/
public class LhPtUnPrizeEventBus {

    private String name;

    public LhPtUnPrizeEventBus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
