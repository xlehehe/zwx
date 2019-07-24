package com.zwx.scan.app.data.bean;

import com.zwx.library.pickerview.wheelview.interfaces.IPickerViewData;

/**
 * author : lizhilong
 * time   : 2018/12/09
 * desc   :
 * version: 1.0
 **/
public class CardQrcBean  implements IPickerViewData {

    private String id ;
    private String cardNo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }


    @Override
    public String getPickerViewText() {
        return cardNo;
    }
}
