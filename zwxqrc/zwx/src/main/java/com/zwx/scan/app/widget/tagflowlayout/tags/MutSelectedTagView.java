package com.zwx.scan.app.widget.tagflowlayout.tags;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;

import com.zwx.library.utils.ScreenUtils;
import com.zwx.scan.app.R;

/**
 * Author   :lizhilong
 * Email    :longxiaotian@163.com
 * Create at 2016/9/5
 * Description: 多选标签
 */
public class MutSelectedTagView extends DefaultTagView {

    public MutSelectedTagView(Context context) {
        super(context);
    }

    public MutSelectedTagView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MutSelectedTagView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected Drawable getBackgroundDrawable() {
        //设置字体颜色的选择器
        ColorStateList colorSateList = mContext.getResources().getColorStateList(R.color.secondary_text);
        setTextColor(colorSateList);

        GradientDrawable normal = new GradientDrawable();
        normal.setShape(GradientDrawable.RECTANGLE);
        normal.setCornerRadius(ScreenUtils.dip2px(mContext, getTagRadius()));
        if (isSolid()) {
            normal.setColor(getNormalBackgroundColor());
        } else {
            normal.setStroke(ScreenUtils.dip2px(mContext, getStrokeWidth()), getNormalBackgroundColor());
            normal.setColor(getBackgroundColor());
        }

        GradientDrawable pressed = new GradientDrawable();
        pressed.setShape(GradientDrawable.RECTANGLE);
        pressed.setCornerRadius(ScreenUtils.dip2px(mContext, getTagRadius()));
        if (isSolid()) {
            pressed.setColor(getPressedBackgroundColor());
        } else {
            pressed.setStroke(ScreenUtils.dip2px(mContext, getStrokeWidth()), getPressedBackgroundColor());
            pressed.setColor(getPressedBackgroundColor());
        }

        StateListDrawable selector = new StateListDrawable();
        selector.addState(new int[]{android.R.attr.state_pressed}, pressed);
        selector.addState(new int[]{android.R.attr.state_selected}, pressed);
        selector.addState(new int[]{}, normal);
        return selector;
    }

    public void toggle() {
        this.setSelected(!isSelected());
    }
}
