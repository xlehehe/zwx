package com.zwx.scan.app.data.bean;

import java.io.Serializable;
import java.util.List;

/**
 * author : lizhilong
 * time   : 2019/04/19
 * desc   :
 * version: 1.0
 **/
public class ProductSetNewBean implements Serializable {

    private boolean isLastPage;

    private List<ProductSetNew>  list;

    public List<ProductSetNew> getList() {
        return list;
    }

    public void setList(List<ProductSetNew> list) {
        this.list = list;
    }

    public boolean isLastPage() {
        return isLastPage;
    }

    public void setLastPage(boolean lastPage) {
        isLastPage = lastPage;
    }
}
