package com.zwx.instalment.app.base;

import com.qmuiteam.qmui.arch.QMUIActivity;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;

/**
 * author : lizhilong
 * time   : 2019/07/03
 * desc   :
 * version: 1.0
 **/
public class BaseQMUIActivity extends QMUIActivity {


    @Override
    protected int backViewInitOffset() {
//        return super.backViewInitOffset();
        return QMUIDisplayHelper.dp2px(this, 100);

    }

    @Override
    protected void onResume() {
        super.onResume();


    }
}
