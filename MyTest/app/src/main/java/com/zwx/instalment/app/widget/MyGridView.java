package com.zwx.instalment.app.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * author : lizhilong
 * time   : 2018/09/04
 * desc   :  自定义类Grid表格，防止与ScrollView冲突
 * version: 1.0
 **/
public class MyGridView extends GridView {

    public MyGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyGridView(Context context) {
        super(context);
    }

    public MyGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int expandSpec = MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

}
