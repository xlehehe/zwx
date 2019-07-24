package com.zwx.scan.app.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.github.mikephil.charting.charts.BarChart;
import com.zwx.library.utils.LogUtils;

/**
 * author : lizhilong
 * time   : 2018/12/01
 * desc   :
 * version: 1.0
 **/
public class CustomBarChart extends BarChart {

    PointF downPoint = new PointF();

    public CustomBarChart(Context context) {
        super(context);

    }

    public CustomBarChart(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public CustomBarChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent evt) {
        switch (evt.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downPoint.x = evt.getX();
                downPoint.y = evt.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                LogUtils.i("getScrollX ", getScrollX() + "" );
                if (getScaleX() > 1 && Math.abs(evt.getX() - downPoint.x) > 5) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                break;
        }
        return super.onTouchEvent(evt);
    }
}
