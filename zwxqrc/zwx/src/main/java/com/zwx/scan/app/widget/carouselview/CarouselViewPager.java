package com.zwx.scan.app.widget.carouselview;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.Interpolator;

import com.zwx.scan.app.widget.carouselview.pager.ScrollToAnimation;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by Sayyam on 3/28/16.
 */
public class CarouselViewPager extends ViewPager {

    private ImageClickListener imageClickListener;
    private float oldX = 0, newX = 0, sens = 5;

    private Animation animation;
    private int animationDuration = 2500;
    private boolean animationStarted = true;

    private float pageWidth = 0.6f;
    private int paddingBetweenItem = 8;

    public void setImageClickListener(ImageClickListener imageClickListener) {
        this.imageClickListener = imageClickListener;
    }

    public CarouselViewPager(Context context) {
        super(context);
        postInitViewPager();
    }

    public CarouselViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        postInitViewPager();
    }

    private CarouselViewPagerScroller mScroller = null;

    /**
     * Override the Scroller instance with our own class so we can change the
     * duration
     */
    private void postInitViewPager() {
        try {
            Class<?> viewpager = ViewPager.class;
            Field scroller = viewpager.getDeclaredField("mScroller");
            scroller.setAccessible(true);
            Field interpolator = viewpager.getDeclaredField("sInterpolator");
            interpolator.setAccessible(true);

            mScroller = new CarouselViewPagerScroller(getContext(),
                    (Interpolator) interpolator.get(null));
            scroller.set(this, mScroller);
        } catch (Exception e) {
        }
    }

    /**
     * Set the factor by which the duration will change
     */
    public void setTransitionVelocity(int scrollFactor) {
        mScroller.setmScrollDuration(scrollFactor);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                oldX = ev.getX();
                break;

            case MotionEvent.ACTION_UP:
                newX = ev.getX();
                if (Math.abs(oldX - newX) < sens) {
                    if(imageClickListener != null)
                        imageClickListener.onClick(getCurrentItem());
                    return true;
                }
                oldX = 0;
                newX = 0;
                break;
        }

        return super.onTouchEvent(ev);
    }





    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        try {
            Method method = ViewPager.class.getDeclaredMethod("setCurrentItemInternal", int.class, boolean.class, boolean.class, int.class);
            method.setAccessible(true);
            method.invoke(this, item, smoothScroll, false, 1500);
        } catch (Exception e) {
            e.printStackTrace();
            super.setCurrentItem(item, smoothScroll);
        }
    }

    public void setCurrentItemWhitoutScrolling(int item) {
        try {
            Field mCurItem = ViewPager.class.getDeclaredField("mCurItem");
            mCurItem.setAccessible(true);
            mCurItem.setInt(this, item);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void scrollingToPage(int offset)  {
        try {
            Method pageScrolled = ViewPager.class.getDeclaredMethod("pageScrolled", int.class);
            pageScrolled.setAccessible(true);
            pageScrolled.invoke(this, offset);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startAnimation(boolean arrived, Animation.AnimationListener listener) {
        animationStarted = false;
        int desiredPosition = (int) (getChildAt(0).getWidth()/1.5f*(getChildCount()));
        if(arrived) {
            animation  = new ScrollToAnimation(this, arrived, 0, desiredPosition, animationDuration);
        } else {
            animation = new ScrollToAnimation(this, arrived, desiredPosition, 0, animationDuration);
        }
        animation.setAnimationListener(listener);
        invalidate();
    }

    private Canvas enterAnimation(final Canvas c) {
        animationStarted = true;
        startAnimation(animation);
        return c;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (!animationStarted) {
            canvas = enterAnimation(canvas);
        }
        super.onDraw(canvas);
    }

    public float getPageWidth() {
        return pageWidth;
    }

    public void setPageWidth(float pageWidth) {
        this.pageWidth = pageWidth;
    }

    public int getPaddingBetweenItem() {
        return paddingBetweenItem;
    }

    public void settPaddingBetweenItem(int paddingBetweenItem) {
        this.paddingBetweenItem = paddingBetweenItem;
    }


}