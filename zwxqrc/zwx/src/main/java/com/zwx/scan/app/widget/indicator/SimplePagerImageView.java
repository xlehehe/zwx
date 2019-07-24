package com.zwx.scan.app.widget.indicator;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.view.Gravity;

import com.zwx.library.utils.ScreenUtils;
import com.zwx.scan.app.R;

/**
 * author : lizhilong
 * time   : 2018/12/05
 * desc   :
 * version: 1.0
 **/
public class SimplePagerImageView extends AppCompatImageView implements IMeasurablePagerTitleView  {
    protected int mSelectedDrawable;
    protected int mNormalDrawable;

    private  Context context;
    public SimplePagerImageView(Context context) {
        super(context, null);
        this.context = context;
        init(context);
    }

    private void init(Context context) {
        int padding = ScreenUtils.dip2px(context, 10);
        setPadding(padding, 0, padding, 0);
    }

    @Override
    public int getContentLeft() {
        Rect bound = new Rect();
        int contentWidth = bound.width();
        return getLeft() + getWidth() / 2 - contentWidth / 2;
    }

    @Override
    public int getContentTop() {
        return 0;
    }

    @Override
    public int getContentRight() {
        return 0;
    }

    @Override
    public int getContentBottom() {
        return 0;
    }

    @Override
    public void onSelected(int index, int totalCount) {

        setBackground(context.getResources().getDrawable(R.drawable.ic_dot_selected));
    }

    @Override
    public void onDeselected(int index, int totalCount) {
        setBackground(context.getResources().getDrawable(R.drawable.ic_dot_unselect));
    }

    @Override
    public void onLeave(int index, int totalCount, float leavePercent, boolean leftToRight) {

    }

    @Override
    public void onEnter(int index, int totalCount, float enterPercent, boolean leftToRight) {

    }

    public int getSelectedDrawable() {
        return mSelectedDrawable;
    }

    public void setSelectedDrawable(int mSelectedDrawable) {
        this.mSelectedDrawable = mSelectedDrawable;
    }

    public int getNormalDrawable() {
        return mNormalDrawable;
    }

    public void setNormalDrawable(int mNormalDrawable) {
        this.mNormalDrawable = mNormalDrawable;
    }
}
